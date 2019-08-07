//  ##########################################################################
//  # File Name: UnitOfWorkManager.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;

namespace Strata.Persistence.UnitOfWork
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
        private IUnitOfWorkProvider context;
        private Action             executeAction;
        private Action             rollbackAction;

        public        
        UnitOfWorkManager(
            IUnitOfWorkProvider cntxt,
            Action             execute): 
            this(cntxt,execute,UnitOfWorkManager.DoNothing) {}

        public        
        UnitOfWorkManager(
            IUnitOfWorkProvider cntxt,
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
