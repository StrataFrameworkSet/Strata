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

import strata1.injector.container.Container;
import strata1.injector.container.IContainer;
import strata1.injector.springcontainerprovider.SpringContainerProvider;
import strata1.client.bootstrap.AbstractClientFactory;
import strata1.client.bootstrap.ClientContainer;
import strata1.client.region.IRegionManager;
import strata1.client.shell.IDispatcher;
import strata1.client.view.ILoginView;
import strata1.client.view.ISplashView;
import strata1.common.authentication.FakeClientAuthenticator;
import strata1.common.authentication.IClientAuthenticator;
import strata1.common.logger.ILogger;
import strata1.common.logger.Logger;
import strata1.common.logger.PrintWriterLogEntryProcessor;
import strata1.swtclient.swtregion.SwtRegionManager;
import strata1.swtclient.swtshell.SwtDispatcher;
import strata1.swtclient.swtview.SwtLoginView;
import strata1.swtclient.swtview.SwtSplashView;
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
    public IContainer 
    createContainer()
    {
        return new Container();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IContainer 
    createContainer(String resourceLocation)
    {
        return new Container();
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
