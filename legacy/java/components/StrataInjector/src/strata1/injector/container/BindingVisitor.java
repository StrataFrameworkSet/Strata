// ##########################################################################
// # File Name:	BindingVisitor.java
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

import strata1.injector.reflection.IConstructor;
import strata1.injector.reflection.IType;
import javax.inject.Inject;
import javax.inject.Provider;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class BindingVisitor<T>
    implements IBindingVisitor<T>
{
    private IContainer            itsContainer;
    private IBindingIdentifier<T> itsIdentifier;
    private Provider<T>           itsProvider;
    
    /************************************************************************
     * Creates a new {@code BindingVisitor}. 
     *
     */
    public 
    BindingVisitor(IContainer container)
    {
        itsContainer  = container;
        itsIdentifier = null;
        itsProvider   = null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    visitBinding(IBinding<T> binding)
    {
        binding.getIdentifier().accept( this );
        binding.getTarget().accept( this );
        binding.getScope().accept( this );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    visitIdentifier(TypeBindingIdentifier<T> identifier)
    {
        itsIdentifier = identifier;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    visitIdentifier(TypeAndNameBindingIdentifier<T> identifier)
    {
        itsIdentifier = identifier;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    visitIdentifier(TypeAndAnnotationBindingIdentifier<T> identifier)
    {
        itsIdentifier = identifier;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    visitTarget(ClassBindingTarget<T> target)
    {
        IType<? extends T>        type        = getType(target.getType());
        IConstructor<? extends T> constructor = getInjectionConstructor(target.getType());
          
        itsProvider = 
            new ConstructorBasedProvider<T>(
                itsContainer,
                type,
                constructor);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @SuppressWarnings("unchecked")
    @Override
    public void 
    visitTarget(ProviderBindingTarget<T> target)
    {
        Class<? extends Provider<? extends T>> providerType = 
            target.getProviderType();
        
        try
        {
            if ( target.hasProviderType() )
                itsProvider = getProviderFromType( providerType );       
            else if ( target.hasProviderInstance() )
                itsProvider = (Provider<T>)target.getProviderInstance();
            else
                throw new IllegalStateException("No provider."); 
        }
        catch(Exception e)
        {
            throw new IllegalStateException(e);
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    visitTarget(InstanceBindingTarget<T> target)
    {
        itsProvider = new InstanceProvider<T>( target.getInstance() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    visitScope(IBindingScope<T> scope)
    {
        itsProvider = scope.getScopedProvider(itsProvider);
    }

    /************************************************************************
     *  
     *
     * @return
     */
    public IBindingIdentifier<T>
    getIdentifier()
    {
        if ( itsIdentifier == null )
            throw new IllegalStateException("identifier is null");
        
        return itsIdentifier;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public Provider<T>
    getProvider()
    {
        if ( itsProvider == null )
            throw new IllegalStateException("provider is null");
        
        return itsProvider;
    }

    /************************************************************************
     *  
     *
     * @param type
     * @return
     */
    private IType<? extends T>
    getType(Class<? extends T> type)
    {
        return 
            itsContainer
            .getTypeManager()
            .getType( type );
    }
    
    /************************************************************************
     *  
     *
     * @param type
     * @return
     */
    private IConstructor<? extends T> 
    getInjectionConstructor(Class<? extends T> type)
    {
        IType<? extends T> wrapperType = 
            itsContainer
                .getTypeManager()
                .getType( type );
        
        if ( wrapperType.hasConstructor( Inject.class ) )
            return wrapperType.getConstructor( Inject.class );
        
        return wrapperType.getDefaultConstructor();        
    }
    
    /************************************************************************
     *  
     *
     * @param providerType
     * @return
     */
    @SuppressWarnings("unchecked")
    private Provider<T>
    getProviderFromType(Class<? extends Provider<? extends T>> providerType)
    {
        IType<? extends Provider<? extends T>> wrapperType = 
            itsContainer
                .getTypeManager()
                .getType( providerType );
        IConstructor<?> constructor = wrapperType.getDefaultConstructor();
        
        return (Provider<T>)constructor.create();
        
    }
}

// ##########################################################################
