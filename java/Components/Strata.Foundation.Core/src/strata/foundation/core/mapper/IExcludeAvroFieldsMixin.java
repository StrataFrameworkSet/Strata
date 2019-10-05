//////////////////////////////////////////////////////////////////////////////
// IExcludeAvroFieldsMixin.java
//////////////////////////////////////////////////////////////////////////////

package strata.foundation.core.mapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.avro.specific.SpecificData;

import javax.xml.validation.Schema;

public
interface IExcludeAvroFieldsMixin
{
    @JsonIgnore
    Schema
    getSchema();

    @JsonIgnore
    SpecificData
    getSpecificData();
}

//////////////////////////////////////////////////////////////////////////////
