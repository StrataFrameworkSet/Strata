// ##########################################################################
// # File Name:	ISwtView.java
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

package strata1.swtclient.swtview;

import strata1.client.view.IViewable;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

/**
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
interface ISwtView
    extends IViewable
{
    public Widget
    getWidget();
    
    public Composite
    getComposite();
    
    /************************************************************************
     * Makes a view visible. 
     *
     */
    public void 
    show();
    
    /************************************************************************
     * Makes a view invisible. 
     *
     */
    public void
    hide();

}


// ##########################################################################
