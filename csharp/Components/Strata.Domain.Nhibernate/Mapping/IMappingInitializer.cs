//  ##########################################################################
//  # File Name: IMappingInitializer.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using NHibernate.Cfg;

namespace Strata.Domain.Nhibernate.Mapping
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    interface IMappingInitializer
    {
        void 
        ApplyMappings(Configuration configuration);
    }
}

//  ##########################################################################
