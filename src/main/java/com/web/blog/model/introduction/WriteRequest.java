package com.web.blog.model.introduction;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class WriteRequest {

    @ApiModelProperty(required = true)
    @NotNull
    @NonNull
    Introduction introduction;

    @ApiModelProperty(required = true)
    @NonNull
    List<HashTag> hashTag = new ArrayList<>();


}