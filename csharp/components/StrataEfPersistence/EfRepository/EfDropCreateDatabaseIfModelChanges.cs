//  ##########################################################################
//  # File Name: EfDropCreateDatabaseIfModelChanges.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Threading;
using System.Transactions;

namespace Strata.EfPersistence.EfRepository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
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
