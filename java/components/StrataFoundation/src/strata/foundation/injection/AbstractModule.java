// ##########################################################################
// # File Name:	AbstractModule.java
// #
// # Copyright:	2014, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInjector Framework.
// #
// #   			The StrataInjector Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInjector Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInjector
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.foundation.injection;

import java.lang.annotation.Annotation;
import strata.foundation.logger.ILogger;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public abstract
class AbstractModule
    implements IModule
{
    private static Class<? extends Annotation> 
    theirDefaultScope = ThreadScope.class;
    
    private String         itsName;
    private IModuleAdapter itsAdapter;
    
    /************************************************************************
     * Creates a new {@code AbstractModule}. 
     *
     */
    protected 
    AbstractModule(String name)
    {
        itsName    = name;
        itsAdapter = null;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getName()
    {
        return itsName;
    }
    
    /************************************************************************
     *  
     *
     * @param adapter
     */
    public void
    setAdapter(IModuleAdapter adapter)
    {
        itsAdapter = adapter;
        itsAdapter.setAdaptee( this );
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public IModuleAdapter
    getAdapter()
    {
        return itsAdapter;
    }
    
    /************************************************************************
     *  
     *
     * @param type
     * @return
     */
    protected <T> ISourceBindingBuilder<T>
    bindType(Class<T> type)
    {
        return itsAdapter.bindType( type );
    }
    
    public static void
    setDefaultScope(Class<? extends Annotation> defaultScope)
    {
        theirDefaultScope = defaultScope;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public static Class<? extends Annotation>
    getDefaultScope()
    {
        return theirDefaultScope;
    }
}

// ##########################################################################
