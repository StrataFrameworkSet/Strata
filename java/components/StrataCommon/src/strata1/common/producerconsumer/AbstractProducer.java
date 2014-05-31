// ##########################################################################
// # File Name:	AbstractProducer.java
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

package strata1.common.producerconsumer;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class AbstractProducer<T>
    implements IProducer<T>
{
    private IDispatcher<T> itsRouter;
    
    /************************************************************************
     * Creates a new {@code AbstractProducer}. 
     *
     */
    public 
    AbstractProducer()
    {
        itsRouter = null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setDispatcher(IDispatcher<T> router)
    {
        itsRouter = router;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    clearDispatcher()
    {
        itsRouter = null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IDispatcher<T> 
    getDispatcher()
    {
        return itsRouter;
    }

}

// ##########################################################################
