//  ##########################################################################
//  # File Name: PhoneNumberTest.cs
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
    class PhoneNumberTest
    {

        [TestCase("(123)456-7890")]
        [TestCase("123-456-7890")]
        [TestCase("123.456.7890")]
        public void
        TestConstructorWithValidInput(string input)
        {
            new PhoneNumber(input);
        }

        [TestCase("#123.456.7890")]
        public void
        TestConstructorWithInvalidInput(string input)
        {
            Assert.Throws<ArgumentException>(() => new PhoneNumber(input));
        }
    }
}

//  ##########################################################################
