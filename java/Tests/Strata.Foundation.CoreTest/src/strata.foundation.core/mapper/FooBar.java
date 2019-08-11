//////////////////////////////////////////////////////////////////////////////
// FooBar.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.mapper;

import strata.foundation.core.utility.HashCodeBuilder;

public
class FooBar
{
    private String itsFoo;
    private Integer itsBar;

    public
    FooBar()
    {
        itsFoo = "";
        itsBar = 0;
    }

    public
    FooBar(String foo,Integer bar)
    {
        itsFoo = foo;
        itsBar = bar;
    }

    public FooBar
    setFoo(String foo)
    {
        itsFoo = foo;
        return this;
    }

    public FooBar
    setBar(Integer bar)
    {
        itsBar = bar;
        return this;
    }

    public String
    getFoo()
    {
        return itsFoo;
    }

    public Integer
    getBar()
    {
        return itsBar;
    }

    @Override
    public int
    hashCode()
    {
        return
            new HashCodeBuilder()
                .append(itsFoo)
                .append(itsBar)
                .getHashCode();
    }

    @Override
    public boolean
    equals(Object other)
    {
        if (other instanceof FooBar)
        {
            FooBar temp = (FooBar)other;

            return
                getFoo().equals(temp.getFoo()) &&
                    getBar().equals(temp.getBar());
        }

        return false;
    }
}

//////////////////////////////////////////////////////////////////////////////
