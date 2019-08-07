//  ##########################################################################
//  # File Name: AccountAllocationConfiguration.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System.ComponentModel.DataAnnotations;
// Commented for EF 4.2 support. DatabaseGeneratedOption moves to .Schema namespace in Ef 4.4.  Thanks, Microsoft.
//using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations.Schema;
using System.Data.Entity.ModelConfiguration;
using Strata.Persistence.TradeDomain;

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
    class AccountAllocationConfiguration:
        EntityTypeConfiguration<AccountAllocation>
    {
        public  
        AccountAllocationConfiguration()
        {
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
        }
    }
}

//  ##########################################################################
