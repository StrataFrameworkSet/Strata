//////////////////////////////////////////////////////////////////////////////
// GuiceRequestScope.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.inject;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;
import org.jboss.resteasy.core.ResteasyContext;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public
class GuiceRequestScope
    implements Scope
{
    @Override
    public <T> Provider<T>
    scope(Key<T> key,Provider<T> creator)
    {
        return
            new Provider<T>()
            {
                @SuppressWarnings("unchecked")
                @Override
                public T get()
                {
                    Class<T> instanceClass = getInstanceType(key);
                    T instance = ResteasyContext.getContextData(instanceClass);

                    if (instance == null)
                    {
                        instance = creator.get();
                        ResteasyContext.pushContext(instanceClass, instance);
                    }

                    return instance;
                }

                @Override
                public String
                toString()
                {
                    return String.format("%s[%s]", creator, super.toString());
                }
            };
    }

    private static <T> Class<T>
    getInstanceType(Key<T> key)
    {
        Type type = key.getTypeLiteral().getType();

        if (type instanceof Class)
            return (Class<T>)type;

        if (type instanceof ParameterizedType)
            return (Class<T>)((ParameterizedType)type).getRawType();

        throw new ClassCastException("Unsupported type: " + type.getTypeName());
    }
}

//////////////////////////////////////////////////////////////////////////////
