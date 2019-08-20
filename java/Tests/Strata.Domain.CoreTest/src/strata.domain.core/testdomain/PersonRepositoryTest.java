package strata.domain.core.testdomain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import strata.domain.core.namedquery.INamedQuery;
import strata.domain.core.unitofwork.IUnitOfWork;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;
import strata.foundation.core.utility.Holder;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletionStage;

import static strata.foundation.core.utility.Awaiter.await;


public abstract
class PersonRepositoryTest
{
    private IUnitOfWorkProvider itsProvider;
    private IPersonRepository   itsTarget;
    
    @Before
    public void 
    setUp() throws Exception
    {
        itsProvider = createUnitOfWorkProvider();
        itsTarget = new PersonRepository(itsProvider);
    }

    @After
    public void 
    tearDown() throws Exception
    {
        INamedQuery<IPerson> finder     = await(itsTarget.getNamedQuery( "GetAll" )).get();
        IUnitOfWork unitOfWork = await(itsProvider.getUnitOfWork());
        
        for (IPerson p : await(finder.getAll()))
            await(itsTarget.removePerson( p ));
        
        await(unitOfWork.commit());
        itsTarget = null;
        itsProvider = null;
    }

    @Test
    public void 
    testInsertPerson() 
        throws Exception
    {
        IUnitOfWork unitOfWork = await(itsProvider.getUnitOfWork());
        IPerson     expected = 
                        new Person(
                            new PersonName("John","Liebenau"),
                            new PersonAge(
                                LocalDate.of(1967,4,25)));
        IPerson     actual = await(itsTarget.insertPerson( expected ));
        
        await(unitOfWork.commit());
        Assert.assertEquals( expected.getName(),actual.getName() );
        Assert.assertEquals( expected.getAge(),actual.getAge() );
    }

    @Test
    public void 
    testUpdatePerson() 
        throws Exception
    {
        /*
        Instant start = Instant.now();
        IUnitOfWork unitOfWork = await(itsProvider.getUnitOfWork());
        IPerson     expected = 
                        new Person(
                            new PersonName("John","Liebenau"),
                            new PersonAge(
                                LocalDate.of(1967,4,25)));
        IPerson     actual = null;
        
        
        expected = await(itsTarget.insertPerson( expected ));
        await(unitOfWork.commit());
        
        unitOfWork = await(itsProvider.getUnitOfWork());
        expected.getName().setMiddleName( "Friedrich" );
        actual = await(itsTarget.updatePerson( expected ));
        await(unitOfWork.commit());

        System.out.println("Execution time = " + Duration.between(start,Instant.now()).toMillis() + " ms");
        Assert.assertEquals( expected.getName(),actual.getName() );
        Assert.assertEquals( expected.getAge(),actual.getAge() );

        */

        Instant start = Instant.now();

        final Holder<IPerson> expected = new Holder<>();
        final Holder<IPerson> actual = new Holder<>();
        CompletionStage<Void> result =
            itsTarget
                .insertPerson(
                    new Person(
                        new PersonName("John","Liebenau"),
                        new PersonAge(
                            LocalDate.of(1967,4,25))))
                .thenCompose(
                    person ->
                    {
                        expected.setItem(person);
                        return itsProvider.getUnitOfWork();
                    })
                .thenCompose(
                    uow ->
                    {
                        IPerson person = expected.getItem();

                        uow.commit();
                        person
                            .getName()
                            .setMiddleName("Friedrich");
                        return itsTarget.updatePerson(person);
                    })
                .thenCompose(
                    person ->
                    {
                        actual.setItem(person);
                        return itsProvider.getUnitOfWork();
                    })
                .thenAccept(
                    uow ->
                    {
                        uow.commit();

                        System.out.println("Execution time = " + Duration.between(start,Instant.now()).toMillis() + " ms");
                        Assert.assertEquals(
                            expected
                                .getItem()
                                .getName(),
                            actual
                                .getItem()
                                .getName() );

                        Assert.assertEquals(
                            expected
                                .getItem()
                                .getAge(),
                            actual
                                .getItem()
                                .getAge() );
                    } );

        await(result);
        Assert.assertFalse(result.toCompletableFuture().isCompletedExceptionally());
    }

