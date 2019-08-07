//  ##########################################################################
//  # File Name: IFile.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.IO;

namespace Strata.Common.FileSystem
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// A file in a file system.
    /// </summary>
    ///  
    public 
    interface IFile:
        IFileSystemElement
    {
        IDirectory Parent { get; }

        FileStream
        GetStreamForWriting();

        FileStream
        GetStreamForReading();
    }
}

//  ##########################################################################
