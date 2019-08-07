//  ##########################################################################
//  # File Name: MoneyConfiguration.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Value;
using System.Data.Entity.ModelConfiguration;

namespace Strata.EntityFramework.Mapping
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class MoneyConfiguration:
        ComplexTypeConfiguration<Money>
    {
        public 
        MoneyConfiguration()
        {
/*
            Property( m => m.Currency.CurrencyCode )
                .HasColumnName( "Currency" )
                .HasMaxLength( 4 )
                .IsRequired();
 */
            Property( m => m.Amount )
                .HasColumnName( "Amount" )
                .IsRequired()
                .HasPrecision( 20,6 );
        }
    }
}

//  ##########################################################################
