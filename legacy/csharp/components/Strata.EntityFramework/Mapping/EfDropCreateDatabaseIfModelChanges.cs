//  ##########################################################################
//  # File Name: EfDropCreateDatabaseIfModelChanges.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System.Data.Entity;
using System.Transactions;

namespace Strata.EntityFramework.Mapping
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class EfDropCreateDatabaseIfModelChanges<C>:
        IDatabaseInitializer<C>
        where C: DbContext
    {
        private DropCreateDatabaseIfModelChanges<C> imp; 

        public
        EfDropCreateDatabaseIfModelChanges()
        {
            imp = new DropCreateDatabaseIfModelChanges<C>();
        }

        public void 
        InitializeDatabase(C context)
        {
            using (new TransactionScope(TransactionScopeOption.Suppress))
            {
                imp.InitializeDatabase( context );
            }
        }
    }
}

//  ##########################################################################
