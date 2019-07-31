// ##########################################################################
// # File Name:	ExecutionManager.java
// #
// # Copyright:	2017, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataPersistence Framework.
// #
// #   			The StrataPersistence Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataPersistence Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataPersistence
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.persistence.unitofwork;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class ExecutionManager 
    implements IExecutionManager
{
    private final IUnitOfWorkProvider itsProvider;
    private final int                 itsMaxAttempts;
    
    /************************************************************************
     * Creates a new {@code ExecutionManager}. 
     *
     */
    public 
    ExecutionManager(IUnitOfWorkProvider provider,int maxAttempts)
    {
        itsProvider    = provider;
        itsMaxAttempts = maxAttempts;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> T
    execute(IExecutionListener listener,IOperationRunner<T> runner) 
        throws Exception
    {
        IUnitOfWork unitOfWork = null;
        T           output     = null;
        Exception   exception  = null;
        long        sleepTime  = 10;
        
        if ( listener == null )
            listener = new NullExecutionListener();
        
        for (int attempt=1;attempt<=itsMaxAttempts;attempt++)
        {
            listener.onAttempt( attempt );
            
            try
            {      
                unitOfWork = itsProvider.getUnitOfWork();
                exception  = null;
                output = runner.run();
                unitOfWork.commit();                
                listener.onCommitSucceeded();
                return output;
            }
            catch (Exception c)
            {
                exception = c;                
                listener.onCommitFailed( c );
                
                try 
                {
                    unitOfWork.rollback();                    
                    listener.onRollbackSucceeded();
                }
                catch (Exception r)
                {
                    listener.onRollbackFailed( r );
                }
            }
            finally
            {
                itsProvider.clearUnitOfWork();
            }
            
            Thread.sleep( sleepTime );
            sleepTime += 10;
        }
        
        if ( exception != null )
            throw exception;
        
        return output;
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public <T> T
    execute(IOperationRunner<T> runner)
        throws Exception
    {
        return execute( new NullExecutionListener(),runner );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void
    execute(IExecutionListener listener,INoOutputOperationRunner runner) 
        throws Exception
    {
        IUnitOfWork unitOfWork = null;
        Exception   exception  = null;
        long        sleepTime  = 10;
        
        if ( listener == null )
            throw new NullPointerException("listener can not be null.");

        for (int attempt=1;attempt<=itsMaxAttempts;attempt++)
        {
            listener.onAttempt( attempt );
            
            try
            {      
                unitOfWork = itsProvider.getUnitOfWork();
                exception  = null;
                runner.run();
                unitOfWork.commit();
                
                listener.onCommitSucceeded();                
                return;
            }
            catch (Exception c)
            {
                exception = c;
                listener.onCommitFailed( c );
                
                try 
                {
                    unitOfWork.rollback();
                    listener.onRollbackSucceeded();
                }
                catch (Exception r)
                {
                    listener.onRollbackFailed( r );
                }
            }
            finally
            {
                itsProvider.clearUnitOfWork();
            }
            
            Thread.sleep( sleepTime );
            sleepTime += 10;
        }
        
        if ( exception != null )
            throw exception;
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void
    execute(INoOutputOperationRunner runner)
        throws Exception
    {
        execute( new NullExecutionListener(),runner );
    }
}

// ##########################################################################
