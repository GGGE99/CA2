/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.CityInfo;
import java.util.ArrayList;

/**
 *
 * @author marcg
 */
public class CityInfoDTO {

    private String zipcode;
    private String city;

    public CityInfoDTO(CityInfo c) {
        this.zipcode = c.getZipCode();
        this.city = c.getCity();
    }
}
