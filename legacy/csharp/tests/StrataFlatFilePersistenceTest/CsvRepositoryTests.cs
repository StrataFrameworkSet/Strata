using System.Collections.Generic;
using System.IO;
using Capgroup.Xwing.FlatFilePersistence.CsvRepository;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Capgroup.Xwing.FlatFilePersistence
{
    [TestClass]
    public class CsvRepositoryTests
    {
        private const string FileWithHeader = "test_with_header.csv";
        private const string FileWithoutHeader = "test_without_header.csv";
        private const string TestPath = @"\main\csharp\tests\XwingCsvPersistenceTestSuite";


        private const int IntExpected = 1;
        private const double DoubleExpected = 100.1;
        private const string StringExpected = "Test";
        private const string SpacedStringExpected = "Test Test";
        private const string CommaStringExpected = "Test,Test";

        [TestMethod]
        public void TestDataParsingNoHeader()
        {
            foreach (var actual in Setup(FileWithoutHeader))
            {
                AssertRecordData(actual);
            }
        }
        

        [TestMethod]
        public void TestDataParsingHeader()
        {
            foreach (var actual in Setup(FileWithHeader))
            {
                AssertRecordData(actual);
            }
        }

        private IEnumerable<TestFile> Setup(string fileName)
        {
            var context = new CsvRepositoryContext(TestPath, Path.Combine(TestPath, fileName), true);
            var repository = new CsvRepositoryProvider<int, TestFile>(context);
            var results = repository.GetAllEntities();
            return results;
        }

        private static void AssertRecordData(TestFile actual)
        {
            Assert.IsNotNull(actual);
            Assert.AreEqual(IntExpected, actual.Int);
            Assert.AreEqual(DoubleExpected, actual.Double);
            Assert.AreEqual(StringExpected, actual.String);
            Assert.AreEqual(SpacedStringExpected, actual.SpacedString);
            //Assert.AreEqual(commaStringExpected, actual.CommaString);
        }
    }
}
