namespace Strata.Common.Utility
{
    /// <summary>
    /// A static class providing some extended <see cref="string"/> utility.
    /// </summary>
    public static class StringTools
    {
        /// <summary>
        /// Attempts to trim whitespace (spaces) from the given string using <see cref="string.Trim()"/>. If 
        /// the given string is null or empty, this method returns <see cref="string.Empty"/>.
        /// </summary>
        /// <param name="stringToTrim">The string to trim.</param>
        /// <returns>The given string minus any whitespace.  </returns>
        public static string TryTrim(string stringToTrim)
        {
            return string.IsNullOrEmpty(stringToTrim)
                       ? string.Empty
                       : stringToTrim.Trim();
        }
    }
}
