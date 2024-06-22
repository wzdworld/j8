package com.star.mkdocshelper.properties.material;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.ALWAYS)
@Getter
@Setter
public class PymdownxHighlight {
    private Boolean linenums = true;
    private Boolean anchor_linenums = true;
    private Boolean auto_title = true;
    private String line_spans = "__span";
    private Boolean pygments_lang_class = true;
}
