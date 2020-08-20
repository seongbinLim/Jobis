package com.web.blog.model.introduction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Introduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int introno;
    
    @NonNull String uid;
    @NonNull private String company;
     private Date startdate;
     private Date enddate;
    @NonNull private int subjectno;
    @NonNull private String subject;
    @NonNull private String contents;
    private int limitlength;

    @JsonIgnore
    @OneToMany(mappedBy = "introduction", cascade = CascadeType.ALL)
    private List<Tagging> taggings = new ArrayList<>();
    
    public void addTagging(Tagging taggings){
        this.taggings.add(taggings);
    }

    public void removeTaggings(Tagging taggings){
        this.taggings.remove(taggings);
    }
}