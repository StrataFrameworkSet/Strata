//////////////////////////////////////////////////////////////////////////////
// CompletableFutureSerializerTest.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public
class CompletableFutureSerializerTest
{
    private ObjectMapper  itsMapper;

    @Before
    public void
    setUp()
    {
        itsMapper =
            new ObjectMapper()
                .registerModules(
            new SimpleModule()
                .addSerializer(
                    new CompletableFutureSerializer())
                .addDeserializer(
                    CompletableFuture.class,
                    new CompletableFutureDeserializer()));
    }

    @Ignore
    @Test
    public void
    testSerialize()
        throws Exception
    {
        CompletableFuture<FooBar> expected = new CompletableFuture<>();

        expected.complete(new FooBar("FOO",23));
        fail(itsMapper.writeValueAsString(expected));
    }

    @Test
    public void
    testDeserialize1()
        throws Exception
    {
        CompletableFuture<String> expected = new CompletableFuture<>();
        CompletableFuture<String> actual = null;

        expected.complete("FOO");
        actual =
            itsMapper.readValue(
                itsMapper.writeValueAsString(expected),
                new TypeReference<CompletableFuture<String>>(){});

        assertEquals(expected.join(),actual.join());
    }

    @Test
    public void
    testDeserialize2()
        throws Exception
    {
        CompletableFuture<FooBar> expected = new CompletableFuture<>();
        CompletableFuture<FooBar> actual = null;

        expected.complete(new FooBar("Foo",7));
        actual =
            itsMapper.readValue(
                itsMapper.writeValueAsString(expected),
                new TypeReference<CompletableFuture<FooBar>>(){});

        assertEquals(expected.join(),actual.join());
    }
}

//////////////////////////////////////////////////////////////////////////////
