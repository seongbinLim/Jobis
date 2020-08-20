package com.web.blog.model.timeline;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Valid
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class TimelineRequest {
    @ApiModelProperty(required = true)
    @NotNull 
    @NonNull
    String uid;
    @ApiModelProperty(required = true)
    @NotNull 
    @NonNull
    int tltype;
    @ApiModelProperty(required = true)
    @NotNull 
    String tlname; 
}