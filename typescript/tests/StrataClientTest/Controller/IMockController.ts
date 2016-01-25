/// <reference path='../Lib/StrataClient.ts'/>
/// <reference path='../View/IMockView.ts'/>
/// <reference path='../Model/IMockModel.ts'/>

module Strata1.Client.Controller
{
    import IController = Strata1.Client.Controller.IController;
    import INullProvider = Strata1.Client.Command.INullProvider;
    import IMockView = Strata1.Client.View.IMockView;
    import IMockModel = Strata1.Client.Model.IMockModel;

    export
    interface IMockController
        extends INullProvider,IController<INullProvider,IMockView,IMockModel> {}
}