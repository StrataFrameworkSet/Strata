//////////////////////////////////////////////////////////////////////////////
// OptionalExtension.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.utility;

import java.util.Optional;
import java.util.function.Consumer;
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

    public static <T> void
    ifPresentOrElse(
        Optional<T> optional,
        Consumer<T> present,
        Runnable    notPresent)
    {
        if (optional.isPresent())
            present.accept(optional.get());
        else
            notPresent.run();
    }

    public static <T> void
    ifNotPresent(Optional<T> optional,Runnable task)
    {
        if (!optional.isPresent())
            task.run();
    }
}

//////////////////////////////////////////////////////////////////////////////
