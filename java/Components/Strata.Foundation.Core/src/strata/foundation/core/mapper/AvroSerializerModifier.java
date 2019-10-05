//////////////////////////////////////////////////////////////////////////////
// AvroSerializerModifier.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.mapper;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificData;

import java.util.List;
import java.util.stream.Collectors;

public
class AvroSerializerModifier
    extends BeanSerializerModifier
{
    @Override
    public List<BeanPropertyWriter>
    changeProperties(
        SerializationConfig      config,
        BeanDescription          beanDesc,
        List<BeanPropertyWriter> beanProperties)
    {
        return
            beanProperties
                .stream()
                .filter( p -> isNotSchemaOrSpecificData(p))
                .collect(Collectors.toList());
    }

    private static boolean
    isNotSchemaOrSpecificData(BeanPropertyWriter property)
    {
        JavaType type = property.getType();

        return
            !(type.isTypeOrSubTypeOf(Schema.class) ||
                type.isTypeOrSubTypeOf(SpecificData.class));
    }
}

//////////////////////////////////////////////////////////////////////////////
