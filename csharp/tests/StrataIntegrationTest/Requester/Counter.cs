using Strata.Integration.Messaging;

namespace Strata.Integration.Requester
{
    public
    class Counter
    {
        private Counter() { }
        public static int CurrentCount { get; set; }
        public static void IncrementCount(IMessage unused)
        {
            ++CurrentCount;
        }
    }
}
