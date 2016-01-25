using System.Collections.Generic;

namespace Strata.Common.Provisioning
{
    public delegate void LibrariesChangedHandler(LibrariesChangedArgs args);

    public class LibrariesChangedArgs
    {
        public List<LibraryInfo> ChangedLibraries { get; private set; }

        public LibrariesChangedArgs(List<LibraryInfo> libraries)
        {
            ChangedLibraries = libraries;
        }
    }
}
