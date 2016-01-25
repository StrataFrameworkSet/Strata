/// <reference path='Suite/CommitSuite.ts'/>
/// <reference path='Lib/Oscar.ts'/>

import CommitSuite = Strata1.Client.Suite.CommitSuite;

window.onload = function()
{
    var suite: CommitSuite = new CommitSuite();

    suite.run(TestSuiteOutput.HTML,5 * 1000);
};