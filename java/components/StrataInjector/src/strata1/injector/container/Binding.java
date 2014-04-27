package strata1.injector.container;

// ##########################################################################
// # File Name:	Binding.java
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

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class Binding<T>
    implements IBinding<T>
{
    private IBindingIdentifier<T> itsIdentifier;
    private IBindingTarget<T>     itsTarget;
    private IBindingScope<T>      itsScope;
    
    /************************************************************************
     * Creates a new {@code Binding}. 
     *
     */
    public 
    Binding()
    {
        itsIdentifier = null;
        itsTarget     = null;
        itsScope      = null;
    }

    /************************************************************************
     * Creates a new {@code Binding}. 
     *
     * @param identifier
     * @param target
     * @param scope
     */
    public 
    Binding(
        IBindingIdentifier<T> identifier,
        IBindingTarget<T>     target,
        IBindingScope<T>      scope)
    {
        itsIdentifier = identifier;
        itsTarget     = target;
        itsScope      = scope;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBindingIdentifier<T> 
    getIdentifier()
    {
        return itsIdentifier;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBindingTarget<T> 
    getTarget()
    {
        return itsTarget;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IBindingScope<T> 
    getScope()
    {
        return itsScope;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    accept(IBindingVisitor<T> visitor)
    {
        visitor.visitBinding( this );
    }

    /************************************************************************
     *  
     *
     * @param identifier
     * @return
     */
    public Binding<T>
    setIdentifier(IBindingIdentifier<T> identifier)
    {
        itsIdentifier = identifier;
        return this;
    }
    
    /************************************************************************
     *  
     *
     * @param target
     * @return
     */
    public Binding<T>
    setTarget(IBindingTarget<T> target)
    {
        itsTarget = target;
        return this;
    }
    
    /************************************************************************
     *  
     *
     * @param scope
     * @return
     */
    public Binding<T>
    setScope(IBindingScope<T> scope)
    {
        itsScope = scope;
        return this;
    }
}

// ##########################################################################
