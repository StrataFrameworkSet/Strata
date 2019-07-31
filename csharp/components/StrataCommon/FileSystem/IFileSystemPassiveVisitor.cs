//  ##########################################################################
//  # File Name: IFileSystemVisitor.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Common.FileSystem
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    interface IFileSystemPassiveVisitor
    {
        void 
        VisitDirectory(IDirectory directory);

        void 
        VisitFile(IFile file);
    }
}

//  ##########################################################################
