//  ##########################################################################
//  # File Name: AccountAllocationConfiguration.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Domain.TradeDomain;
using System.ComponentModel.DataAnnotations.Schema;
using System.Data.Entity.ModelConfiguration;

namespace Strata.EntityFramework.Mapping
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class AccountAllocationConfiguration:
        EntityTypeConfiguration<AccountAllocation>
    {
        public  
        AccountAllocationConfiguration()
        {
            ToTable( "AccountAllocation" );

            HasKey( a => a.PrimaryId );
            Property( a => a.PrimaryId )
                .HasColumnName("AccountAllocationKey")
                .HasDatabaseGeneratedOption( 
                    DatabaseGeneratedOption.Identity );
            Ignore(a => a.Version);
            Ignore(a => a.Created);
            Ignore(a => a.LastModified);

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
