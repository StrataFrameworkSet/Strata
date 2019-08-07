// ##########################################################################
// # File Name:	IContainer.java
// #
// # Copyright:	2013, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInjector Framework.
// #
// #   			The StrataInjector Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInjector Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInjector
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.foundation.standardinjection;

import java.lang.annotation.Annotation;
import strata.foundation.injection.IBinding;
import strata.foundation.injection.IBindingBuilder;
import strata.foundation.injection.IBindingIdentifier;


/****************************************************************************
 * Represents a container of dependency injection bindings and provides 
 * methods for binding types and decorators and getting instances generated
 * from these bindings.
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IBindingMap
{
    /************************************************************************
     * Inserts a new binding into this container via a binding builder. 
     * 
     * @param  builder  built the binding being inserted
     * @return this container for method chaining
     */
    public <T> IBindingMap
    insertBinding(IBindingBuilder<T> builder);
    
    /************************************************************************
     * Inserts a new binding into this container. 
     *
     * @param  binding  the binding being inserted
     * @return this container for method chaining
     */
    public <T> IBindingMap
    insertBinding(IBinding<T> binding);
    
    /************************************************************************
     * Returns an instance created from the 
     * binding mapped to the specified type. 
     *
     * @param  type the binding's type
     * @return an instance created from the binding
     */
    public <T> T
    getInstance(Class<T> type);
    
    /************************************************************************
     * Returns an instance created from the binding
     * mapped to the specified type and key.  
     *
     * @param  type the binding's type
     * @param  key  the binding's key
     * @return an instance created from the binding
     */
    public <T> T
    getInstance(Class<T> type,String key);
    
    /************************************************************************
     * Returns an instance created from the binding
     * mapped to the specified type and key.  
     *
     * @param  type the binding's type
     * @param  key  the binding's key
     * @return an instance created from the binding
     */
    public <T> T
    getInstance(Class<T> type,Class<? extends Annotation> key);
    
    /************************************************************************
     * Returns an instance created from the binding
     * mapped to the specified identifier.  
     *
     * @param  type the binding's type
     * @param  key  the binding's key
     * @return an instance created from the binding
     */
    public <T> T
    getInstance(IBindingIdentifier<T> id);

    /************************************************************************
     * Determines if the container has the specified binding. 
     *
     * @param  type the binding's type
     * @return true if container has specified binding, false otherwise
     */
    public <T> boolean
    hasBinding(Class<T> type);
    
    /************************************************************************
     * Determines if the container has the specified binding. 
     *
     * @param  type the binding's type
     * @param  key  the binding's key
     * @return true if container has specified binding, false otherwise
     */
    public <T> boolean
    hasBinding(Class<T> type,String key);
    
    /************************************************************************
     * Determines if the container has the specified binding. 
     *
     * @param  type the binding's type
     * @param  key  the binding's key
     * @return true if container has specified binding, false otherwise
     */
    public <T> boolean
    hasBinding(Class<T> type,Class<? extends Annotation> key);
    
    /************************************************************************
     * Determines if the container has the specified binding. 
     *
     * @param  id binding's identifier
     * @return true if container has specified binding, false otherwise
     */
    public <T> boolean
    hasBinding(IBindingIdentifier<T> id);
    
}

// ##########################################################################
