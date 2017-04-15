// ##########################################################################
// # File Name:	CommandOption.java
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

package strata.foundation.commandline;


import java.math.BigDecimal;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class CommandOption
    extends    CommandArgument
    implements ICommandOption
{
    private final String itsName;
    private final String itsValue;
    
    /************************************************************************
     * Creates a new {@code CommandOption}. 
     *
     * @param token
     * @param name
     * @param value
     */
    public 
    CommandOption(int position,String input,String name,String value)
    {
        super( position,input );
        itsName     = name;
        itsValue    = value;
    }
    
    /************************************************************************
     * Creates a new {@code CommandOption}. 
     *
     * @param input
     * @param name
     */
    public 
    CommandOption(int position,String input,String name)
    {
        this( position,input,name,null );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getName()
    {
        return itsName;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getValue()
    {
        return itsValue;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    getBoolean() 
        throws NoValueProvidedException,ConversionException
    {
        if ( itsValue == null )
            throw new NoValueProvidedException(this);
        
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
    getInteger() 
        throws NoValueProvidedException,ConversionException
    {
        if ( itsValue == null )
            throw new NoValueProvidedException(this);
        
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
    getLong() 
        throws NoValueProvidedException,ConversionException
    {
        if ( itsValue == null )
            throw new NoValueProvidedException(this);
        
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
    getFloat() 
        throws NoValueProvidedException,ConversionException
    {
        if ( itsValue == null )
            throw new NoValueProvidedException(this);
        
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
    public Double 
    getDouble() 
        throws NoValueProvidedException,ConversionException
    {
        if ( itsValue == null )
            throw new NoValueProvidedException(this);
        
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
    getBigDecimal() 
        throws NoValueProvidedException,ConversionException
    {
        if ( itsValue == null )
            throw new NoValueProvidedException(this);
        
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
    getString() 
        throws NoValueProvidedException
    {
        if ( itsValue == null )
            throw new NoValueProvidedException(this);

        return itsValue;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isNamed(String name)
    {
        return itsName.equals( name );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    hasValue()
    {
        return itsValue != null;
    }

}

// ##########################################################################
