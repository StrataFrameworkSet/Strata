//  ##########################################################################
//  # File Name: IFileSystemElement.cs
//  # Copyright: 2015, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.IO;

namespace Strata.Common.FileSystem
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// A file or directory in a file system.
    /// </summary>
    ///  
    public 
    interface IFileSystemElement
    {
        String AbsolutePath { get; }
        String RelativePath { get; }
        String Extension { get; }
        bool   Exists { get; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Accepts a visitor that passively expects the 
        /// element to control the visitor's navigation.
        /// </summary>
        /// 
        /// <param name="visitor">a "passive" visitor</param>
        /// 
        void 
        Accept(IFileSystemPassiveVisitor visitor);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Accepts a visitor that actively controls its own navigation.
        /// </summary>
        /// 
        /// <param name="visitor">an "active" visitor</param>
        /// 
        void 
        Accept(IFileSystemActiveVisitor visitor);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates the actual element in the filesystem 
        /// if it does not exist.
        /// </summary>
        /// 
        void
        Create();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Deletes the actual element in the filesystem if it exists.
        /// </summary>
        /// 
        void
        Delete();
    }
}

//  ##########################################################################
