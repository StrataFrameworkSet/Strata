//  ##########################################################################
//  # File Name: AbstractMethod.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System.Collections.Generic;
using Strata.Foundation.Core.Utility;

namespace Strata.Domain.Core.NamedQuery
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Base class of all <c>INamedMethod</c> types.
    /// </summary>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public abstract
    class AbstractMethod:
        IMethod
    {
	    public string Name { get;protected set; }

	    private readonly InputKeeper inputs;
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new AbstractNamedMethod.
        /// </summary>
        /// 
        /// <param name="name">method name</param>
        /// 
	    protected 
	    AbstractMethod(string name)
	    {
		    Name      = name;
		    inputs = new InputKeeper();
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new AbstractNamedMethod.
        /// </summary>
        /// 
        /// <param name="other">other method</param>
        /// 
	    public 
	    AbstractMethod(AbstractMethod other)
	    {
		    Name      = other.Name;
		    inputs = new InputKeeper();
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IMethod.SetInput(string,object)"/>
        /// </summary>
        /// 
	    public void 
	    SetInput(string name,object input) 
	    {
		    inputs.SetInput( name,input );
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IMethod.SetInput(int,object)"/>
        /// </summary>
        /// 
	    public void 
	    SetInput(int index,object input) 
	    {
		    inputs.SetInput( index,input );
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IMethod.SetInput(ICollection{object})"/>
        /// </summary>
        /// 
	    public void 
	    SetInput(ICollection<object> inputs)
	    {
		    this.inputs.SetInput( inputs );
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IMethod.ClearInputs()"/>
        /// </summary>
        /// 
	    public void 
	    ClearInputs()
	    {
		    inputs.ClearInputs();
	    }

        //////////////////////////////////////////////////////////////////////
        /// <inheritDoc/>
        /// 
        public abstract ICopyable
        MakeCopy();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IMethod.ClearInputs()"/>
        /// </summary>
        /// 
        public abstract void 
        Clear();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IMethod.Execute()"/>
        /// </summary>
        /// 
        public abstract void 
        Execute();
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IMethod.Clear()"/>
        /// </summary>
        /// 
	    protected InputKeeper
	    GetInputs()
	    {
		    return inputs;
	    }

    }
}

//  ##########################################################################
