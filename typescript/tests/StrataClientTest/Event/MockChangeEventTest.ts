/// <reference path='MockChangeEvent.ts'/>
/// <reference path='../Lib/Oscar.ts'/>

module Strata1.Client.Event
{
     
    export
    class MockChangeEventTest
        extends UnitTestClass
    {
        private target: MockChangeEvent;

        setUp(): void
        {
            this.target = new MockChangeEvent("mock");
        }

        tearDown(): void
        {
            this.target = null;
        }

        testGetSender(): void
        {
            Assert.areEqual("mock",this.target.getSender<String>());
        }
    }
}