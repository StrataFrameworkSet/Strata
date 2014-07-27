// ##########################################################################
// # File Name:	InstanceInserter.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
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

package strata1.injector.containerprovider;

import java.lang.annotation.Annotation;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class InstanceInserter<T>
{
    private IContainerProvider itsProvider;
    
    /************************************************************************
     * Creates a new {@code InstanceInserter}. 
     *
     * @param p
     */
    public
    InstanceInserter(IContainerProvider p)
    {
        itsProvider = p;
    }
    
    /************************************************************************
     *  
     *
     * @param annotation
     * @param instanceName
     * @param instance
     */
    public void
    insertByAnnotation(
        Class<? extends Annotation> annotation,
        String                      instanceName,
        T                           instance)
    {
        Class<?> instanceType = instance.getClass();
        
        if ( instanceType.isAnnotationPresent( annotation ) )
            itsProvider.registerInstance( instanceName,instance );
        else
            throw 
                new IllegalArgumentException( 
                    instanceType + " is not a supported type.");    
    }
    
    /************************************************************************
     *  
     *
     * @param annotation
     * @param instanceName
     * @param instance
     */
    public void
    insertByType(Class<?> type,String instanceName,T instance)
    {
        Class<?> instanceType = instance.getClass();
        
        if ( type.isAssignableFrom( instanceType ) )
            itsProvider.registerInstance( instanceName,instance );
        else
            throw 
                new IllegalArgumentException( 
                    instanceType + " is not a supported type.");    
    }
    
    
}

// ##########################################################################
