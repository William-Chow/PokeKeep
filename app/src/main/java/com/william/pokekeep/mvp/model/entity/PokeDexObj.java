package com.william.pokekeep.mvp.model.entity;

import java.util.List;

/**
 * Created by William Chow on 2019-11-18.
 */
public class PokeDexObj {

    private String name;
    private int generation;
    private List<PokeDexTypeObj> type;
    private String description;
    private String image;
    private List<String> power;
    private int ranking;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public List<PokeDexTypeObj> getType() {
        return type;
    }

    public void setType(List<PokeDexTypeObj> type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getPower() {
        return power;
    }

    public void setPower(List<String> power) {
        this.power = power;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
