//  ##########################################################################
//  # File Name: AbstractRepositoryMethod.cs
//  # Copyright: 2013, Sapientia Systems, LLC.
//  ##########################################################################
 
using System;
using System.Collections.Generic;
using Strata.Common.Utility;

namespace Strata.Persistence.Repository
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
    class AbstractRepositoryMethod:
        IRepositoryMethod
    {
	    public String       Name { get;protected set; }

	    private InputKeeper	inputs;
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new AbstractNamedMethod.
        /// </summary>
        /// 
        /// <param name="name">method name</param>
        /// 
	    protected 
	    AbstractRepositoryMethod(String name)
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
	    AbstractRepositoryMethod(AbstractRepositoryMethod other)
	    {
		    Name      = other.Name;
		    inputs = new InputKeeper();
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IRepositoryMethod.SetInput(String,Object)"/>
        /// </summary>
        /// 
	    public void 
	    SetInput(String name,Object input) 
	    {
		    inputs.SetInput( name,input );
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IRepositoryMethod.SetInput(int,Object)"/>
        /// </summary>
        /// 
	    public void 
	    SetInput(int index,Object input) 
	    {
		    inputs.SetInput( index,input );
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IRepositoryMethod.SetInput(ICollection{Object})"/>
        /// </summary>
        /// 
	    public void 
	    SetInput(ICollection<Object> inputs)
	    {
		    this.inputs.SetInput( inputs );
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IRepositoryMethod.ClearInputs()"/>
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
        /// <see cref="IRepositoryMethod.ClearInputs()"/>
        /// </summary>
        /// 
        public abstract void 
        Clear();

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IRepositoryMethod.Execute()"/>
        /// </summary>
        /// 
        public abstract void 
        Execute();
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// <see cref="IRepositoryMethod.Clear()"/>
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
