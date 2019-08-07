// ##########################################################################
// # File Name:	DateTimeType.java
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

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.TimestampType;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import strata.foundation.value.DateTime;

/****************************************************************************
 * 
 * @author 		
 *     Sapientia Systems
 * @conventions	
 *     <a href="{@docRoot}/NamingConventions.html">Naming Conventions</a>
 */
public 
class DateTimeType 
    implements CompositeUserType
{
    /************************************************************************
     * Creates a new {@code DateTimeType}. 
     *
     */
    public
    DateTimeType() {}
    
    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public String[] 
    getPropertyNames()
    {
        return new String[] {"Timestamp"};
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Type[] 
    getPropertyTypes()
    {
        return new Type[] { new TimestampType() };
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Object 
    getPropertyValue(Object component,int property)
        throws HibernateException
    {
        DateTime datetime = (DateTime)component;
        
        if ( property == 0) 
            return datetime.getTimestamp();
        
        throw new HibernateException("unknown property");
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    setPropertyValue(Object component,int property,Object value)
        throws HibernateException
    {
        throw new HibernateException("DateTime is immutable.");
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Class<?> 
    returnedClass()
    {
        return DateTime.class;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    equals(Object x,Object y) 
        throws HibernateException
    {
        DateTime datetimeX = (DateTime)x;
        DateTime datetimeY = (DateTime)y;
        
        if ( (datetimeX == null) && (datetimeY == null) )
            return true;
        else if ( (datetimeX == null) || (datetimeY==null) )
            return false;
        
        return datetimeX.equals( datetimeY );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public int 
    hashCode(Object x) 
        throws HibernateException
    {
        DateTime datetimeX = (DateTime)x;
        
        return datetimeX.hashCode();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Object 
    nullSafeGet(
        ResultSet          rs,
        String[]           names,
        SessionImplementor session,
        Object             owner) 
            throws 
                HibernateException,
                SQLException
    {
        Timestamp timestamp = rs.getTimestamp( names[0] );
        
        if ( rs.wasNull() )
            return null;
        
        return new DateTime(timestamp);
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public void 
    nullSafeSet(
        PreparedStatement  st,
        Object             value,
        int                index,
        SessionImplementor session) 
            throws 
                HibernateException,
                SQLException
    {
        if ( value == null )
            st.setNull( index,new TimestampType().sqlType() );
        else
            st.setTimestamp( index,((DateTime)value).getTimestamp() );
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Object 
    deepCopy(Object value) 
        throws HibernateException
    {
        if ( value == null )
            return null;
        
        return ((DateTime)value).copy();
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public boolean 
    isMutable()
    {
        return false;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Serializable 
    disassemble(
        Object             value,
        SessionImplementor session)
            throws HibernateException
    {
        return (DateTime)value;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Object 
    assemble(
        Serializable       cached,
        SessionImplementor session,
        Object             owner) 
            throws HibernateException
    {
        return (DateTime)cached;
    }

    /************************************************************************
     * {@inheritDoc} 
     */
    @Override
    public Object 
    replace(
        Object             original,
        Object             target,
        SessionImplementor session,
        Object             owner) 
            throws HibernateException
    {
        return original;
    }

}

// ##########################################################################
