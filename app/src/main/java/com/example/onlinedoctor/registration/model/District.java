package com.example.onlinedoctor.registration.model;

import java.util.ArrayList;

public class District {
    private String name;
    private ArrayList<String> subDistrict;

    public District(String name, ArrayList<String> subDistrict) {
        this.name = name;
        this.subDistrict = subDistrict;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getSubDistrict() {
        return subDistrict;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubDistrict(ArrayList<String> subDistrict) {
        this.subDistrict = subDistrict;
    }
}
