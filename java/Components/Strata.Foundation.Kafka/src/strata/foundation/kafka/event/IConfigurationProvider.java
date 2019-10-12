//////////////////////////////////////////////////////////////////////////////
// IConfigurationProvider.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.kafka.event;

import javax.inject.Provider;
import java.util.Map;

public
interface IConfigurationProvider
    extends Provider<Map<String,Object>> {}

//////////////////////////////////////////////////////////////////////////////