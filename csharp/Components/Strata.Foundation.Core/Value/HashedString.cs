//  ##########################################################################
//  # File Name: HashedString.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using Strata.Foundation.Core.Utility;
using System;
using System.Linq;
using System.Security.Cryptography;
using System.Text;

namespace Strata.Foundation.Core.Value
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// </summary>
    ///  
    public
    class HashedString:
        IEquatable<HashedString>
    {
        public byte[] Value { get; protected set; }
        public byte[] Salt { get; protected set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public
        HashedString(string unhashedValue):
            this(unhashedValue,Guid.NewGuid().ToByteArray()) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public
        HashedString(string unhashedValue,byte[] salt):
            this(Encoding.UTF8.GetBytes(unhashedValue),salt,false) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public
        HashedString(byte[] value,byte[] salt,bool hashed)
        {
            if (value == null)
                throw new NullReferenceException("Cannot hash null value");

            Salt  = salt;
            Value = hashed ? value : Hash(value,Salt);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public bool 
        Equals(HashedString other)
        {
            if (other == null)
                return false;

            if (ReferenceEquals(this,other))
                return true;

            return
                Value.SequenceEqual(other.Value) &&
                Salt.SequenceEqual(other.Salt);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public override bool
        Equals(Object other)
        {
            return other is HashedString && Equals((HashedString)other);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public override int
        GetHashCode()
        {
            int hash = 7;

            hash = 31 * hash + Value.GetHashCode();
            hash = 31 * hash + Salt.GetHashCode();
            return hash;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        ///  
        public override string 
        ToString()
        {
            return
                new StringBuilder()
                    .Append("Hash=")
                    .Append(Encoding.UTF8.GetString(Value))
                    .Append(",Salt=")
                    .Append(Encoding.UTF8.GetString(Salt))
                    .ToString();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public bool
        Matches(string unhashedValue)
        {
            return Matches(Encoding.UTF8.GetBytes(unhashedValue));
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        public bool
        Matches(byte[] unhashedValue)
        {
            return Value.SequenceEqual(Hash(unhashedValue,Salt));
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// </summary>
        ///  
        protected byte[]
        Hash(byte[] unhashedValue,byte[] salt)
        {
            return 
                new SHA256Managed().ComputeHash(
                    unhashedValue.Concat(salt).ToArray());
        }
    }
}

//  ##########################################################################
