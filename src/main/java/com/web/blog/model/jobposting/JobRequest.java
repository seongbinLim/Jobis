package com.web.blog.model.jobposting;


import java.sql.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

@Valid
@ToString
public class JobRequest {
    @ApiModelProperty(required = true)
    @NotNull 
    String id;
    @ApiModelProperty(required = true)
    @NotNull 
    String url;
    @ApiModelProperty(required = true)
    @NotNull 
    Date startdate;
    @ApiModelProperty(required = true)
    @NotNull 
    Date enddate;
    @ApiModelProperty(required = true)
    @NotNull 
    String company;
    @ApiModelProperty(required = true)
    @NotNull 
    String title;
    @ApiModelProperty(required = true)
    @NotNull 
    String location;
    @ApiModelProperty(required = true)
    @NotNull 
    String job_type;
    @ApiModelProperty(required = true)
    @NotNull 
    String industry;
    @ApiModelProperty(required = true)
    @NotNull 
    String job_category;
    @ApiModelProperty(required = true)
    @NotNull 
    String experience_level;
    @ApiModelProperty(required = true)
    @NotNull 
    String required_education_level;
    @ApiModelProperty(required = true)
    @NotNull 
    String keyword;
    @ApiModelProperty(required = true)
    @NotNull 
    String salary;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJob_type() {
        return job_type;
    }

    public void setJob_type(String job_type) {
        this.job_type = job_type;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getJob_category() {
        return job_category;
    }

    public void setJob_category(String job_category) {
        this.job_category = job_category;
    }

    public String getExperience_level() {
        return experience_level;
    }

    public void setExperience_level(String experience_level) {
        this.experience_level = experience_level;
    }

    public String getRequired_education_level() {
        return required_education_level;
    }

    public void setRequired_education_level(String required_education_level) {
        this.required_education_level = required_education_level;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }



}