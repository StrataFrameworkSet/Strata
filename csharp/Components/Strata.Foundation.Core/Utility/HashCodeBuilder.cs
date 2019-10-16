//  ##########################################################################
//  # File Name: HashCodeBuilder.cs
//  # Copyright: 2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Foundation.Core.Utility
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Builds hash codes by appending the properties of a class that 
    /// contribute to its hash code value.
    /// </summary>
    ///  
    public
    class HashCodeBuilder
    {
        private const int PRIME_ENCODER = 127;

        public int HashCode { get; set; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>HashCodeBuilder</c> instance.
        /// </summary>
        /// 
        public
        HashCodeBuilder() :
            this(37) {}

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>HashCodeBuilder</c> instance.
        /// </summary>
        /// 
        /// <param name="seed">seed value of hash code</param>
        /// 
        public
        HashCodeBuilder(int seed)
        {
            HashCode = seed;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Appends a property to the class's hash code.
        /// </summary>
        /// 
        /// <param name="property">
        /// property that contributes to hash code
        /// </param>
        /// <returns>
        /// this <c>HashCodeBuilder</c> to enable method chaining
        /// </returns>
        /// 
        public HashCodeBuilder
        Append(int property)
        {
            HashCode = PRIME_ENCODER * HashCode + property;
            return this;
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Appends a property to the class's hash code.
        /// </summary>
        /// 
        /// <param name="property">
        /// property that contributes to hash code
        /// </param>
        /// <returns>
        /// this <c>HashCodeBuilder</c> to enable method chaining
        /// </returns>
        /// 
        public HashCodeBuilder
        Append(long property)
        {
            return Append(property.GetHashCode());
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Appends a property to the class's hash code.
        /// </summary>
        /// 
        /// <param name="property">
        /// property that contributes to hash code
        /// </param>
        /// <returns>
        /// this <c>HashCodeBuilder</c> to enable method chaining
        /// </returns>
        /// 
        public HashCodeBuilder
        Append(float property)
        {
            return Append(property.GetHashCode());
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Appends a property to the class's hash code.
        /// </summary>
        /// 
        /// <param name="property">
        /// property that contributes to hash code
        /// </param>
        /// <returns>
        /// this <c>HashCodeBuilder</c> to enable method chaining
        /// </returns>
        /// 
        public HashCodeBuilder
        Append(double property)
        {
            return Append(property.GetHashCode());
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Appends a property to the class's hash code.
        /// </summary>
        /// 
        /// <param name="property">
        /// property that contributes to hash code
        /// </param>
        /// <returns>
        /// this <c>HashCodeBuilder</c> to enable method chaining
        /// </returns>
        /// 
        public HashCodeBuilder
        Append(decimal property)
        {
            return Append(property.GetHashCode());
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Appends a property to the class's hash code.
        /// </summary>
        /// 
        /// <param name="property">
        /// property that contributes to hash code
        /// </param>
        /// <returns>
        /// this <c>HashCodeBuilder</c> to enable method chaining
        /// </returns>
        /// 
        public HashCodeBuilder
        Append(string property)
        {
            return
                Append(
                    property != null
                        ? property.GetHashCode()
                        : string.Empty.GetHashCode());
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Appends a property to the class's hash code.
        /// </summary>
        /// 
        /// <param name="property">
        /// property that contributes to hash code
        /// </param>
        /// <returns>
        /// this <c>HashCodeBuilder</c> to enable method chaining
        /// </returns>
        /// 
        public HashCodeBuilder
        Append(DateTime property)
        {
            return Append(property.GetHashCode());
        }
    }
}

//  ##########################################################################
