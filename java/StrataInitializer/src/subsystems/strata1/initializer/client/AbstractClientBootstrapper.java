// ##########################################################################
// # File Name:	AbstractClientBootstrapper.java
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

package strata1.initializer.client;

import strata1.interactor.region.IRegionManager;
import strata1.interactor.region.RegionInitializationException;
import strata1.interactor.shell.IDispatcher;
import strata1.interactor.shell.IShell;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract
class AbstractClientBootstrapper
    implements IClientBootstrapper
{
    private IClientModuleManager itsModuleManager;
    private IClientContainer     itsContainer;
    private IRegionManager       itsRegionManager;
    private IDispatcher          itsDispatcher;
    private IShell               itsShell;
    
    /************************************************************************
     * Creates a new {@code AbstractClientBootstrapper}. 
     *
     */
    public 
    AbstractClientBootstrapper()
    {
        itsModuleManager = null;
        itsContainer     = null;
        itsRegionManager = null;
        itsShell         = null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setModuleManager(IClientModuleManager modules)
    {
        itsModuleManager = modules;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setContainer(IClientContainer container)
    {
        itsContainer = container;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setRegionManager(IRegionManager manager)
    {
        itsRegionManager = manager;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setDispatcher(IDispatcher dispatcher)
    {
        itsDispatcher = dispatcher;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setShell(IShell shell)
    {
        itsShell = shell;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IClientModuleManager 
    getModuleManager()
    {
        return itsModuleManager;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IClientContainer 
    getContainer()
    {
        return itsContainer;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IRegionManager 
    getRegionManager()
    {
        return itsRegionManager;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IDispatcher 
    getDispatcher()
    {
        return itsDispatcher;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IShell 
    getShell()
    {
        return itsShell;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    run(IClientFactory factory)
    {
        try
        {
            setModuleManager( factory.createModuleManager() );
            setContainer( factory.createContainer() );
            setRegionManager( factory.createRegionManager() );
            setDispatcher( factory.createDispatcher() );
            setShell( factory.createShell( getDispatcher() ) );
                        
            configureModules();

            configureRegionManager();
            initializeShell();
            initializeModules();
            initializeRegions();
            startShell();
        }
        catch (Exception e)
        {
            
        }
    }

    protected abstract void
    configureModules();
    
    protected abstract void
    configureRegionManager();
    
    protected void
    initializeShell()
    {
        
    }
    
    protected void 
    initializeModules()
    {
        getModuleManager().initialize( this );
    }

    protected void
    initializeRegions() 
        throws RegionInitializationException
    {
        getRegionManager().initializeRegions();
    }
    
    protected void 
    startShell()
    {
        getShell().getDispatcher().start();
    }
    
    
}

// ##########################################################################
