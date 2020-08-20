package com.web.blog.model.user;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

@Valid
@ToString
public class UserInfoRequest {
    @ApiModelProperty(required = true)
    @NotNull
    String uid;

    public String getUid() {
        return uid;
    }
}