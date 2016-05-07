// ##########################################################################
// # File Name:	IBindingVisitor.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
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

package strata1.injector.container;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IBindingVisitor<T>
{
    public void
    visitBinding(IBinding<T> binding);
    
    public void
    visitIdentifier(TypeBindingIdentifier<T> identifier);
    
    public void
    visitIdentifier(TypeAndNameBindingIdentifier<T> identifier);
    
    public void
    visitIdentifier(TypeAndAnnotationBindingIdentifier<T> identifier);
    
    public void
    visitTarget(ClassBindingTarget<T> target);
   
    public void
    visitTarget(ProviderBindingTarget<T> target);
    
    public void
    visitTarget(InstanceBindingTarget<T> target);
    
    public void
    visitScope(IBindingScope<T> scope);
}

// ##########################################################################
