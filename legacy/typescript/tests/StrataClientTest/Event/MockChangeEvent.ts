/// <reference path='../Lib/StrataClient.ts'/>

module Strata1.Client.Event
{
    export
    class MockChangeEvent
        extends AbstractChangeEvent
    {
        constructor(sender: any)
        {
            super(sender);
        }
    }
}