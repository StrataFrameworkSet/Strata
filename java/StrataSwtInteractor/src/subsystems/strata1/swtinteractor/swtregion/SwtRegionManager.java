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

import strata1.swtinteractor.swtview.SwtView;
import strata1.interactor.region.Region;
import strata1.interactor.region.RegionInitializationException;
import strata1.interactor.region.RegionManager;
import strata1.interactor.view.View;
import org.eclipse.swt.widgets.Composite;
import java.util.HashMap;
import java.util.Map;

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
    private Map<String,Region>                itsRegions;
    private Map<String,Class<? extends View>> itsViewTypes;
    
    /************************************************************************
     * Creates a new {@code SwtRegionManager}. 
     *
     */
    public 
    SwtRegionManager()
    {
        itsRegions   = new HashMap<String,Region>();
        itsViewTypes = new HashMap<String,Class<? extends View>>();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    insertRegion(Region region)
    {
        itsRegions.put( region.getName(),region );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    initializeRegions()
        throws RegionInitializationException
    {
       for (Region region:itsRegions.values())
           region.initializeView();
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
    hasRegion(String regionName)
    {
        // TODO Auto-generated method stub
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isRegisteredWithRegion(String regionName,Class<? extends View> viewType)
    {
        return 
            itsViewTypes.containsKey( regionName ) &&
            itsViewTypes.get( regionName ).equals( viewType );
    }

    /************************************************************************
     *  
     *
     * @param name
     * @param parent
     * @return
     * @throws RegionInitializationException
     */
    public SwtView
    createView(String name,Composite parent) 
        throws RegionInitializationException
    {
        Class<? extends View> viewType = itsViewTypes.get( name );
        SwtView               view     = null;
        
        if ( viewType == null )
            throw new IllegalArgumentException();
        
        try
        {
            return
                (SwtView)viewType
                    .getConstructor( Composite.class )
                    .newInstance( parent );
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
