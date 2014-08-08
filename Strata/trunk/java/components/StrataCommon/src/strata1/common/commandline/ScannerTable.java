// ##########################################################################
// # File Name:	ScannerTable.java
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
class ScannerTable
{
    private final ScannerEntry[][] itsEntries;
    
    ScannerTable()
    {
        itsEntries = 
            new ScannerEntry[][]
            {
                /////////////////////////////////////////////////////////////
                // State: 0
                {
                    // Input Type: dash
                    new ScannerEntry(
                        1,
                        ScannerAction.MOVE_NO_APPEND,
                        TokenKind.INCOMPLETE_TOKEN),
                        
                    // Input Type: alpha|num|sym
                    new ScannerEntry(
                        10,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.PARAMETER_VALUE),
                        
                    // Input Type: equal
                    new ScannerEntry(
                        5,
                        ScannerAction.MOVE_NO_APPEND,
                        TokenKind.INCOMPLETE_TOKEN),
                        
                    // Input Type: quote
                    new ScannerEntry(
                        12,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.INCOMPLETE_TOKEN),
                        
                    // Input Type: whitespace
                    new ScannerEntry(
                        0,
                        ScannerAction.THROW_EXCEPTION,
                        TokenKind.INVALID_INPUT) 
                },
                /////////////////////////////////////////////////////////////
                // State: 1
                {
                    // Input Type: dash
                    new ScannerEntry(
                        2,
                        ScannerAction.MOVE_NO_APPEND,
                        TokenKind.INCOMPLETE_TOKEN),
                        
                    // Input Type: alpha|num|sym
                    new ScannerEntry(
                        3,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.OPTION_ID),
                        
                    // Input Type: equal
                    new ScannerEntry(
                        0,
                        ScannerAction.HALT_REUSE,
                        TokenKind.OPTION_ID),
                        
                    // Input Type: quote
                    new ScannerEntry(
                        0,
                        ScannerAction.HALT_REUSE,
                        TokenKind.OPTION_ID),
                        
                    // Input Type: whitespace
                    new ScannerEntry(
                        0,
                        ScannerAction.HALT_REUSE,
                        TokenKind.OPTION_ID) 
                },
                /////////////////////////////////////////////////////////////
                // State: 2
                {
                    // Input Type: dash
                    new ScannerEntry(
                        0,
                        ScannerAction.HALT_REUSE,
                        TokenKind.OPTION_ID),
                        
                    // Input Type: alpha|num|sym
                    new ScannerEntry(
                        3,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.OPTION_ID),
                        
                    // Input Type: equal
                    new ScannerEntry(
                        0,
                        ScannerAction.HALT_REUSE,
                        TokenKind.OPTION_ID),
                        
                    // Input Type: quote
                    new ScannerEntry(
                        0,
                        ScannerAction.HALT_REUSE,
                        TokenKind.OPTION_ID),
                        
                    // Input Type: whitespace
                    new ScannerEntry(
                        0,
                        ScannerAction.HALT_REUSE,
                        TokenKind.OPTION_ID) 
                },
                /////////////////////////////////////////////////////////////
                // State: 3
                {
                    // Input Type: dash
                    new ScannerEntry(
                        4,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.OPTION_ID),
                        
                    // Input Type: alpha|num|sym
                    new ScannerEntry(
                        4,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.OPTION_ID),
                        
                    // Input Type: equal
                    new ScannerEntry(
                        0,
                        ScannerAction.HALT_REUSE,
                        TokenKind.OPTION_ID),
                        
                    // Input Type: quote
                    new ScannerEntry(
                        0,
                        ScannerAction.HALT_REUSE,
                        TokenKind.OPTION_ID),
                        
                    // Input Type: whitespace
                    new ScannerEntry(
                        0,
                        ScannerAction.HALT_REUSE,
                        TokenKind.OPTION_ID) 
                },
                /////////////////////////////////////////////////////////////
                // State: 4
                {
                    // Input Type: dash
                    new ScannerEntry(
                        4,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.OPTION_ID),
                        
                    // Input Type: alpha|num|sym
                    new ScannerEntry(
                        4,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.OPTION_ID),
                        
                    // Input Type: equal
                    new ScannerEntry(
                        0,
                        ScannerAction.HALT_REUSE,
                        TokenKind.OPTION_ID),
                        
                    // Input Type: quote
                    new ScannerEntry(
                        0,
                        ScannerAction.HALT_REUSE,
                        TokenKind.OPTION_ID),
                        
                    // Input Type: whitespace
                    new ScannerEntry(
                        0,
                        ScannerAction.HALT_REUSE,
                        TokenKind.OPTION_ID) 
                },
                /////////////////////////////////////////////////////////////
                // State: 5
                {
                    // Input Type: dash
                    new ScannerEntry(
                        6,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.OPTION_VALUE),
                        
                    // Input Type: alpha|num|sym
                    new ScannerEntry(
                        6,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.OPTION_VALUE),
                        
                    // Input Type: equal
                    new ScannerEntry(
                        -1,
                        ScannerAction.THROW_EXCEPTION,
                        TokenKind.INVALID_INPUT),
                        
                    // Input Type: quote
                    new ScannerEntry(
                        7,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.INCOMPLETE_TOKEN),
                        
                    // Input Type: whitespace
                    new ScannerEntry(
                        -1,
                        ScannerAction.THROW_EXCEPTION,
                        TokenKind.INVALID_INPUT) 
                },
                /////////////////////////////////////////////////////////////
                // State: 6
                {
                    // Input Type: dash
                    new ScannerEntry(
                        6,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.OPTION_VALUE),
                        
                    // Input Type: alpha|num|sym
                    new ScannerEntry(
                        6,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.OPTION_VALUE),
                        
                    // Input Type: equal
                    new ScannerEntry(
                        0,
                        ScannerAction.HALT_REUSE,
                        TokenKind.OPTION_VALUE),
                        
                    // Input Type: quote
                    new ScannerEntry(
                        0,
                        ScannerAction.HALT_REUSE,
                        TokenKind.OPTION_VALUE),
                        
                    // Input Type: whitespace
                    new ScannerEntry(
                        0,
                        ScannerAction.HALT_REUSE,
                        TokenKind.OPTION_VALUE) 
                },
                /////////////////////////////////////////////////////////////
                // State: 7
                {
                    // Input Type: dash
                    new ScannerEntry(
                        8,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.INCOMPLETE_TOKEN),
                        
                    // Input Type: alpha|num|sym
                    new ScannerEntry(
                        8,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.INCOMPLETE_TOKEN),
                        
                    // Input Type: equal
                    new ScannerEntry(
                        8,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.INCOMPLETE_TOKEN),
                        
                    // Input Type: quote
                    new ScannerEntry(
                        0,
                        ScannerAction.HALT_APPEND,
                        TokenKind.OPTION_VALUE),
                        
                    // Input Type: whitespace
                    new ScannerEntry(
                        8,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.INCOMPLETE_TOKEN) 
                },
                /////////////////////////////////////////////////////////////
                // State: 8
                {
                    // Input Type: dash
                    new ScannerEntry(
                        8,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.INCOMPLETE_TOKEN),
                        
                    // Input Type: alpha|num|sym
                    new ScannerEntry(
                        8,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.INCOMPLETE_TOKEN),
                        
                    // Input Type: equal
                    new ScannerEntry(
                        8,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.INCOMPLETE_TOKEN),
                        
                    // Input Type: quote
                    new ScannerEntry(
                        0,
                        ScannerAction.HALT_APPEND,
                        TokenKind.OPTION_VALUE),
                        
                    // Input Type: whitespace
                    new ScannerEntry(
                        8,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.INCOMPLETE_TOKEN) 
                },
                /////////////////////////////////////////////////////////////
                // State: 9 (unreachable state)
                {},
                /////////////////////////////////////////////////////////////
                // State: 10
                {
                    // Input Type: dash
                    new ScannerEntry(
                        11,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.PARAMETER_VALUE),
                        
                    // Input Type: alpha|num|sym
                    new ScannerEntry(
                        11,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.PARAMETER_VALUE),
                        
                    // Input Type: equal
                    new ScannerEntry(
                        0,
                        ScannerAction.HALT_REUSE,
                        TokenKind.PARAMETER_VALUE),
                        
                    // Input Type: quote
                    new ScannerEntry(
                        0,
                        ScannerAction.HALT_REUSE,
                        TokenKind.PARAMETER_VALUE),
                        
                    // Input Type: whitespace
                    new ScannerEntry(
                        0,
                        ScannerAction.HALT_REUSE,
                        TokenKind.PARAMETER_VALUE) 
                },
                /////////////////////////////////////////////////////////////
                // State: 11
                {
                    // Input Type: dash
                    new ScannerEntry(
                        11,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.PARAMETER_VALUE),
                        
                    // Input Type: alpha|num|sym
                    new ScannerEntry(
                        11,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.PARAMETER_VALUE),
                        
                    // Input Type: equal
                    new ScannerEntry(
                        0,
                        ScannerAction.HALT_REUSE,
                        TokenKind.PARAMETER_VALUE),
                        
                    // Input Type: quote
                    new ScannerEntry(
                        0,
                        ScannerAction.HALT_REUSE,
                        TokenKind.PARAMETER_VALUE),
                        
                    // Input Type: whitespace
                    new ScannerEntry(
                        0,
                        ScannerAction.HALT_REUSE,
                        TokenKind.PARAMETER_VALUE) 
                },
                /////////////////////////////////////////////////////////////
                // State: 12
                {
                    // Input Type: dash
                    new ScannerEntry(
                        13,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.INCOMPLETE_TOKEN),
                        
                    // Input Type: alpha|num|sym
                    new ScannerEntry(
                        13,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.INCOMPLETE_TOKEN),
                        
                    // Input Type: equal
                    new ScannerEntry(
                        13,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.INCOMPLETE_TOKEN),
                        
                    // Input Type: quote
                    new ScannerEntry(
                        0,
                        ScannerAction.HALT_APPEND,
                        TokenKind.PARAMETER_VALUE),
                        
                    // Input Type: whitespace
                    new ScannerEntry(
                        13,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.INCOMPLETE_TOKEN) 
                },
                /////////////////////////////////////////////////////////////
                // State: 13
                {
                    // Input Type: dash
                    new ScannerEntry(
                        13,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.INCOMPLETE_TOKEN),
                        
                    // Input Type: alpha|num|sym
                    new ScannerEntry(
                        13,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.INCOMPLETE_TOKEN),
                        
                    // Input Type: equal
                    new ScannerEntry(
                        13,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.INCOMPLETE_TOKEN),
                        
                    // Input Type: quote
                    new ScannerEntry(
                        0,
                        ScannerAction.HALT_APPEND,
                        TokenKind.PARAMETER_VALUE),
                        
                    // Input Type: whitespace
                    new ScannerEntry(
                        13,
                        ScannerAction.MOVE_APPEND,
                        TokenKind.INCOMPLETE_TOKEN) 
                },
                /////////////////////////////////////////////////////////////
                // State: 14 (unreachable state)
                {}
            };
    }
    
    /************************************************************************
     *  
     *
     * @param state
     * @param inputType
     * @return
     */
    int
    getNextState(int state,int inputType)
    {
        return itsEntries[state][inputType].getNextState();
    }
    
    /************************************************************************
     *  
     *
     * @param state
     * @param inputType
     * @return
     */
    ScannerAction
    getAction(int state,int inputType)
    {
        return itsEntries[state][inputType].getAction();
    }
    
    /************************************************************************
     *  
     *
     * @param state
     * @param inputType
     * @return
     */
    TokenKind
    getTokenKind(int state,int inputType)
    {
        return itsEntries[state][inputType].getKind();
    }
    
    
}

// ##########################################################################
