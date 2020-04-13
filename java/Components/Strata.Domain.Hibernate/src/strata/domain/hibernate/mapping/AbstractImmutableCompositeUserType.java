//////////////////////////////////////////////////////////////////////////////
// AbstractImmutableUserType.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.hibernate.mapping;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.io.Serializable;

public abstract
class AbstractImmutableCompositeUserType<T extends Serializable>
    extends AbstractCompositeUserType<T>
{
    protected AbstractImmutableCompositeUserType(Class<T> type)
    {
        super(type);
    }

    @Override
    public void
    setPropertyValue(Object component,int property,Object value)
        throws HibernateException
    {
        throw
            new HibernateException(
                returnedClass().getSimpleName() + " is immutable");
    }

    @Override
    public Object
    deepCopy(Object original) throws HibernateException
    {
        return returnedClass().cast(original);
    }

    @Override
    public boolean
    isMutable()
    {
        return false;
    }

    @Override
    public Serializable
    disassemble(
        Object                           original,
        SharedSessionContractImplementor sharedSessionContractImplementor)
        throws HibernateException
    {
        return returnedClass().cast(original);
    }

    @Override
    public Object
    assemble(
        Serializable                     serializable,
        SharedSessionContractImplementor sharedSessionContractImplementor,
        Object                           owner)
        throws HibernateException
    {
        return returnedClass().cast(serializable);
    }

    @Override
    public Object
    replace(
        Object                           original,
        Object                           target,
        SharedSessionContractImplementor sharedSessionContractImplementor,
        Object                           owner)
        throws HibernateException
    {
        return returnedClass().cast(original);
    }
}

//////////////////////////////////////////////////////////////////////////////
