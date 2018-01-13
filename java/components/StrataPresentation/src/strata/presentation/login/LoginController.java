// ##########################################################################
// # File Name:	LoginController.java
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

package strata.presentation.login;

import javax.inject.Inject;
import strata.foundation.injection.IContainer;
import strata.foundation.logger.ILogger;
import strata.foundation.logger.LoggingLevel;
import strata.presentation.command.ExecutionException;
import strata.presentation.command.ICommand;
import strata.presentation.command.ILoginProvider;
import strata.presentation.controller.AbstractController;
import strata.presentation.controller.IController;
import strata.presentation.model.INullModel;
import strata.presentation.view.ILoginView;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class LoginController
    extends    AbstractController<ILoginProvider,ILoginView,INullModel>
    implements ILoginController
{
    private ILogger            itsLogger;
    private IContainer         itsContainer;
    private IController<?,?,?> itsController;
    
    /************************************************************************
     * Creates a new {@code LoginController}. 
     *
     */
    @Inject
    public 
    LoginController()
    {
        itsLogger     = null;
        itsContainer  = null;
        itsController = null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ICommand 
    getLoginCommand()
    {
        return 
            new ICommand()
            {
                @Override
                public void 
                execute() 
                    throws ExecutionException
                {
                    doLogin();
                } 
            };
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ICommand 
    getCancelCommand()
    {
        return 
            new ICommand()
            {
                @Override
                public void 
                execute() 
                    throws ExecutionException
                {
                    doCancel();
                } 
            };
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    start()
    {
        getView().show();
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
    setContainer(IContainer container)
    {
        itsContainer = container;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setLoginView(ILoginView loginView)
    {
        setView(loginView,this);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setMainController(IController<?,?,?> controller)
    {
        itsController = controller;
    }

    /************************************************************************
     *  
     *
     */
    protected void
    doLogin()
    {
        itsLogger.log( LoggingLevel.INFO,"Logging in..." );
        
    }

    /************************************************************************
     *  
     *
     */
    protected void
    doCancel()
    {
        itsLogger.log( LoggingLevel.INFO,"Cancelling login and exiting." );
        //System.exit(  0 );
    }
}

// ##########################################################################
