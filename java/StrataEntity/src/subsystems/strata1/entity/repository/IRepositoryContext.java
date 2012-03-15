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

package strata1.entity.repository;


import org.springframework.transaction.PlatformTransactionManager;

import strata1.common.utility.ISynchronizer;

/**
 * The base class of all repository contexts. Participates in the 
 * <a href="{@docRoot}/designpatterns/DomainObjectManager.pdf">RepositoryImp 
 * (aka Domain Object Manager)</a> design pattern.
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IRepositoryContext
{
	/************************************************************************
	 * Returns the synchronizer associated with this 
	 * <code>IRepositoryContext</code> that synchronizes access to all
	 * of the <code>RepositoryImp</code>s associated with this 
	 * <code>IRepositoryContext</code>.
	 *
	 * @return synchronization mechanism
	 */
	public ISynchronizer 
	getSynchronizer();
	
	/************************************************************************
	 * Returns the transaction manager associated with this
	 * <code>IRepositoryContext</code> that manages the transactions to all
	 * of the <code>RepositoryImp</code>s associated with this
	 * <code>IRepositoryContext</code>.
	 * 
	 * @see 	PlatformTransactionManager
	 * @return 	transaction  manager
	 */
	public PlatformTransactionManager
	getTransactionManager();	
}


// ##########################################################################
