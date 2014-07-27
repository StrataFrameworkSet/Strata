// ##########################################################################
// # File Name:	ICommandLineProcessor.java
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


/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface ICommandLineProcessor
{
    public ICommandLineProcessor
    setHelpText(String helpText);
    
    public String
    getHelpText();
    
    public void
    startProcessing();
    
    public void
    finishProcessing() 
        throws CommandLineException;
    
    public void
    processOption(ICommandOption option) 
        throws CommandLineException;
    
    public void
    processParameter(ICommandParameter parameter) 
        throws CommandLineException;
    
    public void
    onCommandLineException(CommandLineException exception);
    
    public void
    onException(Exception exception);
}

// ##########################################################################
