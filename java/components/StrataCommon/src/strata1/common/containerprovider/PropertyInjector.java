// ##########################################################################
// # File Name:	PropertyInjector.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
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

package strata1.common.containerprovider;


/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class PropertyInjector
    implements IPropertyInjector
{

    /************************************************************************
     * Creates a new {@code PropertyInjector}. 
     *
     */
    public 
    PropertyInjector()
    {
        // TODO Auto-generated constructor stub
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public PropertyInjector 
    setPropertyName(String propertyName)
    {
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public PropertyInjector 
    setPropertyValue(String componentName)
    {
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getPropertyName()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getPropertyValue()
    {
        // TODO Auto-generated method stub
        return null;
    }

}

// ##########################################################################
