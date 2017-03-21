package strata.persistence.testdomain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import java.util.Currency;
import java.util.List;
import strata.foundation.value.DateTime;
import strata.foundation.value.Money;
import strata.persistence.namedquery.INamedQuery;
import strata.persistence.unitofwork.IUnitOfWork;
import strata.persistence.unitofwork.IUnitOfWorkProvider;

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
        INamedQuery<IPerson> finder     = itsTarget.getNamedQuery( "GetAll" );
        IUnitOfWork          unitOfWork = itsProvider.getUnitOfWork();
        
        for (IPerson p : finder.getAll())
            itsTarget.removePerson( p );
        
        unitOfWork.commit();
        itsTarget = null;
        itsProvider = null;
    }

    @Test
    public void 
    testInsertPerson() 
        throws Exception
    {
        IUnitOfWork unitOfWork = itsProvider.getUnitOfWork();
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
        IUnitOfWork unitOfWork = itsProvider.getUnitOfWork();
        IPerson     expected = 
                        new Person(
                            new PersonName("John","Liebenau"),
                            new PersonAge(
                                new DateTime(1967,4,25)));
        IPerson     actual = null;
        
        
        expected = itsTarget.insertPerson( expected );
        unitOfWork.commit();
        
        unitOfWork = itsProvider.getUnitOfWork();
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
        IUnitOfWork unitOfWork = itsProvider.getUnitOfWork();
        IPerson     expected = 
                        new Person(
                            new PersonName("John","Liebenau"),
                            new PersonAge(new DateTime(1967,4,25)));

        expected = itsTarget.insertPerson( expected );
        unitOfWork.commit();
        
        unitOfWork = itsProvider.getUnitOfWork();
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
        IUnitOfWork unitOfWork = itsProvider.getUnitOfWork();
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
        IUnitOfWork unitOfWork = itsProvider.getUnitOfWork();
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
        INamedQuery<IPerson> query = itsTarget.getNamedQuery( "GetByName" );
        
        Assert.assertNotNull( query );
        Assert.assertEquals( "strata.persistence.testdomain.IPerson.GetByName",query.getName() );
    }

    @Test
    public void 
    testHasPersonWithPartyKey() 
        throws Exception
    {
        IUnitOfWork unitOfWork = itsProvider.getUnitOfWork();
        IPerson     expected = 
                        new Person(
                            new PersonName("John","Liebenau"),
                            new PersonAge(new DateTime(1967,4,25)));
        IPerson     actual = null;
        
        expected = itsTarget.insertPerson( expected );
        unitOfWork.commit();
        
        Assert.assertTrue( itsTarget.hasPersonWithPartyKey( expected.getPartyKey() ) );
    }

    @Ignore
    @Test
    public void 
    testHasPersonWithName() 
        throws Exception
    {
        IUnitOfWork unitOfWork = itsProvider.getUnitOfWork();
        IPerson     expected = 
                          new Person(
                              new PersonName("John","Liebenau"),
                              new PersonAge(new DateTime(1967,4,25)));
        
        expected = itsTarget.insertPerson( expected );
        unitOfWork.commit();
        
        Assert.assertTrue( itsTarget.hasPersonWithName( expected.getName() ) );
        unitOfWork = itsProvider.getUnitOfWork();
        itsTarget.removePerson( expected );
        unitOfWork.commit();
        Assert.assertFalse( itsTarget.hasPersonWithName( expected.getName() ) );
    }

    @Test
    public void 
    testHasFinder()
    {
        Assert.assertTrue( itsTarget.hasNamedQuery( "GetByName" ) );
    }

    protected abstract IUnitOfWorkProvider
    createUnitOfWorkProvider();
}

// ##########################################################################
