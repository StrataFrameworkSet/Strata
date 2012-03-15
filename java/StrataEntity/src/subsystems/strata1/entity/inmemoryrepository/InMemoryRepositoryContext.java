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

package strata1.entity.inmemoryrepository;

import strata1.entity.repository.IRepositoryContext;
import strata1.common.utility.ReadWriteLockSynchronizer;
import strata1.common.utility.ISynchronizer;
import org.springframework.transaction.PlatformTransactionManager;


/**
 * Contains the shared lock and in-memory transactions used by related
 * <code>PersonManager</code>s and <code>OrganizationManager</code>s.
 *  
 * @author 		
 *     Java Persistence Strategy Workgroup 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class InMemoryRepositoryContext 
	implements IRepositoryContext
{
	private final ISynchronizer        itsSynchronizer;
	private       InMemoryTransaction itsTransaction;
	
	/************************************************************************
	 * Creates a new InMemoryRepositoryContext. 
	 *
	 */
	public 
	InMemoryRepositoryContext()
	{
		super();
		itsSynchronizer = new ReadWriteLockSynchronizer();
		itsTransaction = null;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
    @Override
	public ISynchronizer 
	getSynchronizer()
	{
		return itsSynchronizer;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
	public PlatformTransactionManager 
	getTransactionManager()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/************************************************************************
	 *  
	 */
	public boolean 
	hasActiveTransaction()
	{
		itsSynchronizer.lockForReading();
		
		try
		{
			return (itsTransaction != null) && itsTransaction.isActive();
		}
		finally
		{
			itsSynchronizer.unlockFromReading();
		}
	}

	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public InMemoryTransaction 
	getTransaction()
	{
		itsSynchronizer.lockForReading();
		
		try
		{
			if ( itsTransaction == null )
				itsTransaction = new InMemoryTransaction( this );
			
			return itsTransaction;
		}
		finally
		{
			itsSynchronizer.unlockFromReading();
		}
	}
	
 	/************************************************************************
 	 *  
 	 *
 	 */
 	void clearTransaction()
	{
		itsSynchronizer.lockForWriting();
		
		try
		{
			if ( (itsTransaction != null) && !itsTransaction.isActive() )
				itsTransaction = null;
		}
		finally
		{
			itsSynchronizer.unlockFromWriting();
		}
	}
}


// ##########################################################################
