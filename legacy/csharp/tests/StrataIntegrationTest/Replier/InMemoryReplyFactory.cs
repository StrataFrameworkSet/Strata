using System;
using Strata.Integration.InMemoryReplier;
using Strata.Integration.Messaging;
using Strata.Persistence.InMemoryRepository;

namespace Strata.Integration.Replier
{
    public
    class InMemoryReplyFactory:
        AbstractReplyFactory
    {
        public String ReplyChannelId { get; set; }
        public String RequestChannelId { get; set; }

        public 
        InMemoryReplyFactory() {}

        public override IReplyContext
        CreateReplyContext(IMessagingSession factory)
        {
            return
                new ReplyContext(
                    new ReplyRepository(
                        new InMemoryRepositoryProvider<String,Reply>( 
                            new InMemoryRepositoryContext(),
                            new ReplyReplicator(),
                            ReplyRepository.GetKey )),
                    factory,
                    ReplyChannelId,
                    RequestChannelId);
        }
    }
}
