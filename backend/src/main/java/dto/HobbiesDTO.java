/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Hobby;
import entities.Person;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marcg
 */
public class HobbiesDTO {

    private List<HobbyDTO> all = new ArrayList();

    public HobbiesDTO(List<Hobby> hobbies) {
        hobbies.forEach((h) -> {
            all.add(new HobbyDTO(h));
        });
    }

    public HobbiesDTO() {
    }

    public List<HobbyDTO> getAll() {
        return all;
    }
}
