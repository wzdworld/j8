package com.star.mkdocshelper.properties.mkdocs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class MkDocsTheme {
    private String name = "mkdocs";
}
