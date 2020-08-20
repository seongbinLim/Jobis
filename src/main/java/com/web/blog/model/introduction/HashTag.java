package com.web.blog.model.introduction;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name="hashtag")
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter @Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tagno")
    private int tagno;

    @Column(name = "uid")
    @NonNull String uid;

    @Column(name = "tagname")
    @NonNull String tagname;
    
    @JsonIgnore
    @Column(name= "cnt")
    @NonNull int cnt;

    @JsonIgnore
    @OneToMany(mappedBy = "hashtag")
    private List<Tagging> taggings = new ArrayList<>();

    
}