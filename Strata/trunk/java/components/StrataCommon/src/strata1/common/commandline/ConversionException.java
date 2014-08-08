// ##########################################################################
// # File Name:	ConversionException.java
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
class ConversionException
    extends CommandLineException
{

    private static final long serialVersionUID = 6124365607768340728L;
    
    private final ICommandArgument itsArgument;

    /************************************************************************
     * Creates a new {@code ConversionException}. 
     *
     * @param argument
     * @param cause
     */
    public 
    ConversionException(ICommandArgument argument,Throwable cause)
    {
        super( 
            "Can not convert value: " + 
            (argument instanceof ICommandOption
                ? ((ICommandOption)argument).getValue()
                : argument.getInput()),
            cause);
        itsArgument = argument;
    }

    /************************************************************************
     *  
     *
     * @return
     */
    public ICommandArgument
    getArgument()
    {
        return itsArgument;
    }
}

// ##########################################################################
