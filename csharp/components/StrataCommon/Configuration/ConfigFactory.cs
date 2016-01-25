namespace Strata.Common.Configuration
{
    /// <summary>
    /// A factory providing logic for getting <see cref="Config"/> files.
    /// </summary>
    public class ConfigFactory
    {
        /// <summary>
        /// Gets a <see cref="Config"/> for the specified path.
        /// </summary>
        /// <param name="configPath">The path and name of the config file.</param>
        /// <returns>A <see cref="Config"/> object.</returns>
        public static Config GetConfig(string configPath)
        {
            return new Config(configPath);
        }
    }
}
