using System;

namespace Strata.Common.Exceptions
{
    /// <summary>
    /// An <see cref="Exception"/> thrown when a custom attribute cannot be found.
    /// </summary>
    public class AttributeNotFoundException : Exception
    {
        ///<summary>
        /// Extends the base <see cref="Exception"/> to provide a specific .
        ///</summary>
        public AttributeNotFoundException(string attributeName)
            : base(attributeName + " not found on class which requires it.  See stack trace for details.")
        {
        }
    }
}
