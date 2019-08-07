using System.Collections.Generic;
using System.Configuration;
using System.Linq;

namespace Strata.Common.Provisioning.Providers
{
    /// <summary>
    /// A <see cref="ITargetFoldersProvider"/> implementation which builds the services paths list from an App.config entry
    /// matching <see cref="ProvisionerTargetFoldersKey"/>.  The list is expected to be comma delimited and may contain 
    /// spaces (the values are trimmed after the string is split).
    /// </summary>
    public class ConfigTargetFoldersProvider : ITargetFoldersProvider
    {
        public const string ProvisionerTargetFoldersKey = "ProvisionerTargetFolders";

        /// <summary>
        /// The <see cref="ITargetFoldersProvider"/> implementation of the TargetFolders getter.
        /// </summary>
        public List<string> TargetFolders { get; private set; }

        /// <summary>
        /// Creates a new <see cref="ConfigTargetFoldersProvider"/> instance.
        /// </summary>
        public ConfigTargetFoldersProvider()
        {
            var list = ConfigurationManager.AppSettings[ProvisionerTargetFoldersKey];
            TargetFolders = !string.IsNullOrEmpty(list)
                                ? list.Split(',').Select(v => v.Trim()).ToList()
                                : new List<string>();
        }
    }
}
