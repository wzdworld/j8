package com.star.mkdocshelper.properties.material;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
public class PymdownxHighlightIncluded {
    @JsonProperty("pymdownx.highlight")
    private PymdownxHighlight pymdownxHighlight = new PymdownxHighlight();

}
