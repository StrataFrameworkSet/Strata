using System.IO;
using System.Text;
using Strata.Common.Utility;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Strata.Common
{
    [TestClass]
    public class FileToolsTests
    {
        private const string TestFileName1 = "testFile1.txt"; 
        private const string TestFileName2 = "testFile2.txt";
        private const string OutputPath = "package.zip"; 

        [TestInitialize]
        public void Initialize()
        {
            var sb = new StringBuilder();
            for (int i = 0; i < 100; i++)
                sb.Append("Text       And Whitespace      ");

            using (var w = new StreamWriter(TestFileName1)) 
                w.Write(sb.ToString()); 

            using (var w = new StreamWriter(TestFileName2))
                w.Write(sb.ToString());
        }

        [TestMethod]
        public void CanCompressAndDecompressFile()
        {
            FileTools.Compress(new FileInfo(TestFileName1));

            Assert.IsTrue(File.Exists(TestFileName1 + ".gz"));

            File.Delete(TestFileName1);
            FileTools.Decompress(new FileInfo(TestFileName1 + ".gz"));

            Assert.IsTrue(File.Exists(TestFileName1));
        }

        [TestMethod]
        public void CanCompressTwoFilesIntoPackage()
        {
            FileTools.CompressAndPackageFiles(OutputPath, TestFileName1, TestFileName2);
            Assert.IsTrue(File.Exists(OutputPath));
        }

        [TestCleanup]
        public void Cleanup()
        {
            if (File.Exists(TestFileName1)) 
                File.Delete(TestFileName1);
            if (File.Exists(TestFileName1 + ".gz")) 
                File.Delete(TestFileName1 + ".gz");
            if (File.Exists(TestFileName2)) 
                File.Delete(TestFileName2);
            if (File.Exists(OutputPath)) 
                File.Delete(OutputPath);
        }
    }
}
