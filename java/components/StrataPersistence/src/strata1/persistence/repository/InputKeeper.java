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
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public
class InputKeeper
{
	private InputMode           itsMode;
	private Map<String,Object>  itsNamedInputs;
	private Map<Integer,Object> itsPositionalInputs;

	/************************************************************************
	 * Creates a new InputKeeper. 
	 *
	 */
	public 
	InputKeeper()
	{
		super();
		itsMode             = InputMode.NOT_INITIALIZED;
		itsNamedInputs      = new TreeMap<String,Object>();
		itsPositionalInputs = new TreeMap<Integer,Object>();
	}

	/************************************************************************
	 *  
	 *
	 * @param name
	 * @param value
	 * @throws InvalidInputException
	 */
	public void
	setInput(String name,Object value)
		throws InvalidInputException
	{
		checkMode( InputMode.NAMED );
		itsNamedInputs.put( name,value );
	}
	
	/************************************************************************
	 *  
	 *
	 * @param position
	 * @param value
	 * @throws InvalidInputException
	 */
	public void
	setInput(int position,Object value)
		throws InvalidInputException
	{
		checkMode( InputMode.POSITIONAL );
		itsPositionalInputs.put( position,value );
	}
	
	/************************************************************************
	 *  
	 *
	 * @param position
	 * @param value
	 * @throws InvalidInputException
	 */
	public void
	setInput(Collection<Object> inputs)
		throws InvalidInputException
	{
		int i = 0;
		
		checkMode( InputMode.POSITIONAL );
		
		for (Object input: inputs)
			itsPositionalInputs.put( i++,input );
	}
	
	/************************************************************************
	 *  
	 *
	 */
	public void
	clearInputs()
	{
		itsMode = InputMode.NOT_INITIALIZED;
		itsNamedInputs.clear();
		itsPositionalInputs.clear();
	}
	
	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public InputMode
	getMode()
	{
		return itsMode;
	}
	
	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public Map<String,Object>
	getNamedInputs()
	{
		return itsNamedInputs;
	}
	
	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public Collection<Object>
	getPositionalInputs()
	{
		return itsPositionalInputs.values();
	}
	
	/************************************************************************
	 *  
	 *
	 * @param requestedMode
	 * @throws InvalidInputException
	 */
	private void
	checkMode(InputMode requestedMode) throws InvalidInputException
	{
		if ( getMode() == InputMode.NOT_INITIALIZED )
			itsMode = requestedMode;
		else if ( getMode() != requestedMode )
		{
			throw new InvalidInputException( 
				"Cannot mix input modes: current mode is " + 
				getMode() + " requested mode is " + requestedMode + "." );
		}
	}
}


// ##########################################################################
