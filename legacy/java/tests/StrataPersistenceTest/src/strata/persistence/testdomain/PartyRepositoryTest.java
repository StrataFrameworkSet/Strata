package strata.persistence.testdomain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import junit.framework.Assert;
import strata.foundation.value.DateTime;
import strata.persistence.namedquery.INamedQuery;
import strata.persistence.unitofwork.IUnitOfWork;
import strata.persistence.unitofwork.IUnitOfWorkProvider;

public abstract
class PartyRepositoryTest
{
    private IUnitOfWorkProvider     itsProvider;
    private IOrganizationRepository itsOrgRepository;
    private IPersonRepository       itsPersonRepository;
    private IPartyRepository        itsTarget;
    
    private IOrganization parentOrg;
    private IOrganization childOrg;
    private IPerson       member;

    @Before
    public void 
    setUp() throws Exception
    {
        itsProvider = createUnitOfWorkProvider();
        itsOrgRepository = 
            new OrganizationRepository(itsProvider);
        itsPersonRepository = 
            new PersonRepository(itsProvider);
        itsTarget = 
            new PartyRepository(itsProvider);
        
        parentOrg = new Organization( "Sapientia Systems LLC" );
        childOrg  = new Organization( "Sapientia Development Systems" );
        member    = new Person(new PersonName("John","Liebenau"),new PersonAge(new DateTime(1967,4,25)));
        
        parentOrg = itsOrgRepository.insertOrganization( parentOrg );
        childOrg  = itsOrgRepository.insertOrganization( childOrg );
        member    = itsPersonRepository.insertPerson( member );
        
        parentOrg.getMemberIds().add( childOrg.getPartyKey() );
        parentOrg.getMemberIds().add( member.getPartyKey() );
        childOrg.getMemberIds().add( member.getPartyKey() );
        
        parentOrg = itsOrgRepository.updateOrganization( parentOrg );
        childOrg  = itsOrgRepository.updateOrganization( childOrg );
        itsProvider.getUnitOfWork().commit();
    }

    @After
    public void 
    tearDown() throws Exception
    {
        INamedQuery<IOrganization> orgFinder    = itsOrgRepository.getNamedQuery( "GetAll" );
        INamedQuery<IPerson>       personFinder = itsPersonRepository.getNamedQuery( "GetAll" );
        IUnitOfWork                unitOfWork   = itsProvider.getUnitOfWork();
        
        for (IOrganization o : orgFinder.getAll())
            itsOrgRepository.removeOrganization( o );

        for (IPerson p : personFinder.getAll())
            itsPersonRepository.removePerson( p );

        unitOfWork.commit();
        itsOrgRepository = null;
        itsPersonRepository = null;
        itsTarget = null;
        itsProvider = null;
    }

    @Test
    public void 
    testGetPartyByKey()
    {
        IParty actualParentOrg = itsTarget.getPartyByKey( parentOrg.getPartyKey() );
        IParty actualChildOrg  = itsTarget.getPartyByKey( childOrg.getPartyKey() );
        IParty actualMember    = itsTarget.getPartyByKey( member.getPartyKey() );
        
        Assert.assertEquals( parentOrg,actualParentOrg );
        Assert.assertEquals( childOrg,actualChildOrg );
        Assert.assertEquals( member,actualMember );
    }

    @Test
    public void 
    testGetMembersOf()
    {
        List<IParty> parentMembers = itsTarget.getMembersOf( parentOrg );
        List<IParty> childMembers = itsTarget.getMembersOf( childOrg );
        
        Assert.assertTrue( parentMembers.contains( childOrg ) );
        Assert.assertTrue( parentMembers.contains( member ) );
        Assert.assertTrue( childMembers.contains( member ) );
    }

    @Test
    public void 
    testHasPartyWithKey()
    {
        Assert.assertTrue( itsTarget.hasPartyWithKey( parentOrg.getPartyKey() ) );
        Assert.assertTrue( itsTarget.hasPartyWithKey( childOrg.getPartyKey() ) );
        Assert.assertTrue( itsTarget.hasPartyWithKey( member.getPartyKey() ) );
    }

    @Test
    public void 
    testHasMembersOf()
    {
        Assert.assertTrue( itsTarget.hasMembersOf( parentOrg ) );
        Assert.assertTrue( itsTarget.hasMembersOf( childOrg ) );
    }

    protected abstract IUnitOfWorkProvider
    createUnitOfWorkProvider();

}

// ##########################################################################
