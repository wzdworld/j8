package com.star.mkdocshelper.properties.material;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
public class Toc {
    private String permalink = "True";
    private String slugify = "!!python/object/apply:pymdownx.slugs.slugify {}";
}
