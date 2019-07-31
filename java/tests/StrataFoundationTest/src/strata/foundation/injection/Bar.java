package strata.foundation.injection;
import javax.inject.Inject;

// ##########################################################################
// # File Name:	Bar.java
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

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class Bar
    implements IBar
{
    private int itsBarId;
    
    /************************************************************************
     * Creates a new {@code Bar}. 
     *
     */
    @Inject
    public 
    Bar(Integer id)
    {
        itsBarId = id;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    getBarId()
    {
        return itsBarId;
    }

}

// ##########################################################################
