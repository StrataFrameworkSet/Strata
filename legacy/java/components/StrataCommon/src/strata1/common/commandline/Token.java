// ##########################################################################
// # File Name:	Token.java
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
class Token
    implements IToken
{
    private final String    itsInput;
    private final String    itsBuffer;
    private final TokenKind itsKind;
    
    /************************************************************************
     * Creates a new {@code Token}. 
     *
     * @param input
     * @param buffer
     * @param kind
     */
    public 
    Token(
        String    input,
        String    buffer,
        TokenKind kind)
    {
        itsInput   = input;
        itsBuffer = buffer;
        itsKind   = kind;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getInput()
    {
        return itsInput;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getBuffer()
    {
        return itsBuffer;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public TokenKind 
    getKind()
    {
        return itsKind;
    }

}

// ##########################################################################
