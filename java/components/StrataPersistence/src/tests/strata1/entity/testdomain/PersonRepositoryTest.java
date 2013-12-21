package strata1.entity.testdomain;

import strata1.entity.repository.IFinder;
import strata1.entity.repository.IRepositoryContext;
import strata1.entity.repository.IRepositoryProvider;
import strata1.entity.repository.IUnitOfWork;
import strata1.common.datetime.DateTime;
import strata1.common.money.Money;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Currency;
import java.util.List;

public abstract
class PersonRepositoryTest
{
    private IRepositoryContext itsContext;
    private IPersonRepository  itsTarget;
    
    @Before
    public void 
    setUp() throws Exception
    {
        itsContext = createContext();
        itsTarget = new PersonRepository(createProvider(itsContext));
    }

    @After
    public void 
    tearDown() throws Exception
    {
        IFinder<IPerson> finder = itsTarget.getFinder( "GetAll" );
        IUnitOfWork      unitOfWork = itsTarget.getUnitOfWork();
        
        for (IPerson p : finder.getAll())
            itsTarget.removePerson( p );
        
        unitOfWork.commit();
        itsTarget = null;
        itsContext = null;
    }

    @Test
    public void 
    testInsertPerson() 
        throws Exception
    {
        IUnitOfWork unitOfWork = itsTarget.getUnitOfWork();
        IPerson     expected = 
                        new Person(
                            new PersonName("John","Liebenau"),
                            new PersonAge(
                                new DateTime(1967,4,25)));
        IPerson     actual = 
                        itsTarget.insertPerson( expected );
        
        unitOfWork.commit();
        Assert.assertEquals( expected.getName(),actual.getName() );
        Assert.assertEquals( expected.getAge(),actual.getAge() );
    }

    @Test
    public void 
    testUpdatePerson() 
        throws Exception
    {
        IUnitOfWork unitOfWork = itsTarget.getUnitOfWork();
        IPerson     expected = 
                        new Person(
                            new PersonName("John","Liebenau"),
                            new PersonAge(
                                new DateTime(1967,4,25)));
        IPerson     actual = null;
        
        expected = itsTarget.insertPerson( expected );
        unitOfWork.commit();
        
        unitOfWork = itsTarget.getUnitOfWork();
        expected.getName().setMiddleName( "Friedrich" );
        expected.setNetWorth( new Money( Currency.getInstance( "EUR" ),100.00) );
        actual = itsTarget.updatePerson( expected );
        unitOfWork.commit();
        Assert.assertEquals( expected.getName(),actual.getName() );
        Assert.assertEquals( expected.getAge(),actual.getAge() );
    }

    @Test
    public void 
    testRemovePerson() 
        throws Exception
    {
        IUnitOfWork unitOfWork = itsTarget.getUnitOfWork();
        IPerson     expected = 
                        new Person(
                            new PersonName("John","Liebenau"),
                            new PersonAge(new DateTime(1967,4,25)));

        expected = itsTarget.insertPerson( expected );
        unitOfWork.commit();
        
        unitOfWork = itsTarget.getUnitOfWork();
        Assert.assertTrue( itsTarget.hasPersonWithPartyKey( expected.getPartyKey() ) );
        itsTarget.removePerson( expected );
        unitOfWork.commit();
        
        Assert.assertFalse( itsTarget.hasPersonWithPartyKey( expected.getPartyKey() ) );
    }

    @Test
    public void 
    testGetPersonByPartyKey()
        throws Exception
    {
        IUnitOfWork unitOfWork = itsTarget.getUnitOfWork();
        IPerson     expected = 
                        new Person(
                            new PersonName("John","Liebenau"),
                            new PersonAge(new DateTime(1967,4,25)));
        IPerson     actual = null;
        
        expected = itsTarget.insertPerson( expected );
        unitOfWork.commit();
        
        Assert.assertTrue( itsTarget.hasPersonWithPartyKey( expected.getPartyKey() ) );
        actual = itsTarget.getPersonByPartyKey( expected.getPartyKey() );
        Assert.assertEquals( expected,actual );
    }

    @Test
    public void 
    testGetPersonByName() 
        throws Exception
    {
        IUnitOfWork unitOfWork = itsTarget.getUnitOfWork();
        IPerson       expected = 
                          new Person(
                              new PersonName("John","Liebenau"),
                              new PersonAge(new DateTime(1967,4,25)));
        List<IPerson> actual = null;
        
        expected = itsTarget.insertPerson( expected );
        unitOfWork.commit();
        
        Assert.assertTrue( itsTarget.hasPersonWithPartyKey( expected.getPartyKey() ) );
        actual = itsTarget.getPersonsByName( expected.getName() );
        Assert.assertEquals( 1,actual.size() );
        Assert.assertEquals( expected,actual.get( 0 ) );
        
    }

    @Test
    public void 
    testGetFinder()
    {
        IFinder<IPerson> finder = itsTarget.getFinder( "GetByName" );
        
        Assert.assertNotNull( finder );
        Assert.assertEquals( "strata1.entity.testdomain.IPerson.GetByName",finder.getName() );
    }

    @Test
    public void 
    testHasPersonWithPartyKey() 
        throws Exception
    {
        IUnitOfWork unitOfWork = itsTarget.getUnitOfWork();
        IPerson     expected = 
                        new Person(
                            new PersonName("John","Liebenau"),
                            new PersonAge(new DateTime(1967,4,25)));
        IPerson     actual = null;
        
        expected = itsTarget.insertPerson( expected );
        unitOfWork.commit();
        
        Assert.assertTrue( itsTarget.hasPersonWithPartyKey( expected.getPartyKey() ) );
    }

    @Test
    public void 
    testHasPersonWithName() 
        throws Exception
    {
        IUnitOfWork unitOfWork = itsTarget.getUnitOfWork();
        IPerson       expected = 
                          new Person(
                              new PersonName("John","Liebenau"),
                              new PersonAge(new DateTime(1967,4,25)));
        
        expected = itsTarget.insertPerson( expected );
        unitOfWork.commit();
        
        Assert.assertTrue( itsTarget.hasPersonWithName( expected.getName() ) );
    }

    @Test
    public void 
    testHasFinder()
    {
        Assert.assertTrue( itsTarget.hasFinder( "GetByName" ) );
    }

    protected abstract IRepositoryContext
    createContext();
    
    protected abstract IRepositoryProvider<Long,IPerson>
    createProvider(IRepositoryContext context);
}

// ##########################################################################
