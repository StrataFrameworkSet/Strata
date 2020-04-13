//////////////////////////////////////////////////////////////////////////////
// AbstractUserType.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.hibernate.mapping;

import org.hibernate.HibernateException;
import org.hibernate.usertype.CompositeUserType;

import java.io.Serializable;

public abstract
class AbstractCompositeUserType<T extends Serializable>
    implements CompositeUserType
{
    private final Class<T> itsType;

    protected
    AbstractCompositeUserType(Class<T> type)
    {
        itsType = type;
    }

    @Override
    public Class<? extends Serializable>
    returnedClass()
    {
        return itsType;
    }

    @Override
    public boolean
    equals(Object x,Object y) throws HibernateException
    {
        if (x == y)
            return true;

        if (x == null || y == null)
            return false;

        return x.equals(y);
    }

    @Override
    public int
    hashCode(Object x) throws HibernateException
    {
        return x != null ? x.hashCode() : 0;
    }
}

//////////////////////////////////////////////////////////////////////////////
