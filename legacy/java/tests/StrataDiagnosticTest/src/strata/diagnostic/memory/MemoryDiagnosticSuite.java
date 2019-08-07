// ##########################################################################
// # File Name:		TasksSuite.java
// # Copyright(C):	2007, Capital Group Companies, Inc. 
// #                All Rights Reserved.
// ##########################################################################

package strata.diagnostic.memory;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 
 * @author 		
 *     
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( {
	TotalMemoryCheckTest.class,
	FreeMemoryCheckTest.class,
	MaxMemoryCheckTest.class } )
public 
class MemoryDiagnosticSuite {}


// ##########################################################################
