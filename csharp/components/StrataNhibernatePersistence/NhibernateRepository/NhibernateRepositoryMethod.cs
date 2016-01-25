//  ##########################################################################
//  # File Name: NhibernateRepositoryMethod.cs
//  # Copyright: 2013, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections;
using System.Collections.Generic;
using Strata.Common.Utility;
using Strata.Persistence.Repository;
using NHibernate;

namespace Strata.NhibernatePersistence.NhibernateRepository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// $comments$
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    class NhibernateRepositoryMethod:
        AbstractRepositoryMethod
    {
        private NhibernateUnitOfWork unitOfWork;

        public 
        NhibernateRepositoryMethod(String name,NhibernateUnitOfWork uow):
            base( name )
        {
            unitOfWork = uow;
        }

        public 
        NhibernateRepositoryMethod(NhibernateRepositoryMethod other):
            base( other )
        {
            unitOfWork = other.unitOfWork;
        }

        public override ICopyable 
        MakeCopy()
        {
            return new NhibernateRepositoryMethod( this );
        }

        public override void 
        Clear()
        {
            ClearInputs();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Evaluates the named query, sets the results, and intializes 
        /// the enumerator.
        /// </summary>
        /// 
        /// 
        public override void 
        Execute()
        {
            IQuery      method = unitOfWork.DoGetNamedQuery( Name );
			InputKeeper keeper = GetInputs();
			
			switch ( keeper.Mode )
			{
			case InputMode.NAMED:
				ExecuteWithNamedInputs( method,keeper );
				break;
				
			case InputMode.POSITIONAL:
				ExecuteWithPositionalInputs( method,keeper );
				break;
				
			default:
				ExecuteWithNoInputs( method );
				break;
			}
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Executes the named method using named inputs.
        /// </summary>
        /// <param name="method"> </param>
        /// <param name="keeper">input keeper</param>
        /// 
        private void 
        ExecuteWithNamedInputs(IQuery method,InputKeeper keeper)
        {
    	    IDictionary<String,Object> inputs = keeper.GetNamedInputs();
    	    int rowsAffected = 0;

    	    foreach (KeyValuePair<String,Object> input in inputs)
    	    {
                if ( input.Value is IEnumerable )
                    method.SetParameterList( 
                        input.Key,
                        (IEnumerable)input.Value );
                else
    	            method.SetParameter( input.Key,input.Value );
    	    }

            rowsAffected = method.ExecuteUpdate();
            Console
                .Out
                .WriteLine("Debug: Method " + Name + " affected " + rowsAffected + " rows." );
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Executes the named method using positional inputs.
        /// </summary>
        /// <param name="method"> </param>
        /// <param name="keeper">input keeper</param>
        /// 
        private void 
        ExecuteWithPositionalInputs(IQuery method,InputKeeper keeper)
        {
		    ICollection<Object> inputs   = keeper.GetPositionalInputs();
		    int                 position = 0;
    	
		    foreach (Object input in inputs)
		        method.SetParameter( position++,input );

            method.ExecuteUpdate();
        }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Evaluates the named method with no inputs.
        /// </summary>
        /// <param name="method"> </param>
        /// <returns>query result</returns>
        private void 
	    ExecuteWithNoInputs(IQuery method)
	    {
            method.ExecuteUpdate();
	    }

    }
}

//  ##########################################################################
