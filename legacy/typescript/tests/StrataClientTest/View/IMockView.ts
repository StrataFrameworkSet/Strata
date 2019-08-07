/// <reference path='../Lib/StrataClient.ts'/>

module Strata1.Client.View 
{
    import INullProvider = Strata1.Client.Command.INullProvider;

    export
    interface IMockView
        extends IView<INullProvider> {}
}