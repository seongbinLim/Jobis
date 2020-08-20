package com.web.blog.model.user;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

@Valid
@ToString
public class DesRequest {
    @ApiModelProperty(required = true)
    @NotNull
    String uid;

    @ApiModelProperty(required = true)
    @NotNull
    String description;

    public String getUid() {
        return uid;
    }

    public String getDescription() {
        return description;
    }
}