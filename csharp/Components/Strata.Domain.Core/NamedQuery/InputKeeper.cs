//  ##########################################################################
//  # File Name: InputKeeper.cs
//  # Copyright: 2011-2018, Sapientia Systems, LLC.
//  ##########################################################################

using System;
using System.Collections.Generic;

namespace Strata.Domain.Core.NamedQuery
{
    //////////////////////////////////////////////////////////////////////////
    /// <summary>
    /// Helper class that organizes inputs for <c>INamedQuery{T}</c> 
    /// objects by name or by position.
    /// </summary>
    /// 
    /// <author>JFL</author>
    /// <conventions>$conventionspath$</conventions>
    ///  
    public
    class InputKeeper
    {
	    public  InputMode                  Mode { get; protected set; }
	    private IDictionary<string,object> namedInputs;
	    private IDictionary<int,object>    positionalInputs;

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Creates a new InputKeeper. 
        /// </summary>
        /// 
	    public 
	    InputKeeper()
	    {
		    Mode             = InputMode.NOT_INITIALIZED;
		    namedInputs      = new Dictionary<string,object>();
		    positionalInputs = new Dictionary<int,object>();
	    }

        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Stores a named input. 
        /// </summary>
        /// 
        /// <param name="name">input name</param>
        /// <param name="value">input value</param>
        /// 
        /// <exception cref="InvalidInputException">
        /// Mixing input modes (named vs positional) is invalid.
        /// </exception>
        /// 
	    public void
	    SetInput(string name,object value)
	    {
		    CheckMode( InputMode.NAMED );
		    namedInputs.Add( name,value );
	    }
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        ///  Stores a positional input
        /// </summary>
        /// 
        /// <param name="position">input position</param>
        /// <param name="value">input value</param>
        /// 
        /// <exception cref="InvalidInputException">
        /// Mixing input modes (named vs positional) is invalid.
        /// </exception>
        /// 
 	    public void
	    SetInput(int position,object value)
	    {
		    CheckMode( InputMode.POSITIONAL );
		    positionalInputs.Add( position,value );
	    }
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Stores a collection of positional inputs 
        /// </summary>
        /// 
        /// <param name="inputs">positional inputs</param>
        /// 
        /// <exception cref="InvalidInputException">
        /// Mixing input modes (named vs positional) is invalid.
        /// </exception>
        /// 
	    public void
	    SetInput(ICollection<object> inputs)
	    {
		    int i = 0;
		
            ClearInputs();
		    CheckMode( InputMode.POSITIONAL );
		
		    foreach (object input in inputs)
			    positionalInputs.Add( i++,input );
	    }
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Clears the stored inputs and resets the input mode. 
        /// </summary>
        /// 
	    public void
	    ClearInputs()
	    {
		    Mode = InputMode.NOT_INITIALIZED;
		    namedInputs.Clear();
		    positionalInputs.Clear();
	    }
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Gets the stored named inputs. 
        /// </summary>
        /// 
        /// <returns>the named inputs</returns>
        /// 
	    public IDictionary<string,object>
	    GetNamedInputs()
	    {
		    return namedInputs;
	    }
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Gets the stored positional inputs. 
        /// </summary>
        /// 
        /// <returns>the positional inputs</returns>
        /// 
	    public ICollection<object>
	    GetPositionalInputs()
	    {
		    return positionalInputs.Values;
	    }
	
        //////////////////////////////////////////////////////////////////////
        /// <summary>
        /// Validates the requested input mode. 
        /// </summary>
        /// 
        /// <param name="requestedMode">requested input mode</param>
        /// 
        /// <exception cref="InvalidInputException">
        /// Mixing input modes (named vs positional) is invalid.
        /// </exception>
        /// 
	    private void
	    CheckMode(InputMode requestedMode)
	    {
		    if ( Mode == InputMode.NOT_INITIALIZED )
			    Mode = requestedMode;
		    else if ( Mode != requestedMode )
		    {
			    throw new InvalidInputException( 
				    "Cannot mix input modes: current mode is " + 
				    Mode + " requested mode is " + requestedMode + "." );
		    }
	    }

    }
}

//  ##########################################################################
