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

package strata.persistence.unitofwork;

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
     *
     * @param name
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
    public void 
    rollback(AbstractUnitOfWork context)
        throws RollbackFailedException
    {
        try
        {
            context.doRollback();
            context.changeState( RolledBackUnitOfWorkState.getInstance() );
        }
        catch (Exception e)
        {
            throw new RollbackFailedException( e );
        }
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
