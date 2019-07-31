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

package strata.foundation.commandline;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;

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
    private String                 itsExpected;
    private List<ICommandArgument> itsArguments;
    
    /************************************************************************
     * Creates a new {@code MockCommandLineProcessor}. 
     *
     */
    public 
    MockCommandLineProcessor(String expected)
    {
        itsExpected = expected;
        itsArguments = new ArrayList<ICommandArgument>();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    startProcessing()
    {
        itsArguments.clear();        
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    finishProcessing() 
        throws CommandLineException
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
        itsArguments.add( option );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    processParameter(ICommandParameter parameter) 
        throws CommandLineException
    {
        itsArguments.add( parameter );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    processException(CommandLineException exception) 
        throws CommandLineException
    {
        throw exception;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    processException(Exception exception) 
        throws Exception
    {
        throw exception;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    toString()
    {
        StringBuilder builder = new StringBuilder();
        
        for (ICommandArgument argument:itsArguments)
        {
            if ( !builder.toString().isEmpty() )
                builder.append( " " );
            
            builder.append( argument.getInput() );
        }
        
        return builder.toString();
    }

    
}

// ##########################################################################
