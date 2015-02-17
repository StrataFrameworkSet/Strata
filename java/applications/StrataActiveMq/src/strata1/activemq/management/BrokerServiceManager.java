// ##########################################################################
// # File Name: BrokerServiceManager.java
// #
// # Copyright: 2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License: This file is part of the MessageBroker Framework.
// #
// # The BrokerServiceManager Application is free software: you
// # can redistribute it and/or modify it under the terms of
// # the GNU Lesser General Public License as published by
// # the Free Software Foundation, either version 3 of the
// # License, or (at your option) any later version.
// #
// # The BrokerServiceManager Application is distributed in the
// # hope that it will be useful, but WITHOUT ANY WARRANTY;
// # without even the implied warranty of MERCHANTABILITY or
// # FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
// # General Public License for more details.
// #
// # You should have received a copy of the GNU Lesser
// # General Public License along with the BrokerServiceManager 
// # Application. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.activemq.management;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/****************************************************************************
 * Responsible to for creating and starting an ActiveMQ BrokerService 
 * on servlet init and shutting down the BrokerService on servlet destroy.
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class BrokerServiceManager
    extends HttpServlet
{
    private static final long serialVersionUID = -8289081958495740549L;
    private static Logger     theirLogger = null;
    
    private BrokerService     itsBroker;

    /************************************************************************
     * Creates a new BrokerServiceManager. 
     *
     */
    public
    BrokerServiceManager() 
    {
        itsBroker = null;
    }
    
    /************************************************************************
     * {@inheritDoc}
     * <p>
     * Initializes the {@code BrokerServiceManager} servlet by:
     * <ol>
     * <li>creating the shared logger if necessary</li>
     * <li>creating the {@code BrokerService}, and</li>
     * <li>starting the {@code BrokerService}</li>
     * </ol>
     */
    @Override
    public void 
    init() 
        throws ServletException
    {
        createLogger();        

        try
        {
            theirLogger.info( "Creating broker service." );
            itsBroker = createBroker();
            
            theirLogger.info( 
                "Starting broker service: " + 
                itsBroker.getBrokerName() + 
                " listening at uri: " + 
                itsBroker.getDefaultSocketURIString() );
            itsBroker.start();
           
            theirLogger.info( 
                itsBroker.getBrokerName() + " started successfully." );
        }
        catch(ServletException e)
        {
            StringWriter message = new StringWriter();
            PrintWriter  writer  = new PrintWriter(message);
            
            e.printStackTrace( writer );
            theirLogger.error( 
                "Unable to create or start broker service:\n" + 
                message.toString() );
            throw e;
        }
        catch(Throwable e)
        {
            StringWriter message = new StringWriter();
            PrintWriter  writer  = new PrintWriter(message);
            
            e.printStackTrace( writer );
            theirLogger.error( 
                "Unable to create or start broker service:\n" + 
                message.toString() );
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    service(
        HttpServletRequest request,
        HttpServletResponse response) 
            throws ServletException,IOException {}

    /************************************************************************
     * {@inheritDoc} 
     * <p>
     * Destroys the {@code BrokerServiceManager} servlet by
     * stopping the broker service.
     */
    @Override
    public void 
    destroy()
    {
        try
        {
            theirLogger.info( 
                "Stopping broker service:" +
                itsBroker.getBrokerName() );
            
            if ( itsBroker != null )
                itsBroker.stop();
            
            theirLogger.info( 
                itsBroker.getBrokerName() + " stopped successfully." );
        }
        catch(Throwable e)
        {
            StringWriter message = new StringWriter();
            PrintWriter  writer  = new PrintWriter(message);
            
            e.printStackTrace( writer );
            theirLogger.error( 
                "Unable to stop broker service:\n" + 
                message.toString() );
        }
    }
    
    /************************************************************************
     * Creates the shared logger if its is null. Configures the logger
     * using a <i>log4j.properties</i> file retrieved from the servlet 
     * context. 
     */
    private void
    createLogger()
    {
        String properties = 
            getAbsolutePath(getParameter("log4j-props-location"));
        
        PropertyConfigurator.configure(properties);
        
        if ( theirLogger == null )
            theirLogger = 
                Logger.getLogger(BrokerServiceManager.class.getName());
    }
    
    /************************************************************************
     * Creates the broker service from the config file name specified 
     * by the context parameter: <b>activemq-config-name</b>.
     *
     * @return
     * @throws Exception
     */
    private BrokerService
    createBroker() throws Exception
    {        
        return 
            BrokerFactory.createBroker( 
                "xbean:" + 
                getParameter("activemq-config-name") );
    }
    
    /************************************************************************
     * Returns the context parameter with the specified name. 
     *
     * @param  name  parameter name
     * @return parameter value
     */
    private String
    getParameter(String name)
    {
        return 
            getServletContext()
            .getInitParameter(name);      
    }
    
    /************************************************************************
     * Converts the specified relativePath into an absolute path 
     * using the context's real path.
     *
     * @param relativePath
     * @return absolute path
     */
    private String 
    getAbsolutePath(String relativePath)
    {
        String root = getServletContext().getRealPath( "/" );
        
        return root + relativePath;
    }
}

// ##########################################################################
