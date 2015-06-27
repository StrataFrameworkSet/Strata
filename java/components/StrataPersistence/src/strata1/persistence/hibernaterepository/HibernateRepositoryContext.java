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

package strata1.persistence.hibernaterepository;


import strata1.common.utility.ISynchronizer;
import strata1.common.utility.ReadWriteLockSynchronizer;
import strata1.persistence.repository.IRepositoryContext;
import org.hibernate.SessionFactory;


/**
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class HibernateRepositoryContext 
	implements 	IRepositoryContext
{
	private final ISynchronizer       itsSynchronizer;
	private       SessionFactory      itsSessionFactory;
	private       HibernateUnitOfWork itsUnitOfWork;
	
	/************************************************************************
	 * Creates a new HibernateDomainObjectManagerContext. 
	 *
	 */
	public 
	HibernateRepositoryContext(SessionFactory factory)
	{
		super();
		itsSynchronizer   = new ReadWriteLockSynchronizer();
		itsSessionFactory = factory;
		itsUnitOfWork     = new HibernateUnitOfWork(itsSessionFactory);
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see IRepositoryContext#getSynchronizer()
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
    public HibernateUnitOfWork 
    getUnitOfWork()
    {
        if ( itsUnitOfWork == null || !itsUnitOfWork.isActive() )
            itsUnitOfWork = new HibernateUnitOfWork(itsSessionFactory);
        
        return itsUnitOfWork;
    }

	/************************************************************************
	 *  
	 *
	 * @return
	 */
	public SessionFactory
	getSessionFactory()
	{
	    return itsSessionFactory;
	}
}


// ##########################################################################