    @Test
    public void 
    testRemovePerson() 
        throws Exception
    {
        IUnitOfWork unitOfWork = await(itsProvider.getUnitOfWork());
        IPerson     expected = 
                        new Person(
                            new PersonName("John","Liebenau"),
                            new PersonAge(LocalDate.of(1967,4,25)));

        expected = await(itsTarget.insertPerson( expected ));
        await(unitOfWork.commit());
        
        unitOfWork = await(itsProvider.getUnitOfWork());
        Assert.assertTrue( await(itsTarget.hasPersonWithPartyKey( expected.getPartyKey() )) );
        await(itsTarget.removePerson( expected ));
        await(unitOfWork.commit());
        
        Assert.assertFalse( await(itsTarget.hasPersonWithPartyKey( expected.getPartyKey() )) );
    }

    @Test
    public void 
    testGetPersonByPartyKey()
        throws Exception
    {
        IUnitOfWork unitOfWork = await(itsProvider.getUnitOfWork());
        IPerson     expected = 
                        new Person(
                            new PersonName("John","Liebenau"),
                            new PersonAge(LocalDate.of(1967,4,25)));
        IPerson     actual = null;
        
        expected = await(itsTarget.insertPerson( expected ));
        await(unitOfWork.commit());
        
        Assert.assertTrue( itsTarget.hasPersonWithPartyKey( expected.getPartyKey() ).toCompletableFuture().join() );
        actual = await(itsTarget.getPersonByPartyKey( expected.getPartyKey() )).get();
        Assert.assertEquals( expected,actual );
    }

    @Test
    public void 
    testGetPersonByName() 
        throws Exception
    {
        IUnitOfWork unitOfWork = await(itsProvider.getUnitOfWork());
        IPerson       expected = 
                          new Person(
                              new PersonName("John","Liebenau"),
                              new PersonAge(LocalDate.of(1967,4,25)));
        List<IPerson> actual = null;
        
        expected = await(itsTarget.insertPerson( expected ));
        await(unitOfWork.commit());
        
        Assert.assertTrue( await(itsTarget.hasPersonWithPartyKey( expected.getPartyKey() )) );
        actual = await(itsTarget.getPersonsByName( expected.getName() ));
        Assert.assertEquals( 1,actual.size() );
        Assert.assertEquals( expected,actual.get( 0 ) );
        
    }

    @Test
    public void 
    testGetFinder()
    {
        INamedQuery<IPerson> query = await(itsTarget.getNamedQuery( "GetByName" )).get();
        
        Assert.assertNotNull( query );
        Assert.assertEquals( "strata.domain.core.testdomain.IPerson.GetByName",query.getName() );
    }

    @Test
    public void 
    testHasPersonWithPartyKey() 
        throws Exception
    {
        IUnitOfWork unitOfWork = await(itsProvider.getUnitOfWork());
        IPerson     expected = 
                        new Person(
                            new PersonName("John","Liebenau"),
                            new PersonAge(LocalDate.of(1967,4,25)));
        IPerson     actual = null;
        
        expected = await(itsTarget.insertPerson( expected ));
        await(unitOfWork.commit());
        
        Assert.assertTrue( await(itsTarget.hasPersonWithPartyKey( expected.getPartyKey() )) );
    }

    //@Ignore
    @Test
    public void 
    testHasPersonWithName() 
        throws Exception
    {
        IUnitOfWork unitOfWork = await(itsProvider.getUnitOfWork());
        IPerson     expected = 
                          new Person(
                              new PersonName("John","Liebenau"),
                              new PersonAge(LocalDate.of(1967,4,25)));
        
        expected = await(itsTarget.insertPerson( expected ));
        await(unitOfWork.commit());
        
        Assert.assertTrue( await(itsTarget.hasPersonWithName( expected.getName() )) );
        unitOfWork = await(itsProvider.getUnitOfWork());
        await(itsTarget.removePerson( expected ));
        await(unitOfWork.commit());
        Assert.assertFalse( await(itsTarget.hasPersonWithName( expected.getName() )) );
    }

    @Test
    public void 
    testHasFinder()
    {
        Assert.assertTrue( await(itsTarget.hasNamedQuery( "GetByName" )) );
    }

    protected abstract IUnitOfWorkProvider
    createUnitOfWorkProvider();
}

// ##########################################################################
