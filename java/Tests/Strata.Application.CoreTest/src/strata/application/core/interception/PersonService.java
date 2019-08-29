//////////////////////////////////////////////////////////////////////////////
// PersonService.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.interception;

import strata.application.core.service.AbstractService;
import strata.domain.core.testdomain.IPerson;
import strata.domain.core.testdomain.IPersonRepository;
import strata.domain.core.testdomain.Person;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;
import strata.domain.core.unitofwork.OptimisticLockException;

import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;

public
class PersonService
    extends    AbstractService
    implements IPersonService
{
    private final IPersonRepository itsRepository;
    private int   itsAttempt;

    @Inject
    public
    PersonService(
        IUnitOfWorkProvider p,
        IPersonRepository   r)
    {
        super(p);
        itsRepository = r;
        itsAttempt    = 0;
    }

    @Override
    @UnitOfWork
    @Timed
    public CompletionStage<CreatePersonReply>
    createPerson(CreatePersonRequest request)
    {
        IPerson person = new Person();

        person.setName(request.getName());
        person.setAge(request.getAge());

        return
            itsRepository
                .insertPerson(person)
                .thenCompose(
                    p ->
                    {
                        if (itsAttempt++ < 2)
                            throw new CompletionException(new OptimisticLockException("test"));

                        return CompletableFuture.completedFuture(p);
                    })
                .thenCompose(
                    p ->
                        CompletableFuture.completedFuture(
                            (CreatePersonReply)
                                new CreatePersonReply()
                                    .setPerson(p)
                                    .setSuccess(true)
                                    .setSuccessMessage("Person created.")));
        /*
                .exceptionally(
                    e ->
                    {
                        if (e instanceof OptimisticLockException)
                            throw new CompletionException(e);

                        return
                            (CreatePersonReply)
                                new CreatePersonReply()
                                    .setSuccess(false)
                                    .setFailureMessage(e.getMessage());
                    });
                    */
    }
}

//////////////////////////////////////////////////////////////////////////////
