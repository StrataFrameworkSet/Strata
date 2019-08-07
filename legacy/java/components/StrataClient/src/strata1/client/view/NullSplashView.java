// ##########################################################################
// # File Name:	NullSplashView.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInteractor Framework.
// #
// #   			The StrataInteractor Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInteractor Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInteractor
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.client.view;

import strata1.client.command.INullProvider;
import strata1.client.event.IChangeEvent;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class NullSplashView
    extends    AbstractView<INullProvider>
    implements ISplashView
{

    /************************************************************************
     * Creates a new {@code NullSplashView}. 
     *
     */
    public 
    NullSplashView()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    start()
    {
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
    show()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    hide()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    processChange(IChangeEvent event)
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setMessage(String message)
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setInitializationIncrements(int increments)
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    resetInitializationProgress()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    incrementInitializationProgress()
    {
    }

}

// ##########################################################################
