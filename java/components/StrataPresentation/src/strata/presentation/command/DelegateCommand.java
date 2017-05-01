// ##########################################################################
// # File Name:	DelegateCommand.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInteractor Framework.
// #
// #   			The StrataInteractor Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInteractor Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInteractor
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.presentation.command;

import java.lang.reflect.Method;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class DelegateCommand
    implements ICommand
{
    protected Object itsInstance;
    protected Method itsMethod;
    
    /************************************************************************
     * Creates a new DelegateCommand. 
     *
     */
    public 
    DelegateCommand(Object instance,Method method)
        throws IllegalArgumentException
    {
        if ( 
            !method
                .getDeclaringClass()
                .isAssignableFrom( instance.getClass() ) )
            throw new IllegalArgumentException();
        
        if ( method.getReturnType() != void.class )
            throw new IllegalArgumentException();
        
        if ( method.getParameterTypes().length > 0 )
            throw new IllegalArgumentException();
        
        itsInstance = instance;
        itsMethod   = method;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    execute()
        throws ExecutionException
    {
        try
        {
            itsMethod.invoke( itsInstance );
        }
        catch (Exception e)
        {
            throw new ExecutionException( e );
        }
    }

}

// ##########################################################################
