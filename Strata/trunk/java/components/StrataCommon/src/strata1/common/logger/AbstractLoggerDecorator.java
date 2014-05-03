// ##########################################################################
// # File Name:	AbstractLoggerDecorator.java
// #
// # Copyright:	2013, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataCommon Framework.
// #
// #   			The StrataCommon Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataCommon Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataCommon
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.common.logger;

import javax.inject.*;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public class AbstractLoggerDecorator
    implements ILogger
{
    private ILogger itsTarget;
    
    /************************************************************************
     * Creates a new {@code AbstractLoggerDecorator}. 
     *
     */
    protected 
    AbstractLoggerDecorator(ILogger target)
    {
        itsTarget = target;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ILogger 
    attachProcessor(ILogEntryProcessor processor)
    {
        itsTarget.attachProcessor( processor );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ILogger 
    detachProcessor(ILogEntryProcessor processor)
    {
        itsTarget.detachProcessor( processor );
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasProcessor(ILogEntryProcessor processor)
    {
        return itsTarget.hasProcessor( processor );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    logStart(String message)
    {
        itsTarget.logStart( message );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    logStop(String message)
    {
        itsTarget.logStop( message );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    logDebug(String message)
    {
        itsTarget.logDebug( message );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    logVerbose(String message)
    {
        itsTarget.logVerbose( message );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    logInfo(String message)
    {
        itsTarget.logInfo( message );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    logWarning(String message)
    {
        itsTarget.logWarning( message );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    logError(String message)
    {
        itsTarget.logError( message );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    log(LoggingLevel level,String message)
    {
        itsTarget.log( level,message );
    }

}

// ##########################################################################
