//  ##########################################################################
//  # File Name: TaskExtensionTest.cs
//  ##########################################################################

using System;
using System.Threading.Tasks;
using NUnit.Framework;

namespace Strata.Foundation.Core.Utility
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    [TestFixture]
    public
    class TaskExtensionTest
    {
        [Test]
        public void
        TestThen()
        {
            Task task =
                Task
                    .FromResult(23)
                    .ThenApply(result => result + 11)
                    .ThenCompose(result => Task.FromResult(result.ToString()))
                    .ThenConsume(result => Console.Out.WriteLine(result));

            task.Wait();

            Assert.IsTrue(task.IsCompletedSuccessfully);
        }
    }
}

//  ##########################################################################
