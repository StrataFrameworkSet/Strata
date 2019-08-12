// ##########################################################################
// # File Name:	FailedUnitOfWorkState.java
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

package strata.domain.core.unitofwork;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class FailedUnitOfWorkState
    extends AbstractUnitOfWorkState
{
    private static final FailedUnitOfWorkState theirInstance = 
        new FailedUnitOfWorkState();
    
    /************************************************************************
     * Creates a new {@code FailedUnitOfWorkState}.
     */
    private 
    FailedUnitOfWorkState()
    {
        super( "Failed" );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public CompletionStage<Void>
    rollback(AbstractUnitOfWork context)
    {
        CompletableFuture<Void> result = new CompletableFuture<>();

        try
        {
            context.doRollback();
            context.changeState( RolledBackUnitOfWorkState.getInstance() );
            result.complete(null);
        }
        catch (Exception e)
        {
            result.completeExceptionally(new RollbackFailedException( e ));
        }

        return result;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isFailed(AbstractUnitOfWork context)
    {
        return true;
    }

    /************************************************************************
     *  
     *
     * @return
     */
    public static FailedUnitOfWorkState
    getInstance()
    {
        return theirInstance;
    }
}

// ##########################################################################
