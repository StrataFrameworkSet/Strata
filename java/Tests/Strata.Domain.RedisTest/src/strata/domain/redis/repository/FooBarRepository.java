//////////////////////////////////////////////////////////////////////////////
// FooBarRepository.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.redis.repository;

import strata.domain.core.repository.AbstractRepository;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;

public
class FooBarRepository
    extends AbstractRepository<Long,FooBar>
    implements IFooBarRepository
{
    public
    FooBarRepository(IUnitOfWorkProvider provider)
    {
        super(Long.class,FooBar.class,provider);
    }
}

//////////////////////////////////////////////////////////////////////////////
