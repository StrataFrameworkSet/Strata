//////////////////////////////////////////////////////////////////////////////
// DiagnosticSuitePredicate.java
//////////////////////////////////////////////////////////////////////////////

package strata.diagnostic.core.repository;

import strata.diagnostic.core.common.DiagnosticSuite;
import strata.diagnostic.core.common.IDiagnostic;

import java.util.function.Predicate;

public
class DiagnosticSuitePredicate
    implements Predicate<IDiagnostic>
{
    @Override
    public boolean
    test(IDiagnostic diagnostic)
    {
        return diagnostic instanceof DiagnosticSuite;
    }

}

//////////////////////////////////////////////////////////////////////////////
