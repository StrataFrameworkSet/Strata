//////////////////////////////////////////////////////////////////////////////
// MultiSetTest.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.utility;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public
class MultiSetTest
{
    private IMultiSet<String> itsX;
    private IMultiSet<String> itsY;
    private IMultiSet<String> itsZ;

    @Before
    public void
    setUp()
    {
        itsX = new MultiSet<>();
        itsY = new MultiSet<>();
        itsZ = new MultiSet<>();

        itsX
            .add("A",2L)
            .add("B",1L)
            .add("C",5L)
            .add("D",3L);

        itsY
            .add("B",6L)
            .add("C",3L)
            .add("D",5L)
            .add("E",7L);

        itsZ
            .add("A",2L)
            .add("B",3L)
            .add("C",5L)
            .add("D",3L);
    }

    @After
    public void
    tearDown()
    {
        itsZ = null;
        itsY = null;
        itsX = null;
    }

    @Test
    public void
    testCardinality()
    {
        assertEquals(11L,itsX.getCardinality());
        assertEquals(21L,itsY.getCardinality());
    }

    @Test
    public void
    testUnion()
    {
        IMultiSet<String> union = itsX.getUnion(itsY);

        assertEquals(25L,union.getCardinality());
        assertEquals(2L,union.getMultiplicity("A"));
        assertEquals(6L,union.getMultiplicity("B"));
        assertEquals(5L,union.getMultiplicity("C"));
        assertEquals(5L,union.getMultiplicity("D"));
        assertEquals(7L,union.getMultiplicity("E"));
    }

    @Test
    public void
    testIntersection()
    {
        IMultiSet<String> intersection = itsX.getIntersection(itsY);

        assertEquals(7L,intersection.getCardinality());
        assertEquals(0L,intersection.getMultiplicity("A"));
        assertEquals(1L,intersection.getMultiplicity("B"));
        assertEquals(3L,intersection.getMultiplicity("C"));
        assertEquals(3L,intersection.getMultiplicity("D"));
        assertEquals(0L,intersection.getMultiplicity("E"));
    }

    @Test
    public void
    testSymmetricDifference()
    {
        IMultiSet<String> symmetricDifference = itsX.getSymmetricDifference(itsY);

        assertEquals(9L,symmetricDifference.getCardinality());
        assertEquals(2L,symmetricDifference.getMultiplicity("A"));
        assertEquals(0L,symmetricDifference.getMultiplicity("B"));
        assertEquals(0L,symmetricDifference.getMultiplicity("C"));
        assertEquals(0L,symmetricDifference.getMultiplicity("D"));
        assertEquals(7L,symmetricDifference.getMultiplicity("E"));
    }

    @Test
    public void
    testDistance1()
    {
        long distance1 = itsX.getDistance(itsX);
        long distance2 = itsY.getDistance(itsY);
        long distance3 = itsX.getDistance(itsY);
        long distance4 = itsY.getDistance(itsX);
        long distance5 = itsX.getDistance(itsZ);
        long distance6 = itsZ.getDistance(itsX);

        assertEquals(0L,distance1);
        assertEquals(0L,distance2);
        assertEquals(18L,distance3);
        assertEquals(18L,distance4);
        assertEquals(2L,distance5);
        assertEquals(2L,distance6);
    }

    @Test
    public void
    testDistance2()
    {
        IMultiSet<String> contract1 = new MultiSet<>();
        IMultiSet<String> contract2 = new MultiSet<>();
        IMultiSet<String> contract3 = new MultiSet<>();
        IMultiSet<String> contract4 = new MultiSet<>();

        contract1
            .add("R1",3L)
            .add("R2",1L)
            .add("R3",2L)
            .add("R4",1L);

        contract2
            .add("R1",3L)
            .add("R2",1L)
            .add("R3",2L)
            .add("R4",1L);

        contract3
            .add("R1",4L)
            .add("R2",2L)
            .add("R3",2L)
            .add("R4",1L);

        contract4
            .add("R1",4L)
            .add("R2",4L)
            .add("R3",3L)
            .add("R4",2L)
            .add("R5",10L);

        long distance1 = contract1.getDistance(contract2);
        long distance2 = contract1.getDistance(contract3);
        long distance3 = contract2.getDistance(contract3);
        long distance4 = contract1.getDistance(contract4);
        long distance5 = contract2.getDistance(contract4);
        long distance6 = contract3.getDistance(contract4);

        assertEquals(0L,distance1);
        assertEquals(2L,distance2);
        assertEquals(2L,distance3);
        assertEquals(16L,distance4);
        assertEquals(16L,distance5);
        assertEquals(14L,distance6);
    }

    @Test
    public void
    testCompareTo()
    {
        int comparison1 = itsX.compareTo(itsX);
        int comparison2 = itsY.compareTo(itsY);
        int comparison3 = itsX.compareTo(itsY);
        int comparison4 = itsY.compareTo(itsX);

        assertEquals(0,comparison1);
        assertEquals(0,comparison2);
        assertEquals(-18,comparison3);
        assertEquals(18,comparison4);
    }
}

//////////////////////////////////////////////////////////////////////////////
