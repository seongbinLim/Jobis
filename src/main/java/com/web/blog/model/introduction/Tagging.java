package com.web.blog.model.introduction;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tagging")
@Getter @Setter
public class Tagging implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name="introno")
    private Introduction introduction;

    @Id
    @ManyToOne
    @JoinColumn(name = "tagno")
    private HashTag hashtag;
    

}