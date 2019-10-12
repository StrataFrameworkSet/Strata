//////////////////////////////////////////////////////////////////////////////
// IFooBarRepository.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.redis.repository;

import strata.domain.core.repository.IRepository;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletionStage;

public
interface IFooBarRepository
    extends IRepository<Long,FooBar>
{
    CompletionStage<Collection<FooBar>>
    getAllIn(Set<Long> ids);
}

//////////////////////////////////////////////////////////////////////////////