package com.team4.isamrs.model.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Administrator extends User {
    @Column
    boolean passwordChanged;
}
