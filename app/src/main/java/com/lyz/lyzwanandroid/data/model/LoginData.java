package com.lyz.lyzwanandroid.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author mac
 * @create 2021/01/09
 * @Describe
 */
public class LoginData {
    /**
     * admin : false
     * chapterTops : []
     * coinCount : 0
     * collectIds : []
     * email :
     * icon :
     * id : 9528
     * nickname : lijian9088
     * password :
     * publicName : lijian9088
     * token :
     * type : 0
     * username : lijian9088
     */

    @SerializedName("admin")
    public Boolean admin;
    @SerializedName("coinCount")
    public Integer coinCount;
    @SerializedName("email")
    public String email;
    @SerializedName("icon")
    public String icon;
    @SerializedName("id")
    public Integer id;
    @SerializedName("nickname")
    public String nickname;
    @SerializedName("password")
    public String password;
    @SerializedName("publicName")
    public String publicName;
    @SerializedName("token")
    public String token;
    @SerializedName("type")
    public Integer type;
    @SerializedName("username")
    public String username;
    @SerializedName("chapterTops")
    public List<?> chapterTops;
    @SerializedName("collectIds")
    public List<?> collectIds;
}
