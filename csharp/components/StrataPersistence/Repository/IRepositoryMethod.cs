//  ##########################################################################
//  # File Name: IRepositoryMethod.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using Strata.Common.Utility;

namespace Strata.Persistence.Repository
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Provides abstraction for elements (queries, stored procedures, rules,
    /// ...) that can be executed in a repository backend. 
    /// </summary>
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public 
    interface IRepositoryMethod:
        ICopyable
    {
        String Name { get; }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Sets the method's input using a name and the input value. 
        /// </summary>
        /// 
        /// <param name="name">identifies input parameter</param>
        /// <param name="input">input parameter value</param>
        /// 
        /// <exception cref="InvalidInputException">
        /// Invalid input to method.
        /// </exception>
        /// 
	    void 
	    SetInput(String name,Object input);

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Sets the method's input using a name and the input value. 
        /// </summary>
        /// 
        /// <param name="index">identifies input parameter</param>
        /// <param name="input">input parameter value</param>
        /// 
        /// <exception cref="InvalidInputException">
        /// Invalid input to method.
        /// </exception>
        /// 
	    void 
	    SetInput(int index,Object input);
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Sets the method's inputs using a Collection. 
        /// </summary>
        /// 
        /// <param name="inputs">collection of input values</param>
        /// 
        /// <exception cref="InvalidInputException">
        /// Invalid input to method.
        /// </exception>
        /// 
	    void 
	    SetInput(ICollection<Object> inputs);
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Clears the method's current inputs. 
        /// </summary>
        /// 
	    void
	    ClearInputs();
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Completely clears the method's state including results and inputs. 
        /// </summary>
        /// 
	    void
	    Clear();
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Executes the the named method. 
        /// </summary>
        /// 
	    void
	    Execute();
    }
}

//  ##########################################################################
