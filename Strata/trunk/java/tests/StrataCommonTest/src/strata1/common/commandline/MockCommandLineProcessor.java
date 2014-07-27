// ##########################################################################
// # File Name:	MockCommandLineProcessor.java
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

import org.junit.Assert;
import java.nio.file.Path;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class MockCommandLineProcessor
    extends AbstractCommandLineProcessor
{
    private String itsExpected;
    private ICommandOption    itsHelpOption;
    private ICommandOption    itsFooOption;
    private ICommandOption    itsBarOption;
    private ICommandParameter itsPathParameter;
    
    /************************************************************************
     * Creates a new {@code MockCommandLineProcessor}. 
     *
     */
    public 
    MockCommandLineProcessor(String expected)
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    finishProcessing() throws CommandLineException
    {
        Assert.assertEquals( itsExpected,toString() );
    }

    /************************************************************************
     * {@inheritDoc} 
     * @throws InvalidOptionException 
     */
    @Override
    public void 
    processOption(ICommandOption option) 
        throws CommandLineException
    {
        if ( option.isNamed( "help" ) )
            itsHelpOption = option;
        else if ( option.isNamed( "foo" ) )
            itsFooOption = option;
        else if ( option.isNamed( "bar" ) )
            itsBarOption = option;
        else
            throw new InvalidOptionException(option,getHelpText());
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    processParameter(ICommandParameter parameter) throws CommandLineException
    {
        if ( !parameter.isType(Path.class) )
            throw new InvalidParameterException(parameter);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    onCommandLineException(CommandLineException exception)
    {
        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    onException(Exception exception)
    {
        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    toString()
    {
        StringBuilder builder = new StringBuilder();
        
        for (ICommandArgument argument:getArguments())
        {
            if ( !builder.toString().isEmpty() )
                builder.append( " " );
            
            builder.append( argument.toString() );            
        }
        
        return builder.toString();
    }

    
}

// ##########################################################################
