//////////////////////////////////////////////////////////////////////////////
// HelloWorldView.java
//////////////////////////////////////////////////////////////////////////////

package strata.client.swt.presentation;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import strata.client.core.presentation.IHelloWorldPresenter;
import strata.client.core.presentation.IHelloWorldView;

public
class HelloWorldView
    implements IHelloWorldView
{
    private IHelloWorldPresenter itsPresenter;
    private Menu      itsMenuBar;
    private MenuItem  itsFileMenuItem;
    private Menu      itsFileMenu;
    private MenuItem  itsExitMenuItem;
    private Composite itsPanel;
    private Text      itsNameText;
    private Text      itsGreetingText;
    private Label     itsGreetingLabel;
    private Button    itsSubmitButton;

    public
    HelloWorldView(IHelloWorldPresenter presenter,Display display,Shell shell)
    {
        itsPresenter = presenter;
        itsMenuBar      = new Menu( shell,SWT.BAR );
        itsFileMenuItem = new MenuItem( itsMenuBar,SWT.CASCADE );
        itsFileMenu     = new Menu( shell,SWT.DROP_DOWN );
        itsExitMenuItem = new MenuItem( itsFileMenu,SWT.PUSH );

        shell.setMenuBar( itsMenuBar );
        itsFileMenuItem.setMenu( itsFileMenu );
        itsFileMenuItem.setText( "File" );
        itsExitMenuItem.setText( "Exit" );

        itsExitMenuItem.addSelectionListener(
            new SelectionAdapter()
            {
                @Override
                public void
                widgetSelected(SelectionEvent e)
                {
                    try
                    {
                        itsPresenter.exit();
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            } );

        itsPanel         = new Composite( shell,SWT.NONE);
        itsPanel.setLayout(new GridLayout(1,false));
        itsNameText      = new Text(itsPanel,SWT.SINGLE|SWT.BORDER);
        itsGreetingText  = new Text(itsPanel,SWT.SINGLE|SWT.BORDER);
        itsGreetingLabel = new Label( itsPanel,SWT.NONE );
        itsNameText.setSize(300,20);
        itsGreetingText.setSize(300,20);
        itsGreetingLabel.setSize(500, 20);
        itsGreetingLabel.setText("Hello?                        ");
        itsSubmitButton = new Button(itsPanel,SWT.PUSH);
        itsSubmitButton.setText("Submit");
        itsSubmitButton.addSelectionListener(
            new SelectionAdapter()
            {
                @Override
                public void
                widgetSelected(SelectionEvent selectionEvent)
                {
                    try
                    {
                        itsPresenter.submit();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
        Monitor primary = display.getPrimaryMonitor();
        Rectangle bounds = primary.getBounds();
        Rectangle rect = shell.getBounds();
        int x = bounds.x + (bounds.width - rect.width) / 2;
        int y = bounds.y + (bounds.height - rect.height) / 2;
        shell.setLocation(x, y);

    }

    @Override
    public IHelloWorldView
    setName(String name)
    {
        itsNameText.setText(name);
        return this;
    }

    @Override
    public IHelloWorldView
    setGreeting(String greeting)
    {
        itsGreetingText.setText(greeting);
        return this;
    }

    @Override
    public IHelloWorldView
    setPersonalizedGreeting(String personalizedGreeting)
    {
        itsGreetingLabel.setText(personalizedGreeting);
        return this;
    }

    @Override
    public String
    getName()
    {
        return itsNameText.getText();
    }

    @Override
    public String
    getGreeting()
    {
        return itsGreetingText.getText();
    }
}

//////////////////////////////////////////////////////////////////////////////
