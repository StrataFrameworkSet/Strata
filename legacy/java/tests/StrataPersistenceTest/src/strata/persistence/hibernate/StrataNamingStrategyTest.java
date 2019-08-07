// ##########################################################################
// # File Name:	StrataNamingStrategyTest.java
// #
// # Copyright:	2012, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataEntity Framework.
// #
// #   			The StrataEntity Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataEntity Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataEntity
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

package strata.persistence.hibernate;

import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import junit.framework.Assert;
import strata.persistence.testdomain.EmailAddress;
import strata.persistence.testdomain.IParty;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class StrataNamingStrategyTest
{
    private StrataNamingStrategy itsTarget;
    
    /************************************************************************
     *  
     *
     * @throws java.lang.Exception
     */
    @Before
    public void 
    setUp() throws Exception
    {
        itsTarget = new StrataNamingStrategy();
    }

    /************************************************************************
     *  
     *
     * @throws java.lang.Exception
     */
    @After
    public void 
    tearDown() throws Exception
    {
        itsTarget = null;
    }

    /**
     * Test method for 
     * {@link StrataNamingStrategy#classToTableName(String)}.
     */
    @Test
    public void 
    testClassToTableNameString()
    {
        String className1 = IParty.class.getCanonicalName();
        String expected1  = "Party";
        
        String className2 = EmailAddress.class.getCanonicalName();
        String expected2  = "EmailAddress";
        
        String className3 = "foo.bar.Interceptor";
        String expected3  = "Interceptor";
        
        
        Assert.assertEquals(expected1,itsTarget.classToTableName( className1 ));
        Assert.assertEquals(expected2,itsTarget.classToTableName( className2 ));
        Assert.assertEquals(expected3,itsTarget.classToTableName( className3 ));
    }

    /**
     * Test method for 
     * {@link StrataNamingStrategy#propertyToColumnName(String)}.
     */
    @Test
    public void 
    testPropertyToColumnNameString()
    {
        String propertyName1 = "IParty.name";
        String expected1  = "Name";
        
        String propertyName2 = "EmailAddress.itsHost";
        String expected2  = "Host";
                
        
        Assert.assertEquals(expected1,itsTarget.propertyToColumnName( propertyName1 ));
        Assert.assertEquals(expected2,itsTarget.propertyToColumnName( propertyName2 ));
    }

    /**
     * Test method for {@link strata.persistence.hibernate.StrataNamingStrategy#tableName(java.lang.String)}.
     */
    @Test
    public void 
    testTableNameString()
    {
        String tableName1 = "PARTY";
        String expected1  = "Party";
        
        String tableName2 = "pERSON";
        String expected2  = "Person";
                
        
        Assert.assertEquals(expected1,itsTarget.tableName( tableName1 ));
        Assert.assertEquals(expected2,itsTarget.tableName( tableName2 ));
    }

    /**
     * Test method for {@link strata.persistence.hibernate.StrataNamingStrategy#columnName(java.lang.String)}.
     */
    @Test
    public void testColumnNameString()
    {
        String columnName1 = "FIRSTNAME";
        String expected1  = "Firstname";
        
        String columnName2 = "CONTACTINFORMATION";
        String expected2  = "Contactinformation";
                
        
        Assert.assertEquals(expected1,itsTarget.columnName( columnName1 ));
        Assert.assertEquals(expected2,itsTarget.columnName( columnName2 ));
    }

    /**
     * Test method for {@link strata.persistence.hibernate.StrataNamingStrategy#collectionTableName(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
     */
    @Test
    public void testCollectionTableNameStringStringStringStringString()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata.persistence.hibernate.StrataNamingStrategy#joinKeyColumnName(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testJoinKeyColumnNameStringString()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata.persistence.hibernate.StrataNamingStrategy#foreignKeyColumnName(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
     */
    @Test
    public void testForeignKeyColumnNameStringStringStringString()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata.persistence.hibernate.StrataNamingStrategy#logicalColumnName(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testLogicalColumnNameStringString()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata.persistence.hibernate.StrataNamingStrategy#logicalCollectionTableName(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
     */
    @Test
    public void testLogicalCollectionTableNameStringStringStringString()
    {
        fail( "Not yet implemented" );
    }

    /**
     * Test method for {@link strata.persistence.hibernate.StrataNamingStrategy#logicalCollectionColumnName(java.lang.String, java.lang.String, java.lang.String)}.
     */
    @Test
    public void testLogicalCollectionColumnNameStringStringString()
    {
        fail( "Not yet implemented" );
    }

}

// ##########################################################################
