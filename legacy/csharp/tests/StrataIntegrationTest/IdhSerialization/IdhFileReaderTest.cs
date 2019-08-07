using Strata.Common.Serialization;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace Strata.Common.IdhSerialization
{
    [TestClass]
    public class 
    IdhFileReaderTest
    {
	    public const String DEFAULT_FILE_NAME = "test.txt";
	
	    private IdhFileReader target;

	    [TestCleanup]
	    public void TearDown()
        {
		    //RemoveFile(DEFAULT_FILE_NAME);
	    }

	    [TestMethod]
	    public void TestReadFile()
        {
		    List<string> fileData = new List<string>();
		    fileData.Add("H|This is the header");
            fileData.Add("D|1|USD|5|EUR|1.25|true|2015-01-01|2014-12-15 08:30:55.124|29839928383|||||||");
            fileData.Add("D|1|USD|7|GBP|.985|false|2016-01-01|2014-12-15 08:45:23.664|29839928383|||||||");
		    fileData.Add("T|This is the footer line");
		    CreateFile(DEFAULT_FILE_NAME, fileData);
		    List<NdfCurrencyPair> ndfList = ReadFile(DEFAULT_FILE_NAME);
		    VerifyResult(fileData, ndfList, true, true, 0);
	    }

	    [TestMethod]
	    public void TestReadNoDataLines()
        {
		    List<string> fileData = new List<string>();
		    fileData.Add("H|This is the header");
		    fileData.Add("T|This is the footer line");
		    CreateFile(DEFAULT_FILE_NAME, fileData);
		    List<NdfCurrencyPair> ndfList = ReadFile(DEFAULT_FILE_NAME);
		    VerifyResult(fileData, ndfList, true, true, 0);
	    }

	    [TestMethod]
	    public void TestReadNoHeader()
        {
		    List<string> fileData = new List<string>();
            fileData.Add("D|1|USD|5|EUR|1.25|true|2015-01-01|2014-12-15 08:30:55.124|29839928383|||||||");
            fileData.Add("D|1|USD|7|GBP|.985|false|2016-01-01|2014-12-15 08:45:23.664|29839928383|||||||");
		    fileData.Add("T|This is the footer line");
		    CreateFile(DEFAULT_FILE_NAME, fileData);
		    List<NdfCurrencyPair> ndfList = ReadFile(DEFAULT_FILE_NAME);
		    VerifyResult(fileData, ndfList, false, true, 0);
	    }

	    [TestMethod]
	    public void TestReadNoFooter()
        {
		    List<string> fileData = new List<string>();
		    fileData.Add("H|This is the header");
            fileData.Add("D|1|USD|5|EUR|1.25|true|2015-01-01|2014-12-15 08:30:55.124|29839928383|||||||");
            fileData.Add("D|1|USD|7|GBP|.985|false|2016-01-01|2014-12-15 08:45:23.664|29839928383|||||||");
		    CreateFile(DEFAULT_FILE_NAME, fileData);
		    List<NdfCurrencyPair> ndfList = ReadFile(DEFAULT_FILE_NAME);
		    VerifyResult(fileData, ndfList, true, false, 0);
	    }

	    [TestMethod]
	    public void TestReadEmptyFile()
        {
		    List<string> fileData = new List<string>();
		    CreateFile(DEFAULT_FILE_NAME, fileData);
		    List<NdfCurrencyPair> ndfList = ReadFile(DEFAULT_FILE_NAME);
		    Assert.IsTrue(ndfList.Count() == 0);
	    }

	    //@Rule public ExpectedException expectedException = ExpectedException.none();

	    [TestMethod]
	    public void TestReadBlankLines()
        {
		    List<string> fileData = new List<string>();
		    fileData.Add("H|This is the header");
		    fileData.Add("");
            fileData.Add("D|1|USD|5|EUR|1.25|true|2015-01-01|2014-12-15 08:30:55.124|29839928383|||||||");
		    fileData.Add("");
		    fileData.Add("T|This is the footer line");
		    CreateFile(DEFAULT_FILE_NAME, fileData);
		
		    List<NdfCurrencyPair> ndfList = ReadFile(DEFAULT_FILE_NAME);
		    VerifyResult(fileData, ndfList, true, true, 2);
	    }

	    [TestMethod]
        [ExpectedException(typeof(SerializationException))]
        public void TestReadBadData()
        {
		    List<string> fileData = new List<string>();
		    fileData.Add("H|This is the header");
		    fileData.Add("D|JUNK DATA");
		    fileData.Add("T|This is the footer line");
		    CreateFile(DEFAULT_FILE_NAME, fileData);
		
		    ReadFile(DEFAULT_FILE_NAME);
	    }

	    [TestMethod]
        [ExpectedException(typeof(SerializationException))]
        public void TestReadEmptyIntField()
        {
		    List<string> fileData = new List<string>();
		    fileData.Add("H|This is the header");
            fileData.Add("D|1|USD||EUR|1.25|true|2015-01-01|2014-12-15 08:30:55.124|29839928383|||||||");
		    fileData.Add("T|This is the footer line");
		    CreateFile(DEFAULT_FILE_NAME, fileData);
		    ReadFile(DEFAULT_FILE_NAME);
	    }

	    [TestMethod]
        [ExpectedException(typeof(SerializationException))]
        public void TestReadEmptyLongField()
        {
		    List<string> fileData = new List<string>();
		    fileData.Add("H|This is the header");
            fileData.Add("D|1|USD|5|EUR|1.25|true|2015-01-01|2014-12-15 08:30:55.124||||||||");
		    fileData.Add("T|This is the footer line");
		    CreateFile(DEFAULT_FILE_NAME, fileData);
		    ReadFile(DEFAULT_FILE_NAME);
	    }

	    [TestMethod]
        [ExpectedException(typeof(SerializationException))]
        public void TestReadEmptyStringField()
        {
		    List<string> fileData = new List<string>();
		    fileData.Add("H|This is the header");
            fileData.Add("D|1|USD|5||1.25|true|2015-01-01|2014-12-15 08:30:55.124|29839928383|||||||");
		    fileData.Add("T|This is the footer line");
		    CreateFile(DEFAULT_FILE_NAME, fileData);
		    ReadFile(DEFAULT_FILE_NAME);
	    }

	    [TestMethod]
        [ExpectedException(typeof(SerializationException))]
        public void TestReadEmptyDecimalField()
        {
		    List<string> fileData = new List<string>();
		    fileData.Add("H|This is the header");
            fileData.Add("D|1|USD|5|EUR||true|2015-01-01|2014-12-15 08:30:55.124|29839928383|||||||");
		    fileData.Add("T|This is the footer line");
		    CreateFile(DEFAULT_FILE_NAME, fileData);
		    ReadFile(DEFAULT_FILE_NAME);
	    }

	    [TestMethod]
        [ExpectedException(typeof(SerializationException))]
        public void TestReadEmptyDateField()
        {
		    List<string> fileData = new List<string>();
		    fileData.Add("H|This is the header");
            fileData.Add("D|1|USD|5|EUR|1.25|true||2014-12-15 08:30:55.124|29839928383|||||||");
		    fileData.Add("T|This is the footer line");
		    CreateFile(DEFAULT_FILE_NAME, fileData);
		    ReadFile(DEFAULT_FILE_NAME);
	    }

	    [TestMethod]
        [ExpectedException(typeof(SerializationException))]
        public void TestReadEmptyDateTimeField()
        {
		    List<string> fileData = new List<string>();
		    fileData.Add("H|This is the header");
            fileData.Add("D|1|USD|5|EUR|1.25|true|2015-01-01||29839928383|||||||");
		    fileData.Add("T|This is the footer line");
		    CreateFile(DEFAULT_FILE_NAME, fileData);
		    ReadFile(DEFAULT_FILE_NAME);
	    }

	    [TestMethod]
        [ExpectedException(typeof(SerializationException))]
        public void TestReadEmptyBooleanField()
        {
		    List<string> fileData = new List<string>();
		    fileData.Add("H|This is the header");
            fileData.Add("D|1|USD|5|EUR|1.25||2015-01-01|2014-12-15 08:30:55.124|29839928383|||||||");
		    fileData.Add("T|This is the footer line");
		    CreateFile(DEFAULT_FILE_NAME, fileData);
		    ReadFile(DEFAULT_FILE_NAME);
	    }

	    [TestMethod]
	    public void TestReadNullableFields() 
        {
		    List<String> fileData = new List<string>();
		    fileData.Add("H|This is the header");
		    fileData.Add("D|1|USD|5|EUR|1.25|true |2015-01-01|2014-12-15 08:30:55.124|29839928383|||||||");
		    fileData.Add("D|1|USD|7|GBP|.985|false|2016-01-01|2014-12-15 08:45:23.664|29839928383|1|2|1.2|true|abc|2015-01-01|2014-12-15 08:30:55.124");
		    fileData.Add("T|This is the footer line");
		    CreateFile(DEFAULT_FILE_NAME, fileData);
		    List<NdfCurrencyPair> ndfList = ReadFile(DEFAULT_FILE_NAME);
		    VerifyResult(fileData, ndfList, true, true, 0);
	    }
	
	    //[TestMethod] - not supported yet
	    public void TestReadDoubleQuotedField()
        {
		    List<string> fileData = new List<string>();
		    fileData.Add("H|This is the header");
		    fileData.Add("D|1|\"ABC|DEF\"|5|EUR");
		    fileData.Add("T|This is the footer line");
		    CreateFile(DEFAULT_FILE_NAME, fileData);
		    List<NdfCurrencyPair> ndfList = ReadFile(DEFAULT_FILE_NAME);
		    VerifyResult(fileData, ndfList, true, true, 0);
	    }

	    //[TestMethod] - not supported yet
	    public void TestReadEscapedDoubleQuote()
        {
		    List<string> fileData = new List<string>();
		    fileData.Add("H|This is the header");
		    fileData.Add("D|1|GEORGE \"W\" BUSH|5|EUR");
		    fileData.Add("T|This is the footer line");
		    CreateFile(DEFAULT_FILE_NAME, fileData);
		    List<NdfCurrencyPair> ndfList = ReadFile(DEFAULT_FILE_NAME);
		    VerifyResult(fileData, ndfList, true, true, 0);
	    }

	    private List<NdfCurrencyPair> ReadFile(String fileName) 
        {
            target = new IdhFileReader(fileName)
                .InsertConfiguration(Type.GetType("Strata.Common.IdhSerialization.NdfCurrencyPair"), 
                new NdfCurrencyFileConfiguration());
            List<NdfCurrencyPair> ndfList = new List<NdfCurrencyPair>();
		    target.Open();
            try
            {
                while (target.GetNext())
                {
                    NdfCurrencyPair ndf = new NdfCurrencyPair();
                    ndf.ReadFrom(target);
                    ndfList.Add(ndf);
                }
            }
            finally
            {
		        target.Close();
            }
		    return ndfList;
	    }

	    private void VerifyResult(List<string> data, List<NdfCurrencyPair> ndfList, bool hasHeader, bool hasFooter,
			    int numBlankLines) 
{
		    int nonDataLines = 0;
		    if (hasHeader) {
			    nonDataLines++;
		    }
		    if (hasFooter) {
			    nonDataLines++;
		    }
		    nonDataLines += numBlankLines;
		    Assert.AreEqual(data.Count()-nonDataLines, ndfList.Count());
		
		    List<string> lines = new List<string>();
		    lines.AddRange( data );
		    if (hasHeader)
			    lines.RemoveAt( 0 );
		    if (hasFooter)
			    lines.RemoveAt( lines.Count()-1 );
		
		    int index = 0;
		    foreach (string line in lines) {
			    if (line.Length > 0 && line.ElementAt( 0 ) =='D') {
				    NdfCurrencyPair ndf = ndfList[index++];
                    string[] strings = line.Substring(2).Split(new char[] { '|' });
				    // the following lines verify that if the file value is empty the object value is also empty, 
				    // and if the file value is not empty, the object value is also not empty.
				    if (strings[0].Trim().Equals( "" )) {
					    if (ndf.FromCurrencyId != 0)
						    Assert.Fail("FromCurrencyId is empty in file but non-zero in object");
				    }
				    else {
					    if (ndf.FromCurrencyId == 0)
                            Assert.Fail("FromCurrencyId is present in file but zero in object");
				    }
				    if (strings[1].Trim().Equals( "" )) {
					    if (!ndf.FromCurrencyCode.Equals( "" ))
                            Assert.Fail("FromCurrencyCode is empty in file but not empty in object");
				    }
				    else {
					    if (ndf.FromCurrencyCode.Equals(""))
                            Assert.Fail("FromCurrencyCode is present in file but empty in object");
				    }
				    if (strings[2].Trim().Equals( "" )) {
					    if (ndf.ToCurrencyId != 0)
                            Assert.Fail("ToCurrencyId is empty in file but non-zero in object");
				    }
				    else {
					    if (ndf.ToCurrencyId == 0)
                            Assert.Fail("ToCurrencyId is present in file but zero in object");
				    }
				    if (strings[3].Trim().Equals( "" )) {
					    if (!ndf.ToCurrencyCode.Equals( "" ))
                            Assert.Fail("ToCurrencyCode is empty in file but not empty in object");
				    }
				    else {
					    if (ndf.ToCurrencyCode.Equals(""))
						    Assert.Fail("ToCurrencyCode is present in file but empty in object");
				    }
				    if (strings[4].Trim().Equals( "" )) {
					    if (ndf.ExchangeRate != 0.0m)
                            Assert.Fail("ExchangeRate is empty in file but non-zero in object");
				    }
				    else {
					    if (ndf.ExchangeRate == 0.0m)
                            Assert.Fail("ExchangeRate is present in file but null in object");
				    }
				    if (strings[6].Trim().Equals( "" )) {
					    if (ndf.ActiveDate != null)
                            Assert.Fail("ActiveDate is empty in file but not null in object");
				    }
				    else {
					    if (ndf.ActiveDate == null)
                            Assert.Fail("ActiveDate is present in file but null in object");
				    }
				    if (strings[7].Trim().Equals( "" )) {
					    if (ndf.CreatedDateTime != null)
                            Assert.Fail("CreatedDateTime is empty in file but not null in object");
				    }
				    else {
					    if (ndf.CreatedDateTime == null)
                            Assert.Fail("CreatedDateTime is present in file but null in object");
				    }
				    if (strings[8].Trim().Equals( "" )) {
					    if (ndf.SecurityGuid != 0)
                            Assert.Fail("securityGuid is empty in file but non-zero in object");
				    }
				    else {
					    if (ndf.SecurityGuid == 0)
						    Assert.Fail("securityGuid is present in file but zero in object");
				    }
				    if (strings[9].Trim().Equals( "" )) {
					    if (ndf.NullableInt != null)
                            Assert.Fail("nullableInt is empty in file but not null in object");
				    }
				    else {
					    if (ndf.NullableInt == null)
                            Assert.Fail("nullableInt is present in file but null in object");
				    }
				    if (strings[10].Trim().Equals( "" )) {
					    if (ndf.NullableLong != null)
                            Assert.Fail("nullableLong is empty in file but not null in object");
				    }
				    else {
					    if (ndf.NullableLong == null)
                            Assert.Fail("nullableLong is present in file but null in object");
				    }
				    if (strings[11].Trim().Equals( "" )) {
					    if (ndf.NullableDecimal != null)
                            Assert.Fail("nullableDecimal is empty in file but not null in object");
				    }
				    else {
					    if (ndf.NullableDecimal == null)
                            Assert.Fail("nullableDecimal is present in file but null in object");
				    }
				    if (strings[12].Trim().Equals( "" )) {
					    if (ndf.NullableBoolean != null)
                            Assert.Fail("nullableBoolean is empty in file but not null in object");
				    }
				    else {
					    if (ndf.NullableBoolean == null)
                            Assert.Fail("nullableBoolean is present in file but null in object");
				    }
				    if (strings[13].Trim().Equals( "" )) {
					    if (ndf.NullableDate != null)
                            Assert.Fail("nullableDate is empty in file but not null in object");
				    }
				    else {
					    if (ndf.NullableDate == null)
                            Assert.Fail("nullableDate is present in file but null in object");
				    }
				    if (strings[14].Trim().Equals( "" )) {
					    if (ndf.NullableDateTime != null)
                            Assert.Fail("nullableDateTime is empty in file but not null in object");
				    }
				    else {
					    if (ndf.NullableDateTime == null)
                            Assert.Fail("nullableDateTime is present in file but null in object");
				    }
			    }
		    }
	    }

	    private void CreateFile(String fileName, List<string> lines) 
        {
		    new TestFileCreator().Write(fileName, lines);		
	    }

	    private static void RemoveFile(String defaultFileName) 
        {
		    File.Delete(defaultFileName);
	    }
    }
}
