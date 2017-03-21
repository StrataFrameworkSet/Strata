// ##########################################################################
// # File Name:	StrataNamingStrategy.java
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

import org.hibernate.cfg.DefaultNamingStrategy;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.internal.util.StringHelper;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class StrataNamingStrategy
    extends    DefaultNamingStrategy
    implements NamingStrategy
{

    private static final long serialVersionUID = -3077939262096275562L;
    
    private static final String INTERFACE_EXPR = "^[I][A-Z][a-zA-Z_0-9]*";
    private static final String FIELD_EXPR = "^its[a-zA-Z_0-9]+";
    
    /************************************************************************
     * Creates a new {@code StrataNamingStrategy}. 
     *
     */
    public 
    StrataNamingStrategy()
    {
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    classToTableName(String className)
    {
        StringBuilder tableName = 
            new StringBuilder(StringHelper.unqualify(className));
        
        if ( tableName.toString().matches( INTERFACE_EXPR ) )
            tableName.deleteCharAt( 0 );
        
        return tableName.toString();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    propertyToColumnName(String propertyName)
    {
        StringBuilder columnName = 
            new StringBuilder(StringHelper.unqualify(propertyName));
        char          first;
        
        if ( columnName.toString().matches( FIELD_EXPR ) )
            columnName.delete( 0,3 );
        
        first = columnName.charAt( 0 );
        
        if ( Character.isLowerCase( first ) )
            columnName.setCharAt( 0,Character.toUpperCase( first ) );
        
        return columnName.toString();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    tableName(String tableName)
    {
        return tableName;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    columnName(String columnName)
    {
        return columnName;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    collectionTableName(
        String ownerEntity,
        String ownerEntityTable,
        String associatedEntity,
        String associatedEntityTable,
        String propertyName)
    {
        return propertyName;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    joinKeyColumnName(String joinedColumn,String joinedTable)
    {
        return super.joinKeyColumnName( joinedColumn,joinedTable );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    foreignKeyColumnName(
        String propertyName,
        String propertyEntityName,
        String propertyTableName,
        String referencedColumnName)
    {
        return 
            super.foreignKeyColumnName( 
                propertyName,
                propertyEntityName,
                propertyTableName,
                referencedColumnName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    logicalColumnName(String columnName,String propertyName)
    {
        return super.logicalColumnName( columnName,propertyName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    logicalCollectionTableName(
        String tableName,
        String ownerEntityTable,
        String associatedEntityTable,
        String propertyName)
    {
        return 
            super.logicalCollectionTableName( 
                tableName,
                ownerEntityTable,
                associatedEntityTable,
                propertyName );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String 
    logicalCollectionColumnName(
        String columnName,
        String propertyName,
        String referencedColumn)
    {
        return
            super.logicalCollectionColumnName( 
                columnName,
                propertyName,
                referencedColumn );
    }

}

// ##########################################################################
