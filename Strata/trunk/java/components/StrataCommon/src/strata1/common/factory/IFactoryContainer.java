// ##########################################################################
// # File Name:	IFactoryContainer.java
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

package strata1.common.factory;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IFactoryContainer
{
    public <F> void
    registerFactory(F factory);
    
    public <F> void
    registerFactory(String factoryName,F factory);
    
    public <F> F
    getFactory(Class<F> factoryType);
    
    public <F> F
    getFactory(Class<F> factoryType,String factoryName);
    
    public <F> boolean
    hasFactory(Class<F> factoryType);
    
    public <F> boolean
    hasFactory(Class<F> factoryType,String factoryName);
}

// ##########################################################################
