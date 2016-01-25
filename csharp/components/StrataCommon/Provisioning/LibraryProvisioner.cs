using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using Strata.Common.Logging;
using Strata.Common.Provisioning.Providers;
using Strata.Common.Utility;
using Microsoft.Practices.Unity;

namespace Strata.Common.Provisioning
{
    /// <summary>
    /// A class used for provisioning libraries from a remote repository.  When files change, an event is fired so any listeners are notified.
    /// </summary>
    public class LibraryProvisioner
    {
        /// <summary>
        /// The application <see cref="ILogger"/> provided by the container.
        /// </summary>
        [Dependency]
        public ILogger Log { get; set; }

        /// <summary>
        /// The strategy this provisioner should use to get its list of target folders.
        /// </summary>
        [Dependency]
        public ITargetFoldersProvider TargetFoldersProviderStrategy { get; set; }

        /// <summary>
        /// Creates a new <see cref="LibraryProvisioner"/> instance.
        /// </summary>
        public LibraryProvisioner()
        {
            Libraries = new List<LibraryInfo>();
        }

        /// <summary>
        /// The local system path.  This is where libraries will get copied, and files here will be used for local copy version evaluation.
        /// </summary>
        public string LocalPath { get; set; }

        /// <summary>
        /// The remote repository path.  This path is where the provisioner will look for the subfolders listed in the <see cref="ILibraryProvisioner.TargetFolders"/>.
        /// </summary>
        public string RepositoryPath { get; set; }

        /// <summary>
        /// A collection of information about the libraries this provisioner is working with.
        /// </summary>
        public List<LibraryInfo> Libraries { get; private set; }
      
        /// <summary>
        /// Scans the repository for updated file path information, then refreshes any updated services in the file path collection.
        /// </summary>
        public void Refresh()
        {
            RefreshRepositoryFilePaths();
            RefreshServices();
        }
        
        /// <summary>
        /// Clears and repopulates the entire list of file paths starting from the root repository DirectoryInfo.
        /// </summary>
        public void RefreshRepositoryFilePaths()
        {
            if (TargetFoldersProviderStrategy.TargetFolders == null || TargetFoldersProviderStrategy.TargetFolders.Count == 0) return;

            foreach (string folder in (TargetFoldersProviderStrategy.TargetFolders))
            {
                string folderPath = Path.Combine(RepositoryPath, folder);
                DirectoryInfo directory = new DirectoryInfo(folderPath);

                if (!directory.Exists)
                {
                    Log.Warn("Directory not found: " + directory.FullName);
                    continue;
                }

                Log.Verbose("Scanning directory " + directory.FullName + "...");

                foreach (FileInfo file in directory.GetFiles())
                {
                    string remotePath = file.FullName;
                    Log.Verbose("Found file: " + remotePath);

                    if (!Libraries.Exists(lib => lib.RemotePath.Equals(remotePath)))
                    {
                        Libraries.Add(new LibraryInfo
                                      {
                                          RemotePath = remotePath,
                                          FileName = file.Name
                                      });
                    }
                }

                GetFolderResources(directory.GetDirectories().Where(d => !d.Name.Equals("Configs")));
            }
        }

        private void GetFolderResources(IEnumerable<DirectoryInfo> directories)
        {
            foreach (DirectoryInfo dir in directories)
            {
                FileTools.CopyFolder(dir.FullName, Path.Combine(LocalPath, dir.Name));
            }
        }

        /// <summary>
        /// Loads the updated Types from the DirectoryCatalog and compares them against the loaded Types.  Any obsolete types
        /// will be updated locally with their new versions.
        /// </summary>
        private void RefreshServices()
        {

            var localDirectory = new DirectoryInfo(LocalPath);

            if (!localDirectory.Exists)
            {
                localDirectory.Create();
            }

            var changedLibraries = new List<LibraryInfo>();
            
            foreach (var libraryInfo in Libraries)
            {
                bool doCopy;
                string message;

                Log.Verbose("Evaluating " + libraryInfo.FileName + "...");

                libraryInfo.LocalPath = Path.Combine(LocalPath, libraryInfo.FileName);
                
                if (libraryInfo.LocalPath.ToLowerInvariant().EndsWith(".dll") || libraryInfo.LocalPath.ToLowerInvariant().EndsWith(".exe"))
                {
                    AssemblyName localAssembly = null;
                    AssemblyName remoteAssembly = AssemblyName.GetAssemblyName(libraryInfo.RemotePath);

                    if (File.Exists(libraryInfo.LocalPath))
                    {
                        localAssembly = AssemblyName.GetAssemblyName(libraryInfo.LocalPath);

                        int compareResult = localAssembly.Version.CompareTo(remoteAssembly.Version);

                        if (compareResult == 0)
                        {
                            message = string.Format("Local ({0}) and remote ({1}) versions are the same.",
                                                    localAssembly.Version, remoteAssembly.Version);
                            doCopy = false;
                        }
                        else if (compareResult < 0)
                        {
                            message = string.Format("Remote ({0}) version is newer than local ({1}) version.",
                                                    remoteAssembly.Version, localAssembly.Version);
                            doCopy = true;
                        }
                        else
                        {
                            message = string.Format("Local ({0}) version is newer than remote ({1}) version.",
                                                    localAssembly.Version, remoteAssembly.Version);
                            doCopy = true;
                        }
                    }
                    else
                    {
                        message = string.Format("Local version of {0} does not exist.", libraryInfo.FileName);
                        doCopy = true;
                    }

                    libraryInfo.FileVersion = (doCopy) ? remoteAssembly.Version.ToString() : localAssembly.Version.ToString();
                }
                else
                {
                    message = "Non-binary service file detected.";
                    doCopy = true;
                } 

                Log.Verbose(message);

                // Only copy the file if necessary.
                if (!doCopy) continue;

                Log.Verbose("Copying " + libraryInfo.FileName + ".");
                var remoteFile = new FileInfo(libraryInfo.RemotePath);
                remoteFile.CopyTo(libraryInfo.LocalPath, true);

                changedLibraries.Add(libraryInfo);
            }

            if (changedLibraries.Count <= 0) return;

            OnLibrariesChanged(changedLibraries);
        }

        /// <summary>
        /// The event raised when libraries have changed after a Refresh is run.
        /// </summary>
        public event LibrariesChangedHandler LibrariesChanged;

        /// <summary>
        /// Raises the ServiceFilesChanged event.
        /// </summary>
        /// <param name="libraries">The list of files to be included in the <see cref="LibrariesChangedArgs"/>.</param>
        private void OnLibrariesChanged(List<LibraryInfo> libraries)
        {
            if (LibrariesChanged != null)
            {
                LibrariesChanged(new LibrariesChangedArgs(libraries));
            }
        }

    }
}
