//  ##########################################################################
//  # File Name: TradeEntityConfiguration.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System.ComponentModel.DataAnnotations.Schema;
using System.Data.Entity.ModelConfiguration;
using Strata.Domain.TradeDomain;

namespace Strata.EntityFramework.Mapping
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class TradeConfiguration:
        EntityTypeConfiguration<Trade>
    {
        public  
        TradeConfiguration()
        {
            ToTable( "Trade" );

            HasKey( t => t.PrimaryId );
            Property( t => t.PrimaryId)
                .HasColumnName("TradeKey")
                .HasDatabaseGeneratedOption( 
                    (System.ComponentModel.DataAnnotations.Schema.DatabaseGeneratedOption?)DatabaseGeneratedOption.Identity );

            Property(t => t.Version)
                .IsConcurrencyToken();

            Ignore(t => t.Created);
            Ignore(t => t.LastModified);

            Property( t => t.TradeId )
                .HasColumnName( "TradeId" )
                .IsRequired();
            Property( t => t.ExternalTradeId )
                .HasColumnName( "ExternalTradeId" )
                .IsRequired();
            Property( t => t.Cusip )
                .HasColumnName( "Cusip" )
                .IsRequired();

            Ignore( t => t.TransactionType );

            Property( t => t.Price.Currency.CurrencyCode )
                .HasColumnName( "PriceCurrency" )
                .IsRequired();            
            Property( t => t.Price.Amount )
                .HasColumnName( "PriceAmount" )
                .HasPrecision( 20,6 )
                .IsRequired();

            Property( t => t.TradeAmount.Currency.CurrencyCode )
                .HasColumnName( "TradeCurrency" )
                .IsRequired();            
            Property( t => t.TradeAmount.Amount )
                .HasColumnName( "TradeAmount" )
                .HasPrecision( 20,6 )
                .IsRequired();

            Property( t => t.TraderId )
                .HasColumnName( "TraderId" )
                .IsRequired();
            Property( t => t.BrokerCode )
                .HasColumnName( "BrokerCode" )
                .IsRequired();
            Property( t => t.TradeDate )
                .HasColumnName( "TradeDate" )
                .IsRequired();
            Property( t => t.SettlementDate )
                .HasColumnName( "SettlementDate" )
                .IsRequired();
            Ignore(t => t.DeliveryDate);

            HasMany( t => t.AccountAllocations )
                .WithRequired()
                .HasForeignKey( a => a.TradeKey )
                .WillCascadeOnDelete();      
        }
    }
}

//  ##########################################################################
