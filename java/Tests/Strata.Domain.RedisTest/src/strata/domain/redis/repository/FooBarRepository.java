//////////////////////////////////////////////////////////////////////////////
// FooBarRepository.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.redis.repository;

import strata.domain.core.namedquery.InvalidInputException;
import strata.domain.core.repository.AbstractRepository;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;

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

    @Override
    public CompletionStage<Collection<FooBar>>
    getAllIn(Set<Long> ids)
    {
        return
            getNamedQuery("getAllIn")
                .thenCompose(
                    query ->
                    {
                        try
                        {
                            return
                                query
                                    .setInput("ids",ids)
                                    .getAll();
                        }
                        catch (InvalidInputException e)
                        {
                            throw new CompletionException(e);
                        }
                    });
    }
}

//////////////////////////////////////////////////////////////////////////////
