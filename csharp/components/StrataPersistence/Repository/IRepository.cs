//  ##########################################################################
//  # File Name: IRepository.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################

namespace Strata.Persistence.Repository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Root interface of all 
    /// <a href="http://tinyurl.com/fifx-arch/Patterns/Repository.pdf">
    /// persistence repositories</a>. Provides access to the context 
    /// associated with the repository and the current unit of work. 
    /// Domain-specific repositories extend this interface and  
    /// declare insert, update, remove, and query methods for the 
    /// domain's entity type. Repositories are a key element of 
    /// <a href="http://en.wikipedia.org/wiki/Domain-driven_design">
    /// Domain-Driven Design</a>.
    /// </summary>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    interface IRepository
    {
        IRepositoryContext Context { get; }
        IUnitOfWork        UnitOfWork { get; }
    }
}

//  ##########################################################################
