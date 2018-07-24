package com.example.q.cs496_week4.MyPageActivity.myrecipe;

public class MyRecipeItem {
    private String _id;
    private String image;
    private String nickname;
    private String category;
    //private String emailAddress;

    public MyRecipeItem(String _id, String image, String nickname, String category) {
        this._id = _id;
        this.image = image;
        this.nickname = nickname;
        this.category = category;
    }

    public String get_id() {
        return _id;
    }

    public String getImage() {
        return image;
    }

    public String getNickname() {
        return nickname;
    }

    public String getCategory() {
        return category;
    }
}
