//////////////////////////////////////////////////////////////////////////////
// IDiagnosticRepository.java
//////////////////////////////////////////////////////////////////////////////

package strata.diagnostic.core.repository;

import strata.diagnostic.core.common.IDiagnostic;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public
interface IDiagnosticRepository
{
    IDiagnosticRepository
    insert(IDiagnostic diagnostic);

    IDiagnosticRepository
    remove(IDiagnostic diagnostic);

    IDiagnosticRepository
    clear();

    Optional<IDiagnostic>
    getUnique(String diagnosticName);

    Collection<IDiagnostic>
    getMatching(Predicate<IDiagnostic> predicate);

    Collection<IDiagnostic>
    getAll();

    boolean
    hasUnique(String diagnosticName);

    boolean
    hasMatching(Predicate<IDiagnostic> predicate);
}

//////////////////////////////////////////////////////////////////////////////