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

package strata.domain.hibernate.unitofwork;

import org.hibernate.SessionFactory;
import strata.domain.core.unitofwork.AbstractUnitOfWorkProvider;
import strata.domain.core.unitofwork.IUnitOfWork;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/****************************************************************************
 *
 */
public 
class HibernateUnitOfWorkProvider
	extends    AbstractUnitOfWorkProvider
	implements IUnitOfWorkProvider
{
	private SessionFactory      itsSessionFactory;
	private ExecutorService     itsExecutor;
	private HibernateUnitOfWork itsUnitOfWork;

	/************************************************************************
	 * Creates a new HibernateDomainObjectManagerContext. 
	 *
	 */
	public 
	HibernateUnitOfWorkProvider(SessionFactory factory)
	{
		super();
		itsSessionFactory = factory;
		itsExecutor       = Executors.newSingleThreadExecutor();
		itsUnitOfWork =
			new HibernateUnitOfWork(
				this,
				itsSessionFactory,
				itsExecutor);
	}

	/************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<IUnitOfWork>
    getUnitOfWork()
    {
    	return
			CompletableFuture.supplyAsync(
				() ->
				{
					if (itsUnitOfWork == null || !itsUnitOfWork.isActive())
						itsUnitOfWork =
							new HibernateUnitOfWork(
								this,
								itsSessionFactory,
								itsExecutor);

					return itsUnitOfWork;
				},
				itsExecutor);
    }

	/************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<Void>
    clearUnitOfWork()
    {
        return
			CompletableFuture.supplyAsync(
				() ->
				{
					//itsUnitOfWork.close();
					itsUnitOfWork = null;
					return null;
				},
				itsExecutor);
    }

	/************************************************************************
	 * {@inheritDoc}
	 */
	@Override
	public ExecutorService
	getExecutor()
	{
		return itsExecutor;
	}

	/************************************************************************
	 * {@inheritDoc}
	 */
	@Override
	public boolean
	hasActiveUnitOfWork()
	{
		return itsUnitOfWork != null && itsUnitOfWork.isActive();
	}

	/************************************************************************
	 * {@inheritDoc}
	 */
	@Override
	public boolean
	isClosed()
	{
		return false;
	}

	/************************************************************************
	 * {@inheritDoc}
	 */
	@Override
	public void
	close() {}

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
