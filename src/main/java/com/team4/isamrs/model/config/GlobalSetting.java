package com.team4.isamrs.model.config;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
public class GlobalSetting {
    @Id
    private String name;

    @Column
    private String value;

    public GlobalSetting() {
        // Empty constructor required for @AllArgsConstructor
    }
}
