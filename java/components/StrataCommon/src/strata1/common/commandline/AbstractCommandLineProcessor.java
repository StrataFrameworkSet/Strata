// ##########################################################################
// # File Name:	AbstractCommandLineProcessor.java
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

package strata1.common.commandline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract 
class AbstractCommandLineProcessor
    implements ICommandLineProcessor
{
    private Set<String>         itsOptions;
    private List<String>        itsArguments;
    private Map<String,String>  itsNamedArguments;
    private Map<Integer,String> itsPositionedArguments;
    private int                 itsCurrentPosition;
    
    /************************************************************************
     * Creates a new {@code AbstractCommandLineProcessor}. 
     *
     */
    public 
    AbstractCommandLineProcessor()
    {
        itsOptions             = new TreeSet<String>();
        itsArguments           = new ArrayList<String>();
        itsNamedArguments      = new TreeMap<String,String>();
        itsPositionedArguments = new TreeMap<Integer,String>();
        itsCurrentPosition     = 0;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setOptions(Set<String> options)
    {
        itsOptions.clear();
        itsOptions.addAll( options );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Set<String> 
    getOptions()
    {
        return itsOptions;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public List<String> 
    getArguments()
    {
        return itsArguments;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    process(String[] arguments)
    {
        parse( arguments );
        validate();
        execute();
    }
    
    /************************************************************************
     *  
     *
     * @param arguments
     */
    protected void
    setArguments(String[] arguments)
    {
        itsArguments = Arrays.asList( arguments );
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    protected Map<String,String>
    getNamedArguments()
    {
        return itsNamedArguments;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    protected Map<Integer,String>
    getPositionedArguments()
    {
        return itsPositionedArguments;
    }
    /************************************************************************
     *  
     *
     * @param arguments
     */
    protected void
    parse(String[] arguments)
    {
        Iterator<String> argument = null;
        
        setArguments( arguments );
        itsNamedArguments.clear();
        itsPositionedArguments.clear();
        
        argument           = getArguments().iterator();
        itsCurrentPosition = 0;
        
        while ( argument.hasNext() )
            parseArgument( argument );
    }
    
    /************************************************************************
     *  
     *
     */
    protected abstract void
    validate();
    
    /************************************************************************
     *  
     *
     */
    protected abstract void
    execute();

    /************************************************************************
     *  
     *
     * @param argument
     */
    private void
    parseArgument(Iterator<String> argument)
    {
        String token = argument.next();
        
        if ( itsOptions.contains( token ) )
            parseNamedArgument( token,argument );
        else
            parsePositionedArgument( token );
    }
    
    /************************************************************************
     *  
     *
     * @param token
     * @param argument
     */
    private void
    parseNamedArgument(String token,Iterator<String> argument)
    {
        if ( argument.hasNext() )
        {
            String nextToken = argument.next();
            
            if ( itsOptions.contains( nextToken ) )
            {
                itsNamedArguments.put( token,"" );
                parseNamedArgument( nextToken,argument );
            }
            else
                itsNamedArguments.put( token,nextToken );
            
        }
        else
            itsNamedArguments.put( token,"" );
    }

    /************************************************************************
     *  
     *
     * @param token
     */
    private void
    parsePositionedArgument(String token)
    {
        itsPositionedArguments.put( itsCurrentPosition++,token );
    }
}

// ##########################################################################
