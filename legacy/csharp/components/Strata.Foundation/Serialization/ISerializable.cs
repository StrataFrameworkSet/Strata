//  ##########################################################################
//  # File Name: ISerializable.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################


namespace Strata.Foundation.Serialization
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Supports the Serialization pattern for reading/writing 
    /// files, databases, streams, etc.
    /// </summary>
    /// 
    public interface ISerializable
    {
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Reads in the object's fields from the reader.
        /// </summary>
        /// 
	    void 
        ReadFrom(IObjectReader reader);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Writes the object's fields using the writer.
        /// </summary>
        /// 
	    void 
        WriteTo(IObjectWriter writer);
    }
}
