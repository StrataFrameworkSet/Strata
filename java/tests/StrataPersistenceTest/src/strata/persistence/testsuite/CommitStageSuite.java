// ##########################################################################
// # File Name: CommitStageSuite.java
// #
// # Copyright: 2011, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:   This file is part of the StrataEntity Framework.
// #
// #            The StrataEntity Framework is free software: you 
// #            can redistribute it and/or modify it under the terms of 
// #            the GNU Lesser General Public License as published by
// #            the Free Software Foundation, either version 3 of the 
// #            License, or (at your option) any later version.
// #
// #            The StrataEntity Framework is distributed in the 
// #            hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #            without even the implied warranty of MERCHANTABILITY or 
// #            FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #            General Public License for more details.
// #
// #            You should have received a copy of the GNU Lesser 
// #            General Public License along with the StrataEntity
// #            Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.persistence.testsuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import strata.persistence.inmemorytestdomain.InMemoryExecutionManagerTest;
import strata.persistence.inmemorytestdomain.InMemoryOrganizationRepositoryTest;
import strata.persistence.inmemorytestdomain.InMemoryPartyRepositoryTest;
import strata.persistence.inmemorytestdomain.InMemoryPersonRepositoryTest;

/**
 * 
 * @author 		
 *     Sapientia Systems 
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    InMemoryPersonRepositoryTest.class,
    InMemoryOrganizationRepositoryTest.class,
    InMemoryPartyRepositoryTest.class,
    InMemoryExecutionManagerTest.class})
public 
class CommitStageSuite {}

// ##########################################################################
