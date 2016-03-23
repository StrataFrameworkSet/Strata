/// <reference path='StrataSqsIntegratorTest.ts'/>
/// <reference path='lib/Oscar.ts'/>

import CommitSuite = Strata1.SqsIntegrator.Suite.CommitSuite;

window.onload = function()
{
    var suite: CommitSuite = new CommitSuite();

    suite.run(TestSuiteOutput.HTML,5 * 1000);
};