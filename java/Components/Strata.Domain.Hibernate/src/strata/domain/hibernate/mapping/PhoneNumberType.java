//////////////////////////////////////////////////////////////////////////////
// PhoneNumberType.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.hibernate.mapping;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import strata.foundation.core.value.PhoneNumber;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public
class PhoneNumberType
    extends AbstractImmutableUserType<PhoneNumber>
{
    public PhoneNumberType()
    {
        super(PhoneNumber.class);
    }

    @Override
    public int[]
    sqlTypes()
    {
        return new int[] {Types.VARCHAR};
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
        String value = reader.getString(names[0]);

        return
            value != null && !value.isEmpty()
                ? new PhoneNumber(value)
                : null;
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
        if (value != null)
            stmt.setString(
                index,
                returnedClass()
                    .cast(value)
                    .toString());
        else
            stmt.setNull(index,Types.VARCHAR);
    }
}

//////////////////////////////////////////////////////////////////////////////
