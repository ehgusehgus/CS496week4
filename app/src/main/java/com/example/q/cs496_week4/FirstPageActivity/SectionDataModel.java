package com.example.q.cs496_week4.FirstPageActivity;

import java.util.ArrayList;

public class SectionDataModel {

    private String headerTitle;
    private ArrayList<CardItemModel> allItemsInSection;

    public SectionDataModel() {

    }

    public ArrayList<CardItemModel> getAllItemsInSection() {
        return allItemsInSection;
    }

    public void setAllItemsInSection(ArrayList<CardItemModel> allItemsInSection) {
        this.allItemsInSection = allItemsInSection;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }
}
