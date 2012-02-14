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

package strata1.entity.hibernaterepository;


import strata1.entity.repository.RepositoryContext;
import strata1.common.utility.ReadWriteLockSynchronizer;
import strata1.common.utility.Synchronizer;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


/**
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class HibernateRepositoryContext 
	extends 	HibernateDaoSupport
	implements 	RepositoryContext
{
	private final Synchronizer          itsSynchronizer;
	private HibernateTransactionManager itsTransactionManager;
	
	/************************************************************************
	 * Creates a new HibernateDomainObjectManagerContext. 
	 *
	 */
	public 
	HibernateRepositoryContext(
		SessionFactory              factory,
		HibernateTransactionManager manager)
	{
		super();
		createHibernateTemplate( factory );
		
		itsSynchronizer       = new ReadWriteLockSynchronizer();
		itsTransactionManager = manager;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see RepositoryContext#getSynchronizer()
	 */
	@Override
	public Synchronizer 
	getSynchronizer()
	{
		return itsSynchronizer;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see RepositoryContext#getTransactionManager()
	 */
	@Override
	public HibernateTransactionManager 
	getTransactionManager()
	{
		return itsTransactionManager;
	}
}


// ##########################################################################
