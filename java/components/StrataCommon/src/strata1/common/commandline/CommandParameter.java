// ##########################################################################
// # File Name:	CommandParameter.java
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

import strata1.common.datetime.DateTime;
import strata1.common.money.Money;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Currency;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class CommandParameter
    extends    CommandArgument
    implements ICommandParameter
{
    private final String itsValue;

    /************************************************************************
     * Creates a new {@code CommandParameter}. 
     *
     * @param i
     * @param input
     * @param value
     */
    public 
    CommandParameter(
        int    position,
        String input,
        String value)
    {
        super( position,input );
        itsValue = value;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    toBoolean() 
        throws ConversionException
    {
        try
        {
            return new Boolean(itsValue);
        }
        catch (Throwable cause)
        {
            throw new ConversionException(this,cause);
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    toInteger() 
        throws ConversionException
    {
        try
        {
            return new Integer(itsValue);
        }
        catch (Throwable cause)
        {
            throw new ConversionException(this,cause);
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public long 
    toLong() 
        throws ConversionException
    {
        try
        {
            return new Long(itsValue);
        }
        catch (Throwable cause)
        {
            throw new ConversionException(this,cause);
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public float 
    toFloat() 
        throws ConversionException
    {
        try
        {
            return new Float(itsValue);
        }
        catch (Throwable cause)
        {
            throw new ConversionException(this,cause);
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public double 
    toDouble() 
        throws ConversionException
    {
        try
        {
            return new Double(itsValue);
        }
        catch (Throwable cause)
        {
            throw new ConversionException(this,cause);
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public BigDecimal 
    toBigDecimal() 
        throws ConversionException
    {
        try
        {
            return new BigDecimal(itsValue);
        }
        catch (Throwable cause)
        {
            throw new ConversionException(this,cause);
        }
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    toString()
    {
        return itsValue;
    }
}

// ##########################################################################
