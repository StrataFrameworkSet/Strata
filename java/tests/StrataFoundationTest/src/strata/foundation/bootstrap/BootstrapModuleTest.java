package strata.foundation.bootstrap;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import strata.foundation.injection.AbstractModule;
import strata.foundation.injection.IContainer;
import strata.foundation.injection.IModule;
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
                        public Definition<ILogger> 
                        createLoggerDefinition()
                        {
                            return 
                                new Definition<ILogger>(
                                    LoggerProvider.class,
                                    AbstractModule.getDefaultScope());
                        }

                        @Override
                        public IStartStopController 
                        createStartStopController()
                        {
                            return 
                                new IStartStopController()
                                {

                                    @Override
                                    public IStartStopController 
                                    setContainer(IContainer container)
                                    {
                                        return this;
                                    }

                                    @Override
                                    public void 
                                    startApplication()
                                    {
                                    }

                                    @Override
                                    public void 
                                    stopApplication()
                                    {
                                    }
                                
                                };
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
