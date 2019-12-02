//////////////////////////////////////////////////////////////////////////////
// RegularExpressionPredicate.java
//////////////////////////////////////////////////////////////////////////////

package strata.diagnostic.core.repository;

import strata.diagnostic.core.common.IDiagnostic;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public
class RegularExpressionPredicate
    implements Predicate<IDiagnostic>
{
    private final Pattern itsPattern;

    public
    RegularExpressionPredicate(String pattern)
    {
        itsPattern = Pattern.compile(pattern);
    }

    @Override
    public boolean
    test(IDiagnostic diagnostic)
    {
        return
            diagnostic
                .getName()
                .matches(itsPattern.pattern());
    }
}

//////////////////////////////////////////////////////////////////////////////
