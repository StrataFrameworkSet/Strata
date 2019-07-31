//  ##########################################################################
//  # File Name: MappableListConfiguration.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Utility;
using System.Data.Entity.ModelConfiguration;

namespace Strata.EntityFramework.Mapping
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class MappableListConfiguration<T,L>:
        ComplexTypeConfiguration<L>
        where L: MappableList<T>
    {
        public
        MappableListConfiguration()
        {
            Property( m => m.Contents )
                .HasColumnName( "Contents" )
                .IsRequired();

            Ignore( m => m.Count );
            Ignore(m => m.IsReadOnly);
        }
    }
}

//  ##########################################################################
