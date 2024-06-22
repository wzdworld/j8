package com.star.mkdocshelper.properties.material;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ColorScheme {
    // TODO 初始值问题优化问题
    // 硬编码问题

    public static ColorScheme dayColorScheme = new ColorScheme(
            "default",
            "indigo",
            "blue",
            Toggle.dayToggle
    );
    public static ColorScheme nightColorScheme = new ColorScheme(
            "slate",
            "black",
            "indigo",
            Toggle.nightToggle
    );

    private String scheme;
    private String primary;
    private String accent;
    private Toggle toggle;

}
