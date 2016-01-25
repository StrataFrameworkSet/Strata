//  ##########################################################################
//  # File Name: IDirectory.cs
//  # Copyright: 2015, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Common.FileSystem
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// A directory in a file system.
    /// </summary>
    ///  
    public 
    interface IDirectory:
        IFileSystemElement
    {
        IDirectory        Parent { get; }
        IList<IDirectory> Directories { get; }
        IList<IFile>      Files { get; }
        bool              IsEmpty { get; }
    }
}

//  ##########################################################################
