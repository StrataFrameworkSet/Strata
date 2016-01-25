using System;
using System.Configuration;
using System.IO;
using System.Linq;

namespace Strata.Common.Configuration
{
    /// <summary>
    /// A wrapper around the <see cref="Configuration"/> framework class which provides easy access
    /// to a Mapped Exe Configuration and the AppSettings or ConnectionStrings therein.
    /// </summary>
    public class Config
    {
        private System.Configuration.Configuration config;

        /// <summary>
        /// Creates a new <see cref="Config"/> instance from the provided filename.
        /// </summary>
        /// <param name="fileName">The name of the config file to get.</param>
        /// <param name="path">An option path to combine with the config file name.</param>
        internal Config(string fileName, string path = null)
        {
            if (path == null)
            {
                path = AppDomain.CurrentDomain.BaseDirectory;
            }

            ExeConfigurationFileMap fileMap = new ExeConfigurationFileMap
                                              {
                                                  ExeConfigFilename = Path.Combine(path, fileName)
                                              };
            config = ConfigurationManager.OpenMappedExeConfiguration(fileMap, ConfigurationUserLevel.None);
        }

        /// <summary>
        /// Gets a value from the AppSettings for a given key.  No exceptions are thrown.
        /// </summary>
        /// <param name="key">The AppSettings key to retrieve the value from.</param>
        /// <returns>The value associated with the key.</returns>
        public string GetValue(string key)
        {
            return GetValue(key, false);
        }

        /// <summary>
        /// Gets a value from the AppSettings for a given key.
        /// </summary>
        /// <param name="key">The AppSettings key to retrieve the value from.</param>
        /// <param name="throwException">Indicates whether the method should throw
        ///  an <see cref="ArgumentOutOfRangeException"/> if they key doesn't exist in the config file.</param>
        /// <returns>The value associated with the key.</returns>
        /// <exception cref="ArgumentOutOfRangeException"/>
        public string GetValue(string key, bool throwException)
        {

            if (config.AppSettings.Settings.AllKeys.Contains(key))
            {
                return config.AppSettings.Settings[key].Value;
            }

            if (throwException)
            {
                throw new ArgumentOutOfRangeException(key + " does not exist in the provided configuration file.");
            }
            
            return string.Empty;
        }

        public string GetConnectionString(string key, bool throwException)
        {
            try
            {
                return config.ConnectionStrings.ConnectionStrings[key].ConnectionString;
            } 
            catch (Exception)
            {
                if (throwException)
                {
                    throw;
                }
                
                return string.Empty;
            }
        }
    }
}
