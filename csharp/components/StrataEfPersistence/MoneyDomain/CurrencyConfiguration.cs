//  ##########################################################################
//  # File Name: CurrencyConfiguration.cs
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
    class CurrencyConfiguration:
        ComplexTypeConfiguration<Currency>
    {
        public 
        CurrencyConfiguration()
        {
            Property( c => c.CurrencyCode )
                .HasColumnName( "Currency" )
                .HasMaxLength( 4 )
                .IsRequired();

            Ignore( c => c.CurrencyId );
            Ignore( c => c.Symbol );
        }
    }
}

//  ##########################################################################
