//////////////////////////////////////////////////////////////////////////////
// DiagnosticRepository.java
//////////////////////////////////////////////////////////////////////////////

package strata.diagnostic.core.repository;

import strata.diagnostic.core.common.DiagnosticSuite;
import strata.diagnostic.core.common.IDiagnostic;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public
class DiagnosticRepository
    implements IDiagnosticRepository
{
    private final Map<String,IDiagnostic> itsDiagnostics;

    public
    DiagnosticRepository()
    {
        itsDiagnostics = new ConcurrentHashMap<>();
    }

    @Override
    public IDiagnosticRepository
    insert(IDiagnostic diagnostic)
    {
        itsDiagnostics.put(diagnostic.getName(),diagnostic);

        if (diagnostic instanceof DiagnosticSuite)
        {
            DiagnosticSuite suite = (DiagnosticSuite)diagnostic;

            suite
                .getDiagnostics()
                .stream()
                .forEach(d -> insert(d));
        }

        return this;
    }

    @Override
    public IDiagnosticRepository
    remove(IDiagnostic diagnostic)
    {
        itsDiagnostics.remove(diagnostic.getName(),diagnostic);
        return this;
    }

    @Override
    public IDiagnosticRepository
    clear()
    {
        itsDiagnostics.clear();
        return this;
    }

    @Override
    public Optional<IDiagnostic>
    getUnique(String diagnosticName)
    {
        return
            hasUnique(diagnosticName)
                ? Optional.ofNullable(itsDiagnostics.get(diagnosticName))
                : Optional.empty();
    }

    @Override
    public Collection<IDiagnostic>
    getMatching(Predicate<IDiagnostic> predicate)
    {
        return
            itsDiagnostics
                .values()
                .stream()
                .filter(predicate)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<IDiagnostic>
    getAll()
    {
        return itsDiagnostics.values();
    }

    @Override
    public boolean
    hasUnique(String diagnosticName)
    {
        return itsDiagnostics.containsKey(diagnosticName);
    }

    @Override
    public boolean
    hasMatching(Predicate<IDiagnostic> predicate)
    {
        return
            itsDiagnostics
                .values()
                .stream()
                .anyMatch(predicate);
    }
}

//////////////////////////////////////////////////////////////////////////////
