// ##########################################################################
// # File Name:	CommandLineParser.java
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

import java.util.ArrayDeque;
import java.util.Queue;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class CommandLineParser
    implements ICommandLineParser
{
    private final ICommandLineScanner itsScanner;
    private ICommandLineProcessor     itsProcessor;
    private Queue<IToken>             itsTokens; 
    private int                       itsCurrentPosition;
    
    /************************************************************************
     * Creates a new {@code CommandLineParser}. 
     *
     */
    public 
    CommandLineParser(ICommandLineScanner scanner)
    {
        itsScanner         = scanner;
        itsProcessor       = new NullCommandLineProcessor();
        itsTokens          = new ArrayDeque<IToken>();
        itsCurrentPosition = 0;
    }

    /************************************************************************
     * Creates a new {@code CommandLineParser}. 
     *
     */
    public 
    CommandLineParser()
    {
        this( new CommandLineScanner() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ICommandLineParser 
    setProcessor(ICommandLineProcessor processor)
    {
        itsProcessor = processor;
        return this;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    parse(String[] arguments) 
        throws Exception
    {
        try
        {       
            itsScanner.setInput( arguments );
            getToken();
            itsProcessor.startProcessing();
            
            while (isArgument())
                parseArgument();
            
            match( TokenKind.DONE );
            itsProcessor.finishProcessing();       
        }
        catch (CommandLineException e)
        {
            itsProcessor.processException( e );
        }
        catch (Exception e)
        {
            itsProcessor.processException( e );
        }
    }

    /************************************************************************
     *  
     *
     * @return
     */
    private boolean 
    isArgument()
    {
        IToken    token = itsTokens.peek();
        TokenKind kind  = token.getKind();
        
        return 
            kind == TokenKind.OPTION_ID ||
            kind == TokenKind.PARAMETER_VALUE;
    }

    /************************************************************************
     *  
     *
     * @throws CommandLineException
     */
    private void
    parseArgument() 
        throws CommandLineException
    {
        if ( isOption() )
            parseOption();
        else
            parseParameter();
    }

    /************************************************************************
     *  
     *
     * @param token
     * @return
     */
    private boolean
    isOption()
    {
        return itsTokens.peek().getKind() == TokenKind.OPTION_ID;
    }

    /************************************************************************
     *  
     *
     * @throws CommandLineException
     */
    private void 
    parseOption() 
        throws CommandLineException
    {
        ICommandOption option = null;
        IToken         id     = match( TokenKind.OPTION_ID );     
        
        if ( hasValue() )
        {
            IToken value = match( TokenKind.OPTION_VALUE );
            
            option = 
                new CommandOption(
                    itsCurrentPosition++,
                    id.getInput()+value.getInput(),
                    id.getBuffer(),
                    value.getBuffer() );
        }
        else
            option = 
                new CommandOption(
                    itsCurrentPosition++,
                    id.getInput(),
                    id.getBuffer());
        
        itsProcessor.processOption( option );
    }

    /************************************************************************
     *  
     *
     * @throws CommandLineException
     */
    private void 
    parseParameter() 
        throws CommandLineException
    {
        IToken value = match( TokenKind.PARAMETER_VALUE ); 
        
        itsProcessor.processParameter( 
            new CommandParameter(
                itsCurrentPosition++,
                value.getInput(),
                value.getBuffer()) );
    }

    /************************************************************************
     *  
     *
     * @return
     */
    private boolean 
    hasValue()
    {
        return itsTokens.peek().getKind() == TokenKind.OPTION_VALUE;
    }

    /************************************************************************
     *  
     *
     * @return
     */
    private void 
    getToken()
    {
        itsTokens.add( itsScanner.getNext() );
    }

    /************************************************************************
     *  
     *
     * @param kind
     * @return
     */
    private IToken
    match(TokenKind kind)
    {
        IToken first  = itsTokens.peek();
        
        if ( first.getKind() == kind )
        {
            itsTokens.remove();
            
            if ( itsTokens.isEmpty() )
                getToken();
        }
        
        return first;
    }
    
 }

// ##########################################################################
