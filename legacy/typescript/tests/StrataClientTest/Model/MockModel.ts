/// <reference path='../Lib/StrataClient.ts'/>
/// <reference path='../Event/MockChangeEvent.ts'/>

module Strata1.Client.Model
{
    import AbstractModel = Strata1.Client.Model.AbstractModel;
    import MockChangeEvent = Strata1.Client.Event.MockChangeEvent;

    export
    class MockModel
        extends AbstractModel
    {
        constructor()
        {
            super();
        }

        simulateChange(): void
        {
            this.notifyChange(new MockChangeEvent(this));
        }
    }
}