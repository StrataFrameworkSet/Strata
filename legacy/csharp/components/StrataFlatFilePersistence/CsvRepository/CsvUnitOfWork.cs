using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using Strata.Persistence.Repository;
using CapitalGroup.Common.General;
using CapitalGroup.Common.RecordStreamer;

namespace Strata.FlatFilePersistence.CsvRepository
{
    /// <summary>
    /// An <see cref="IUnitOfWork"/> implementation which interacts with CSV files.   
    /// </summary>
    /// <typeparam name="K">The business type's key type.</typeparam>
    /// <typeparam name="T">The business type the repository supports.</typeparam>
    /// <typeparam name="TAdapter">The adapter type which is what this class primarily works with.</typeparam>
    public class CsvUnitOfWork<K, T, TAdapter> : IUnitOfWork
        where T : class, new()
        where TAdapter : class, T, new()
    {
        private readonly CsvRepositoryContext<K, T, TAdapter> context;
        private UnitOfWorkState state = UnitOfWorkState.ACTIVE;

        private string TempFileName { get { return context.FileName + ".tmp"; } }

        public IDictionary<string, string> ErrorList { get; set; } 

        protected CsvUnitOfWork()
        {
            ErrorList = new Dictionary<string, string>();
        } 

        public CsvUnitOfWork(CsvRepositoryContext<K, T, TAdapter> ctx) 
            : this()
        {
            context = ctx;
        } 

        public IRepositoryMethod
        GetRepositoryMethod(String name)
        {
            return null;
        }

        /// <see cref="IUnitOfWork.Commit()"/>
        /// 
        public void Commit()
        {
            try
            {
                if (state == UnitOfWorkState.COMMITTED) return;

                ReplaceFile(context.FileName, TempFileName);
                state = UnitOfWorkState.COMMITTED;
            }
            catch (Exception e)
            {
                throw new CommitFailedException("commit failed", e);
            }
        }

        /// <see cref="IUnitOfWork.Rollback()"/>
        /// 
        public void Rollback()
        {
            try
            {
                if (state == UnitOfWorkState.ROLLED_BACK) return;

                ReplaceFile(TempFileName, context.FileName);
                state = UnitOfWorkState.ROLLED_BACK;
            }
            catch (Exception e)
            {
                throw new RollbackFailedException("rollback failed", e);
            }
        }

        /// <see cref="IUnitOfWork.IsActive()"/>
        /// 
        public bool IsActive()
        {
            return state == UnitOfWorkState.ACTIVE;
        }

        /// <see cref="IUnitOfWork.IsCommitted()"/>
        /// 
        public bool IsCommitted()
        {
            return state == UnitOfWorkState.COMMITTED;
        }

        /// <see cref="IUnitOfWork.IsRolledBack()"/>
        /// 
        public bool IsRolledBack()
        {
            return state == UnitOfWorkState.ROLLED_BACK;
        }

        /// <summary>
        /// Uses a <see cref="ObjectWriter{T}"/> to append the entity to the end of the file.  
        /// There is -NO- primary key checking or constraint enforcement.
        /// You may insert as many duplicate records as you want.
        /// </summary>
        public TAdapter DoInsert(TAdapter entity, KeyRetriever<K, T> retriever)
        {
            try
            {
                if (context.EnforcePrimaryKey)
                {
                    using (var reader = CreateReader())
                    {
                        if (reader.Records.Any(record => retriever(entity).Equals(retriever(record))))
                        {
                            throw new InsertFailedException("primary key violation");
                        }
                    }
                }
                using (var writer = CreateWriter(true))
                {
                    writer.WriteLine(entity);
                }
                return entity;
            }
            catch (Exception ex)
            {
                throw new InsertFailedException("insert failed", ex);
            }
        }

        /// <summary>
        /// </summary>
        /// 
        public TAdapter DoUpdate(TAdapter entity, KeyRetriever<K, T> retriever)
        {
            try
            {
                var records = new List<TAdapter>();
                using (var reader = CreateReader())
                {
                    records.AddRange(reader.Records
                                         .Select(record => retriever(record).Equals(retriever(entity))
                                                               ? entity
                                                               : record));
                }
                using (var writer = CreateWriter())
                {
                    foreach (var record in records)
                    {
                        writer.WriteLine(record);
                    }
                }
                return entity;
            }
            catch (Exception ex)
            {
                throw new UpdateFailedException("update failed", ex);
            }
        }

        /// <summary>
        /// </summary>
        /// 
        public void DoRemove(T entity, KeyRetriever<K, T> retriever)
        {
            try
            {
                List<TAdapter> records;
                using (var reader = CreateReader())
                {
                    records = reader.Records
                        .Where(record => !retriever(record).Equals(retriever(entity)))
                        .ToList();
                }
                using (var writer = CreateWriter())
                {
                    foreach (var record in records)
                    {
                        writer.WriteLine(record);
                    }
                }
            }
            catch (Exception ex)
            {
                throw new RemoveFailedException("remove failed", ex);
            }
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public TAdapter DoGet(K key, KeyRetriever<K, T> retriever)
        {
            var result = DoQuery().SingleOrDefault(entity => retriever(entity).Equals(key));
            return result;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        /// 
        public IQueryable<TAdapter> DoQuery()
        {
            List<TAdapter> records;

            using (var reader = CreateReader())
            {
                records = new List<TAdapter>(reader.Records);
            }

            return records.AsQueryable();
        }


        /// <summary>
        /// Indiactes whether the repository has an entity matching the given key.
        /// </summary>
        /// <param name="key">The key to match.</param>
        /// <param name="retriever">A delegate for retrieving the key from the entities.</param>
        /// <returns>True if the repository contains an entity matching the key.</returns>
        public bool DoHas(K key, KeyRetriever<K, T> retriever)
        {
            using (var reader = CreateReader())
            {
                if (reader.Records.Select(record => retriever(record)).Contains(key))
                {
                    return true;
                }
            }
            return false;
        }

                private static string GetErrorMessages(EventArgs<RecordArg> args)
        {
            string errorMessage = args.Exceptions
                .Aggregate(string.Empty, (current, exception) => current.Equals(string.Empty) ? current + exception.Message : current + ", " + exception.Message);
            return errorMessage;
        }

        /// <summary>
        /// Creates a new <see cref="ObjectWriter{T}"/> based on 
        /// this <see cref="CsvUnitOfWork{K, T, TAdapter}"/>'s context-set 
        /// parameters.
        /// </summary>
        /// <typeparam name="T">The type to be associated with the writer.  
        /// This should be a type that has <see cref="MappingAttribute"/>s 
        /// set.</typeparam>
        /// <returns>The <see cref="ObjectWriter{T}"/>.</returns>
        public ObjectWriter<TAdapter> CreateWriter(bool append = false)
        {
            var writer = new ObjectWriter<TAdapter>(
                new FileInfo(Path.Combine(context.Location, TempFileName)),
                context.RecordFormat,
                context.Delimiter,
                null,
                append,
                context.ErrorThreshold,
                headerStyle: context.WriteHeaderStyle);
            writer.ExceptionHandler += (sender, args) => HandleStreamerException(args);
            return writer;
        }

        /// <summary>
        /// Creates a new <see cref="ObjectReader{T}"/> based on 
        /// this <see cref="CsvUnitOfWork{K, T, TAdapter}"/>'s context-set 
        /// parameters.
        /// </summary>
        /// <typeparam name="T">The type to be associated with the reader.  
        /// This should be a type that has <see cref="MappingAttribute"/>s 
        /// set.</typeparam>
        /// <returns>The <see cref="ObjectReader{T}"/>.</returns>
        public ObjectReader<TAdapter> CreateReader()
        {
            var reader = new ObjectReader<TAdapter>(
                new FileInfo(Path.Combine(context.Location, IsCommitted() ? context.FileName : TempFileName)),
                context.RecordFormat,
                context.Delimiter,
                null,
                context.ErrorThreshold,
                context.ReadHeaderStyle);
            reader.ExceptionHandler += (sender, args) => HandleStreamerException(args);

            return reader;
        }

        private void HandleStreamerException(EventArgs<RecordArg> args)
        {
            if (args.Content != null && args.Content.Record != null)
            {
                var data = args.Content.Record.ToString();
                if (ErrorList.ContainsKey(data))
                {
                    data = data + Guid.NewGuid();
                }
                ErrorList.Add(
                    data,
                    GetErrorMessages(args));
            }
        }

        /// <summary>
        /// Replaces the target file with the source file.
        /// </summary>
        /// <param name="targetFile">The file to be replaced.</param>
        /// <param name="sourceFile">The file containing the data to be moved to the target file.</param>
        private void ReplaceFile(string targetFile, string sourceFile)
        {
            File.Delete(Path.Combine(context.Location, targetFile));

            File.Copy(Path.Combine(context.Location, sourceFile),
                      Path.Combine(context.Location, targetFile));
        }
    }
}
