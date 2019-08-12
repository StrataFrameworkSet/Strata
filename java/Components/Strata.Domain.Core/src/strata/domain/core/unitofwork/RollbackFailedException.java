// ##########################################################################
// # File Name:	RollbackFailedException.java
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

package strata.domain.core.unitofwork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class RollbackFailedException
    extends UnitOfWorkException
{

    private static final long serialVersionUID = 8377279956740593279L;
    private final List<Exception> causes;

    /************************************************************************
     * Creates a new {@code RollbackFailedException}. 
     *
     */
    public 
    RollbackFailedException()
    {
        causes = new ArrayList<Exception>();
    }

    /************************************************************************
     * Creates a new {@code RollbackFailedException}. 
     *
     * @param message
     */
    public 
    RollbackFailedException(String message)
    {
        super( message );
        causes = new ArrayList<Exception>();
    }

    /************************************************************************
     * Creates a new {@code RollbackFailedException}. 
     *
     * @param cause
     */
    public 
    RollbackFailedException(Throwable cause)
    {
        super( cause );
        causes = new ArrayList<Exception>();
    }

    /************************************************************************
     * Creates a new {@code RollbackFailedException}. 
     *
     * @param message
     * @param cause
     */
    public 
    RollbackFailedException(String message,Throwable cause)
    {
        super( message,cause );
        causes = new ArrayList<Exception>();
    }

    /*************************************************************************
     *
     * @param cause
     * @return
     */
    public RollbackFailedException
    addCause(Exception cause)
    {
        causes.add(cause);
        return this;
    }

    /*************************************************************************
     *
     * @return
     */
    public List<Exception>
    getCauses()
    {
        return Collections.unmodifiableList(causes);
    }
}

// ##########################################################################
