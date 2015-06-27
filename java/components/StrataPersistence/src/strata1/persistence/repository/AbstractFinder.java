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

package strata1.persistence.repository;

import java.util.Collection;

/**
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract
class AbstractFinder<T> 
	implements IFinder<T>
{
	private String      itsName;
	private InputKeeper	itsInputs;
	
	/************************************************************************
	 * Creates a new AbstractFinder. 
	 *
	 * @param name
	 */
	public 
	AbstractFinder(String name)
	{
		super();
		itsName   = name;
		itsInputs = new InputKeeper();
	}

	/************************************************************************
	 * Creates a new AbstractFinder. 
	 *
	 * @param other
	 */
	public 
	AbstractFinder(AbstractFinder<T> other)
	{
		super();
		itsName   = other.itsName;
		itsInputs = new InputKeeper();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IFinder#setInput(String,Object)
	 */
	@Override
	public void 
	setInput(String name,Object input) 
		throws InvalidInputException
	{
		itsInputs.setInput( name,input );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IFinder#setInput(int,Object)
	 */
	@Override
	public void 
	setInput(int index,Object input) 
		throws InvalidInputException
	{
		itsInputs.setInput( index,input );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IFinder#setInput(Collection)
	 */
	@Override
	public void 
	setInput(Collection<Object> inputs)
		throws InvalidInputException
	{
		itsInputs.setInput( inputs );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IFinder#clearInputs()
	 */
	@Override
	public void 
	clearInputs()
	{
		itsInputs.clearInputs();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IFinder#getName()
	 */
	@Override
	public String 
	getName()
	{
		return itsName;
	}
	
	protected InputKeeper
	getInputs()
	{
		return itsInputs;
	}
}

// ##########################################################################
