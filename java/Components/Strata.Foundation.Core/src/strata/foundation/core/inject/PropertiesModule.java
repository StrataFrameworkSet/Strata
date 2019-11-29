//////////////////////////////////////////////////////////////////////////////
// PropertiesModule.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.inject;

import com.google.inject.Scopes;

import java.util.Properties;

public
class PropertiesModule
    extends AbstractModule
{
    @Override
    protected void
    configure()
    {
        bind(Properties.class)
            .toProvider(ApplicationPropertiesProvider.class)
            .in(Scopes.SINGLETON);
    }
}

//////////////////////////////////////////////////////////////////////////////
