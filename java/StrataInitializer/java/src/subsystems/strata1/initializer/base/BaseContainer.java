// ##########################################################################
// # File Name:	BaseContainer.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInitializer Framework.
// #
// #   			The StrataInitializer Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInitializer Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInitializer
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.initializer.base;


/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface BaseContainer
{
    /************************************************************************
     *  
     *
     * @param name
     * @param instance
     */
    public <T> void
    registerInstance(String name,T instance);
    
    /************************************************************************
     *  
     *
     * @param gatewayType
     * @param name
     * @return
     */
    public <G> G
    getGateway(Class<G> gatewayType,String name);

    /************************************************************************
     *  
     *
     * @param factoryType
     * @param name
     * @return
     */
    public <F> F
    getFactory(Class<F> factoryType,String name);
    
    /************************************************************************
     *  
     *
     * @param type
     * @param name
     * @return
     */
    public boolean
    hasInstance(Class<?> type,String name);
}


// ##########################################################################
