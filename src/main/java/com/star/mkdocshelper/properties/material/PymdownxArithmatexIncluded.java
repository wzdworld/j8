package com.star.mkdocshelper.properties.material;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class PymdownxArithmatexIncluded {
    @JsonProperty("pymdownx.arithmatex")
    @Getter
    private PymdownxArithmatex pymdownxArithmatex = new PymdownxArithmatex();
}
