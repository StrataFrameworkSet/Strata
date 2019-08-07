//  ##########################################################################
//  # File Name: MappableSetConfiguration.cs
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
    class MappableSetConfiguration<T,S>:
        ComplexTypeConfiguration<S>
        where S: MappableSet<T>
    {
        public 
        MappableSetConfiguration()
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
