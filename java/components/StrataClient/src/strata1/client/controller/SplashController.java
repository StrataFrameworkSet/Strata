// ##########################################################################
// # File Name:	SplashController.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataClient Framework.
// #
// #   			The StrataClient Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataClient Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataClient
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.client.controller;

import strata1.client.command.INullProvider;
import strata1.client.model.INullModel;
import strata1.client.view.ISplashView;
import strata.foundation.logger.ILogger;
import strata.foundation.logger.LoggingLevel;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SplashController
    extends    AbstractController<INullProvider,ISplashView,INullModel>
    implements ISplashController
{
    private ILogger     itsLogger;
    private Runnable    itsInitializer;
    private Runnable    itsActivator;
    
    /************************************************************************
     * Creates a new {@code LoginController}. 
     *
     */
    public 
    SplashController()
    {
        itsInitializer   = null;
        itsActivator     = null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    start()
    {
        getView().start();        
        getView().setMessage( "Initializing modules..." );
        itsInitializer.run();
        getView().setMessage( "Initialization complete." );
        getView().stop(); 
        itsLogger.log( LoggingLevel.INFO,"Starting application." );
        itsActivator.run();
    
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stop()
    {      
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setLogger(ILogger logger)
    {
        itsLogger = logger;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setSplashView(ISplashView splashView)
    {
        setView( splashView,this );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setInitializer(Runnable initializer)
    {
        itsInitializer = initializer;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setActivator(Runnable activator)
    {
        itsActivator = activator;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setInitializationIncrements(int increments)
    {
        getView().setInitializationIncrements( increments );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setInitializationMessage(String message)
    {
        getView().setMessage( message );
        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    resetInitializationProgress()
    {
        getView().resetInitializationProgress();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    incrementInitializationProgress()
    {
        getView().incrementInitializationProgress();
    }

}

// ##########################################################################
