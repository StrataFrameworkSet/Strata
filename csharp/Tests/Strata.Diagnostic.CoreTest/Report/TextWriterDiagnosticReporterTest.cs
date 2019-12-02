//  ##########################################################################
//  # File Name: TextWriterDiagnosticReporterTest.cs
//  ##########################################################################

using System;
using System.Threading.Tasks;
using NUnit.Framework;
using Strata.Diagnostic.Core.Common;
using Strata.Diagnostic.Core.Evaluation;

namespace Strata.Diagnostic.Core.Report
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    [TestFixture]
    public
    class TextWriterDiagnosticReporterTest
    {
        private TextWriterDiagnosticReporter itsTarget;
        private FreeMemoryCheck              checkFreeMemory;
        private DirectoryExistsCheck         checkDirectoryExists;
        private IDiagnostic                  checkMock;
        private DiagnosticSuite              suite;

        [SetUp]
        public void 
        SetUp()
        {
            itsTarget = new TextWriterDiagnosticReporter(Console.Out);

            checkFreeMemory = new FreeMemoryCheck("FreeMemory");
            checkFreeMemory.FreeMemoryBytesMinimum = 1000;
            checkDirectoryExists = new DirectoryExistsCheck("TempExists");
            checkDirectoryExists.Path = @"C:\Temp";
            checkMock = new MockDiagnosticCheck("MockCheck",false,true,true,false);

            suite = new DiagnosticSuite("Suite");
            suite
                .AddDiagnostic(checkFreeMemory)
                .AddDiagnostic(checkDirectoryExists)
                .AddDiagnostic(checkMock);
        }

        [TearDown]
        public void
        TearDown()
        {
            itsTarget = null;
        }

        [Test]
        public async Task
        TestReporting()
        {
            IDiagnosticResult result = new DefaultDiagnosticResult();

            result.AttachReporter(itsTarget);
            await suite.RunDiagnostic(result);
        }

    }
}

//  ##########################################################################
