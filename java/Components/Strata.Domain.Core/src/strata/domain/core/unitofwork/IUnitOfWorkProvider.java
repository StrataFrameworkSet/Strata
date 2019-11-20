// ##########################################################################
// # File Name:	IUnitOfWorkProvider.java
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
// #			General Public License along with the StrataEntity
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.domain.core.unitofwork;

import strata.foundation.core.pool.IPoolable;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;

/****************************************************************************
 * The interface supported by all unit of work providers. 
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IUnitOfWorkProvider
    extends IPoolable<IUnitOfWorkProvider,IUnitOfWorkProviderPool>
{
	/************************************************************************
     * Returns the current unit of work associated with this
     * {@code IUnitOfWorkProvider} that manages the transactions to all
     * of the {@code IRepositoryProvider}s associated with this
     * {@code IUnitOfWorkProvider}.
     * 
     * @see     IUnitOfWork
     * @return  current unit of work
     */
    CompletionStage<IUnitOfWork>
    getUnitOfWork();  
    
    /************************************************************************
     *  
     *
     */
    CompletionStage<Void>
    clearUnitOfWork();

    /************************************************************************
     *
     */
    ExecutorService
    getExecutor();

    /************************************************************************
     *
     */
    boolean
    hasActiveUnitOfWork();

    /************************************************************************
     *
     */
    boolean
    isClosed();
}


// ##########################################################################
