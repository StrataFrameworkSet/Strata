// ##########################################################################
// # File Name:	SwtSplashView.java
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

package strata1.swtclient.swtview;

import strata1.client.command.ILoginProvider;
import strata1.client.command.INullProvider;
import strata1.client.shell.IDispatcher;
import strata1.client.view.AbstractView;
import strata1.client.view.ISplashView;
import strata1.swtclient.swtshell.ISwtDispatcher;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.wb.swt.SWTResourceManager;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class SwtSplashView
    extends    AbstractView<INullProvider>
    implements ISplashView,
               ISwtView
{
    private ISwtDispatcher     itsDispatcher;
    private Shell              itsShell;
    private Composite          itsBanner;
    private Label              itsSystemLabel;

    private Composite          itsControls;
    private Label              itsVersionLabel;
    private Label              itsMessageLabel;
    private Label              itsCopyrightLabel;
    private Canvas             itsProgressBar;
    private int                itsNumberOfSteps;
    private int                itsCurrentStep;
    
    /************************************************************************
     * Creates a new {@code SwtSplashView}. 
     *
     * @param dispatcher
     * @param appName
     * @param version
     * @param copyright
     */
    public 
    SwtSplashView(
        IDispatcher dispatcher,
        String      appName,
        String      version,
        String      copyright)
    {
        itsDispatcher = (ISwtDispatcher)dispatcher;
        createShell( appName );
        createBanner( appName );
        createControls( version,copyright );
        centerShell();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    start()
    {
        itsShell.open();
        show();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    stop()
    {
        itsDispatcher.processWork();
        sleep( 3 );
        hide();
        itsShell.close();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    show()
    {
        itsShell.setVisible( true );
        itsDispatcher.processWork();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    hide()
    {
        itsShell.setVisible( false );
        itsDispatcher.processWork();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Widget 
    getWidget()
    {
        return itsShell;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Composite 
    getComposite()
    {
        return itsShell;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setMessage(String message)
    {
        itsMessageLabel.setText( message );
        itsDispatcher.processWork();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setInitializationIncrements(int increments)
    {
        itsNumberOfSteps = increments;
        itsProgressBar.redraw();
        itsDispatcher.processWork();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    resetInitializationProgress()
    {
        itsCurrentStep = 0;
        itsProgressBar.redraw();   
        itsDispatcher.processWork();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    incrementInitializationProgress()
    {
        itsCurrentStep += 1;
        itsProgressBar.redraw();    
        itsDispatcher.processWork();
        sleep( 1 );
    }

    /************************************************************************
     *  
     *
     * @param appName
     */
    private void 
    createShell(String appName)
    {
        GridLayout shellLayout = null;
        
        itsShell = 
            new Shell( itsDispatcher.getDisplay(),SWT.NONE);
        itsShell.setSize(449, 204);
        itsShell.setMinimumSize(new Point(300, 275));
       
        itsShell.setBackground(
            SWTResourceManager.getColor(SWT.COLOR_TITLE_BACKGROUND));
        itsShell.setText( appName );
        shellLayout = new GridLayout(1, true);
        shellLayout.horizontalSpacing = 10;
        itsShell.setLayout(shellLayout);
    }

    /************************************************************************
     *  
     *
     * @param appName
     */
    private void 
    createBanner(String appName)
    {
        GridData bannerGridData = null;
        
        itsBanner = new Composite(itsShell, SWT.NONE);
        itsBanner.setLayout(new FillLayout(SWT.HORIZONTAL));
        bannerGridData = new GridData(
            SWT.CENTER, SWT.BOTTOM, false, false, 1, 1);
        bannerGridData.heightHint = 100;
        bannerGridData.widthHint = 435;
        itsBanner.setLayoutData(bannerGridData);
        itsBanner.setBackgroundMode( SWT.INHERIT_DEFAULT );
        itsBanner.setBackgroundImage( 
            SWTResourceManager.getImage( 
                "C:/Users/Public/Pictures/Sample Pictures/Chrysanthemum.jpg" ) );
        
        itsSystemLabel = new Label(itsBanner, SWT.CENTER);
        itsSystemLabel.setAlignment(SWT.CENTER);
        itsSystemLabel.setForeground(
            SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
        itsSystemLabel.setFont(
            SWTResourceManager.getFont("Segoe UI", 16, SWT.BOLD));
        itsSystemLabel.setText(appName);
    }

    /************************************************************************
     *  
     *
     * @param version
     * @param copyright
     */
    private void 
    createControls(String version,String copyright)
    {
        GridData progressBarGridData = null;
        
        createComposite(); 
        
        itsVersionLabel = new Label(itsControls, SWT.NONE);
        itsVersionLabel.setForeground(SWTResourceManager.getColor(102, 51, 51));
        itsVersionLabel.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
        itsVersionLabel.setText( "Version " + version );
        new Label(itsControls, SWT.NONE);
        new Label(itsControls, SWT.NONE);
        
        itsMessageLabel = new Label(itsControls, SWT.CENTER);
        itsMessageLabel.setForeground(SWTResourceManager.getColor(51, 51, 51));
        itsMessageLabel.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.NORMAL));
        itsMessageLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND));
        itsMessageLabel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
        new Label(itsControls, SWT.NONE);
        new Label(itsControls, SWT.NONE);
        
        itsCopyrightLabel = new Label(itsControls, SWT.NONE);
        itsCopyrightLabel.setForeground(SWTResourceManager.getColor(102, 51, 51));
        itsCopyrightLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
        itsCopyrightLabel.setText( copyright );
        
        itsProgressBar = new Canvas(itsControls,SWT.NONE);
        itsProgressBar.addPaintListener(
            new PaintListener() 
            {
                public void 
                paintControl(PaintEvent p) 
                {
                    GC g = p.gc;
                    Rectangle area = itsProgressBar.getClientArea();
                    
                    g.setBackground( SWTResourceManager.getColor( 160,160,180) );
                    g.fillRectangle( 0,0,getBarWidth(),area.height );
                }
            } );

        progressBarGridData = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
        progressBarGridData.heightHint = 10;
        progressBarGridData.minimumHeight = 10;
        progressBarGridData.widthHint = 275;
        progressBarGridData.minimumWidth = 275;
        itsProgressBar.setLayoutData(progressBarGridData);
    }

    /************************************************************************
     *  
     *
     */
    private void 
    createComposite()
    {
        GridData controlGridData = null;
        
        itsControls = new Composite(itsShell, SWT.NONE);
        itsControls.setBackgroundMode( SWT.INHERIT_DEFAULT );
        itsControls.setBackground(
            SWTResourceManager.getColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND));
        itsControls.setLayout(new GridLayout(1, false));
        
        controlGridData = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
        itsControls.setLayoutData(controlGridData);
    }

    /************************************************************************
     *  
     *
     */
    private void 
    centerShell()
    {
        itsShell.pack();
    
        Monitor primary = itsDispatcher.getDisplay().getPrimaryMonitor();
        Rectangle bounds = primary.getBounds();
        Rectangle rect = itsShell.getBounds();
        int x = bounds.x + (bounds.width - rect.width) / 2;
        int y = bounds.y + (bounds.height - rect.height) / 2;
        itsShell.setLocation(x, y);
    }

    /************************************************************************
     *  
     *
     * @return
     */
    private int
    getBarWidth()
    {
        Rectangle area = itsProgressBar.getClientArea();
        return area.width * itsCurrentStep / itsNumberOfSteps;
    }

    /************************************************************************
     *  
     *
     * @param sec
     */
    private void 
    sleep(int sec)
    {
        try
        {
            Thread.sleep( sec*1000 );
        }
        catch(InterruptedException e)
        {
        }
    }

}

// ##########################################################################
