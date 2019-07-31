//  ##########################################################################
//  # File Name: MappableDictionaryConfiguration.cs
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
    class MappableDictionaryConfiguration<K,T,D>:
        ComplexTypeConfiguration<D>
        where D: MappableDictionary<K,T>
    {
        public
        MappableDictionaryConfiguration()
        {
            Property( m => m.Contents )
                .HasColumnName( "Contents" )
                .IsRequired();

            Ignore( m => m.Count );
            Ignore(m => m.IsReadOnly);
            Ignore(m => m.Keys);
            Ignore(m => m.Values);
        }
    }
}

//  ##########################################################################
