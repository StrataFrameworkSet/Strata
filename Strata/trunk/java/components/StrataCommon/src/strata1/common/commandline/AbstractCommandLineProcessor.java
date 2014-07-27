// ##########################################################################
// # File Name:	AbstractCommandLineProcessor.java
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class AbstractCommandLineProcessor
    implements ICommandLineProcessor
{
    private String                         itsHelpText;
    private List<ICommandArgument>         itsArguments;
    private Map<String,ICommandOption>     itsOptions;
    private Map<Integer,ICommandParameter> itsParameters;
    
    /************************************************************************
     * Creates a new {@code AbstractCommandLineProcessor}. 
     *
     */
    public 
    AbstractCommandLineProcessor()
    {
        itsHelpText        = "";
        itsArguments       = new ArrayList<ICommandArgument>();
        itsOptions         = new TreeMap<String,ICommandOption>();
        itsParameters      = new TreeMap<Integer,ICommandParameter>();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ICommandLineProcessor 
    setHelpText(String helpText)
    {
        itsHelpText = helpText;
        return null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getHelpText()
    {
        return itsHelpText;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    startProcessing()
    {
        itsArguments.clear();
        itsOptions.clear();
        itsParameters.clear();
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    protected List<ICommandArgument>
    getArguments()
    {
        return itsArguments;
    }
}

// ##########################################################################
