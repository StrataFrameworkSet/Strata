// ##########################################################################
// # File Name:	ICommandOption.java
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
interface ICommandOption
    extends ICommandArgument
{
    /************************************************************************
     *  
     *
     * @return
     */
    public String
    getName();
    
    /************************************************************************
     *  
     *
     * @return
     */
    public String
    getValue();
    
    /************************************************************************
     *  
     *
     * @return
     * @throws NoValueProvidedException
     * @throws ConversionException
     */
    public boolean
    getBoolean() 
        throws NoValueProvidedException,ConversionException;
    
    /************************************************************************
     *  
     *
     * @return
     * @throws NoValueProvidedException
     * @throws ConversionException
     */
    public int
    getInteger() 
        throws NoValueProvidedException,ConversionException;
    
    /************************************************************************
     *  
     *
     * @return
     * @throws NoValueProvidedException
     * @throws ConversionException
     */
    public long
    getLong() 
        throws NoValueProvidedException,ConversionException;
    
    /************************************************************************
     *  
     *
     * @return
     * @throws NoValueProvidedException
     * @throws ConversionException
     */
    public float
    getFloat() 
        throws NoValueProvidedException,ConversionException;
    
    /************************************************************************
     *  
     *
     * @return
     * @throws NoValueProvidedException
     * @throws ConversionException
     */
    public Double
    getDouble() 
        throws NoValueProvidedException,ConversionException;
    
    /************************************************************************
     *  
     *
     * @return
     * @throws NoValueProvidedException
     * @throws ConversionException
     */
    public BigDecimal
    getBigDecimal() 
        throws NoValueProvidedException,ConversionException;
    
    /************************************************************************
     *  
     *
     * @return
     * @throws NoValueProvidedException
     */
    public String
    getString() 
        throws NoValueProvidedException;
    
    /************************************************************************
     *  
     *
     * @param name
     * @return
     */
    public boolean
    isNamed(String name);
   
    /************************************************************************
     *  
     *
     * @return
     */
    public boolean
    hasValue();
}

// ##########################################################################
