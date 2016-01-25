//  ##########################################################################
//  # File Name: FileFinder.cs
//  # Copyright: 2015, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using System.Text.RegularExpressions;

namespace Strata.Common.FileSystem
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    ///  
    public 
    class FileFinder:
        IFileSystemPassiveVisitor
    {
        public Regex        Expression { get;set; }
        public IList<IFile> Results { get;protected set; } 

        public
        FileFinder()
        {
            Results = new List<IFile>();
        }

        public void
        VisitDirectory(IDirectory directory) {}

        public void 
        VisitFile(IFile file)
        {
            if ( Expression.IsMatch(file.RelativePath) )
                Results.Add( file );
        }
    }
}

//  ##########################################################################
