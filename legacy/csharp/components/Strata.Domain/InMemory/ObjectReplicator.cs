//  ##########################################################################
//  # File Name: ObjectReplicator.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Domain.InMemory
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class ObjectReplicator<T>:
        IEntityReplicator<object>
        where T: class
    {
        private IEntityReplicator<T> itsImp;

        public
        ObjectReplicator(IEntityReplicator<T> imp)
        {
            itsImp = imp;
        }

        public object 
        Replicate(object entity)
        {
            return itsImp.Replicate((T)entity);
        }
    }
}

//  ##########################################################################
