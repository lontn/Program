package com.fcu.jpa.domain.entity;

import javax.persistence.*;

@Entity
@Table(name="UserInfo")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
