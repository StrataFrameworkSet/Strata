/// <reference path='../Event/MockChangeEventTest.ts'/>
/// <reference path='../Model/MockModelTest.ts'/>
/// <reference path='../Controller/MockControllerTest.ts'/>
/// <reference path='../Lib/Oscar.ts'/>

module Strata1.Client.Suite
{
    import MockChangeEventTest = Strata1.Client.Event.MockChangeEventTest;
    import MockModelTest = Strata1.Client.Model.MockModelTest;
    import MockControllerTest = Strata1.Client.Controller.MockControllerTest;

    export
    class CommitSuite
        extends TestSuite
    {
        constructor()
        {
            super();
            this.add(new MockChangeEventTest());
            this.add(new MockModelTest());
            this.add(new MockControllerTest());
        }
    }
}