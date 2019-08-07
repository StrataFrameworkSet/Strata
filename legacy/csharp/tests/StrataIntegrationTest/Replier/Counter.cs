using System;

namespace Strata.Integration.Replier
{
    public
    class Counter
    {
        public int Count { get; protected set; }

        public 
        Counter()
        {
            Count = 0;
        }

        public void 
        Increment(String unused)
        {
            ++Count;
        }
    }
}
