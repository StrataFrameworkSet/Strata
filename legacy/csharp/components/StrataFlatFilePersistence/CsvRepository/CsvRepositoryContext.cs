using System;
using System.Collections.Generic;
using System.IO;
using Strata.Persistence.Repository;
using CapitalGroup.Common.RecordStreamer;
using Microsoft.Practices.Unity;

namespace Strata.FlatFilePersistence.CsvRepository
{
    /// <summary>
    /// The context of the CSV repository.  Manages the file location(s) and keeps track of settings
    /// relavent to the <see cref="ObjectReader{T}"/> which the repository uses.  The context will 
    /// initialize the files upon creation.
    /// </summary>
    /// <typeparam name="TKey">The key of entity <see cref="T"/>.</typeparam>
    /// <typeparam name="T">The type the repository provides consumers.</typeparam>
    /// <typeparam name="TAdapter">The adapter used to convert the <see cref="ObjectReader{T}"/>s internal
    /// record into a <see cref="T"/>.</typeparam>
    public class CsvRepositoryContext<TKey, T, TAdapter> : AbstractRepositoryContext
        where T : class, new()
        where TAdapter : class, T, new()
    {
        private CsvUnitOfWork<TKey, T, TAdapter> unitOfWork;
        private IDictionary<Type, IList<Object>> finders;

        public string Location { get; private set; }
        public string FileName { get; private set; }
        public WriteHeaderStyle WriteHeaderStyle { get; private set; }
        public ReadHeaderStyle ReadHeaderStyle { get; private set; }
        public RecordFormat RecordFormat { get; private set; }
        public char Delimiter { get; private set; }
        public ushort ErrorThreshold { get; private set; }
        public bool EnforcePrimaryKey { get; private set; }

        /// <summary>
        /// Creates a new <see cref="CsvRepositoryContext{TKey, T, TAdapter}"/> instance.
        /// </summary>
        /// <param name="loc">The path or URI of the file to work with.</param>
        /// <param name="filename">The file's name and extension.</param>
        /// <param name="hdrStyle"> </param>
        /// <param name="delim">Delimiting character</param>
        /// <param name="errorCount"> </param>
        public CsvRepositoryContext(
            string loc, 
            string filename,
            string recFormat = "CharDelimited",
            char delim = ',', 
            string readHdrStyle = "NoHeaderRow",
            string writeHdrStyle = "NoHeaderRow",
            ushort errorCount = 10, 
            bool initializeClean = false,
            bool enforcePrimaryKey = false) 
        {
            Location = loc;
            FileName = filename;
            Delimiter = delim;
            ReadHeaderStyle = (ReadHeaderStyle)Enum.Parse(typeof(ReadHeaderStyle), readHdrStyle);
            WriteHeaderStyle = (WriteHeaderStyle)Enum.Parse(typeof(WriteHeaderStyle), writeHdrStyle);
            RecordFormat = (RecordFormat)Enum.Parse(typeof(RecordFormat), recFormat);
            ErrorThreshold = errorCount;
            EnforcePrimaryKey = enforcePrimaryKey;

            finders = new Dictionary<Type, IList<Object>>();

            InitializeCsvFileContext(initializeClean);
        }
        
        /// <summary>
        /// Initializes the CSV file context to be used by the repository.  Ensures that the necessary
        /// files exist and are ready to be used.
        /// </summary>
        /// <param name="clean">True to initialize the context with an empty file if one of the same
        /// name already exists.  False will work with data in the existing file.</param>
        private void InitializeCsvFileContext(bool clean)
        {
            var path = Path.Combine(Location, FileName);

            if (clean || !File.Exists(path))
            {
                using (var file = File.Create(path))
                {
                    file.Close();
                }
            }

            File.Copy(path, path+ ".tmp", true);
        }
        
        /// <summary>
        /// Returns the current unit of work for this context. 
        /// </summary> 
        /// <returns>Current unit of work, or a new one if there is no existing UnitOfWork, or if the existing unit of work is not Active.</returns>
        public override IUnitOfWork GetUnitOfWork()
        {
            if (unitOfWork == null || !unitOfWork.IsActive())
            {
                unitOfWork = new CsvUnitOfWork<TKey, T, TAdapter>(this);
            }

            return unitOfWork;
        }

        public void InsertFinder(CsvFinder<TKey, T, TAdapter> finder)
        {
            Type type = typeof(T);

            if (!finders.ContainsKey(type))
                finders.Add(type, new List<Object>());

            finders[type].Add(finder);
        }
    }
}
