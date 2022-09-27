package com.ssw.fssw.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ID")
    private Long id;

    @Column(name="USER_EMAIL_ID")
    private String emailId;

    @Column(name="USER_NICKNAME")
    private String nickname;

    @Column(name="USER_PASSWORD")
    private String password;

    @Column(name="USER_Role")
    private String role;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Community> communityList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

//    @ManyToMany(cascade=CascadeType.MERGE)
//    @JoinTable(
//            name="user_role",
//            joinColumns={@JoinColumn(name="USER_ID",referencedColumnName = "ID")},
//            inverseJoinColumns={@JoinColumn(name = "ROLE_ID",referencedColumnName = "ID")})
//    private List<User_Role> users;

}
