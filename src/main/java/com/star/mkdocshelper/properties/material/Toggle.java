package com.star.mkdocshelper.properties.material;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Toggle {
    public static Toggle dayToggle = new Toggle(
            "material/weather-night",
            "切换至夜间模式"
    );
    public static Toggle nightToggle = new Toggle(
            "material/weather-sunny",
            "切换至日间模式"
    );

    private String icon;
    private String name;
}