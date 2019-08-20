package strata.domain.core.testdomain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import strata.domain.core.namedquery.INamedQuery;
import strata.domain.core.unitofwork.IUnitOfWork;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static strata.foundation.core.utility.Awaiter.await;


public abstract
class PartyRepositoryTest
{
    private IUnitOfWorkProvider itsProvider;
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
        member    = new Person(new PersonName("John","Liebenau"),new PersonAge(LocalDate.of(1967,4,25)));

        parentOrg = await(itsOrgRepository.insertOrganization( parentOrg ));
        childOrg  = await(itsOrgRepository.insertOrganization( childOrg ));
        member    = await(itsPersonRepository.insertPerson( member ));

        parentOrg.getMemberIds().add( childOrg.getPartyKey() );
        parentOrg.getMemberIds().add( member.getPartyKey() );
        childOrg.getMemberIds().add( member.getPartyKey() );

        parentOrg = await(itsOrgRepository.updateOrganization( parentOrg ));
        childOrg  = await(itsOrgRepository.updateOrganization( childOrg ));
        await(itsProvider.getUnitOfWork()).commit();
    }

    @After
    public void 
    tearDown() throws Exception
    {
        INamedQuery<IOrganization> orgFinder    = await(itsOrgRepository.getNamedQuery( "GetAll" )).get();
        INamedQuery<IPerson> personFinder = await(itsPersonRepository.getNamedQuery( "GetAll" )).get();
        IUnitOfWork unitOfWork   = await(itsProvider.getUnitOfWork());
        
        for (IOrganization o : await(orgFinder.getAll()))
            await(itsOrgRepository.removeOrganization( o ));

        for (IPerson p : await(personFinder.getAll()))
            await(itsPersonRepository.removePerson( p ));

        await(unitOfWork.commit());
        itsOrgRepository = null;
        itsPersonRepository = null;
        itsTarget = null;
        itsProvider = null;
    }

    @Test
    public void 
    testGetPartyByKey()
    {
        IParty actualParentOrg = await(itsTarget.getPartyByKey( parentOrg.getPartyKey() )).get();
        IParty actualChildOrg  = await(itsTarget.getPartyByKey( childOrg.getPartyKey() )).get();
        IParty actualMember    = await(itsTarget.getPartyByKey( member.getPartyKey() )).get();

        assertEquals( parentOrg,actualParentOrg );
        assertEquals( childOrg,actualChildOrg );
        assertEquals( member,actualMember );
    }

    @Test
    public void 
    testGetMembersOf()
    {
        List<IParty> parentMembers = await(itsTarget.getMembersOf( parentOrg ));
        List<IParty> childMembers = await(itsTarget.getMembersOf( childOrg ));
        
        assertTrue( parentMembers.contains( childOrg ) );
        assertTrue( parentMembers.contains( member ) );
        assertTrue( childMembers.contains( member ) );
    }

    @Test
    public void 
    testHasPartyWithKey()
    {
        assertTrue( await(itsTarget.hasPartyWithKey( parentOrg.getPartyKey() )) );
        assertTrue( await(itsTarget.hasPartyWithKey( childOrg.getPartyKey() )) );
        assertTrue( await(itsTarget.hasPartyWithKey( member.getPartyKey() )) );
    }

    @Test
    public void 
    testHasMembersOf()
    {
        assertTrue( await(itsTarget.hasMembersOf( parentOrg )) );
        assertTrue( await(itsTarget.hasMembersOf( childOrg )) );
    }

    protected abstract IUnitOfWorkProvider
    createUnitOfWorkProvider();

}

// ##########################################################################
