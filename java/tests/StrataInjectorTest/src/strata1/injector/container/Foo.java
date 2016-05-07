// ##########################################################################
// # File Name:	Foo.java
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
class Foo
    implements IFoo
{
    private String itsFooName;
        
    /************************************************************************
     * Creates a new {@code Foo}. 
     *
     */
    @Inject
    public 
    Foo(
        @Named("test1") 
        String name)
    {
        itsFooName = name;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getFooName()
    {
        return itsFooName;
    }

}

// ##########################################################################
