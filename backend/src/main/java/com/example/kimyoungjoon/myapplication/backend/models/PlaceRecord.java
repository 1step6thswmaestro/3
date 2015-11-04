package com.example.kimyoungjoon.myapplication.backend.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by kimyoungjoon on 2015. 10. 31..
 */
@Entity
public class PlaceRecord {
    @Id
    private Long id;
    private String name;
    private String location;
    private String tel;
    private String price;
    private String opening;
    private String category;
    private String description;
    private String img_url;
    private int grade;
    private int views;

    public PlaceRecord(Long id, String name, String location, String tel, String price, String opening, String category, String description, String img_url, int grade, int views) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.tel = tel;
        this.price = price;
        this.opening = opening;
        this.category = category;
        this.description = description;
        this.grade = grade;
        this.views = views;
    }

    public PlaceRecord(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOpening() {
        return opening;
    }

    public void setOpening(String opening) {
        this.opening = opening;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String views) {
        this.img_url = img_url;
    }
}
