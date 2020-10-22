/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Phone;

/**
 *
 * @author marcg
 */
public class PhoneDTO {

    private String number;
    private String description;

    public PhoneDTO(Phone p) {
        this.number = p.getNumber();
        this.description = p.getDescription();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    
}
