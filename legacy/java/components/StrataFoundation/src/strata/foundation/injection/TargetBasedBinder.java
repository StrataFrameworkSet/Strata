// ##########################################################################
// # File Name:	TargetBasedBinder.java
// #
// # Copyright:	2017, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataFoundation Framework.
// #
// #   			The StrataFoundation Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataFoundation Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataFoundation
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.foundation.injection;

import java.lang.annotation.Annotation;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class TargetBasedBinder<T>extends AbstractBinder<T>
{
    private final Class<? extends T> itsTarget;
    
    /************************************************************************
     * Creates a new TargetBasedBinder. 
     *
     * @param builder
     * @param scope
     */
    public 
    TargetBasedBinder(
        ISourceBindingBuilder<T>       builder,
        Class<? extends T>          target,
        Class<? extends Annotation> scope)
    {
        super( builder,scope );
        itsTarget = target;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    bind()
    {
        getBuilder()
            .toType( itsTarget )
            .withScope( getScope() );
    }

}

// ##########################################################################
