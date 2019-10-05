//////////////////////////////////////////////////////////////////////////////
// ExcludeAvroFieldsMixinResolver.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.mapper;

import com.fasterxml.jackson.databind.introspect.ClassIntrospector;

public
class ExcludeAvroFieldsMixinResolver
    implements ClassIntrospector.MixInResolver
{
    @Override
    public Class<?>
    findMixInClassFor(Class<?> type)
    {
        System.out.println("debug: findMixInClassFor");
        return IExcludeAvroFieldsMixin.class;
    }

    @Override
    public ClassIntrospector.MixInResolver
    copy()
    {
        return this;
    }
}

//////////////////////////////////////////////////////////////////////////////
