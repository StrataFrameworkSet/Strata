// ##########################################################################
// # File Name:	DisruptorExceptionHandler.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
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

package strata1.common.producerconsumer;

import com.lmax.disruptor.ExceptionHandler;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class DisruptorExceptionHandler
    implements ExceptionHandler
{
    private IExceptionHandler itsAdaptee;
    
    /************************************************************************
     * Creates a new {@code DisruptorExceptionHandler}. 
     *
     */
    public 
    DisruptorExceptionHandler(IExceptionHandler adaptee)
    {
        itsAdaptee = adaptee;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    handleEventException(Throwable ex,long sequence,Object event)
    {
        itsAdaptee.onException( ex );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    handleOnStartException(Throwable ex)
    {
        itsAdaptee.onException( ex );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    handleOnShutdownException(Throwable ex)
    {
        itsAdaptee.onException( ex );
    }

}

// ##########################################################################
