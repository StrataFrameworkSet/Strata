// ##########################################################################
// # File Name:	HibernateRepositoryProvider.java
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

import strata1.persistence.repository.AbstractRepositoryProvider;
import strata1.persistence.repository.IFinder;
import strata1.persistence.repository.InsertFailedException;
import strata1.persistence.repository.RemoveFailedException;
import strata1.persistence.repository.UpdateFailedException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.io.Serializable;

/****************************************************************************
 * 
 * @param <K>
 * @param <T>

 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class HibernateRepositoryProvider<K extends Serializable,T> 
	extends AbstractRepositoryProvider<K,T>
{
	private Class<T>				   itsEntityClass;
	private HibernateRepositoryContext itsContext;
	
	/************************************************************************
	 * Creates a new HibernateRepositoryImp. 
	 *
	 */
	public 
	HibernateRepositoryProvider(
		Class<T> 				   entityClass,
		HibernateRepositoryContext context)
	{
		super();
		itsEntityClass = entityClass;
		itsContext     = context;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see RepositoryImp#getContext()
	 */
	@Override
	public HibernateRepositoryContext 
	getContext()
	{
		return itsContext;
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @return 
	 * @see AbstractRepositoryProvider#doInsertNew(Object)
	 */
	@Override
	protected T 
	doInsertNew(T entity)
	    throws InsertFailedException
	{
		try
		{
		    return  
		        itsContext
		            .getUnitOfWork()
		            .doInsertNew(itsEntityClass,entity);
		}
		catch (Throwable cause)
		{
		    throw new InsertFailedException(cause);
		}	
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @return 
	 * @see AbstractRepositoryProvider#doUpdateExisting(Object)
	 */
	@Override
	protected T 
	doUpdateExisting(T entity)
	    throws UpdateFailedException
	{
        try
        {
            return  
                itsContext
                    .getUnitOfWork()
                    .doUpdateExisting(itsEntityClass,entity);
        }
        catch (Throwable cause)
        {
            throw new UpdateFailedException(cause);
        }
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see AbstractRepositoryProvider#doRemoveExisting(Object)
	 */
	@Override
	protected void 
	doRemoveExisting(T entity)
	    throws RemoveFailedException
	{
        try
        {
            itsContext
                .getUnitOfWork()
                .doRemoveExisting(entity);
        }
        catch (Throwable cause)
        {
            throw new RemoveFailedException(cause);
        }
	}

	/************************************************************************
	 * {@inheritDoc} 
	 * @see AbstractRepositoryProvider#doGetExisting(Serializable)
	 */
	@Override
	protected T 
	doGetExisting(K key)
	{
	    return
	        itsContext
	            .getUnitOfWork()
	            .doGetExisting( itsEntityClass,key );
	}

	/************************************************************************
	 * {@inheritDoc} 
	 */
	@Override
    protected IFinder<T> 
	doGetFinder(String finderName)
    {
	    if ( doHasFinder(finderName))
	        return 
	            new HibernateFinder<T>(finderName,itsEntityClass,itsContext);
	    
        return null; 
    }

    /************************************************************************
	 * {@inheritDoc} 
	 * @see AbstractRepositoryProvider#doHasExisting(Serializable)
	 */
	@Override
	protected boolean 
	doHasExisting(K key)
	{
	    return
	        itsContext
	            .getUnitOfWork()
	            .doHasExisting( itsEntityClass,key );
	}

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected boolean 
    doHasFinder(String finderName)
    {   
        SessionFactory factory = itsContext.getSessionFactory();
        Session        session = factory.openSession();
        
        return session.getNamedQuery(finderName) != null;
    }

}


// ##########################################################################
