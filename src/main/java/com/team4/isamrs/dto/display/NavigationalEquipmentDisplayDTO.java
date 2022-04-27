package com.team4.isamrs.dto.display;

import lombok.Data;

import javax.persistence.Column;

@Data
public class NavigationalEquipmentDisplayDTO implements DisplayDTO {
    private Long id;
    private String name;
}
