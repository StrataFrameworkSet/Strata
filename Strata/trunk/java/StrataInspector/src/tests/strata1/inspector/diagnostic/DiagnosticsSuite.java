// ##########################################################################
// # File Name:		TasksSuite.java
// # Copyright(C):	2007, Capital Group Companies, Inc. 
// #                All Rights Reserved.
// ##########################################################################

package strata1.inspector.diagnostic;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 
 * @author 		
 *     AFS Strategic Initiative 5 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( {
	DiagnosticCheckTest.class } )
public 
class DiagnosticsSuite
{
	public static Test 
	suite()
	{
		return new JUnit4TestAdapter( DiagnosticsSuite.class );
	}

}


// ##########################################################################
