/// <reference path='IMockController.ts'/>
/// <reference path='MockController.ts'/>
/// <reference path='../View/IMockView.ts'/>
/// <reference path='../View/MockView.ts'/>
/// <reference path='../Model/IMockModel.ts'/>
/// <reference path='../Model/MockModel.ts'/>
/// <reference path='../Lib/Oscar.ts'/>

module Strata1.Client.Controller
{
    import IMockView = Strata1.Client.View.IMockView;
    import MockView = Strata1.Client.View.MockView;
    import IMockModel = Strata1.Client.Model.IMockModel;
    import MockModel = Strata1.Client.Model.MockModel;

    export
    class MockControllerTest
        extends UnitTestClass
    {
        private view: IMockView;
        private model: IMockModel;
        private target: IMockController;

        setUp(): void
        {
            this.view = new MockView();
            this.model = new MockModel();
            this.target = new MockController(this.view,this.model);
        }

        tearDown(): void
        {
            this.target = null;
        }

        testGetView(): void
        {
            Assert.areEqual(this.view,this.target.getView());
        }

        testGetModel(): void
        {
            Assert.areEqual(this.model,this.target.getModel());
        }
    }
}