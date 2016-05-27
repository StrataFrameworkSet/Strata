package strata1.sqsintegrator.sqsmessaging;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import strata1.integrator.inmemorymessaging.InMemoryStringMessage;
import strata1.integrator.messaging.ISelector;
import strata1.integrator.messaging.IStringMessage;

public 
class SelectorFactoryTest
{
    private SelectorFactory target;
    
    @Before
    public void 
    setUp() throws Exception
    {
        target = new SelectorFactory();
    }

    @After
    public void 
    tearDown() throws Exception
    {
        target = null;
    }

    @Test
    public void 
    testIsStringEquals()
    {        
        assertTrue( target.isStringEquals( "ReturnAddress='Foo'" ));
        assertTrue( target.isStringEquals( "MESSAGE_TYPE_1='XYZ ABC'" ));
        assertFalse( target.isStringEquals( "ReturnAddress=Foo" ));
        assertFalse( target.isStringEquals( "ReturnAddress>='Foo'" ));
        assertFalse( target.isStringEquals( "ReturnAddress!='Foo'" ));
        assertFalse( target.isStringEquals( "ReturnAddress<='Foo'" ));
    }

    @Test
    public void 
    testCreateStringEqualsSelector()
    {
        ISelector      selector = target.createStringEqualsSelector( "ReturnAddress='Foo'" );
        IStringMessage message = new InMemoryStringMessage();
        
        assertFalse( selector.evaluate( message ) );
        message.setReturnAddress( "Foo" );
        assertTrue( selector.evaluate( message ) );
        message.setReturnAddress( "FooBar" );
        assertFalse( selector.evaluate( message ) );
       
    }

}

