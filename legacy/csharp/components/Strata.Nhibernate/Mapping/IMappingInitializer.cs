//  ##########################################################################
//  # File Name: IMappingInitializer.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using NHibernate.Cfg;

namespace Strata.Nhibernate.Mapping
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
