// ##########################################################################
// # File Name:	InvalidOptionException.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
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

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class InvalidOptionException
    extends CommandLineException
{

    private static final long serialVersionUID = -4768027356266954628L;
    
    private final ICommandOption itsOption;
    private final String         itsHelpText;

    /************************************************************************
     * Creates a new {@code InvalidOptionException}. 
     *
     * @param option
     * @param helpText
     */
    public 
    InvalidOptionException(ICommandOption option,String helpText)
    {
        super( "Invalid commandline option: " + option.getName() );
        itsOption = option;
        itsHelpText = helpText;
    }

    /************************************************************************
     *  
     *
     * @return
     */
    public ICommandOption
    getInvalidOption()
    {
        return itsOption;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public String
    getHelpText()
    {
        return itsHelpText;
    }
}

// ##########################################################################
