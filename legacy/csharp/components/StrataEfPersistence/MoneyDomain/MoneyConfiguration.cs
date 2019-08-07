//  ##########################################################################
//  # File Name: MoneyConfiguration.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Common.MoneyDomain;
using System.Data.Entity.ModelConfiguration;

namespace Strata.EfPersistence.MoneyDomain
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
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
