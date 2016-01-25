using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Strata.Common.Utility;
using Strata.Persistence.InMemoryRepository;
using Strata.Persistence.Repository;

namespace Strata.FlatFilePersistence.CsvRepository
{
    public class CsvFinder<TKey, T, TAdapter> : AbstractFinder<T>
        where T : class, new()
        where TAdapter : class, T, new()
    {
        private CsvRepositoryContext<TKey, T, TAdapter> itsContext;
        private Type itsType;
        private IPredicate<T> itsPredicate;
        private IList<T> result;
        private IEnumerator<T> current;

        public CsvFinder(
            CsvRepositoryContext<TKey, T, TAdapter> cntx,
            String name,
            IPredicate<T> pred)
            : base(name)
        {
            itsContext = cntx;
            itsType = typeof (T);
            itsPredicate = pred;
            result = null;
            current = null;

            itsContext.InsertFinder(this);
        }

        public CsvFinder(CsvFinder<TKey,T,TAdapter> other) 
            : base(other)
        {
            itsContext = other.itsContext;
            itsType    = other.itsType;
            itsPredicate = other.itsPredicate;
            result = null;
            current = null;
        }

        public override ICopyable 
        MakeCopy()
        {
            return new CsvFinder<TKey,T,TAdapter>( this );
        }

        public override void Clear()
        {
            ClearInputs();
            result = null;
            current = null;
        }

        public override void 
        Execute()
        {
            if (result != null)
                return;

            result = GetResults(GetInputs().GetNamedInputs());
            current = result.GetEnumerator();
        }

        public override ICollection<T> GetAll()
        {
            Execute();
            return result;
        }

        public override T GetUnique()
        {
            Execute();

            return result.SingleOrDefault();
        }

        public override T GetNext()
        {
            Execute();

            try
            {
                T next = current.Current;

                current.MoveNext();
                return next;
            }
            catch (InvalidOperationException)
            {
                return null;
            }
        } 

        public override bool HasUnique()
        {
            Execute();
            return result.Count == 1;
        }

        public override bool HasAny()
        {
            Execute();
            return result.Count > 0;
        }

        public override bool HasNext()
        {
            Execute();

            try
            {
                T next = current.Current;

                return true;
            }
            catch (InvalidOperationException)
            {
                return false;
            }
        }

        protected IList<T> GetResults(IDictionary<String, Object> inputs)
        {
            var output = new List<T>();
            var uow = itsContext.GetUnitOfWork() as CsvUnitOfWork<TKey, T, TAdapter>;
            if (uow != null)
            {
                using (var reader = uow.CreateReader())
                {
                    foreach (var record in reader.Records)
                    {
                        if (itsPredicate.Evaluate(record, inputs))
                        {
                            output.Add(record);
                        }
                    }
                }
            }

            return output;
        }
    }
}
