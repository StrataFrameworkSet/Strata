// ##########################################################################
// # File Name:	INamedQuery.java
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
import strata.foundation.utility.ICopyable;


/****************************************************************************
 * Generic interface that participates in the 
 * <a href="{@docRoot}/designpatterns/DomainObjectManager.pdf">
 * RepositoryImp (aka Domain Object Manager)</a> design pattern and declares:
 * <ul>
 *     <li>an operation for identification</li>
 *     <li>operations for parameterizing the query process</li>
 *     <li>an operation for obtaining the list of domain objects 
 *         retrieved by the query process.</li>
 * </ul>
 * @param <T> - entity type
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface INamedQuery<T>
	extends ICopyable
{
	/************************************************************************
	 * {@inheritDoc} 
	 * @return {@code INamedQuery<T>}
	 * 
	 */
	@Override
	public INamedQuery<T>
	copy();
	
	/************************************************************************
	 * Sets the finder's input using a name and the input value. 
	 *
	 * @param	name	identifies input parameter
	 * @param 	input	input parameter value
	 * @return TODO
	 * @throws  InvalidFormatException invalid input to finder.
	 */
	public INamedQuery<T> 
	setInput(String name,Object input) throws InvalidInputException;

	/************************************************************************
	 * Sets the finder's input using a name and the input value. 
	 *
	 * @param 	index	identifies input parameter
	 * @param 	input	input parameter value
	 * @return TODO
	 * @throws 	InvalidFormatException	invalid input to finder
	 */
	public INamedQuery<T> 
	setInput(int index,Object input) throws InvalidInputException;
	
	/************************************************************************
	 * Sets the finder's input using a Collection. 
	 *
	 * @param 	inputs	list of input values
	 * @return TODO
	 * @throws 	InvalidFormatException	invalid input to finder
	 */
	public INamedQuery<T> 
	setInput(Collection<Object> inputs) throws InvalidInputException;
	
	/************************************************************************
	 * Clears the finder's current inputs.
	 * @return TODO
	 *
	 */
	public INamedQuery<T>
	clearInputs();
	
	/************************************************************************
	 * Completely clears the finder's state including results and inputs. 
	 * @return TODO
	 *
	 */
	public INamedQuery<T>
	clear();
	
	/************************************************************************
	 * Returns the <code>INamedQuery</code>'s name. 
	 *
	 * @return	finder's name
	 */
	public String
	getName();
	
	/************************************************************************
	 * Returns all results of the find process. 
	 *
	 * @return	all results
	 */
	public Collection<T> 
	getAll();
	
	/************************************************************************
	 * Returns the unique result of finding a Person. 
	 *
	 * @return	the unique person
	 * @throws NotUniqueException
	 */
	public T 
	getUnique() 
	    throws NotUniqueException;
	
	/************************************************************************
	 * Returns the next result during the process of finding Persons. 
	 *
	 * @return the next person
	 */
	public T 
	getNext();

	/************************************************************************
	 * Returns true if there is a unique result. 
	 *
	 * @return
	 * @throws NotUniqueException
	 */
	public boolean
	hasUnique() 
	    throws NotUniqueException;
	
	/************************************************************************
	 * Returns true if there are any results. 
	 *
	 * @return
	 */
	public boolean
	hasAny();
	
	/************************************************************************
	 * Asks the finder if there is more to retrieve. 
	 *
	 * @return	true if there is more
	 */
	public boolean 
	hasNext();
}


// ##########################################################################
