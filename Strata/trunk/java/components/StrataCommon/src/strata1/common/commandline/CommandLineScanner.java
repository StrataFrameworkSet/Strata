// ##########################################################################
// # File Name:	CommandLineScanner.java
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

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class CommandLineScanner
    implements ICommandLineScanner
{
    private static final ScannerTable theirTable = new ScannerTable();
    
    private CharacterIterator itsIterator;
    private StringBuilder     itsInput;
    private StringBuilder     itsBuffer;
    private int               itsCurrentState;
    
    /************************************************************************
     * Creates a new {@code CommandLineScanner}. 
     *
     */
    public 
    CommandLineScanner()
    {
        itsIterator  = new StringCharacterIterator("");
        itsInput     = new StringBuilder();
        itsBuffer    = new StringBuilder();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ICommandLineScanner 
    setInput(String[] arguments)
    {
        if ( arguments.length == 0 )
            itsIterator = new StringCharacterIterator("");
        else
        {
            StringBuilder builder = new StringBuilder();
            
            builder.append( arguments[0] );
            
            for (int i=1;i<arguments.length;i++)
                builder
                    .append( " " )
                    .append( arguments[i] );
            
            itsIterator = new StringCharacterIterator(builder.toString());
        }
        
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public IToken 
    getNext()
    {
        itsInput        = new StringBuilder();
        itsBuffer       = new StringBuilder();
        itsCurrentState = 0;

        eatWhitespace();
        
        while (true)
        {
            if ( isDone() )
                return new Token("","",TokenKind.DONE);
            
            switch ( getAction() )
            {
            case MOVE_APPEND:
                moveAppend();
                break;
                
            case MOVE_NO_APPEND:
                moveNoAppend();
                break;
                
            case HALT_APPEND:
                return haltAppend();
                
            case HALT_NO_APPEND:
                return haltNoAppend();
                
            case HALT_REUSE:
                return haltReuse();
                
            case THROW_EXCEPTION:
                throw 
                    new IllegalStateException(
                        "State:" + itsCurrentState + 
                        ", InputType:" + getInputType());
            }
        }
    }

    /************************************************************************
     *  
     *
     */
    private void 
    eatWhitespace()
    {
        boolean continueLoop = true;
        
        while ( continueLoop )
        {
            if ( peek() == CharacterIterator.DONE )
                return;
            
            if ( Character.isWhitespace( peek() ) )
                consume();
            else
                continueLoop = false;
        }
    }

    /************************************************************************
     *  
     *
     * @return
     */
    private boolean 
    isDone()
    {
        return peek() == CharacterIterator.DONE && itsBuffer.length() == 0;
    }

    /************************************************************************
     *  
     *
     */
    private void 
    moveAppend()
    {
        itsInput.append(  peek() );
        itsBuffer.append(  peek() );
        itsCurrentState = getNextState();
        consume();
    }

    /************************************************************************
     *  
     *
     */
    private void 
    moveNoAppend()
    {
        itsInput.append(  peek() );
        itsCurrentState = getNextState();
        consume();
    }

    /************************************************************************
     *  
     *
     * @return
     */
    private IToken 
    haltAppend()
    {
        String    input = null;
        String    buffer = null;
        TokenKind kind = null;
        
        itsInput.append( peek() );
        itsBuffer.append( peek() );
        
        input  = itsInput.toString();
        buffer = itsBuffer.toString();
        kind   = getTokenKind();
        
        consume();
        
        return new Token(input,buffer,kind);
    }

    /************************************************************************
     *  
     *
     * @return
     */
    private IToken 
    haltNoAppend()
    {
        String    input = null;
        String    buffer = null;
        TokenKind kind = null;
        
        itsInput.append( peek() );
        
        input  = itsInput.toString();
        buffer = itsBuffer.toString();
        kind   = getTokenKind();
        
        consume();
        
        return new Token(input,buffer,kind);
    }

    /************************************************************************
     *  
     *
     * @return
     */
    private IToken 
    haltReuse()
    {
        String    input = null;
        String    buffer = null;
        TokenKind kind = null;
        
        input  = itsInput.toString();
        buffer = itsBuffer.toString();
        kind   = getTokenKind();
        
        return new Token(input,buffer,kind);
    }

    /************************************************************************
     *  
     *
     * @param state
     * @param input
     * @return
     */
    private int
    getNextState()
    {
        return theirTable.getNextState( itsCurrentState,getInputType());       
    }
    
    /************************************************************************
     *  
     *
     * @param currentState
     * @param peek
     * @return
     */
    private ScannerAction 
    getAction()
    {
        return theirTable.getAction( itsCurrentState,getInputType());       
    }

    /************************************************************************
     *  
     *
     * @param state
     * @param input
     * @return
     */
    private TokenKind
    getTokenKind()
    {
        return theirTable.getTokenKind( itsCurrentState,getInputType());       
    }
    
    /************************************************************************
     *  
     *
     * @param input
     * @return
     */
    private int
    getInputType()
    {
        char input = peek();
                
        if ( 
            Character.isLetterOrDigit( input ) ||
            (input == '_') ||
            (input == '!') ||
            (input == '@') ||
            (input == '#') ||
            (input == '$') ||
            (input == '%') ||
            (input == '^') ||
            (input == '&') ||
            (input == '*') ||
            (input == '(') ||
            (input == ')') ||
            (input == '+') ||
            (input == '{') ||
            (input == '}') ||
            (input == '[') ||
            (input == ']') ||
            (input == '|') ||
            (input == '\\') ||
            (input == ';') ||
            (input == ':') ||
            (input == '\'') ||
            (input == ',') ||
            (input == '.') ||
            (input == '/') ||
            (input == '<') ||
            (input == '>') ||
            (input == '?') )
            return 1; // alpha|num|sym
        else if ( input == '-' )
            return 0; // dash
        else if ( input == '=' )
            return 2; // equals
        else if ( input == '"' )
            return 3; // quote
        else if ( 
            Character.isWhitespace( input ) || 
            input == CharacterIterator.DONE )
            return 4; // whitespace
        
        throw new IllegalArgumentException("bad input = '" + input + "'." );
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    private char 
    peek()
    {
        return itsIterator.current();
    }

    /************************************************************************
     *  
     *
     * @return
     */
    private char 
    consume()
    {
        return itsIterator.next();
    }

}

// ##########################################################################
