using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Globalization;
using System.IO;
using System.IO.Compression;
using System.IO.Packaging;
using System.Net.Mime;
using System.Runtime.InteropServices;
using System.Security;
using System.Security.Permissions;

namespace Strata.Common.Utility
{
    public class FileTools
    {
        public static void CopyFile(FileInfo source, FileInfo destination)
        {
            CopyFile(source, destination, CopyFileOptions.All);
        }

        public static void CopyFile(FileInfo source, FileInfo destination, CopyFileOptions options)
        {
            CopyFile(source, destination, options, null);
        }

        public static void CopyFile(FileInfo source, FileInfo destination, CopyFileOptions options, CopyFileCallback callback)
        {
            CopyFile(source, destination, options, callback, null);
        }

        public static void CopyFile(FileInfo source, FileInfo destination, CopyFileOptions options, CopyFileCallback callback, object state)
        {
            if (source == null)
                throw new ArgumentNullException("source");

            if (destination == null)
                throw new ArgumentNullException("destination");

            if ((options & ~CopyFileOptions.All) != 0)
                throw new ArgumentOutOfRangeException("options");

            new FileIOPermission(FileIOPermissionAccess.Read, source.FullName).Demand();
            new FileIOPermission(FileIOPermissionAccess.Write, destination.FullName).Demand();

            CopyProgressRoutine cpr = (callback == null ? null : new CopyProgressRoutine(new CopyProgressData(source, destination, callback, state).CallbackHandler));

            bool cancel = false;
            if (!CopyFileEx(source.FullName, destination.FullName, cpr, IntPtr.Zero, ref cancel, (int)options))
            {
                throw new IOException(new Win32Exception().Message);
            }
        }

        private class CopyProgressData
        {
            private FileInfo source;
            private FileInfo destination;
            private CopyFileCallback callback;
            private object state;

            public CopyProgressData(FileInfo source, FileInfo destination, CopyFileCallback callback, object state)
            {
                this.source = source;
                this.destination = destination;
                this.callback = callback;
                this.state = state;
            }

            public int CallbackHandler(long totalFileSize, long totalBytesTransferred, long streamSize, long streamBytesTransferred, int streamNumber, int callbackReason, IntPtr sourceFile, IntPtr destinationFile, IntPtr data)
            {
                return (int)this.callback(this.source, this.destination, this.state, totalFileSize, totalBytesTransferred);
            }
        }

        private delegate int CopyProgressRoutine(long totalFileSize, long totalBytesTransferred, long streamSize, long streamBytesTransferred, int streamNumber, int callbackReason, IntPtr sourceFile, IntPtr destinationFile, IntPtr data);

        [SuppressUnmanagedCodeSecurity]
        [DllImport("Kernel32.dll", CharSet = CharSet.Auto, SetLastError = true)]
        private static extern bool CopyFileEx(string lpExistingFileName, string lpNewFileName, CopyProgressRoutine lpProgressRoutine, IntPtr lpData, ref bool pbCancel, int dwCopyFlags);
    
        public static void CopyFolder(string source, string target)
        {
            DirectoryInfo sourceDirectory = new DirectoryInfo(source);

            if (!sourceDirectory.Exists)
            {
                throw new DirectoryNotFoundException(source + " directory must exist.");
            }

            DirectoryInfo targetDirectory = new DirectoryInfo(target);
            if (!targetDirectory.Exists)
            {
                targetDirectory.Create();
            }

            foreach (FileInfo file in sourceDirectory.GetFiles())
            {
                file.CopyTo(Path.Combine(target, file.Name), true);
            }

            foreach (DirectoryInfo directory in sourceDirectory.GetDirectories())
            {
                CopyFolder(directory.FullName, Path.Combine(target, directory.Name));
            }
        }

        /// <summary>
        /// Uses the <see cref="System.IO.Compression"/> framework to compress a <see cref="FileInfo"/>.
        /// </summary>
        /// <param name="fileToCompress"></param>
        public static void Compress(FileInfo fileToCompress)
        {
            using (FileStream originalFileStream = fileToCompress.OpenRead())
            {
                if (!((File.GetAttributes(fileToCompress.FullName) & FileAttributes.Hidden) != FileAttributes.Hidden & fileToCompress.Extension != ".gz"))
                {
                    return;
                }
                using (FileStream compressedFileStream = File.Create(fileToCompress.FullName + ".gz"))
                {
                    using (var compressionStream = new GZipStream(compressedFileStream, CompressionMode.Compress))
                    {
                        originalFileStream.CopyTo(compressionStream);
                        Trace.WriteLine(string.Format("Compressed {0} from {1} to {2} bytes.",
                                                      fileToCompress.Name,
                                                      fileToCompress.Length.ToString(CultureInfo.InvariantCulture),
                                                      compressedFileStream.Length.ToString(CultureInfo.InvariantCulture)));
                    }
                }
            }
        }

        /// <summary>
        /// Uses the <see cref="System.IO.Compression"/> framework to decompress a <see cref="FileInfo"/>.
        /// </summary>
        /// <param name="fileToDecompress"></param>
        public static void Decompress(FileInfo fileToDecompress)
        {
            using (FileStream originalFileStream = fileToDecompress.OpenRead())
            {
                string currentFileName = fileToDecompress.FullName;
                string newFileName = currentFileName.Remove(currentFileName.Length - fileToDecompress.Extension.Length);

                using (FileStream decompressedFileStream = File.Create(newFileName))
                {
                    using (var decompressionStream = new GZipStream(originalFileStream, CompressionMode.Decompress))
                    {
                        decompressionStream.CopyTo(decompressedFileStream);
                        Trace.WriteLine(string.Format("Decompressed: {0}",
                                                      fileToDecompress.Name));
                    }
                }
            }
        }

        /// <summary>
        /// Uses the WindowsBase Packaging framework to create a .zip package of the specified files. 
        /// </summary>
        /// <param name="outputPath"></param>
        /// <param name="filePaths"></param>
        public static void CompressAndPackageFiles(string outputPath, params string[] filePaths)
        {
            using (var package = Package.Open(outputPath, FileMode.Create, FileAccess.ReadWrite))
            {
                foreach (string targetPath in filePaths)
                {
                    try
                    {
                        var partUri = PackUriHelper.CreatePartUri(new Uri(Path.GetFileName(targetPath), UriKind.Relative));
                        var part = package.CreatePart(partUri, MediaTypeNames.Text.Plain, CompressionOption.Normal);
                        var buffer = File.ReadAllBytes(targetPath);
                        if (part != null && buffer.Length > 0)
                        {
                            part.GetStream().Write(buffer, 0, buffer.Length);
                        }
                    }
                    catch (Exception ex)
                    {
                        Trace.WriteLine(ex.ToString());
                    }
                }
                package.Close();
            }
        }
    }

    public delegate CopyFileCallbackAction CopyFileCallback(FileInfo source, FileInfo destination, object state, long totalFileSize, long totalBytesTransferred);

    public enum CopyFileCallbackAction
    {
        Continue = 0,
        Cancel = 1,
        Stop = 2,
        Quiet = 3
    }

    [Flags]
    public enum CopyFileOptions
    {
        None = 0x0,
        FailIfDestinationExists = 0x1,
        Restartable = 0x2,
        AllowDecryptedDestination = 0x8,
        All = FailIfDestinationExists | Restartable | AllowDecryptedDestination
    }
}
