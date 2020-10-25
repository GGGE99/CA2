/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.CityInfo;
import entities.Hobby;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marcg
 */
public class CityInfosDTO {

    private List<CityInfoDTO> all = new ArrayList();

    public CityInfosDTO(List<CityInfo> cityInfos) {
        cityInfos.forEach((c) -> {
            all.add(new CityInfoDTO(c));
        });
    }

    public CityInfosDTO() {
    }

    public List<CityInfoDTO> getAll() {
        return all;
    }
}
