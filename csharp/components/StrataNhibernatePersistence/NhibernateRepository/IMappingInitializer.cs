//  ##########################################################################
//  # File Name: IMappingInitializer.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using NHibernate.Cfg;

namespace Strata.NhibernatePersistence.NhibernateRepository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    interface IMappingInitializer
    {
        void 
        ApplyMappings(Configuration configuration);
    }
}

//  ##########################################################################
