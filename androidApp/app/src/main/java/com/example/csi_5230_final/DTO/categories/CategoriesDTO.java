package com.example.csi_5230_final.DTO.categories;

import java.util.ArrayList;

public class CategoriesDTO {
    private ArrayList<String> primaryCategories;
    private ArrayList<ArrayList<String>> secondaryCategories;

    public ArrayList<String> getPrimaryCategories() {
        return primaryCategories;
    }

    public void setPrimaryCategories(ArrayList<String> primaryCategories) {
        this.primaryCategories = primaryCategories;
    }

    public ArrayList<ArrayList<String>> getSecondaryCategories() {
        return secondaryCategories;
    }

    public void setSecondaryCategories(ArrayList<ArrayList<String>> secondaryCategories) {
        this.secondaryCategories = secondaryCategories;
    }
}
