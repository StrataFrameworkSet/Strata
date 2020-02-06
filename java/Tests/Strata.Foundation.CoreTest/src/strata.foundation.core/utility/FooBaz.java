//////////////////////////////////////////////////////////////////////////////
// FooBaz.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.utility;

import strata.foundation.core.mapper.FooBar;

import java.util.ArrayList;
import java.util.List;

public
class FooBaz
    extends FooBar
{
    private List<String> itsBaz;

    public
    FooBaz()
    {
        itsBaz = new ArrayList<>();
    }

    public FooBaz
    setBaz(List<String> baz)
    {
        itsBaz = baz;
        return this;
    }

    public List<String>
    getBaz() { return itsBaz; }
}

//////////////////////////////////////////////////////////////////////////////
