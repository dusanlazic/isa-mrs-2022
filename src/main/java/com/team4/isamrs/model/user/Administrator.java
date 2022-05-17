package com.team4.isamrs.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Administrator extends User {
    @Column(name = "password_changed", nullable = false)
    private boolean passwordChanged;
}
