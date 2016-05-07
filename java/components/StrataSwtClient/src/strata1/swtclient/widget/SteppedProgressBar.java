// ##########################################################################
// # File Name:	SteppedProgressBar.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
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

package strata1.swtclient.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SteppedProgressBar
{
    private Canvas itsBar;
    private int    itsNumberOfSteps;
    private int    itsCurrentStep;
   
    /************************************************************************
     * Creates a new {@code SteppedProgressBar}. 
     *
     */
    public 
    SteppedProgressBar(Composite parent,int numSteps)
    {
        itsBar = new Canvas( parent,SWT.None );
        itsBar.addPaintListener(
            new PaintListener() 
            {
                public void 
                paintControl(PaintEvent p) 
                {
                    GC g = p.gc;
                    Rectangle area = itsBar.getClientArea();
                    
                    g.setBackground( SWTResourceManager.getColor(210, 180, 140) );
                    g.fillRectangle( 0,0,area.height,getBarWidth() );
                }
            } );
        
        itsNumberOfSteps = numSteps;
        itsCurrentStep   = 0;
    }
    
    /************************************************************************
     *  
     *
     * @param numSteps
     */
    public void
    setNumberOfSteps(int numSteps)
    {
        itsNumberOfSteps = numSteps;
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    public Canvas
    getBar()
    {
        return itsBar;
    }
    
    /************************************************************************
     *  
     *
     */
    public void
    resetCurrentStep()
    {
        itsCurrentStep = 0;
        itsBar.redraw();
    }
    
    /************************************************************************
     *  
     *
     */
    public void
    incrementCurrentStep()
    {
        ++itsCurrentStep;
        itsBar.redraw();
    }
    
    /************************************************************************
     *  
     *
     * @return
     */
    private int
    getBarWidth()
    {
        Rectangle area = itsBar.getClientArea();
        return area.width * itsCurrentStep / itsNumberOfSteps;
    }

}

// ##########################################################################
