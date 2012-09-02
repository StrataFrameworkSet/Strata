// ##########################################################################
// # File Name:	SwtRegion.java
// #
// # Copyright:	2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataInteractor Framework.
// #
// #   			The StrataInteractor Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataInteractor Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataInteractor
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.swtclient.swtregion;

import strata1.client.region.IRegion;
import strata1.client.region.IRegionManager;
import strata1.client.region.RegionInitializationException;
import strata1.swtclient.swtview.ISwtView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public class SwtRegion
    extends    Composite
    implements IRegion
{
    private static SwtRegionManager theirManager;
    private String                  itsName;
    private ISwtView                itsView;

    /************************************************************************
     * Creates a new {@code SwtRegion}. 
     *
     * @param name
     * @param parent
     */
    public 
    SwtRegion(String name,Composite parent)
    {
        super( parent,SWT.NONE ); 
        super.setLayout( new GridLayout(1,false) );
        itsName = name;
        itsView = null;
        
        getManager().insertRegion( this );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void
    initializeView()
        throws RegionInitializationException
    {
        itsView = getManager().createView( getRegionName(),this );
        itsView.show();
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    getRegionName()
    {
        return itsName;
    }
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public ISwtView 
    getView()
    {
        return itsView;
    }
    
    /************************************************************************
     *  
     *
     * @param manager
     */
    public static void
    setManager(IRegionManager manager)
    {
        theirManager = (SwtRegionManager)manager;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public static SwtRegionManager
    getManager()
    {
        return theirManager;
    }

}


// ##########################################################################
