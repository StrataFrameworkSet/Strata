//  ##########################################################################
//  # File Name: IMethod.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System.Collections.Generic;
using Strata.Foundation.Utility;

namespace Strata.Domain.NamedQuery
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Provides abstraction for elements (queries, stored procedures, rules,
    /// ...) that can be executed in a repository backend. 
    /// </summary>
    ///  
    public 
    interface IMethod:
        ICopyable
    {
        string Name { get; }

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
	    SetInput(string name,object input);

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
	    SetInput(int index,object input);
	
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
	    SetInput(ICollection<object> inputs);
	
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
