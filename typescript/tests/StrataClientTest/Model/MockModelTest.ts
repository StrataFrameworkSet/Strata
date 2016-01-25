/// <reference path='MockModel.ts'/>
/// <reference path='../Lib/StrataClient.ts'/>
/// <reference path='../Lib/Oscar.ts'/>

module Strata1.Client.Model
{
    import IChangeEventProcessor = Strata1.Client.Event.IChangeEventProcessor;
    import IChangeEvent = Strata1.Client.Event.IChangeEvent;

    export
    class MockModelTest
        extends UnitTestClass
        implements IChangeEventProcessor
    {
        private target: MockModel;
        private notified: boolean;

        setUp(): void
        {
            this.target = new MockModel();
            this.target.setProcessor(this);
            this.notified = false;
        }

        tearDown(): void
        {
            this.target = null;
        }

        testNotifyChange(): void
        {
            this.target.simulateChange();
            Assert.isTrue(this.notified);
        }

        processChange(change: IChangeEvent): void
        {
            Assert.isNotNull(change.getSender<MockModel>());
            this.notified = true;
        }
    }
}