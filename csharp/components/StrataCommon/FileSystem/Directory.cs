//  ##########################################################################
//  # File Name: Directory.cs
//  # Copyright: 2015, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace Strata.Common.FileSystem
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Implements the <c>IDirectory</c> interface by 
    /// adapting the <c>DirectoryInfo</c> class.
    /// </summary>
    ///  
    public 
    class Directory:
        IDirectory
    {
        public String
        AbsolutePath
        {
            get { return directory.FullName; }
        }

        public String
        RelativePath
        {
            get { return directory.Name; }
        }

        public String
        Extension
        {
            get { return directory.Extension; }
        }

        public bool
        Exists
        {
            get {  return System.IO.Directory.Exists( AbsolutePath ); }
        }

        public IDirectory
        Parent
        {
            get { return new Directory( directory.Parent );}
        }

        public IList<IDirectory>
        Directories
        {
            get
            {
                IList<IDirectory> directories = 
                    new List<IDirectory>();

                foreach (DirectoryInfo d in directory.GetDirectories())
                    directories.Add( new Directory( d ) );

                return directories;
            }
        }

        public IList<IFile>
        Files
        {
            get
            {
                IList<IFile> files = 
                    new List<IFile>();

                foreach (FileInfo f in directory.GetFiles())
                    files.Add( new File( f ) );

                return files;
            }
        }

        public bool
        IsEmpty
        {
            get
            {
                return 
                    !directory
                        .EnumerateFileSystemInfos()
                        .Any();
            }
        }

        private DirectoryInfo directory;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>Directory</c> instance.
        /// </summary>
        /// 
        /// <param name="absolutePath">
        /// the absolute path of the directory
        /// </param>
        /// 
        public
        Directory(String absolutePath)
        {
            directory = new DirectoryInfo( absolutePath );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>Directory</c> instance.
        /// </summary>
        /// 
        /// <param name="parent">
        /// the parent directory
        /// </param>
        /// <param name="relativePath">
        /// the relative path of the directory
        /// </param>
        /// 
        public
        Directory(IDirectory parent,String relativePath)
        {
            directory = new DirectoryInfo( 
                parent.AbsolutePath + 
                Path.DirectorySeparatorChar + 
                relativePath );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new <c>Directory</c> instance.
        /// </summary>
        /// 
        /// <param name="dir">a directory info object</param>
        /// 
        public
        Directory(DirectoryInfo dir)
        {
            directory = dir;
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void
        Accept(IFileSystemPassiveVisitor visitor)
        {
            visitor.VisitDirectory( this );

            foreach (IFile file in Files)
                file.Accept( visitor );

            foreach (IDirectory dir in Directories)
                dir.Accept( visitor );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        Accept(IFileSystemActiveVisitor visitor)
        {
            visitor.VisitDirectory( this );
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        Create()
        {
            if ( !directory.Exists )
                directory.Create();

            directory = new DirectoryInfo(directory.FullName);
        }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public void 
        Delete()
        {
            if ( directory.Exists )
                directory.Delete();

            directory = new DirectoryInfo(directory.FullName);
        }

    }
}

//  ##########################################################################
