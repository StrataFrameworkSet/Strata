package strata1.entity.testdomain;

import strata1.entity.repository.IFinder;
import strata1.entity.repository.IRepositoryContext;
import strata1.entity.repository.IRepositoryProvider;
import strata1.entity.repository.IUnitOfWork;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract 
class OrganizationRepositoryTest
{
    private IRepositoryContext       itsContext;
    private IOrganizationRepository  itsTarget;
    
    @Before
    public void 
    setUp() throws Exception
    {
        itsContext = createContext();
        itsTarget = 
            new OrganizationRepository(
                createProvider(itsContext));
    }

    @After
    public void 
    tearDown() throws Exception
    {
        IFinder<IOrganization> finder = itsTarget.getFinder( "GetAll" );
        IUnitOfWork      unitOfWork = itsContext.getUnitOfWork();
        
        for (IOrganization p : finder.getAll())
            itsTarget.removeOrganization( p );
        
        unitOfWork.commit();
        itsTarget = null;
        itsContext = null;
    }

    @Test
    public void 
    testInsertOrganization() 
        throws Exception
    {
        IUnitOfWork   unitOfWork = itsContext.getUnitOfWork();
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
        IUnitOfWork   unitOfWork = itsContext.getUnitOfWork();
        IOrganization expected = new Organization("FooBar Inc.");
        IOrganization actual = null;
        
        expected = itsTarget.insertOrganization( expected );
        unitOfWork.commit();
        
        unitOfWork = itsContext.getUnitOfWork();
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
        IUnitOfWork   unitOfWork = itsContext.getUnitOfWork();
        IOrganization expected = new Organization("FooBar Inc.");

        expected = itsTarget.insertOrganization( expected );
        unitOfWork.commit();
        
        unitOfWork = itsContext.getUnitOfWork();
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
        IUnitOfWork   unitOfWork = itsContext.getUnitOfWork();
        IOrganization expected = new Organization("FooBar Inc.");
        IOrganization actual = null;
        
        expected = itsTarget.insertOrganization( expected );
        unitOfWork.commit();
        
        Assert.assertTrue( itsTarget.hasOrganizationWithPartyKey( expected.getPartyKey() ) );
        actual = itsTarget.getOrganizationByPartyKey( expected.getPartyKey() );
        Assert.assertEquals( expected,actual );
    }

    @Test
    public void 
    testGetOrganizationByName() 
        throws Exception
    {
        IUnitOfWork   unitOfWork = itsContext.getUnitOfWork();
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
        IFinder<IOrganization> finder = itsTarget.getFinder( "GetByName" );
        
        Assert.assertNotNull( finder );
        Assert.assertEquals( "strata1.entity.testdomain.IOrganization.GetByName",finder.getName() );
    }

    @Test
    public void 
    testHasOrganizationWithPartyKey() 
        throws Exception
    {
        IUnitOfWork   unitOfWork = itsContext.getUnitOfWork();
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
        IUnitOfWork   unitOfWork = itsContext.getUnitOfWork();
        IOrganization expected = new Organization("FooBar Inc.");
        
        expected = itsTarget.insertOrganization( expected );
        unitOfWork.commit();
        
        Assert.assertTrue( itsTarget.hasOrganizationWithName( expected.getName() ) );
    }

    @Test
    public void 
    testHasFinder()
    {
        Assert.assertTrue( itsTarget.hasFinder( "GetByName" ) );
    }

    protected abstract IRepositoryContext
    createContext();
    
    protected abstract IRepositoryProvider<Long,IOrganization>
    createProvider(IRepositoryContext context);
}

// ##########################################################################
