// ##########################################################################
// # File Name:	Bootstrapper.java
// #
// # Copyright:	2013, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataServer Framework.
// #
// #   			The StrataServer Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataServer Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataServer
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.foundation.bootstrap;

import java.util.List;
import strata.foundation.commandline.ICommandLineParser;
import strata.foundation.injection.IContainer;
import strata.foundation.injection.IModule;


/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class Bootstrapper
    implements IBootstrapper
{
    private ICommandLineParser itsCommandLineParser;
    private IContainer         itsContainer;
    
    /************************************************************************
     * Creates a new {@code Bootstrapper}. 
     *
     * @param start
     */
    public 
    Bootstrapper()
    {
        itsCommandLineParser   = null;
        itsContainer           = null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ICommandLineParser 
    getCommandLineParser()
    {
        return itsCommandLineParser;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IContainer 
    getContainer()
    {
        return itsContainer;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    run(IApplicationFactory factory)
    {
        try
        {
            createContainer(factory);
        }
        catch (Exception e)
        {
            throw new IllegalStateException( e );
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    run(IApplicationFactory factory,String[] arguments)
    {
        try
        {
            createCommandLineParser(factory);
            
            if ( mustParseCommandLine(arguments) )
                parseCommandLine(arguments);
            
            createContainer(factory);
        }
        catch (Exception e)
        {
            throw new IllegalStateException( e );
        }
    }

    /************************************************************************
     *  
     *
     * @param factory
     */
    protected void
    createCommandLineParser(IApplicationFactory factory)
    {
        itsCommandLineParser = factory.createCommandLineParser();
    }
    
    /************************************************************************
     *  
     *
     * @param factory
     */
    protected void 
    createContainer(IApplicationFactory factory)
    {
        itsContainer = factory.createContainer();
        
        if ( getContainer() == null )
            throw new IllegalStateException("container is null");
    }

    /************************************************************************
     *  
     *
     * @param arguments
     * @return
     */
    protected boolean
    mustParseCommandLine(String[] arguments)
    {
        return
            (arguments != null) &&
            (arguments.length > 0) &&
            (getCommandLineParser() != null);
    }
    
    /************************************************************************
     *  
     *
     * @param arguments
     * @throws Exception 
     */
    protected void
    parseCommandLine(String[] arguments) 
        throws Exception
    {
        getCommandLineParser().parse( arguments );
    }


}

// ##########################################################################
