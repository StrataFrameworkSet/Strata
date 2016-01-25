//  ##########################################################################
//  # File Name: IdhFileWriter.cs
//  # Copyright: 2015, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Strata.Common.IdhSerialization
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Implements Serialization pattern for writing files.  
    /// This class writes IDH-formatted files containing a header, 
    /// multiple data lines, and a trailing footer.
    /// </summary>
    /// 
    /// <author>MKCE</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public class IdhFileWriter
    {
	    protected String filePath;
	    protected StreamWriter writer;
	    protected IIdhFileConfiguration config;
	    protected List<String> tokens;

	    protected string dateFormat = null;
        protected string dateTimeFormat = null;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Constructs an IdhFileWriter using a file path and a configuration.
        /// </summary>
        /// 
	    public IdhFileWriter(String filePathAndName, IIdhFileConfiguration fileConfig) {
		    filePath = filePathAndName;
		    config = fileConfig;
		    tokens = new List<String>();
	    }
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Writes attribute [name] of type int to the current line.
        /// </summary>
        /// 
	    public void 
        WriteInt(String name, int value) {
		    // TODO Auto-generated method stub

	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Writes attribute [name] of type long to the current line.
        /// </summary>
        /// 
	    public void 
        WriteLong(String name, long value) {
		    // TODO Auto-generated method stub

	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Writes attribute [name] of type string to the current line.
        /// </summary>
        /// 
	    public void 
        WriteString(String name, String value) {
		    // TODO Auto-generated method stub

	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Writes attribute [name] of type date to the current line.
        /// </summary>
        /// 
	    public void 
        WriteDate(String name, DateTime date) {
		    // TODO Auto-generated method stub

	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Writes attribute [name] of type date to the current line.
        /// </summary>
        /// 
        public void
        WriteDateTime(String name, DateTime date)
        {
            // TODO Auto-generated method stub

        }

    }
}
