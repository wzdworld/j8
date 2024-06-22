package com.star.mkdocshelper.properties.readthedocs;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class ReadthedocsTheme {
    private String name = "readthedocs";
}
