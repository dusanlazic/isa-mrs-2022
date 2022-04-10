package com.team4.isamrs.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Administrator extends User {
    @Column(name = "password_changed", nullable = false)
    boolean passwordChanged;
}
