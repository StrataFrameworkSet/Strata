// ##########################################################################
// # File Name:	ExampleCommandLineProcessor.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataCommonTest Framework.
// #
// #   			The StrataCommonTest Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataCommonTest Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataCommonTest
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.foundation.commandline;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class ExampleCommandLineProcessor
    extends AbstractCommandLineProcessor
{
    @SuppressWarnings("unused")
    private ICommandOption itsHelpOption;
    private ICommandOption itsBooleanOption;
    private ICommandOption itsIntegerOption;
    private ICommandOption itsLongOption;
    private ICommandOption itsFloatOption;
    private ICommandOption itsDoubleOption;
    private ICommandOption itsDecimalOption;
    
    private ICommandParameter itsBooleanParameter;
    private ICommandParameter itsIntegerParameter;
    private ICommandParameter itsLongParameter;
    private ICommandParameter itsFloatParameter;
    private ICommandParameter itsDoubleParameter;
    private ICommandParameter itsDecimalParameter;
    
    
    /************************************************************************
     * Creates a new {@code ExampleCommandLineProcessor}. 
     *
     */
    public 
    ExampleCommandLineProcessor()
    {
        itsHelpOption = null;
        itsBooleanOption = null;
        itsIntegerOption = null;
        itsLongOption = null;
        itsFloatOption = null;
        itsDoubleOption = null;
        itsDecimalOption = null;
        
        itsBooleanParameter = null;
        itsIntegerParameter = null;
        itsLongParameter = null;
        itsFloatParameter = null;
        itsDoubleParameter = null;
        itsDecimalParameter = null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    startProcessing()
    {
        itsHelpOption = null;
        itsBooleanOption = null;
        itsIntegerOption = null;
        itsLongOption = null;
        itsFloatOption = null;
        itsDoubleOption = null;
        itsDecimalOption = null;
        
        itsBooleanParameter = null;
        itsIntegerParameter = null;
        itsLongParameter = null;
        itsFloatParameter = null;
        itsDoubleParameter = null;
        itsDecimalParameter = null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    finishProcessing() 
        throws CommandLineException
    {
        try
        {
            itsBooleanOption.getBoolean();
            itsIntegerOption.getInteger();
            itsLongOption.getLong();
            itsFloatOption.getFloat();
            itsDoubleOption.getDouble();
            itsDecimalOption.getBigDecimal();
            
            itsBooleanParameter.toBoolean();
            itsIntegerParameter.toInteger();
            itsLongParameter.toLong();
            itsFloatParameter.toFloat();
            itsDoubleParameter.toDouble();
            itsDecimalParameter.toBigDecimal();

        }
        catch (Throwable cause)
        {
            throw new CommandLineException(cause);
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    processOption(ICommandOption option)
        throws CommandLineException
    {
        if ( option.isNamed( "boolean" ))
            itsBooleanOption = option;
        else if ( option.isNamed( "integer" ))
            itsIntegerOption = option;
        else if ( option.isNamed( "long" ))
            itsLongOption = option;
        else if ( option.isNamed( "float" ))
            itsFloatOption = option;
        else if ( option.isNamed( "double" ))
            itsDoubleOption = option;
        else if ( option.isNamed( "decimal" ))
            itsDecimalOption = option;
        else
            throw 
                new CommandLineException(
                    "Invalid option: " + option.getName() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    processParameter(ICommandParameter parameter)
        throws CommandLineException
    {
        switch ( parameter.getPosition() )
        {
        case 0:
            itsBooleanParameter = parameter;
            break;
            
        case 1:
            itsIntegerParameter = parameter;
            break;
            
        case 2:
            itsLongParameter = parameter;
            break;
            
        case 3:
            itsFloatParameter = parameter;
            break;
            
        case 4:
            itsDoubleParameter = parameter;
            break;
            
        case 5:
            itsDecimalParameter = parameter;
            break;
            
        default:
            throw
                new CommandLineException(
                    "Invalid parameter: " + parameter.toString() + 
                    " at position: " + parameter.getPosition() );
           
        }
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

}

// ##########################################################################
