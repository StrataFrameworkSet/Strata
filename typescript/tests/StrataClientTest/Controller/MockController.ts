/// <reference path='../Lib/StrataClient.ts'/>
/// <reference path='../View/IMockView.ts'/>
/// <reference path='../Model/IMockModel.ts'/>
/// <reference path='IMockController.ts'/>

module Strata1.Client.Controller
{
    import AbstractController = Strata1.Client.Controller.AbstractController;
    import INullProvider = Strata1.Client.Command.INullProvider;
    import IMockView = Strata1.Client.View.IMockView;
    import IMockModel = Strata1.Client.Model.IMockModel;

    export
    class MockController
        extends AbstractController<INullProvider,IMockView,IMockModel>
        implements IMockController
    {
        constructor(view: IMockView,model: IMockModel)
        {
            super();
            this.setView(view,this);
            this.setModel(model);
        }

        start(): void
        {
        }

        stop(): void
        {
        }
    }
}