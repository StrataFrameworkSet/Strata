//  ##########################################################################
//  # File Name: AccountAllocationConfiguration.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
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
    class AccountAllocationMapping:
        ClassMapping<AccountAllocation>
    {
        public  
        AccountAllocationMapping()
        {
/*
            ToTable( "AccountAllocation" );

            HasKey( a => a.AccountAllocationKey );
            Property( a => a.AccountAllocationKey )
                .HasDatabaseGeneratedOption( 
                    DatabaseGeneratedOption.Identity );
            Property( a => a.TradeKey )
                .HasColumnName( "TradeKey" );

            Property( a => a.AccountAllocationId )
                .HasColumnName( "AccountAllocationId" )
                .IsRequired();
            Property( a => a.TradeId )
                .HasColumnName( "TradeId" )
                .IsRequired();
            Property( a => a.DownstreamAllocationIdOriginal )
                .HasColumnName( "DownstreamAllocationIdOriginal" )
                .IsRequired();
            Property( a => a.DownstreamAllocationIdLatest )
                .HasColumnName( "DownstreamAllocationIdLatest" )
                .IsRequired();
            Property( a => a.ExternalAccountAllocationId )
                .HasColumnName( "ExternalAccountAllocationId" )
                .IsRequired();
            Property( a => a.AccountCode )
                .HasColumnName( "AccountCode" )
                .IsRequired();

            Property( a => a.AccountAmount.Currency.CurrencyCode )
                .HasColumnName( "AccountCurrency" )
                .IsRequired();            
            Property( a => a.AccountAmount.Amount )
                .HasColumnName( "AccountAmount" )
                .IsRequired();

             Property( a => a.Comment )
                .HasColumnName( "Comment" )
                .IsOptional();

            HasMany( a => a.ManagerAllocations )
                .WithRequired()
                .HasForeignKey( a => a.AccountAllocationKey )
                .WillCascadeOnDelete();      
 */
        }
    }
}

//  ##########################################################################
