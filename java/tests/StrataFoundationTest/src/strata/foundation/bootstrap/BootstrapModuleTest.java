package strata.foundation.bootstrap;

import static org.junit.Assert.assertNotNull;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import strata.foundation.injection.AbstractModule;
import strata.foundation.injection.IBinder;
import strata.foundation.injection.IContainer;
import strata.foundation.injection.IKeyBindingBuilder;
import strata.foundation.injection.IModule;
import strata.foundation.injection.ProviderBasedBinder;
import strata.foundation.injection.SingletonScope;
import strata.foundation.injection.TargetBasedBinder;
import strata.foundation.injection.TestModule;
import strata.foundation.logger.ILogger;

public abstract
class BootstrapModuleTest
{
    private IContainer itsContainer;
    
    @Before
    public void 
    setUp() throws Exception
    {
        itsContainer = 
            createContainer( 
                new BootstrapModule(
                    new AbstractApplicationFactory() 
                    {

                        @Override
                        public IBinder<ILogger> 
                        createLoggerBinder(IKeyBindingBuilder<ILogger> builder)
                        {
                            return 
                                new ProviderBasedBinder<ILogger>(
                                    builder,
                                    new LoggerProvider(),
                                    AbstractModule.getDefaultScope());
                        }

                        @Override
                        public IBinder<IStartStopController> 
                        createControllerBinder(IKeyBindingBuilder<IStartStopController> builder)
                        {
                            return 
                                new TargetBasedBinder<IStartStopController>(
                                    builder,
                                    TestStartStopController.class,
                                    SingletonScope.class);
                            
                        }

                        @Override
                        protected List<IModule> 
                        createModules()
                        {
                            List<IModule> modules = new ArrayList<IModule>();
                            
                            modules.add( new TestModule() );
                            return modules;
                        }
                        
                    }) );
    }

    @After
    public void 
    tearDown() throws Exception
    {
        itsContainer = null;
    }

    @Test
    public void 
    testInitialize()
    {
        assertNotNull( itsContainer.getInstance( ILogger.class ) );
        assertNotNull( itsContainer.getInstance( IStartStopController.class ) );
    }

    protected abstract IContainer
    createContainer(IModule... modules);
}

// ##########################################################################
