//  ##########################################################################
//  # File Name: UnitOfWorkManager.cs
//  # Copyright: 2012, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;

namespace Strata.Persistence.Repository
{
    public delegate void Action(); 

    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class UnitOfWorkManager
    {
        private IRepositoryContext context;
        private Action             executeAction;
        private Action             rollbackAction;

        public        
        UnitOfWorkManager(
            IRepositoryContext cntxt,
            Action             execute): 
            this(cntxt,execute,UnitOfWorkManager.DoNothing) {}

        public        
        UnitOfWorkManager(
            IRepositoryContext cntxt,
            Action             execute,
            Action             rollback)
        {
            context        = cntxt;
            executeAction  = execute;
            rollbackAction = rollback;
        }

        public void 
        Execute()
        {
            IUnitOfWork unitOfWork = null;

            try
            {
                unitOfWork = context.GetUnitOfWork();

                executeAction();
                unitOfWork.Commit();
            }
            catch (Exception)
            {
                unitOfWork.Rollback();
                rollbackAction();
            }
        }

        public static void 
        DoNothing() {}
    }
}

//  ##########################################################################
