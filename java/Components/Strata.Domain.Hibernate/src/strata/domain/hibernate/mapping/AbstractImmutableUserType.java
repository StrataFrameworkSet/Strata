//////////////////////////////////////////////////////////////////////////////
// AbstractUserType.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.hibernate.mapping;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import java.io.Serializable;

public abstract
class AbstractImmutableUserType<T extends Serializable>
    implements UserType
{
    private final Class<T> itsType;

    protected
    AbstractImmutableUserType(Class<T> type) { itsType = type; }

    @Override
    public Class<? extends Serializable>
    returnedClass()
    {
        return itsType;
    }

    @Override
    public boolean
    equals(Object x,Object y)
        throws HibernateException
    {
        if (x == y)
            return true;

        if (x == null || y == null)
            return false;

        return x.equals(y);
    }

    @Override
    public int
    hashCode(Object x)
        throws HibernateException
    {
        return x != null ? x.hashCode() : 0;
    }

    @Override
    public Object
    deepCopy(Object original)
        throws HibernateException
    {
        return itsType.cast(original);
    }

    @Override
    public boolean
    isMutable()
    {
        return false;
    }

    @Override
    public Serializable
    disassemble(Object original)
        throws HibernateException
    {
        return itsType.cast(original);
    }

    @Override
    public Object
    assemble(Serializable serializable,Object owner)
        throws HibernateException
    {
        return itsType.cast(serializable);
    }

    @Override
    public Object
    replace(Object original,Object target,Object owner)
        throws HibernateException
    {
        return itsType.cast(original);
    }
}

//////////////////////////////////////////////////////////////////////////////
