package strata.persistence.testdomain;

import static org.junit.Assert.*;
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
import strata.persistence.repository.InsertFailedException;
import strata.persistence.unitofwork.ExecutionManager;
import strata.persistence.unitofwork.IExecutionListener;
import strata.persistence.unitofwork.IUnitOfWork;
import strata.persistence.unitofwork.IUnitOfWorkProvider;

public abstract
class ExecutionManagerTest
    implements IExecutionListener
{
    private IUnitOfWorkProvider itsProvider;
    private ExecutionManager    itsTarget;
    private IPersonRepository   itsRepository;
    
    @Before
    public void 
    setUp() throws Exception
    {
        itsProvider   = createUnitOfWorkProvider();
        itsTarget     = new ExecutionManager(itsProvider,5);
        itsRepository = new PersonRepository(itsProvider);
    }

    @After
    public void 
    tearDown() throws Exception
    {
        INamedQuery<IPerson> finder = itsRepository.getNamedQuery( "GetAll" );
        IUnitOfWork          unitOfWork = itsProvider.getUnitOfWork();
        
        for (IPerson p : finder.getAll())
            itsRepository.removePerson( p );
        
        unitOfWork.commit();
        itsRepository = null;
        itsProvider = null;
    }

    @Test
    public void 
    testExecute1() 
        throws Exception
    {
        IPerson expectedOutput = 
            itsTarget
                .execute( 
                    this,
                    ()-> {
                        IPerson expected = 
                                    new Person(
                                        new PersonName("John","Liebenau"),
                                        new PersonAge(
                                            new DateTime(1967,4,25)));
    
                        expected = itsRepository.insertPerson( expected );   
                        assertNotNull( expected );
                        return expected;
                    } );
        IPerson actualOutput = 
            itsTarget
                .execute( 
                    this,
                    ()-> {
                        IPerson actual = itsRepository.getExisting( expectedOutput.getPartyKey() );
                        
                        assertNotNull( actual );
                        return actual;
                    } );
        
        Assert.assertEquals( expectedOutput.getName(),actualOutput.getName() );
        Assert.assertEquals( expectedOutput.getAge(),actualOutput.getAge() );
    }

    @Test
    public void 
    testExecute2() 
        throws Exception
    {
        try
        {
            itsTarget
                .execute( 
                    this,
                    ()-> {
                        IPerson expected = 
                                    new Person(
                                        new PersonName("John","Liebenau"),
                                        new PersonAge(
                                            new DateTime(1967,4,25)));
    
                        expected = itsRepository.insertPerson( expected );   
                        expected = itsRepository.insertPerson( null );   
                        assertNotNull( expected );
                    } );
            fail( "Should have thrown exception." );
        }
        catch (InsertFailedException e) {}
    }

    @Override
    public void 
    onAttempt(int attempt)
    {
        System.out.println( "Starting attempt: " + attempt );
    }

    @Override
    public void 
    onCommitSucceeded()
    {
        System.out.println( "Commit succeeded." );                        
    }

    @Override
    public void 
    onCommitFailed(Exception e)
    {
        System.out.println( "Commit failed: " + e );                        
    }

    @Override
    public void 
    onRollbackSucceeded()
    {
        System.out.println( "Rollback succeeded." );                        
    }

    @Override
    public void 
    onRollbackFailed(Exception e)
    {
        System.out.println( "Rollback failed: " + e );
    }

    protected abstract IUnitOfWorkProvider
    createUnitOfWorkProvider();
}

// ##########################################################################
