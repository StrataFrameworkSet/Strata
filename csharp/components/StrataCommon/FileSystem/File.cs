//  ##########################################################################
//  # File Name: File.cs
//  # Copyright: 2015, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.IO;

namespace Strata.Common.FileSystem
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class File:
        IFile
    {
        public String
        AbsolutePath
        {
            get { return file.FullName; }
        }

        public String
        RelativePath
        {
            get { return file.Name; }
        }

        public String
        Extension
        {
            get { return file.Extension; }    
        }

        public IDirectory
        Parent
        {
            get { return new Directory( file.Directory ); }
        }

        public bool
        Exists
        {
            get { return System.IO.File.Exists( AbsolutePath ); }
        }

        private FileInfo file;

        public
        File(String absolutePath)
        {
            file = new FileInfo( absolutePath );
        }

        public
        File(IDirectory parent,String relativePath)
        {
            file = 
                new FileInfo( 
                    parent.AbsolutePath + 
                    Path.DirectorySeparatorChar + 
                    relativePath );
        }

        public
        File(FileInfo f)
        {
            file = f;
        }

        public void
        Accept(IFileSystemPassiveVisitor visitor)
        {
            visitor.VisitFile( this );
        }

        public void 
        Accept(IFileSystemActiveVisitor visitor)
        {
            visitor.VisitFile( this );            
        }

        public void
        Create()
        {
            if (!Exists)
                file
                    .Create()
                    .Close();

            file = new FileInfo( file.FullName );
        }

        public void 
        Delete()
        {
            if ( Exists )
                file.Delete();

            file = new FileInfo( file.FullName );
        }

        public FileStream
        GetStreamForWriting()
        {
            return file.OpenWrite();   
        }

        public FileStream
        GetStreamForReading()
        {
            return file.OpenRead();   
        }
    }
}

//  ##########################################################################
