// ##########################################################################
// # File Name: IExecutionManager.java
// #
// # Copyright: 2017, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:   This file is part of the StrataPersistence Framework.
// #
// #            The StrataPersistence Framework is free software: you 
// #            can redistribute it and/or modify it under the terms of 
// #            the GNU Lesser General Public License as published by
// #            the Free Software Foundation, either version 3 of the 
// #            License, or (at your option) any later version.
// #
// #            The StrataPersistence Framework is distributed in the 
// #            hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #            without even the implied warranty of MERCHANTABILITY or 
// #            FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #            General Public License for more details.
// #
// #            You should have received a copy of the GNU Lesser 
// #            General Public License along with the StrataPersistence
// #            Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.domain.core.unitofwork;

/****************************************************************************
 * 
 * @author      
 *     Sapientia Systems
 * @conventions 
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface IExecutionManager
{

    /************************************************************************
     *  
     *
     * @param  listener
     * @param  runner
     * @return 
     * @throws Exception
     */
    <T> T 
    execute(
        IExecutionListener listener,
        IOperationRunner<T> runner) throws Exception;

    /************************************************************************
     *  
     *
     * @param  runner
     * @return 
     * @throws Exception
     */
    <T> T 
    execute(IOperationRunner<T> runner) throws Exception;

    /************************************************************************
     *  
     *
     * @param  listener
     * @param  runner
     * @return 
     * @throws Exception
     */
    void 
    execute(
        IExecutionListener listener,
        INoOutputOperationRunner runner) throws Exception;

    /************************************************************************
     *  
     *
     * @param  runner
     * @return 
     * @throws Exception
     */
    void 
    execute(INoOutputOperationRunner runner) throws Exception;

}
// ##########################################################################
