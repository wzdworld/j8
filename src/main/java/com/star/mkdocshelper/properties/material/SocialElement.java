package com.star.mkdocshelper.properties.material;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class SocialElement {
    private String icon;
    private String name;
    private String link;
}
