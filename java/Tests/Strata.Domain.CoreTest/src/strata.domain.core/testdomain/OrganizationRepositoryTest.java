package strata.domain.core.testdomain;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import strata.domain.core.namedquery.INamedQuery;
import strata.domain.core.unitofwork.IUnitOfWork;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;

import static strata.foundation.core.utility.Awaiter.await;


public abstract 
class OrganizationRepositoryTest
{
    private IUnitOfWorkProvider itsProvider;
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
        INamedQuery<IOrganization> query = await(itsTarget.getNamedQuery( "GetAll" ));
        IUnitOfWork unitOfWork = await(itsProvider.getUnitOfWork());
        
        for (IOrganization p : await(query.getAll()))
            await(itsTarget.removeOrganization( p ));
        
        await(unitOfWork.commit());
        itsTarget = null;
        itsProvider = null;
    }

    @Test
    public void 
    testInsertOrganization() 
        throws Exception
    {
        IUnitOfWork   unitOfWork = await(itsProvider.getUnitOfWork());
        IOrganization expected = new Organization("FooBar Inc.");
        IOrganization actual = null;
        
        expected = await(itsTarget.insertOrganization( expected ));
        await(unitOfWork.commit());
        
        actual   = await(itsTarget.getOrganizationByPartyKey( expected.getPartyKey() )).get();
        Assert.assertEquals( expected,actual );
    }

    @Test
    public void 
    testUpdateOrganization() 
        throws Exception
    {
        IUnitOfWork   unitOfWork = await(itsProvider.getUnitOfWork());
        IOrganization expected = new Organization("FooBar Inc.");
        IOrganization actual = null;
        
        expected = await(itsTarget.insertOrganization( expected ));
        await(unitOfWork.commit());
        
        unitOfWork = await(itsProvider.getUnitOfWork());
        expected.getMemberIds().add( 10L );
        actual = await(itsTarget.updateOrganization( expected ));
        await(unitOfWork.commit());
        Assert.assertEquals( expected,actual );
    }

    @Test
    public void 
    testRemoveOrganization() 
        throws Exception
    {
        IUnitOfWork   unitOfWork = await(itsProvider.getUnitOfWork());
        IOrganization expected = new Organization("FooBar Inc.");

        expected = await(itsTarget.insertOrganization( expected ));
        await(unitOfWork.commit());
        
        unitOfWork = await(itsProvider.getUnitOfWork());
        Assert.assertTrue( itsTarget.hasOrganizationWithPartyKey( expected.getPartyKey() ).toCompletableFuture().join() );
        await(itsTarget.removeOrganization( expected ));
        await(unitOfWork.commit());
        
        Assert.assertFalse( itsTarget.hasOrganizationWithPartyKey( expected.getPartyKey() ).toCompletableFuture().join() );
    }

    @Test
    public void 
    testGetOrganizationByPartyKey()
        throws Exception
    {
        IUnitOfWork   unitOfWork = await(itsProvider.getUnitOfWork());
        IOrganization expected = new Organization("FooBar Inc.");
        IOrganization actual = null;
        
        expected = await(itsTarget.insertOrganization( expected ));
        await(unitOfWork.commit());
        
        Assert.assertTrue( itsTarget.hasOrganizationWithPartyKey( expected.getPartyKey() ).toCompletableFuture().join() );
        actual = await(itsTarget.getOrganizationByPartyKey( expected.getPartyKey() )).get();
        Assert.assertEquals( expected,actual );
    }

    //@Ignore
    @Test
    public void 
    testGetOrganizationByName() 
        throws Exception
    {
        IUnitOfWork   unitOfWork = await(itsProvider.getUnitOfWork());
        IOrganization expected = new Organization("FooBar Inc.");
        IOrganization actual = null;
        
        expected = await(itsTarget.insertOrganization( expected ));
        await(unitOfWork.commit());
        
        Assert.assertTrue( await(itsTarget.hasOrganizationWithPartyKey( expected.getPartyKey() )) );
        actual = await(itsTarget.getOrganizationByName( expected.getName() )).get();
        Assert.assertEquals( expected,actual );
        
    }

    @Test
    public void 
    testGetFinder()
    {
        INamedQuery<IOrganization> finder = await(itsTarget.getNamedQuery( "GetByName" ));
        
        Assert.assertNotNull( finder );
        Assert.assertEquals( "strata.domain.core.testdomain.IOrganization.GetByName",finder.getName() );
    }

    @Test
    public void 
    testHasOrganizationWithPartyKey() 
        throws Exception
    {
        IUnitOfWork   unitOfWork = await(itsProvider.getUnitOfWork());
        IOrganization expected = new Organization("FooBar Inc.");
        
        expected = await(itsTarget.insertOrganization( expected ));
        await(unitOfWork.commit());
        
        Assert.assertTrue( await(itsTarget.hasOrganizationWithPartyKey( expected.getPartyKey() )) );
    }

    @Test
    public void 
    testHasOrganizationWithName() 
        throws Exception
    {
        IUnitOfWork   unitOfWork = await(itsProvider.getUnitOfWork());
        IOrganization expected = new Organization("FooBar Inc.");
        
        expected = await(itsTarget.insertOrganization( expected ));
        await(unitOfWork.commit());
        
        Assert.assertTrue( await(itsTarget.hasOrganizationWithName( expected.getName() )) );
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
