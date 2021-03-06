// ##########################################################################
// # File Name:	Variable.java
// #
// # Copyright:	2015, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataClasspathGenerator Framework.
// #
// #   			The StrataClasspathGenerator Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataClasspathGenerator Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataClasspathGenerator
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.classpathgenerator.task;

import org.apache.tools.ant.types.Path;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class Variable
{
    private String itsName;
    private Path   itsValue;
    
    /************************************************************************
     * Creates a new Variable. 
     *
     */
    public 
    Variable()
    {
        itsName = null;
        itsValue = null;
    }

    public void
    setName(String name)
    {
        itsName = name;
    }
    
    public void
    setValue(Path value)
    {
        itsValue = value;
    }
    
    public String
    getName()
    {
        return itsName;
    }
    
    public String
    getValue()
    {
        return itsValue.toString();
    }
}

// ##########################################################################
