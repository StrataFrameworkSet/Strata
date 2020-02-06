//////////////////////////////////////////////////////////////////////////////
// NamePredicate.java
//////////////////////////////////////////////////////////////////////////////

package strata.diagnostic.core.repository;

import strata.diagnostic.core.common.IDiagnostic;

import java.util.function.Predicate;

public
class NamePredicate
    implements Predicate<IDiagnostic>
{
    private final String itsName;

    public
    NamePredicate(String name)
    {
        itsName = name;
    }

    @Override
    public boolean
    test(IDiagnostic diagnostic)
    {
        return
            diagnostic
                .getName()
                .equals(itsName);
    }
}

//////////////////////////////////////////////////////////////////////////////
