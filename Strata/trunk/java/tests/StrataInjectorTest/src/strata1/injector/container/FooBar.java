// ##########################################################################
// # File Name:	FooBar.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInjectorTest Framework.
// #
// #   			The StrataInjectorTest Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInjectorTest Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInjectorTest
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.injector.container;

import javax.inject.Inject;
import javax.inject.Named;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class FooBar
    implements IFooBar
{
    private IFoo   itsFoo;
    private IBar   itsBar;
    
    @Inject
    @Named("test2")
    private String itsBaz;
    
    /************************************************************************
     * Creates a new {@code FooBar}. 
     *
     */
    @Inject
    public 
    FooBar(IFoo foo)
    {
        itsFoo = foo;
        itsBar = null;
        itsBaz = null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    @Inject
    public void 
    setBar(IBar bar)
    {
        itsBar = bar;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IFoo 
    getFoo()
    {
        return itsFoo;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBar 
    getBar()
    {
        return itsBar;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getBaz()
    {
        return itsBaz;
    }

}

// ##########################################################################
