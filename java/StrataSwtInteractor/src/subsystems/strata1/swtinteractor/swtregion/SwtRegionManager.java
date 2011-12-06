// ##########################################################################
// # File Name:	SwtRegionManager.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataSwtInteractor Framework.
// #
// #   			The StrataSwtInteractor Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataSwtInteractor Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataSwtInteractor
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.swtinteractor.swtregion;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import strata1.interactor.region.RegionInitializationException;
import strata1.interactor.region.RegionManager;
import strata1.interactor.view.View;
import strata1.swtinteractor.swtview.SwtView;
import org.eclipse.swt.widgets.Composite;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SwtRegionManager
    implements RegionManager
{
    private Map<String,Class<? extends View>> itsViewTypes;
    
    /************************************************************************
     * Creates a new {@code SwtRegionManager}. 
     *
     */
    public 
    SwtRegionManager()
    {
        itsViewTypes = new HashMap<String,Class<? extends View>>();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    registerViewWithRegion(
        String                regionName,
        Class<? extends View> viewType)
    {
        checkViewType( viewType );
        itsViewTypes.put( regionName,viewType );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isRegisteredWithRegion(String regionName)
    {
        return itsViewTypes.containsKey( regionName );
    }

    /************************************************************************
     *  
     *
     * @param name
     * @param parent
     * @return
     * @throws RegionInitializationException
     */
    public Composite
    createComposite(String name,Composite parent) 
        throws RegionInitializationException
    {
        Class<? extends View> viewType = itsViewTypes.get( name );
        SwtView               view     = null;
        
        if ( viewType == null )
            throw new IllegalArgumentException();
        
        try
        {
            view = (SwtView)viewType
                    .getConstructor( Composite.class )
                    .newInstance( parent );
            
            return view.getComposite();
        }
        catch(Exception e)
        {
            throw new RegionInitializationException( e );
        }
    }
    
    /************************************************************************
     *  
     *
     * @param viewType
     * @throws IllegalArgumentException
     */
    private void 
    checkViewType(Class<? extends View> viewType)
        throws IllegalArgumentException
    {
        if ( !(SwtView.class).isAssignableFrom( viewType ) ) 
            throw new IllegalArgumentException();
    }

}

// ##########################################################################
