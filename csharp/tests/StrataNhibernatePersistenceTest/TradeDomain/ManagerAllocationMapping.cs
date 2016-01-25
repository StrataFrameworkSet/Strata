//  ##########################################################################
//  # File Name: ManagerAllocationConfiguration.cs
//  # Copyright: 2013, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Persistence.TradeDomain;
using NHibernate.Mapping.ByCode.Conformist;

// Commented for EF 4.2 support. DatabaseGeneratedOption moves to .Schema namespace in Ef 4.4.  Thanks, Microsoft.
//using System.ComponentModel.DataAnnotations.Schema;

namespace Strata.NhibernatePeristence.TradeDomain
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class ManagerAllocationMapping:
        ClassMapping<ManagerAllocation>
    {
        public  
        ManagerAllocationMapping()
        {
/*
            Table( "ManagerAllocation" );

            HasKey( m => m.ManagerAllocationKey );
            Property( m => m.ManagerAllocationKey )
                .HasDatabaseGeneratedOption( 
                    DatabaseGeneratedOption.Identity );
            Property( a => a.AccountAllocationKey )
                .HasColumnName( "AccountAllocationKey" );

            Property( m => m.ManagerAllocationId )
                .HasColumnName( "ManagerAllocationId" )
                .IsRequired();
            Property( m => m.AccountAllocationId )
                .HasColumnName( "AccountAllocationId" )
                .IsRequired();
            Property( m => m.ManagerId )
                .HasColumnName( "ManagerId" )
                .IsRequired();

            Property( m => m.ManagerAmount.Currency.CurrencyCode )
                .HasColumnName( "ManagerCurrency" )
                .IsRequired();            
            Property( m => m.ManagerAmount.Amount )
                .HasColumnName( "ManagerAmount" )
                .IsRequired();
 */
        }
    }
}

//  ##########################################################################
