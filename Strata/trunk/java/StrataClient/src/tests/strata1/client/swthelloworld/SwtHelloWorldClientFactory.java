// ##########################################################################
// # File Name:	SwtHelloWorldClientFactory.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInitializer Framework.
// #
// #   			The StrataInitializer Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInitializer Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInitializer
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.client.swthelloworld;

import strata1.swtinteractor.swtregion.SwtRegionManager;
import strata1.swtinteractor.swtshell.SwtDispatcher;
import strata1.swtinteractor.swtview.SwtLoginView;
import strata1.swtinteractor.swtview.SwtSplashView;
import strata1.interactor.region.IRegionManager;
import strata1.interactor.shell.IDispatcher;
import strata1.interactor.view.ILoginView;
import strata1.interactor.view.ISplashView;
import strata1.common.authentication.FakeClientAuthenticator;
import strata1.common.authentication.IClientAuthenticator;
import strata1.common.logger.ILogger;
import strata1.common.logger.Logger;
import strata1.common.logger.PrintWriterLogEntryProcessor;
import strata1.common.springcontainerprovider.SpringContainerProvider;
import strata1.client.bootstrap.AbstractClientFactory;
import strata1.client.bootstrap.ClientContainer;
import strata1.client.bootstrap.IClientContainer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import java.io.PrintWriter;
import java.util.HashMap;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SwtHelloWorldClientFactory
    extends AbstractClientFactory
{

    /************************************************************************
     * Creates a new {@code SwtHelloWorldClientFactory}. 
     *
     */
    public 
    SwtHelloWorldClientFactory()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ILogger 
    createLogger()
    {
        ILogger logger = new Logger();
        
        logger.attachProcessor( 
            new PrintWriterLogEntryProcessor(
                new PrintWriter(System.out) ) );
        return logger;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IClientContainer 
    createContainer()
    {
        return 
            new ClientContainer(
                new SpringContainerProvider(
                    new GenericApplicationContext()));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IClientContainer 
    createContainer(String resourceLocation)
    {
        return 
            new ClientContainer(
                new SpringContainerProvider(
                    new GenericXmlApplicationContext(resourceLocation)));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IRegionManager 
    createRegionManager()
    {
        return new SwtRegionManager();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IDispatcher 
    createDispatcher()
    {
        return new SwtDispatcher();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ILoginView 
    createLoginView(IDispatcher dispatcher)
    {
        return new SwtLoginView( dispatcher,"Hello World" );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ISplashView 
    createSplashView(IDispatcher dispatcher)
    {
        return 
            new SwtSplashView( 
                dispatcher,
                "Hello World",
                "1.0.0",
                "Copyright 2012, Sapientia Systems LLC" );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IClientAuthenticator 
    createAuthenticator()
    {
        return 
            new FakeClientAuthenticator(
                new HashMap<String,String>()
                {
    
                    private static final long serialVersionUID = 1L;
                    
                    {
                        put("john","foobar");
                        put("nay","FOOBAR");
                    }
                });
    }

}

// ##########################################################################
