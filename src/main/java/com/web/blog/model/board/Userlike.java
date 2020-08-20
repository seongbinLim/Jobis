package com.web.blog.model.board;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class Userlike {
    @Id int likeno;
    int boardno;
    String uid;
}