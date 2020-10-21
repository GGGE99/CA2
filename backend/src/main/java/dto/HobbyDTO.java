/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Hobby;

/**
 *
 * @author madsa
 */
public class HobbyDTO {

    private String name;
    private String type;
    private String category;
    private String wikiLink;

    public HobbyDTO() {
    }

    public HobbyDTO(Hobby h) {
        this.name = h.getName();
        this.wikiLink = h.getWikiLink();
        this.category = h.getCategory();
        this.type = h.getType();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getWikiLink() {
        return wikiLink;
    }

    public void setWikiLink(String wikiLink) {
        this.wikiLink = wikiLink;
    }
    
    
}
