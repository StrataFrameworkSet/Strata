//  ##########################################################################
//  # File Name: TaskExtensionTest.cs
//  ##########################################################################

using System;
using System.IO;
using System.Linq;
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
                TaskFactory
                    .SupplyAsyc(() => 23)
                    .ThenApply(result => result + 11)
                    .ThenCompose(result => Task.FromResult(result.ToString()))
                    .ThenConsume(result => Console.Out.WriteLine(result));
            
            task.Wait();

            Assert.IsTrue(task.IsCompletedSuccessfully);
        }

        [Test]
        public void
        TestWhenException()
        {
            Task task =
                TaskFactory
                    .SupplyAsyc(() => 23)
                    .ThenApply(
                        result =>
                        {
                            Console
                                .Out
                                .WriteLine("ThenApply");

                            if (result == 23)
                                throw new InvalidDataException();

                            return result + 11;
                        })
                    .ThenCompose(
                        result =>
                        {
                            Console
                                .Out
                                .WriteLine("ThenCompose");

                            return Task.FromResult(result.ToString());
                        })
                    .WhenException(
                        exception =>
                        {
                            Console
                                .Out
                                .WriteLine("WhenException");

                            Console
                                .Out
                                .WriteLine(exception.InnerExceptions.First().Message);

                            return string.Empty;
                        });

            task.Wait();

            Assert.IsTrue(task.IsCompletedSuccessfully);
        }

        [Test]
        public void
        TestException()
        {
            Task<string> task =
                TaskFactory
                    .SupplyAsyc(() => 23)
                    .ThenApply(
                        result =>
                        {
                            Console
                                .Out
                                .WriteLine("ThenApply");

                            if (result == 23)
                                throw new InvalidDataException();

                            return result + 11;
                        })
                    .ThenCompose(
                        result =>
                        {
                            Console
                                .Out
                                .WriteLine("ThenCompose");

                            return Task.FromResult(result.ToString());
                        });

            task.WaitNoThrow();

            Assert.IsTrue(task.IsFaulted);
            Assert.Throws<AggregateException>( () =>
            {
                string result = task.Result;
            });
        }
    }
}

//  ##########################################################################
