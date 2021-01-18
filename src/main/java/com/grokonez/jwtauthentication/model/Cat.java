package com.grokonez.jwtauthentication.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "cats")
@Data
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long age;
    //    @OneToOne
//    @JoinColumn(name = "userid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "userid", nullable = false)
    private User user;
}
