//////////////////////////////////////////////////////////////////////////////
// OptionalExtension.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.utility;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public
class OptionalExtension
{
    public static <T,U> U
    ifPresentOrElse(
        Optional<T>   optional,
        Function<T,U> present,
        Supplier<U>   notPresent)
    {
        if (optional.isPresent())
            return present.apply(optional.get());

        return notPresent.get();
    }
}

//////////////////////////////////////////////////////////////////////////////
