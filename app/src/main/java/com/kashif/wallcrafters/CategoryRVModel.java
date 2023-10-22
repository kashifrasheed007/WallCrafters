package com.kashif.wallcrafters;

public class CategoryRVModel {
    private String category;
    private String categotryImageUrl;

    public CategoryRVModel(String category, String categotryImageUrl) {
        this.category = category;
        this.categotryImageUrl = categotryImageUrl;
    }

    public String getCategory() {
        return category;
    }

    public String getCategotryImageUrl() {
        return categotryImageUrl;
    }

}
