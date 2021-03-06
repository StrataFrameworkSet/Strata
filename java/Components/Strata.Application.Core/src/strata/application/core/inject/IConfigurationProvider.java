//////////////////////////////////////////////////////////////////////////////
// IConfigurationProvider.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.inject;

import javax.inject.Provider;
import java.util.Map;

public
interface IConfigurationProvider
    extends Provider<Map<String,String>> {}

//////////////////////////////////////////////////////////////////////////////