package com.star.mkdocshelper.properties.material;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class Extra {
    // 可选
    private List<SocialElement> social = new ArrayList<>();

    public void addSocialElementToSocial(SocialElement socialElement){
        social.add(socialElement);
    }

}
