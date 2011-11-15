// ##########################################################################
// # File Name:	ParameterizedDelegateCommand.java
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

package strata1.interactor.command;

import java.lang.reflect.Method;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class ParameterizedDelegateCommand<T>
    implements ParameterizedCommand<T>
{
    protected Object itsInstance;
    protected Method itsMethod;
    protected T      itsInput;
    
    /************************************************************************
     * Creates a new ParameterizedDelegateCommand. 
     *
     */
    public 
    ParameterizedDelegateCommand()
    {
        // TODO Auto-generated constructor stub
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
            itsMethod.invoke( itsInstance,getInput() );
        }
        catch (Exception e)
        {
            throw new ExecutionException( e );
        }
        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setInput(T input)
    {
        itsInput = input;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public T 
    getInput()
    {
        return itsInput;
    }

}


// ##########################################################################
