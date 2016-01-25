//  ##########################################################################
//  # File Name: IdhFileReader.cs
//  # Copyright: 2015, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Common.Serialization;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Strata.Common.IdhSerialization
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Implements Serialization pattern for reading files.  
    /// This class reads IDH-formatted files containing a header, 
    /// multiple data lines, and a trailing footer.
    /// </summary>
    /// 
    /// <author>MKCE</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public class IdhFileReader : IObjectReader
    {
	    public const string IDH_DATE_FORMAT = "yyyy-MM-dd";
	    public const string IDH_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss.fff";
	    public const string HEADER_INDICATOR_CHAR 	= "H";
	    public const string DATA_INDICATOR_CHAR 	= "D";
	    public const string FOOTER_INDICATOR_CHAR 	= "T";
	
	    protected string filePath;
	    protected StreamReader reader;
	    protected IIdhFileConfiguration config;
	    protected Dictionary<Type, IIdhFileConfiguration> configurations;
	    protected Stack<IIdhFileConfiguration> context;
	    protected List<string> tokens;
	    protected string nextLine = null;

        protected string dateFormat = null;
        protected string dateTimeFormat = null;
        protected CultureInfo cultureInfo = new CultureInfo("en-US");

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Constructs an IdhFileReader using a file path and a configuration.
        /// </summary>
        /// 
	    public IdhFileReader(String filePathAndName) 
        {
		    filePath = filePathAndName;
		    configurations = new Dictionary<Type, IIdhFileConfiguration>();
		    context = new Stack<IIdhFileConfiguration>();
		    tokens = new List<String>();
	    }
	
	    public IdhFileReader 
	    InsertConfiguration(Type key, IIdhFileConfiguration config) 
        {
		    configurations.Add(key, config);
		    return this;
	    }
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Gets attribute by name from the current line and converts to an int.
        /// If the value is null, an exception is thrown.
        /// </summary>
        /// 
        public int ReadRequiredInt(String name) 
        {
            int index = GetCurrent().GetAttributeIndex(name);
		    return GetAttributeAsRequiredInt(name, index);
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Gets attribute by name from the current line and converts to a long.
        /// If the value is null, an exception is thrown.
        /// </summary>
        /// 
	    public long
        ReadRequiredLong(String name)
        {
            int index = GetCurrent().GetAttributeIndex(name);
            return GetAttributeAsRequiredLong(name, index);
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Gets attribute by name from the current line and converts to a Big Decimal.
        /// If the value is null, an exception is thrown.
        /// </summary>
        /// 
	    public decimal
        ReadRequiredDecimal(String name) 
        {
            int index = GetCurrent().GetAttributeIndex(name);
            return GetAttributeAsRequiredDecimal(name, index);
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Gets attribute by name from the current line and converts to a boolean.
        /// If the value is null, an exception is thrown.
        /// </summary>
        /// 
	    public bool
        ReadRequiredBoolean(String name) 
        {
            int index = GetCurrent().GetAttributeIndex(name);
            return GetAttributeAsRequiredBoolean(name, index);
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Gets attribute by name from the current line and converts to a string.
        /// If the value is null, an exception is thrown.
        /// </summary>
        /// 
	    public String
        ReadRequiredString(String name) 
        {
            int index = GetCurrent().GetAttributeIndex(name);
            return GetAttributeAsRequiredString(name, index);
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Gets attribute by name from the current line and converts to a DateTime.
        /// If the value is null, an exception is thrown.
        /// </summary>
        /// 
	    public DateTime
        ReadRequiredDate(String name) 
        {
            int index = GetCurrent().GetAttributeIndex(name);
            return GetAttributeAsRequiredDate(name, index);
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Gets attribute by name from the current line and converts to a DateTime.
        /// If the value is null, an exception is thrown.
        /// </summary>
        /// 
        public DateTime
        ReadRequiredDateTime(String name)
        {
            int index = GetCurrent().GetAttributeIndex(name);
            return GetAttributeAsRequiredDateTime(name, index);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Gets attribute by name from the current line and converts to an int.
        /// If the value is null, an exception is thrown.
        /// </summary>
        /// 
        public int? 
        ReadNullableInt(String name)
        {
            int index = GetCurrent().GetAttributeIndex(name);
            return GetAttributeAsInt(name, index);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Gets attribute by name from the current line and converts to a long.
        /// If the value is null, an exception is thrown.
        /// </summary>
        /// 
        public long?
        ReadNullableLong(String name)
        {
            int index = GetCurrent().GetAttributeIndex(name);
            return GetAttributeAsLong(name, index);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Gets attribute by name from the current line and converts to a Big Decimal.
        /// If the value is null, an exception is thrown.
        /// </summary>
        /// 
        public decimal?
        ReadNullableDecimal(String name)
        {
            int index = GetCurrent().GetAttributeIndex(name);
            return GetAttributeAsDecimal(name, index);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Gets attribute by name from the current line and converts to a boolean.
        /// If the value is null, an exception is thrown.
        /// </summary>
        /// 
        public bool?
        ReadNullableBoolean(String name)
        {
            int index = GetCurrent().GetAttributeIndex(name);
            return GetAttributeAsBoolean(name, index);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Gets attribute by name from the current line and converts to a string.
        /// If the value is null, an exception is thrown.
        /// </summary>
        /// 
        public string
        ReadNullableString(String name)
        {
            int index = GetCurrent().GetAttributeIndex(name);
            return tokens.ElementAt(index);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Gets attribute by name from the current line and converts to a DateTime.
        /// If the value is null, an exception is thrown.
        /// </summary>
        /// 
        public DateTime?
        ReadNullableDate(String name)
        {
            int index = GetCurrent().GetAttributeIndex(name);
            return GetAttributeAsDate(name, index);
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Gets attribute by name from the current line and converts to a DateTime.
        /// If the value is null, an exception is thrown.
        /// </summary>
        /// 
        public DateTime?
        ReadNullableDateTime(String name)
        {
            int index = GetCurrent().GetAttributeIndex(name);
            return GetAttributeAsDateTime(name, index);
        }

        public void 
        PushRoot(ISerializable obj) 
        {
		    Type key = obj.GetType();
		    IIdhFileConfiguration config = configurations[key];
		    if (config == null) {
			    throw new SerializationException("Could not find configuration for passed class type.");
		    }
		    context.Push(config);
	    }

	    public void 
        PopRoot()
        {
            if (context.Count() > 0)
            {
                context.Pop();
            }
        }

	    public bool 
        GetNext()
        {
            tokens.Clear();
		    nextLine = GetNextDataLine();
		    if (nextLine != null) {
			    String[] strings = nextLine.Split(new char[] {'|'} );
			    for (int i = 0; i < strings.Length; i++) {
				    tokens.Add(strings[i]);
			    }
		    }

		    return nextLine != null;
	    }
	
	    public void 
        Open() 
        {
            reader = new StreamReader(filePath);
	    }
	
	    public void 
        Close() 
        {
		    if (reader != null) {
			    reader.Close();
                reader.Dispose();
		    }
	    }

	    protected string 
        GetDateFormat() 
        {
		    if (dateFormat == null) {
                dateFormat = IDH_DATE_FORMAT;
		    }
            return dateFormat;
	    }
	
	    public void 
        SetDateFormat(string format) 
        {
            dateFormat = format;
	    }

        protected string
        GetDateTimeFormat()
        {
            if (dateTimeFormat == null)
            {
                dateTimeFormat = IDH_DATETIME_FORMAT;
            }
            return dateTimeFormat;
        }

        public void
        SetDateTimeFormat(string format)
        {
            dateTimeFormat = format;
        }

	    private IIdhFileConfiguration 
	    GetCurrent()
        {
		    if (context.Count() == 0) {
			    throw new SerializationException("No configuration available.");
		    }
		    return context.Peek();
	    }

        private String 
        GetNextDataLine() 
        {
		    String line = null;
			while(true) {
				line = reader.ReadLine();
				if (line == null) {
					return null;
				}
				if (line.Length == 0) {
					continue;
				}
				else if (line.StartsWith(DATA_INDICATOR_CHAR)) {
					return line.Substring(2);
				}
				else if (line.StartsWith(HEADER_INDICATOR_CHAR)) {
					continue;
				}
				else if (line.StartsWith(FOOTER_INDICATOR_CHAR)) {
					return null;
				}
			}
	    }

	    private int? 
        GetAttributeAsInt(String name, int index) 
        {
		    String value = null;
		    try {
                value = tokens.ElementAt(index).Trim();
                if (value.Equals(""))
                {
                    return null;
                }
                return Convert.ToInt32(value);
		    }
		    catch(Exception) {
			    throw new SerializationException("Could not parse int from value " + value + 
					    " for attribute name " + name +
					    " at index " + index);
		    }
	    }

	    private long? 
        GetAttributeAsLong(String name, int index) 
        {
		    String value = null;
		    try {
                value = tokens.ElementAt(index).Trim();
                if (value.Equals(""))
                {
                    return null;
                }
                return Convert.ToInt64(value);
		    }
		    catch(Exception) {
			    throw new SerializationException("Could not parse long from value " + value + 
					    " for attribute name " + name +
					    " at index " + index);
		    }
	    }

	    private DateTime? 
        GetAttributeAsDate(String name, int index) 
        {
		    String value = null;
		    try {
                value = tokens.ElementAt(index).Trim();
			    if (value.Equals("")) {
				    return null;
			    }
			    return DateTime.ParseExact(value, GetDateFormat(), cultureInfo);
		    }
		    catch(Exception e) {
			    throw new SerializationException("Could not parse date from value " + value + 
					    " for attribute name " + name +
					    " at index " + index +
                        " using format " + GetDateFormat() +
					    " \n" + e);
		    }
	    }

        private DateTime?
        GetAttributeAsDateTime(String name, int index)
        {
            String value = null;
            try
            {
                value = tokens.ElementAt(index).Trim();
                if (value.Equals(""))
                {
                    return null;
                }
                return DateTime.ParseExact(value, GetDateTimeFormat(), cultureInfo);
            }
            catch (Exception e)
            {
                throw new SerializationException("Could not parse date/time from value " + value +
                        " for attribute name " + name +
                        " at index " + index +
                        " using format " + GetDateTimeFormat() +
                        " \n" + e);
            }
        }

        private bool? 
        GetAttributeAsBoolean(String name, int index) 
        {
		    String value = null;
            value = tokens.ElementAt(index).Trim();
            if (value.Equals(""))
            {
                return null;
            }
            else if (value.ToLower().Equals("true"))
            {
			    return true;
		    }
		    else if (value.ToLower().Equals("false")) {
			    return false;
		    }
		    else {
			    throw new SerializationException("Could not parse boolean from value " + value + 
					    " for attribute name " + name +
					    " at index " + index);
		    }
	    }

	    private decimal? 
        GetAttributeAsDecimal(String name, int index) 
        {
		    String value = null;
		    try {
                value = tokens.ElementAt(index).Trim();
			    if (value.Equals("")) {
				    return null;
			    }
                return Convert.ToDecimal(value);
		    }
		    catch(Exception) {
			    throw new SerializationException("Could not create decimal from value " + value + 
					    " for attribute name " + name +
					    " at index " + index);
		    }
	    }

        private string
        GetAttributeAsString(String name, int index)
        {
            return tokens.ElementAt(index).Trim();
        }

        private int
        GetAttributeAsRequiredInt(String name, int index)
        {
            int? value = GetAttributeAsInt(name, index);
            if (!value.HasValue)
            {
                throw new SerializationException("Required int field " + name + " was null");
            }
            return (int)value;
        }

        private long
        GetAttributeAsRequiredLong(String name, int index)
        {
            long? value = GetAttributeAsLong(name, index);
            if (!value.HasValue)
            {
                throw new SerializationException("Required long field " + name + " was null");
            }
            return (long)value;
        }

        private DateTime
        GetAttributeAsRequiredDate(String name, int index)
        {
            DateTime? value = GetAttributeAsDate(name, index);
            if (!value.HasValue)
            {
                throw new SerializationException("Required date field " + name + " was null");
            }
            return (DateTime)value;
        }

        private DateTime
        GetAttributeAsRequiredDateTime(String name, int index)
        {
            DateTime? value = GetAttributeAsDateTime(name, index);
            if (!value.HasValue)
            {
                throw new SerializationException("Required date/time field " + name + " was null");
            }
            return (DateTime)value;
        }

        private bool
        GetAttributeAsRequiredBoolean(String name, int index)
        {
            bool? value = GetAttributeAsBoolean(name, index);
            if (!value.HasValue)
            {
                throw new SerializationException("Required boolean field " + name + " was null");
            }
            return (bool)value;
        }

        private decimal
        GetAttributeAsRequiredDecimal(String name, int index)
        {
            decimal? value = GetAttributeAsDecimal(name, index);
            if (!value.HasValue)
            {
                throw new SerializationException("Required decimal field " + name + " was null");
            }
            return (decimal)value;
        }

        private string
        GetAttributeAsRequiredString(String name, int index)
        {
            string value = GetAttributeAsString(name, index);
            if (value.Equals(""))
            {
                throw new SerializationException("Required string field " + name + " was null");
            }
            return value;
        }

    }
}
