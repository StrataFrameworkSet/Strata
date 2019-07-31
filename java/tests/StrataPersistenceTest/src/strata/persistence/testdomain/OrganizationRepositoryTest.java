package strata.persistence.testdomain;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import strata.persistence.namedquery.INamedQuery;
import strata.persistence.unitofwork.IUnitOfWork;
import strata.persistence.unitofwork.IUnitOfWorkProvider;

public abstract 
class OrganizationRepositoryTest
{
    private IUnitOfWorkProvider      itsProvider;
    private IOrganizationRepository  itsTarget;
    
    @Before
    public void 
    setUp() throws Exception
    {
        itsProvider = createUnitOfWorkProvider();
        itsTarget = new OrganizationRepository(itsProvider);
    }

    @After
    public void 
    tearDown() throws Exception
    {
        INamedQuery<IOrganization> query = itsTarget.getNamedQuery( "GetAll" );
        IUnitOfWork      unitOfWork = itsProvider.getUnitOfWork();
        
        for (IOrganization p : query.getAll())
            itsTarget.removeOrganization( p );
        
        unitOfWork.commit();
        itsTarget = null;
        itsProvider = null;
    }

    @Test
    public void 
    testInsertOrganization() 
        throws Exception
    {
        IUnitOfWork   unitOfWork = itsProvider.getUnitOfWork();
        IOrganization expected = new Organization("FooBar Inc.");
        IOrganization actual = null;
        
        expected = itsTarget.insertOrganization( expected );
        unitOfWork.commit();
        
        actual   = itsTarget.getOrganizationByPartyKey( expected.getPartyKey() );
        Assert.assertEquals( expected,actual );
    }

    @Test
    public void 
    testUpdateOrganization() 
        throws Exception
    {
        IUnitOfWork   unitOfWork = itsProvider.getUnitOfWork();
        IOrganization expected = new Organization("FooBar Inc.");
        IOrganization actual = null;
        
        expected = itsTarget.insertOrganization( expected );
        unitOfWork.commit();
        
        unitOfWork = itsProvider.getUnitOfWork();
        expected.getMemberIds().add( 10L );
        actual = itsTarget.updateOrganization( expected );
        unitOfWork.commit();
        Assert.assertEquals( expected,actual );
    }

    @Test
    public void 
    testRemoveOrganization() 
        throws Exception
    {
        IUnitOfWork   unitOfWork = itsProvider.getUnitOfWork();
        IOrganization expected = new Organization("FooBar Inc.");

        expected = itsTarget.insertOrganization( expected );
        unitOfWork.commit();
        
        unitOfWork = itsProvider.getUnitOfWork();
        Assert.assertTrue( itsTarget.hasOrganizationWithPartyKey( expected.getPartyKey() ) );
        itsTarget.removeOrganization( expected );
        unitOfWork.commit();
        
        Assert.assertFalse( itsTarget.hasOrganizationWithPartyKey( expected.getPartyKey() ) );
    }

    @Test
    public void 
    testGetOrganizationByPartyKey()
        throws Exception
    {
        IUnitOfWork   unitOfWork = itsProvider.getUnitOfWork();
        IOrganization expected = new Organization("FooBar Inc.");
        IOrganization actual = null;
        
        expected = itsTarget.insertOrganization( expected );
        unitOfWork.commit();
        
        Assert.assertTrue( itsTarget.hasOrganizationWithPartyKey( expected.getPartyKey() ) );
        actual = itsTarget.getOrganizationByPartyKey( expected.getPartyKey() );
        Assert.assertEquals( expected,actual );
    }

    @Ignore
    @Test
    public void 
    testGetOrganizationByName() 
        throws Exception
    {
        IUnitOfWork   unitOfWork = itsProvider.getUnitOfWork();
        IOrganization expected = new Organization("FooBar Inc.");
        IOrganization actual = null;
        
        expected = itsTarget.insertOrganization( expected );
        unitOfWork.commit();
        
        Assert.assertTrue( itsTarget.hasOrganizationWithPartyKey( expected.getPartyKey() ) );
        actual = itsTarget.getOrganizationByName( expected.getName() );
        Assert.assertEquals( expected,actual );
        
    }

    @Test
    public void 
    testGetFinder()
    {
        INamedQuery<IOrganization> finder = itsTarget.getNamedQuery( "GetByName" );
        
        Assert.assertNotNull( finder );
        Assert.assertEquals( "strata.persistence.testdomain.IOrganization.GetByName",finder.getName() );
    }

    @Test
    public void 
    testHasOrganizationWithPartyKey() 
        throws Exception
    {
        IUnitOfWork   unitOfWork = itsProvider.getUnitOfWork();
        IOrganization expected = new Organization("FooBar Inc.");
        
        expected = itsTarget.insertOrganization( expected );
        unitOfWork.commit();
        
        Assert.assertTrue( itsTarget.hasOrganizationWithPartyKey( expected.getPartyKey() ) );
    }

    @Test
    public void 
    testHasOrganizationWithName() 
        throws Exception
    {
        IUnitOfWork   unitOfWork = itsProvider.getUnitOfWork();
        IOrganization expected = new Organization("FooBar Inc.");
        
        expected = itsTarget.insertOrganization( expected );
        unitOfWork.commit();
        
        Assert.assertTrue( itsTarget.hasOrganizationWithName( expected.getName() ) );
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
