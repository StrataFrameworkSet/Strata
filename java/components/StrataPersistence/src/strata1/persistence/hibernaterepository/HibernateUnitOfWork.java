// ##########################################################################
// # File Name:	HibernateUnitOfWork.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
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

import strata1.persistence.repository.CommitFailedException;
import strata1.persistence.repository.IUnitOfWork;
import strata1.persistence.repository.RollbackFailedException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.io.Serializable;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public class HibernateUnitOfWork
    implements IUnitOfWork
{
    private Session        itsSession;
    private Transaction    itsTransaction;
    
    /************************************************************************
     * Creates a new {@code HibernateUnitOfWork}. 
     *
     */
    public 
    HibernateUnitOfWork(SessionFactory factory)
    {
        itsSession     = factory.openSession();
        itsTransaction = itsSession.beginTransaction();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    commit() 
        throws CommitFailedException
    {
        try
        {
            itsSession.flush();
            itsTransaction.commit();
            
            if ( itsSession.isOpen() )
                itsSession.close(); 
        }
        catch (Throwable cause)
        {
            throw new CommitFailedException( cause );
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    rollback() 
        throws RollbackFailedException
    {
        try
        {
            itsTransaction.rollback();
        }
        catch (Throwable cause)
        {
            throw new RollbackFailedException( cause );
        }
        finally
        {
            if ( itsSession.isOpen() )
                itsSession.close();
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isActive()
    {
        return itsTransaction.isActive();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isCommitted()
    {
        return itsTransaction.wasCommitted();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isRolledback()
    {
        return itsTransaction.wasRolledBack();
    }
    
    /************************************************************************
     *  
     *
     * @param entity
     * @return
     */
    public <T> T
    doInsertNew(Class<T> type,T entity)
    {
        Serializable key = itsSession.save( entity );
        
        return type.cast( itsSession.get( type,key ) );
    }
    
    /************************************************************************
     *  
     *
     * @param entity
     * @return
     */
    public <T> T
    doUpdateExisting(Class<T> type,T entity)
    {
        return type.cast( itsSession.merge( entity ) );
    }
    
    /************************************************************************
     *  
     *
     * @param entity
     * @return
     */
    public <T> void
    doRemoveExisting(T entity)
    {
        itsSession.delete( itsSession.merge( entity ) );
    }
    
    /************************************************************************
     *  
     *
     * @param type
     * @param key
     * @return
     */
    public <K extends Serializable,T> T
    doGetExisting(Class<T> type,K key)
    {
        return type.cast( itsSession.get( type,key ) );
    }
    
    /************************************************************************
     *  
     *
     * @param type
     * @param key
     * @return
     */
    public <K extends Serializable,T> boolean
    doHasExisting(Class<T> type,K key)
    {
        return doGetExisting(type,key) != null;
    }
}

// ##########################################################################
