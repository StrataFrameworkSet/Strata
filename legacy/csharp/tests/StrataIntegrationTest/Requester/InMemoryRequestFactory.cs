using System;
using Strata.Integration.InMemoryReplier;
using Strata.Integration.Messaging;
using Strata.Persistence.InMemoryRepository;

namespace Strata.Integration.Requester
{
    public
    class InMemoryRequestFactory:
        AbstractRequestFactory
    {
        public String RequestChannelId { get; set; }
        public String ReplyChannelId { get; set; }

        public 
        InMemoryRequestFactory()
        {
            RequestChannelId = "REQUESTS";
            ReplyChannelId   = "REPLIES";
        }

        public override IRequestContext
        CreateRequestContext(IMessagingSession factory)
        {
            return
                new RequestContext(
                    new RequestRepository(
                        new InMemoryRepositoryProvider<String,IRequest>( 
                            new InMemoryRepositoryContext(),
                            new RequestReplicator(),
                            RequestRepository.GetKey )),
                    factory,
                    RequestChannelId,
                    ReplyChannelId,
                    1,
                    1);
        }
    }
}
