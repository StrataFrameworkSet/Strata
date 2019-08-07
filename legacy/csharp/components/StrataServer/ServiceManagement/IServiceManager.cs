using System.Collections.Generic;

namespace Strata.Server.ServiceManagement
{
    public interface IServiceManager
    {
        Dictionary<string, IService> Services { get; }

        void Start();
        void Stop();
        void Refresh();
    }
}
