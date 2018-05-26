//  ##########################################################################
//  # File Name: CountRequest.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Common.ProducerConsumer
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class CountRequest
    {
        public int TypeId { get; protected set; }

        public
        CountRequest(int typeId)
        {
            TypeId = typeId;
        }

        public override String
        ToString()
        {
            return "CountRequest: TypeId=" + TypeId;
        }

    }
}

//  ##########################################################################
