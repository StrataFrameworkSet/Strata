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

package strata1.swtclient.swtregion;

import strata1.client.region.IRegion;
import strata1.client.region.IRegionManager;
import strata1.client.region.RegionInitializationException;
import strata1.client.view.IViewable;
import strata1.swtclient.swtview.ISwtView;
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
    implements IRegionManager
{
    private Map<String,IRegion>                    itsRegions;
    private Map<String,Class<? extends IViewable>> itsViewTypes;
    
    /************************************************************************
     * Creates a new {@code SwtRegionManager}. 
     *
     */
    public 
    SwtRegionManager()
    {
        itsRegions   = new HashMap<String,IRegion>();
        itsViewTypes = new HashMap<String,Class<? extends IViewable>>();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    insertRegion(IRegion region)
    {
        itsRegions.put( region.getRegionName(),region );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    initializeRegions()
        throws RegionInitializationException
    {
       for (IRegion region:itsRegions.values())
           region.initializeView();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    registerWithRegion(
        String                     regionName,
        Class<? extends IViewable> viewType)
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
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isRegisteredWithRegion(String regionName,Class<? extends IViewable> viewType)
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
    public ISwtView
    createView(String name,Composite parent) 
        throws RegionInitializationException
    {
        Class<? extends IViewable> viewType = itsViewTypes.get( name );
        
        if ( viewType == null )
            throw new IllegalArgumentException();
        
        try
        {
            return
                (ISwtView)viewType
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
    checkViewType(Class<? extends IViewable> viewType)
        throws IllegalArgumentException
    {
        if ( !(ISwtView.class).isAssignableFrom( viewType ) ) 
            throw new IllegalArgumentException();
    }

}

// ##########################################################################
