package com.star.mkdocshelper.properties.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class GithubProperties {
    private String sshLocation;
    private String user;
    private String email;

    public GithubProperties(String sshLocation, String user, String email) {
        this.sshLocation = sshLocation;
        if (!user.isEmpty()) {
            this.user = user;
        }
        if (!email.isEmpty()) {
            this.email = email;
        }
    }
}
