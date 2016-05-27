// ##########################################################################
// # File Name:	SelectorFactory.java
// #
// # Copyright:	2016, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataSqsIntegrator Framework.
// #
// #   			The StrataSqsIntegrator Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataSqsIntegrator Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataSqsIntegrator
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.sqsintegrator.sqsmessaging;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import strata1.integrator.inmemorymessaging.SimpleSelector;
import strata1.integrator.messaging.ISelector;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SelectorFactory
{

    /************************************************************************
     * Creates a new SelectorFactory. 
     *
     */
    public 
    SelectorFactory() {}
    
    /************************************************************************
     *  
     *
     * @param expression
     * @return
     */
    public boolean 
    isStringEquals(String expression)
    {
        return expression.matches( "^[a-zA-Z0-9_]+='.*'$" );
    }

    /************************************************************************
     *  
     *
     * @param expression
     * @return
     */
    public ISelector 
    createStringEqualsSelector(String expression)
    {
        StringTokenizer    tokenizer  = new StringTokenizer(expression,"=");
        String             property   = tokenizer.nextToken();
        String             value      = tokenizer.nextToken();
        Map<String,String> properties = new HashMap<String,String>();
        
        properties.put( property,removeQuotes(value) );
        return new SimpleSelector( properties );
    }
    
    /************************************************************************
     *  
     *
     * @param input
     * @return
     */
    private String
    removeQuotes(String input)
    {
        if ( input.matches( "^'.*'$" ) )
            return input.substring( 1,input.length()-1 );
        
        return input;
    }

}

// ##########################################################################
