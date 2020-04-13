//////////////////////////////////////////////////////////////////////////////
// HashedStringType.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.hibernate.mapping;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.BinaryType;
import org.hibernate.type.Type;
import strata.foundation.core.value.HashedString;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public
class HashedStringType
    extends AbstractImmutableCompositeUserType<HashedString>
{
    public
    HashedStringType()
    {
        super(HashedString.class);
    }

    @Override
    public String[]
    getPropertyNames()
    {
        return new String[] {"Value","SecretKey"};
    }

    @Override
    public Type[]
    getPropertyTypes()
    {
        return new Type[] {new BinaryType(),new BinaryType()};
    }

    @Override
    public Object
    getPropertyValue(Object component,int property)
        throws HibernateException
    {
        HashedString hashed = (HashedString)component;

        if (hashed == null)
            return null;

        switch (property)
        {
            case 0:
                return hashed.getValue();
            case 1:
                return hashed.getSalt();
            default:
                throw new HibernateException("unknown property");
        }
    }

    @Override
    public Object
    nullSafeGet(
        ResultSet                        reader,
        String[]                         names,
        SharedSessionContractImplementor session,
        Object                           owner)
        throws HibernateException, SQLException
    {
        String valueColumn = names[0];
        String saltColumn  = names[1];
        Object value       = null;
        Object salt        = null;

        if( reader == null )
            return null;

        value =
            BinaryType
                .INSTANCE
                .get(
                    reader,
                    valueColumn,
                    session);
        salt =
            BinaryType
                .INSTANCE
                .get(
                    reader,
                    saltColumn,
                    session);

        return
            value == null && salt == null
                ? null
                : new HashedString((byte[])value,(byte[])salt,true);
    }

    @Override
    public void
    nullSafeSet(
        PreparedStatement                stmt,
        Object                           value,
        int                              index,
        SharedSessionContractImplementor session)
        throws HibernateException, SQLException
    {
        HashedString hashed = (HashedString)value;

        if (value == null)
        {
            BinaryType
                .INSTANCE
                .set(stmt,null,index,session);
            BinaryType
                .INSTANCE
                .set(stmt,null,index+1,session);
            return;
        }

        BinaryType
            .INSTANCE
            .set(stmt,hashed.getValue(),index,session);
        BinaryType
            .INSTANCE
            .set(stmt,hashed.getSalt(),index+1,session);

    }
}

//////////////////////////////////////////////////////////////////////////////
