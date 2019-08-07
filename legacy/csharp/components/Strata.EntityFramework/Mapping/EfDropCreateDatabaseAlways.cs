//  ##########################################################################
//  # File Name: EfDropCreateDatabaseAlways.cs
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
    class EfDropCreateDatabaseAlways<C>:
        IDatabaseInitializer<C>
        where C: DbContext
    {
        private DropCreateDatabaseAlways<C> imp; 

        public 
        EfDropCreateDatabaseAlways()
        {
            imp = new DropCreateDatabaseAlways<C>();
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
