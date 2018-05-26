//  ##########################################################################
//  # File Name: ManagerAllocationConfiguration.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System.ComponentModel.DataAnnotations;
// Commented for EF 4.2 support. DatabaseGeneratedOption moves to .Schema namespace in Ef 4.4.  Thanks, Microsoft.
//using System.ComponentModel.DataAnnotations.Schema;
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
    class ManagerAllocationConfiguration:
        EntityTypeConfiguration<ManagerAllocation>
    {
        public  
        ManagerAllocationConfiguration()
        {
            ToTable( "ManagerAllocation" );

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
        }
    }
}

//  ##########################################################################
