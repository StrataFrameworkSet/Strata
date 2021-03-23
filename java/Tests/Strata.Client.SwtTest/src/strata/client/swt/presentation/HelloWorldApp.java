//////////////////////////////////////////////////////////////////////////////
// HelloWorldApp.java
//////////////////////////////////////////////////////////////////////////////

package strata.client.swt.presentation;

import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import strata.client.core.presentation.HelloWorldModel;
import strata.client.core.presentation.HelloWorldPresenter;
import strata.client.core.presentation.IHelloWorldModel;
import strata.client.core.presentation.IHelloWorldPresenter;
import strata.client.core.presenter.IModelStore;
import strata.client.core.presenter.ModelStore;

import java.util.concurrent.ConcurrentLinkedQueue;

public
class HelloWorldApp
{
    private Shell                itsShell;
    private Display              itsDisplay;
    private IModelStore          itsModelStore;
    private IHelloWorldPresenter itsPresenter;
    private ConcurrentLinkedQueue<Runnable> itsTasks;


    public
    HelloWorldApp()
    {
        itsDisplay = new Display();
        itsShell = new Shell( itsDisplay );
        itsShell.addShellListener(
            new ShellAdapter()
            {
                @Override
                public void
                shellClosed(ShellEvent arg0)
                {
                    System.exit( 0 );
                }
            });
        itsShell.setText("Hello World");
        itsShell.setSize(550, 327);
        itsShell.setLayout(new GridLayout(1, false));

        itsModelStore =
            new ModelStore()
                .insert(
                    IHelloWorldModel.class,
                    new HelloWorldModel("",""));
        itsPresenter =
            new HelloWorldPresenter(
                itsModelStore,
                presenter -> new HelloWorldView(presenter,itsDisplay,itsShell));
        itsTasks = new ConcurrentLinkedQueue<>();
    }

    public void
    start()
    {
        itsTasks.add(this::doStartUp);

        while (!itsDisplay.isDisposed())
        {
            Runnable task = itsTasks.poll();

            if (task != null)
                itsDisplay.syncExec(task);

            if (!itsDisplay.readAndDispatch())
                itsDisplay.sleep();
        }
    }

    private void
    doStartUp()
    {
        itsShell.pack();
        itsShell.open();
    }

    public static void
    main(String[] args)
    {

        new HelloWorldApp().start();
    }
}

//////////////////////////////////////////////////////////////////////////////
