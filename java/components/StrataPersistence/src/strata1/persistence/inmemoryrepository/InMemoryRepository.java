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

package strata1.persistence.inmemoryrepository;

import strata1.persistence.repository.IRepository;

/****************************************************************************
 * Base class of all <i>in-memory</i> RepositoryImp objects.
 * Declares a method for committing changes by processing a ChangeSet.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class InMemoryRepository
	implements IRepository
{
	private InMemoryRepositoryContext itsContext;
	
	/************************************************************************
	 * Creates a new InMemoryRepository. 
	 *
	 * @param context
	 */
	public 
	InMemoryRepository(InMemoryRepositoryContext context)
	{
		super();
		itsContext = context;
	}
	
	/************************************************************************
	 * {@inheritDoc} 
	 * @see RepositoryImp#getContext()
	 */
	@Override
	public InMemoryRepositoryContext 
	getContext()
	{
		return itsContext;
	}

	/************************************************************************
	 * Apply the change set to the repository's internal storage. 
	 * 
	 */
	public abstract void 
	applyChanges();
	
	/************************************************************************
	 * Discard the change set. 
	 *
	 */
	public abstract void
	discardChanges();
}


// ##########################################################################
