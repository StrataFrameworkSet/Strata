//////////////////////////////////////////////////////////////////////////////
// IServiceEvent.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.service;

import java.time.LocalDateTime;

public
interface IServiceEvent<S>
{
    String
    getName();

    LocalDateTime
    getTimestamp();

    S
    getSource();
}

//////////////////////////////////////////////////////////////////////////////