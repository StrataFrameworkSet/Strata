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

package strata.persistence.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.io.Serializable;
import strata.persistence.namedquery.INamedQuery;
import strata.persistence.unitofwork.AbstractUnitOfWork;
import strata.persistence.unitofwork.CommitFailedException;
import strata.persistence.unitofwork.IUnitOfWork;
import strata.persistence.unitofwork.IUnitOfWorkProvider;
import strata.persistence.unitofwork.RollbackFailedException;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class HibernateUnitOfWork
    extends    AbstractUnitOfWork
    implements IUnitOfWork
{
    private Session        itsSession;
    private Transaction    itsTransaction;
    
    /************************************************************************
     * Creates a new {@code HibernateUnitOfWork}. 
     *
     */
    public 
    HibernateUnitOfWork(IUnitOfWorkProvider provider,SessionFactory factory)
    {
        super( provider );
        itsSession     = factory.openSession();
        itsTransaction = itsSession.beginTransaction();
    }
   
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    close() 
        throws Exception
    {
        if ( itsSession != null && itsSession.isOpen() )
        {
            itsSession.close();
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected <K extends Serializable,E> E 
    doInsertNew(Class<K> keyType,Class<E> entityType,E newEntity)
    {
        Serializable key = itsSession.save( newEntity );
        
        return entityType.cast( itsSession.get( entityType,key ) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected <K extends Serializable,E> E 
    doUpdateExisting(Class<K> keyType,Class<E> entityType,E existingEntity)
    {
        return entityType.cast( itsSession.merge( existingEntity ) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected <K extends Serializable,E> void 
    doRemoveExisting(Class<K> keyType,Class<E> entityType,E existingEntity)
    {
        itsSession.delete( itsSession.merge( existingEntity ) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected <K extends Serializable,E> E 
    doGetExisting(Class<E> type,K key)
    {
        return type.cast( itsSession.get( type,key ) );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected <E> INamedQuery<E> 
    doGetNamedQuery(Class<E> type,String queryName)
    {
        if ( doHasNamedQuery(type,queryName))
            return 
                new HibernateNamedQuery<E>(
                    queryName,
                    type,
                    (HibernateUnitOfWorkProvider)getProvider());
        
        return null; 
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected <K extends Serializable,E> boolean 
    doHasExisting(Class<E> type,K key)
    {
        return doGetExisting(type,key) != null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected <E> boolean 
    doHasNamedQuery(Class<E> type,String queryName)
    {
        SessionFactory factory = null;
        Session        session = null;
        
        factory = ((HibernateUnitOfWorkProvider)getProvider()).getSessionFactory();
        session = factory.openSession();
        
        return 
            session
                .getNamedQuery(
                    getQualifiedQueryName(type,queryName)) != null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected void 
    doCommit() 
        throws CommitFailedException
    {
        try
        {
            itsSession.flush();
            itsTransaction.commit();
            
            if ( itsSession.isOpen() )
                itsSession.close(); 
        }
        catch (Exception cause)
        {
            throw new CommitFailedException( cause );
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected void 
    doRollback() 
        throws RollbackFailedException
    {
        try
        {
            itsTransaction.rollback();
        }
        catch (Exception cause)
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
     *  
     *
     * @param type
     * @param queryName
     * @return
     */
    protected <E> String
    getQualifiedQueryName(Class<E> type,String queryName)
    {
        return type.getName() + "." + queryName;
    }
}

// ##########################################################################
