//////////////////////////////////////////////////////////////////////////////
// MultiSet.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.utility;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public
class MultiSet<T>
    implements IMultiSet<T>
{
    private final Map<T,AtomicLong> itsImplementation;

    public
    MultiSet()
    {
        itsImplementation = new ConcurrentHashMap<>();
    }

    @Override
    public IMultiSet<T>
    add(T element)
    {
        return add(element,1L);
    }

    @Override
    public IMultiSet<T>
    add(T element,Long multiplicity)
    {
        if (itsImplementation.containsKey(element))
            itsImplementation
                .get(element)
                .addAndGet(multiplicity);
        else
            itsImplementation.put(element,new AtomicLong(multiplicity));

        return this;
    }

    @Override
    public IMultiSet<T>
    remove(T element)
    {
        return remove(element,1L);
    }

    @Override
    public IMultiSet<T>
    remove(T element,Long multiplicity)
    {
        if (itsImplementation.containsKey(element))
        {
            AtomicLong mult =
                itsImplementation.get(element);

            if (mult.get() >= multiplicity)
                mult.set(mult.get() - multiplicity);
        }

        return this;
    }

    @Override
    public IMultiSet<T>
    removeFromUnderlying(T element)
    {
        itsImplementation.remove(element);
        return this;
    }

    @Override
    public Set<T>
    getUnderlying()
    {
        return Collections.unmodifiableSet(itsImplementation.keySet());
    }

    @Override
    public long
    getUnderlyingSize()
    {
        return itsImplementation.size();
    }

    @Override
    public long
    getCardinality()
    {
        return
            itsImplementation
                .values()
                .stream()
                .map(m -> m.get())
                .reduce(0L,Long::sum);
    }

    @Override
    public boolean
    isEmpty()
    {
        return itsImplementation.isEmpty();
    }

    @Override
    public long
    getMultiplicity(T element)
    {
        return
            itsImplementation
                .getOrDefault(
                    element,
                    new AtomicLong(0L))
                .get();
    }

    @Override
    public IMultiSet<T>
    getUnion(IMultiSet<T> other)
    {
        IMultiSet<T> union = new MultiSet<>();
        Set<T>       underlyingUnion = new HashSet<>(getUnderlying());

        underlyingUnion.addAll(other.getUnderlying());

        underlyingUnion
            .stream()
            .forEach(
                e ->
                    union.add(
                        e,
                        Long.max(
                            getMultiplicity(e),
                            other.getMultiplicity(e))));

        return union;
    }

    @Override
    public IMultiSet<T>
    getIntersection(IMultiSet<T> other)
    {
        IMultiSet<T> intersection = new MultiSet<>();
        Set<T>       underlyingIntersection = new HashSet<>(getUnderlying());

        underlyingIntersection.retainAll(other.getUnderlying());

        underlyingIntersection
            .stream()
            .forEach(
                e ->
                    intersection.add(
                        e,
                        Long.min(
                            getMultiplicity(e),
                            other.getMultiplicity(e))));

        return intersection;
    }

    @Override
    public IMultiSet<T>
    getSymmetricDifference(IMultiSet<T> other)
    {

        return null;
    }

    @Override
    public Stream<Pair<T,Long>>
    stream()
    {
        return
            itsImplementation
                .entrySet()
                .stream()
                .map(e -> Pair.create(e.getKey(),e.getValue().get()));
    }

    @Override
    public Iterator<Pair<T,Long>>
    iterator()
    {
        return stream().iterator();
    }

    @Override
    public Spliterator<Pair<T,Long>>
    spliterator()
    {
        return stream().spliterator();
    }

    @Override
    public long
    compare(IMultiSet<T> other)
    {
        return
            getSymmetricDifference(other).getCardinality() +
            getIntersection(other)
                .getUnderlying()
                .stream()
                .map(
                    e ->
                        Math.abs(
                            getMultiplicity(e) - other.getMultiplicity(e)))
                .reduce(0L,Long::sum);
    }
}

//////////////////////////////////////////////////////////////////////////////
