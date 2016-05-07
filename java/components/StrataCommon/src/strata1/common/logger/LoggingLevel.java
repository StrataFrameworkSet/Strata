// ##########################################################################
// # File Name:	LoggingLevel.java
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

package strata1.common.logger;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
enum LoggingLevel
{
    DEBUG(0),
    VERBOSE(1),
    INFO(2),
    WARNING(3),
    ERROR(4),
    START(5),
    STOP(5);
    
    private final int itsValue;
    
    private 
    LoggingLevel(int value)
    {
        itsValue = value;
    }
    
    public boolean 
    isEqual(LoggingLevel other)
    {
        return itsValue == other.itsValue;
    }

    public boolean 
    isLess(LoggingLevel other)
    {
        return itsValue < other.itsValue;
    }

    public boolean 
    isLessOrEqual(LoggingLevel other)
    {
        return itsValue <= other.itsValue;
    }
    
    public boolean 
    isGreater(LoggingLevel other)
    {
        return itsValue > other.itsValue;
    }
       
    public boolean 
    isGreaterOrEqual(LoggingLevel other)
    {
        return itsValue >= other.itsValue;
    }

}

// ##########################################################################
