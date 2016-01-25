using System;
using System.Collections.Generic;
using Strata.Common.Provisioning.Providers;

namespace Strata.Server.ServiceManagement
{
    /// <summary>
    /// An <see cref="ITargetFoldersProvider"/> implementation which derives the service list to put in TargetFolders from the IDS_APP schema tables.  The query is
    /// built based on the executing systems <see cref="Environment.MachineName"/> and the paths in each service profile assigned to that system.
    /// </summary>
    public class DbServicesTargetFoldersProvider : ITargetFoldersProvider
    {
        private const string Core = "Core";

        /// <summary>
        /// The published listed of target folders provided by this provider.
        /// </summary>
        public List<string> TargetFolders { get; private set; }

        /// <summary>
        /// Creates a new <see cref="DbServicesTargetFoldersProvider"/> instance.
        /// </summary>
        //public DbServicesTargetFoldersProvider(IRepository<ApplicationEntities> repository)
        //{
        //    // The core is always loaded.
        //    TargetFolders = new List<string> {Core};

        //    TargetFolders.AddRange(repository.Fetch<ServerProfile>()
        //                               .Include(server => server.Services)
        //                               .Where(server => server.Name == Environment.MachineName)
        //                               .SelectMany(server => server.Services)
        //                               .Select(svc => svc.Path));
        //}
    }
}
