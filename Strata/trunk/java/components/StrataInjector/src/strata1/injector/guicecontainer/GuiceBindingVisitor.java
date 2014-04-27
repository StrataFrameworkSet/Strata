// ##########################################################################
// # File Name:	GuiceAdapterModule.java
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

package strata1.injector.guicecontainer;

import strata1.injector.container.ClassBindingTarget;
import strata1.injector.container.IBinding;
import strata1.injector.container.IBindingScope;
import strata1.injector.container.IBindingVisitor;
import strata1.injector.container.InstanceBindingTarget;
import strata1.injector.container.ProviderBindingTarget;
import strata1.injector.container.SingletonScope;
import strata1.injector.container.TypeAndAnnotationBindingIdentifier;
import strata1.injector.container.TypeAndNameBindingIdentifier;
import strata1.injector.container.TypeBindingIdentifier;
import com.google.inject.Binder;
import com.google.inject.CreationException;
import com.google.inject.ProvisionException;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.binder.ScopedBindingBuilder;
import com.google.inject.name.Names;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class GuiceBindingVisitor<T>
    implements IBindingVisitor<T>
{
    private Binder                     itsBinder;
    private LinkedBindingBuilder<T>    itsBuilder1;
    private ScopedBindingBuilder       itsBuilder2;
    
    public GuiceBindingVisitor()
    {
        itsBinder   = null;
        itsBuilder1 = null;
        itsBuilder2 = null;
    }
    
    /************************************************************************
     * Creates a new {@code GuiceAdapterModule}. 
     *
     */
    public 
    GuiceBindingVisitor(Binder binder)
    {
        itsBinder   = binder;
        itsBuilder1 = null;
        itsBuilder2 = null;
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    visitBinding(IBinding<T> binding)
    {
        System.out.println( "debug: visitBinding");

        binding
            .getIdentifier()
            .accept( this );
        
        if ( binding.getTarget() != null )
            binding
                .getTarget()
                .accept( this );
        else
            System.out.println( "debug: target is null");
        
        if ( binding.getScope() != null )
            binding
                .getScope()
                .accept( this );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    visitIdentifier(TypeBindingIdentifier<T> identifier)
    {
        System.out.println( "debug: visitIdentifier");
        
        try
        {
            itsBuilder1 = 
                itsBinder.bind(identifier.getType());
        }
        catch (ProvisionException e) {}
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    visitIdentifier(TypeAndNameBindingIdentifier<T> identifier)
    {
        System.out.println( "debug: visitIdentifier");
        itsBuilder1 = 
            itsBinder
                .bind(identifier.getType())
                .annotatedWith( Names.named( identifier.getKey() ) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    visitIdentifier(TypeAndAnnotationBindingIdentifier<T> identifier)
    {
        System.out.println( "debug: visitIdentifier");
        itsBuilder1 = 
            itsBinder
                .bind(identifier.getType())
                .annotatedWith( identifier.getKey() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    visitTarget(ClassBindingTarget<T> target)
    {
        System.out.println( "debug: visitTarget");
        itsBuilder2 = 
            itsBuilder1.to(target.getType());
 
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    visitTarget(ProviderBindingTarget<T> target)
    {
        System.out.println( "debug: visitTarget");
        itsBuilder2 = itsBuilder1.toProvider( target.getProviderType() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    visitTarget(InstanceBindingTarget<T> target)
    {
        System.out.println( "debug: visitTarget");
        itsBuilder1.toInstance( target.getInstance() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    visitScope(IBindingScope<T> scope)
    {
        System.out.println( "debug: visitScope");
        if ( itsBuilder2 == null )
            throw new IllegalStateException();
        
        if ( scope instanceof SingletonScope )
            itsBuilder2.in( Singleton.class );
    }

    /************************************************************************
     *  
     *
     * @param binder
     */
    public void
    setBinder(Binder binder)
    {
        itsBinder = binder;
    }
    
    /************************************************************************
     *  
     *
     * @param literal
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> com.google.inject.TypeLiteral<T>
    convertToGuiceTypeLiteral(TypeLiteral<T> literal)
    {
        return 
            (com.google.inject.TypeLiteral<T>)
                com.google.inject.TypeLiteral.get(literal.getType());
    }

}

// ##########################################################################
