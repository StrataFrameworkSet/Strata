/// <reference path='../Lib/StrataClient.ts'/>

module Strata1.Client.View
{
    import AbstractView = Strata1.Client.View.AbstractView;
    import INullProvider = Strata1.Client.Command.INullProvider;

    export
    class MockView
        extends AbstractView<INullProvider>
        implements IMockView
    {
        constructor()
        {
            super("MockView");
        }
    }
}