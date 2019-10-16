//  ##########################################################################
//  # File Name: PersonName.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System.Runtime.Serialization;

namespace Strata.Foundation.Core.Value
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    [DataContract]
    public
    class PersonName
    {
        [DataMember]
        public string FirstName { get; set; }
        [DataMember]
        public string MiddleName { get; set; }
        [DataMember]
        public string LastName { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public
        PersonName():
            this(string.Empty,string.Empty,string.Empty) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public
        PersonName(string first,string last):
            this(first,string.Empty,last) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public
        PersonName(string first,string middle,string last)
        {
            FirstName = first;
            MiddleName = middle;
            LastName = last;
        }
    }
}

//  ##########################################################################
