//////////////////////////////////////////////////////////////////////////////
// IDomainEvent.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.core.domainevent;

import java.time.Instant;
import java.util.UUID;

public
interface IDomainEvent<S>
{
    String
    getName();

    UUID
    getCorrelationId();

    Instant
    getTimestamp();

    S
    getSource();
}

//////////////////////////////////////////////////////////////////////////////