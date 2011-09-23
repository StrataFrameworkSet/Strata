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

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

/**
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class InMemoryTransactionManager 
	extends AbstractPlatformTransactionManager
{
	private static final long serialVersionUID = 0;
	
	protected InMemoryRepositoryContext itsContext;
	
	/************************************************************************
	 * Creates a new InMemoryTransactionManager. 
	 *
	 */
	public InMemoryTransactionManager(InMemoryRepositoryContext context)
	{
		super();
		itsContext = context;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see AbstractPlatformTransactionManager#doGetTransaction()
	 */
	@Override
	protected Object 
	doGetTransaction() 
		throws TransactionException
	{
		return itsContext.getTransaction();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see AbstractPlatformTransactionManager#doBegin(
	 * 			Object,TransactionDefinition)
	 */
	@Override
	protected void 
	doBegin(Object transaction,TransactionDefinition definition)
		throws TransactionException
	{
		InMemoryTransaction t = (InMemoryTransaction)transaction;
		
		t.begin();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see AbstractPlatformTransactionManager#doCommit(
	 * 			DefaultTransactionStatus)
	 */
	@Override
	protected void 
	doCommit(DefaultTransactionStatus status)
		throws TransactionException
	{
		itsContext.getTransaction().commit();
		itsContext.clearTransaction();
		status.setCompleted();
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see AbstractPlatformTransactionManager#doRollback(
	 * 			DefaultTransactionStatus)
	 */
	@Override
	protected void 
	doRollback(DefaultTransactionStatus status)
		throws TransactionException
	{
		itsContext.getTransaction().commit();
		itsContext.clearTransaction();
		status.setCompleted();
	}

	
}


// ##########################################################################
