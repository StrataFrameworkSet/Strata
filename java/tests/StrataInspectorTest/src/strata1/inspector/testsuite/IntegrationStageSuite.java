// ##########################################################################
// # File Name: IntegrationStageSuite.java
// #
// # Copyright: 2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:   This file is part of the StrataEntity Framework.
// #
// #            The StrataInspector Framework is free software: you 
// #            can redistribute it and/or modify it under the terms of 
// #            the GNU Lesser General Public License as published by
// #            the Free Software Foundation, either version 3 of the 
// #            License, or (at your option) any later version.
// #
// #            The StrataInspector Framework is distributed in the 
// #            hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #            without even the implied warranty of MERCHANTABILITY or 
// #            FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #            General Public License for more details.
// #
// #            You should have received a copy of the GNU Lesser 
// #            General Public License along with the StrataInspector
// #            Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata1.inspector.testsuite;

import strata1.inspector.diagnostic.DiagnosticsSuite;
import strata1.inspector.memorydiagnostic.MemoryDiagnosticSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    DiagnosticsSuite.class,
    MemoryDiagnosticSuite.class})
public 
class IntegrationStageSuite {}

// ##########################################################################
