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
public class Comment {
    @Id
    private int commentno;
    private int boardno;
    private String content;
    private String uid;
    private String reg_date;

}