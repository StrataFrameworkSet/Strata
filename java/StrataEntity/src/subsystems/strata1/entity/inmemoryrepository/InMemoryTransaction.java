// ##########################################################################
// # File Name:	InMemoryTransaction.java
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

package strata1.entity.inmemoryrepository;

import java.util.HashSet;
import java.util.Set;

/****************************************************************************
 * Implements the Transaction interface as an <i>in-memory</i>
 * transaction that groups changes into <code>ChangeSet</code>s 
 * and commits them in an all-or-nothing manner.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class InMemoryTransaction
{
	private enum State
	{
		CREATED,
		ACTIVE,
		COMMITTED,
		ROLLEDBACK;
	}
	
	private InMemoryRepositoryContext itsContext;
	private Set<ITransactionParticipant> itsRepositories;
	private State 					  itsState;
	
	/************************************************************************
	 * Creates a new InMemoryTransaction. 
	 *
	 */
	InMemoryTransaction(InMemoryRepositoryContext context)
	{
		super();
		itsContext      = context;
		itsRepositories = null;
		itsState        = State.CREATED;
	}

	/************************************************************************
	 *  
	 *
	 */
	public void 
	begin()
	{
		itsContext.getSynchronizer().lockForWriting();
		
		try
		{
			if ( itsState != State.CREATED )
				return;
			
			itsRepositories = new HashSet<ITransactionParticipant>();
			itsState = State.ACTIVE;
		}
		finally
		{
			itsContext.getSynchronizer().unlockFromWriting();
		}
	}

	/************************************************************************
	 * 
	 */
	public void 
	commit()
	{
		itsContext.getSynchronizer().lockForWriting();
		
		try
		{
			if ( !isActive() )
				throw new RuntimeException( 
				"Cannot commit non-active transaction." );
			
			for (ITransactionParticipant r:itsRepositories)
				r.applyChanges();
			
			itsState = State.COMMITTED;
			itsContext.clearTransaction();
		}
		finally
		{
			itsContext.getSynchronizer().unlockFromWriting();
		}
	}

	/************************************************************************
	 * 
	 */
	public void 
	rollback() 
	{
		itsContext.getSynchronizer().lockForWriting();
		
		try
		{
			if ( !isActive() )
				throw new RuntimeException( 
				"Cannot rollback non-active transaction." );
			
			for (ITransactionParticipant r:itsRepositories)
				r.discardChanges();
			
			itsRepositories = null;
			itsState = State.ROLLEDBACK;
			itsContext.clearTransaction();
		}
		finally
		{
			itsContext.getSynchronizer().unlockFromWriting();
		}
	}

	/************************************************************************
	 *  
	 */
	public boolean 
	isActive()
	{
		itsContext.getSynchronizer().lockForReading();
		
		try
		{
			return itsState == State.ACTIVE;
		}
		finally
		{
			itsContext.getSynchronizer().unlockFromReading();
		}
	}

	/************************************************************************
	 * 
	 */
	public boolean isCommitted()
	{
		synchronized ( itsContext.getSynchronizer() )
		{
			return itsState == State.COMMITTED;
		}
	}

	/************************************************************************
	 * 
	 */
	public boolean isRolledback()
	{
		synchronized ( itsContext.getSynchronizer() )
		{
			return itsState == State.ROLLEDBACK;
		}
	}

	/************************************************************************
	 *  
	 *
	 * @param repository
	 */
	public void
	joinTransaction(ITransactionParticipant repository)
	{
		if ( !itsRepositories.contains( repository ) )
			itsRepositories.add( repository );
	}
}


// ##########################################################################
