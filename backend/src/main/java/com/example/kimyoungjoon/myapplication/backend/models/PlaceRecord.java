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
    private String keyword;
    private int grade;
    private int views;
    private String standard_resolution;
    private String low_resolution;
    private String thumbnail;

    public PlaceRecord(Long id, String name, String location, String tel, String price,
                       String opening, String category, String description, String keyword,
                       int grade, int views, String standard_resolution, String low_resolution, String thumbnail) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.tel = tel;
        this.price = price;
        this.opening = opening;
        this.category = category;
        this.description = description;
        this.keyword = keyword;
        this.grade = grade;
        this.views = views;
        this.standard_resolution = standard_resolution;
        this.low_resolution = low_resolution;
        this.thumbnail = thumbnail;
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getStandard_resolution() {
        return standard_resolution;
    }

    public void setStandard_resolution(String standard_resolution) {
        this.standard_resolution = standard_resolution;
    }

    public String getLow_resolution() {
        return low_resolution;
    }

    public void setLow_resolution(String low_resolution) {
        this.low_resolution = low_resolution;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}