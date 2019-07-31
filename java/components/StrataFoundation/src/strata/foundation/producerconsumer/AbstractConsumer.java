// ##########################################################################
// # File Name:	AbstractConsumer.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
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

package strata.foundation.producerconsumer;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract
class AbstractConsumer<T>
    implements IConsumer<T>
{
    private ISelector<T>      itsSelector;
    private IExceptionHandler itsExceptionHandler;
    
    /************************************************************************
     * Creates a new {@code AbstractConsumer}. 
     *
     */
    protected 
    AbstractConsumer()
    {
        this( null );
    }

    /************************************************************************
     * Creates a new {@code AbstractConsumer}. 
     *
     */
    protected 
    AbstractConsumer(ISelector<T> selector)
    {
        itsSelector         = selector;
        itsExceptionHandler = new NullExceptionHandler();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IConsumer<T> 
    setSelector(ISelector<T> selector)
    {
        itsSelector = selector;
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IConsumer<T> 
    setExceptionHandler(IExceptionHandler handler)
    {
        itsExceptionHandler = handler;
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    consume(T element)
    {
        try
        {
            doConsume( element );
        }
        catch (Exception e)
        {
            getExceptionHandler().onException( e );
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ISelector<T>
    getSelector()
    {
        return itsSelector;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IExceptionHandler 
    getExceptionHandler()
    {
        return itsExceptionHandler;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    getWaitingCount()
    {
        return 0;
    }

    /************************************************************************
     *  
     *
     * @param element
     */
    protected abstract void 
    doConsume(T element);

}

// ##########################################################################
