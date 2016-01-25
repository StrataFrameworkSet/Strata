using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

namespace Strata.Common.IdhSerialization
{
    public class 
    TestFileCreator
    {
	    public void Write(string fileName, List<string> lines)
	    {
		    StreamWriter writer = new StreamWriter(fileName);
		    foreach (String line in lines)
		    {
			    writer.WriteLine( line );
		    }
		    writer.Close();
	    }
    }
}
