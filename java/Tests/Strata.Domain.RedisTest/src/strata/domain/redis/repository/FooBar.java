//////////////////////////////////////////////////////////////////////////////
// FooBar.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.redis.repository;

import strata.foundation.core.utility.HashCodeBuilder;

public
class FooBar
{
    private Long   itsId;
    private String itsFoo;
    private Integer itsBar;

    public FooBar()
    {
        itsId = null;
        itsFoo = "";
        itsBar = 0;
    }

    public
    FooBar(Long id,String foo,Integer bar)
    {
        itsId = id;
        itsFoo = foo;
        itsBar = bar;
    }

    public
    FooBar(FooBar other)
    {
        itsId = other.itsId;
        itsFoo = other.itsFoo;
        itsBar = other.itsBar;
    }

    public FooBar
    setId(Long id)
    {
        itsId = id;
        return this;
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

    public Long
    getId() { return itsId; }

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
                .append(itsId)
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
