package com.web.blog.model.board;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@ToString
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class Board {
    @Id
    int boardno;
    String uid;
    String subject;
    int likes;
    String content;
    String interest;
    String picture;
    
    String createdat;
    String updatedat;
}