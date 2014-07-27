// ##########################################################################
// # File Name:	ICommandParameter.java
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
import java.nio.file.Path;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface ICommandParameter
    extends ICommandArgument
{
    public boolean
    toBoolean();
    
    public int
    toInteger();
    
    public long
    toLong();
    
    public float
    toFloat();
    
    public double
    toDouble();
    
    public BigDecimal
    toBigDecimal();
    
    public String
    toString();
    
    public Path
    toPath();
    
    public Money
    toMoney();
    
    public DateTime
    toDateTime();
    
    public <T> boolean
    isType(Class<T> type);
}

// ##########################################################################
