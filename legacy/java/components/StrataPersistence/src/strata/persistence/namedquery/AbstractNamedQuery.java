// ##########################################################################
// # File Name:	.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataEntity Framework.
// #
// #   			The StrataEntity Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataEntity Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataEntity
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.persistence.namedquery;

import java.util.Collection;

/**
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract
class AbstractNamedQuery<T> 
	implements INamedQuery<T>
{
	private String      itsName;
	private InputKeeper	itsInputs;
	
	/************************************************************************
	 * Creates a new AbstractNamedQuery. 
	 *
	 * @param name
	 */
	public 
	AbstractNamedQuery(String name)
	{
		super();
		itsName   = name;
		itsInputs = new InputKeeper();
	}

	/************************************************************************
	 * Creates a new AbstractNamedQuery. 
	 *
	 * @param other
	 */
	public 
	AbstractNamedQuery(AbstractNamedQuery<T> other)
	{
		super();
		itsName   = other.itsName;
		itsInputs = new InputKeeper();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see INamedQuery#setInput(String,Object)
	 */
	@Override
	public INamedQuery<T> 
	setInput(String name,Object input) 
		throws InvalidInputException
	{
		itsInputs.setInput( name,input );
		return this;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see INamedQuery#setInput(int,Object)
	 */
	@Override
	public INamedQuery<T> 
	setInput(int index,Object input) 
		throws InvalidInputException
	{
		itsInputs.setInput( index,input );
        return this;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see INamedQuery#setInput(Collection)
	 */
	@Override
	public INamedQuery<T> 
	setInput(Collection<Object> inputs)
		throws InvalidInputException
	{
		itsInputs.setInput( inputs );
        return this;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see INamedQuery#clearInputs()
	 */
	@Override
	public INamedQuery<T> 
	clearInputs()
	{
		itsInputs.clearInputs();
        return this;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see INamedQuery#getName()
	 */
	@Override
	public String 
	getName()
	{
		return itsName;
	}
	
	/************************************************************************
	 *  
	 *
	 * @return
	 */
	protected InputKeeper
	getInputs()
	{
		return itsInputs;
	}
}

// ##########################################################################
