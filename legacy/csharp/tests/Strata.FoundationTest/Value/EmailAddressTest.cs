//  ##########################################################################
//  # File Name: EmailAddressTest.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using NUnit.Framework;
using System;

namespace Strata.Foundation.Value
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// 
    /// </summary>
    ///  
    [TestFixture]
    public
    class EmailAddressTest
    {

        [SetUp]
        public void
        SetUp()
        {

        }

        [TearDown]
        public void
        TearDown()
        {

        }

        [TestCase("abc@xyz.com")]
        [TestCase("first.last@xyz.com")]
        public void
        TestConstructorWithValidInput(string input)
        {
            EmailAddress email = new EmailAddress(input);
        }

        [TestCase("abc@xyz@com")]
        [TestCase("first#last@xyz.com")]
        public void
        TestConstructorWithInvalidInput(string input)
        {

            Assert.Throws<ArgumentException>(() => new EmailAddress(input));
        }
    }
}

//  ##########################################################################
