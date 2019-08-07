//  ##########################################################################
//  # File Name: TestContext.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System.Linq;
using Strata.EfPersistence.DbContextAdapter;
using Strata.EfPersistence.MoneyDomain;
using Strata.Persistence.TradeDomain;
using System.Data.Entity;

namespace Strata.EfPersistence.TradeDomain
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class RepositoryTestContext:
        DbContext,
        IDataContext
    {
        public DbSet<Trade>             Trades { get; set; }
        public DbSet<AccountAllocation> AccountAllocations { get; set; }
        public DbSet<ManagerAllocation> ManagerAllocations { get; set; }

        public 
        RepositoryTestContext():
            base( "name=RepositoryTestContext" ) {}

        public IDbSet<T> 
        GetDbSet<T>() where T: class
        {
            return Set<T>();
        }

        public override int 
        SaveChanges()
        {
            DeleteOrphanAccountAllocations();
            DeleteOrphanManagerAllocations();
            return base.SaveChanges();
        }

        protected override void 
        OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder
                .Configurations
                .Add( new CurrencyConfiguration() );
            modelBuilder
                .Configurations
                .Add( new MoneyConfiguration() );

            modelBuilder
                .Configurations
                .Add( new TradeConfiguration() );
            modelBuilder
                .Configurations
                .Add( new AccountAllocationConfiguration() );
            modelBuilder
                .Configurations
                .Add( new ManagerAllocationConfiguration() );
        }

        private void
        DeleteOrphanAccountAllocations()
        {
            foreach (AccountAllocation a in AccountAllocations)
            {
                Trade trade = Trades.SingleOrDefault( t => t.TradeKey == a.TradeKey );

                if ( IsOrphan( trade,a ) )
                    AccountAllocations.Remove( a );
            }         
        }

        private void
        DeleteOrphanManagerAllocations()
        {
            foreach (ManagerAllocation a in ManagerAllocations)
            {
                AccountAllocation allocation = 
                    AccountAllocations.SingleOrDefault( 
                        t => t.AccountAllocationKey == a.AccountAllocationKey );

                if ( IsOrphan( allocation,a ) )
                    ManagerAllocations.Remove( a );
            }           
        }

        private bool
        IsOrphan(Trade t,AccountAllocation a)
        {
            return 
                t == null || !t.AccountAllocations.Contains( a );
        }

        private bool
        IsOrphan(AccountAllocation a,ManagerAllocation m)
        {
            return 
                a == null || !a.ManagerAllocations.Contains( m );
        }
    }
}

//  ##########################################################################
