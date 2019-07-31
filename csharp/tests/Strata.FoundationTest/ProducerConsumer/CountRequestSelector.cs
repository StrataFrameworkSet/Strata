//  ##########################################################################
//  # File Name: CountRequestSelector.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Foundation.ProducerConsumer
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    public
    class CountRequestSelector :
        ISelector<CountRequest>,
        IEquatable<CountRequestSelector>
    {
        private readonly int itsTypeId;

        public 
        CountRequestSelector(int typeId)
        {
            itsTypeId = typeId;
        }

        public bool 
        Match(CountRequest element)
        {
            return element.TypeId == itsTypeId;
        }

        public bool 
        Equals(CountRequestSelector other)
        {
            if (other == null)
                return false;

            return itsTypeId == other.itsTypeId;
        }

        public override bool 
        Equals(Object other)
        {
            if (other is CountRequestSelector)
                return Equals((CountRequestSelector)other);

            return false;
        }

        public override int 
        GetHashCode()
        {
            return itsTypeId;
        }
    }
}

//  ##########################################################################
