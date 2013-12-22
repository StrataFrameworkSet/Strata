// ##########################################################################
// # File Name:	NullCommandLineProcessor.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataCommon Framework.
// #
// #   			The StrataCommon Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataCommon Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataCommon
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.common.commandline;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class NullCommandLineProcessor
    implements ICommandLineProcessor
{
    private List<String> itsArguments;
    
    /************************************************************************
     * Creates a new {@code NullCommandLineProcessor}. 
     *
     */
    public 
    NullCommandLineProcessor() 
    {
        itsArguments = Collections.emptyList();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setOptions(Set<String> options) {}

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Set<String> 
    getOptions()
    {
        return Collections.emptySet();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public List<String> 
    getArguments()
    {
        return itsArguments;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    process(String[] arguments) 
    {
        itsArguments = Arrays.asList( arguments );
    }

}

// ##########################################################################
