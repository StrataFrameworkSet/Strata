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

package strata.domain.hibernate.unitofwork;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import strata.domain.core.namedquery.INamedQuery;
import strata.domain.core.unitofwork.*;
import strata.domain.hibernate.namedquery.HibernateNamedQuery;

import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;


/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class HibernateUnitOfWork
    extends AbstractUnitOfWork
    implements IUnitOfWork
{
    private Session         itsSession;
    private Transaction     itsTransaction;
    private ExecutorService itsExecutor;
    
    /************************************************************************
     * Creates a new {@code HibernateUnitOfWork}. 
     *
     */
    public 
    HibernateUnitOfWork(
        IUnitOfWorkProvider provider,
        SessionFactory      factory,
        ExecutorService     executor)
    {
        super( provider );
        itsSession     = factory.openSession();
        itsTransaction = itsSession.beginTransaction();
        itsExecutor    = executor;
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
    protected <K extends Serializable,E> CompletionStage<E>
    doInsertNew(Class<K> keyType,Class<E> entityType,E newEntity)
    {
        return
            CompletableFuture.supplyAsync(
                () ->
                {
                    Serializable key = itsSession.save(newEntity);

                    return entityType.cast(itsSession.get(entityType,key));
                },
                itsExecutor);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected <K extends Serializable,E> CompletionStage<E>
    doUpdateExisting(Class<K> keyType,Class<E> entityType,E existingEntity)
    {
        return
            CompletableFuture.supplyAsync(
                () -> entityType.cast(itsSession.merge(existingEntity)),
                itsExecutor);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected <K extends Serializable,E> CompletionStage<Void>
    doRemoveExisting(Class<K> keyType,Class<E> entityType,E existingEntity)
    {
        return
            CompletableFuture.supplyAsync(
                () ->
                {
                    itsSession.delete(itsSession.merge(existingEntity));
                    return null;
                },
                itsExecutor);
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    protected <K extends Serializable,E> CompletionStage<Optional<E>>
    doGetExisting(Class<E> type,K key)
    {
        return
            CompletableFuture.supplyAsync(
                () -> doGetExistingInternal(type,key),
                itsExecutor);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    protected <K extends Serializable,E> Optional<E>
    doGetExistingInternal(Class<E> type,K key)
    {
        if (key == null)
            return Optional.empty();

        return
            Optional.ofNullable(
                type.cast(itsSession.get(type,key)));
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected <E> CompletionStage<Optional<INamedQuery<E>>>
    doGetNamedQuery(Class<E> type,String queryName)
    {
        return
            CompletableFuture.supplyAsync(
                () ->
                {
                    if (doHasNamedQueryInternal(type,queryName))
                        return
                            Optional.ofNullable(
                                new HibernateNamedQuery<E>(
                                    queryName,
                                    type,
                                    (HibernateUnitOfWorkProvider)getProvider()));

                    return Optional.empty();
                },
                itsExecutor);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected <K extends Serializable,E> CompletionStage<Boolean>
    doHasExisting(Class<E> type,K key)
    {
        return
            CompletableFuture.supplyAsync(
                () -> doGetExistingInternal(type,key).isPresent(),
                itsExecutor);
    }

    /************************************************************************
     * {@inheritDoc}
     */
    @Override
    protected <E> CompletionStage<Boolean>
    doHasNamedQuery(Class<E> type,String queryName)
    {
        return
            CompletableFuture.supplyAsync(
                () -> doHasNamedQueryInternal(type,queryName),
                itsExecutor);
    }

    /************************************************************************
     * {@inheritDoc}
     */
    protected <E> boolean
    doHasNamedQueryInternal(Class<E> type,String queryName)
    {
        SessionFactory factory = null;
        Session session = null;

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
    protected CompletionStage<Void>
    doCommit() 
    {
        return
            CompletableFuture.supplyAsync(
                () ->
                {
                    try
                    {
                        itsSession.flush();
                        itsTransaction.commit();

                        if (itsSession.isOpen())
                            itsSession.close();

                        return null;
                    }
                    catch (Exception cause)
                    {
                        throw
                            new CompletionException(
                                new CommitFailedException(cause));
                    }
                },
                itsExecutor);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    protected CompletionStage<Void>
    doRollback() 
    {
        return
            CompletableFuture.supplyAsync(
                () ->
                {
                    try
                    {
                        itsTransaction.rollback();
                        return null;
                    }
                    catch (Exception cause)
                    {
                        throw
                            new CompletionException(
                                new RollbackFailedException(cause));
                    }
                    finally
                    {
                        if ( itsSession.isOpen() )
                            itsSession.close();
                    }
                },
                itsExecutor);
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
