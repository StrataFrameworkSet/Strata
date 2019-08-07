using System.Collections.Generic;

namespace Strata.Common.Provisioning.Providers
{
    public interface ITargetFoldersProvider
    {
        List<string> TargetFolders { get; }
    }
}
