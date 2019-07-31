//  ##########################################################################
//  # File Name: TradeEntityConfiguration.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Persistence.TradeDomain;
using NHibernate.Driver;
using NHibernate.Mapping.ByCode;
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
    class TradeMapping:
        ClassMapping<Trade>
    {
        public  
        TradeMapping()
        {
            Table( "Trade" );

            Id( t => t.TradeKey,m => m.Generator(Generators.Identity) );
            
            Property( 
                t => t.TradeId,
                m => m.Column(
                    c => 
                    {
                        c.Name("TradeId"); 
                        c.NotNullable(true);
                        c.Unique(true);
                    } ) );

            Property( 
                t => t.ExternalTradeId,
                m => m.Column(
                    c => 
                    {
                        c.Name("ExternalTradeId"); 
                        c.NotNullable(true);
                        c.Unique(true);
                    } ) ); 
            Property( 
                t => t.Cusip,
                m => m.Column(
                    c => 
                    {
                        c.Name("Cusip"); 
                        c.NotNullable(true);
                    } ) );
            Property( 
                t => t.TransactionType,
                m => m.Column(
                    c => 
                    {
                        c.Name("TransactionType"); 
                        c.NotNullable(true);
                    } ) );
/*
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
            Property( t => t.DeliveryDate )
                .HasColumnName( "DeliveryDate" )
                .IsRequired();

            HasMany( t => t.AccountAllocations )
                .WithRequired()
                .HasForeignKey( a => a.TradeKey )
                .WillCascadeOnDelete();      
*/
        }
    }
}

//  ##########################################################################
