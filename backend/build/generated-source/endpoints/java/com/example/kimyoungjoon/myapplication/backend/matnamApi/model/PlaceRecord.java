/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2015-08-03 17:34:38 UTC)
 * on 2015-11-04 at 09:06:28 UTC 
 * Modify at your own risk.
 */

package com.example.kimyoungjoon.myapplication.backend.matnamApi.model;

/**
 * Model definition for PlaceRecord.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the matnamApi. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class PlaceRecord extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String category;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String description;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer grade;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String location;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String name;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String opening;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String price;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String tel;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer views;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getCategory() {
    return category;
  }

  /**
   * @param category category or {@code null} for none
   */
  public PlaceRecord setCategory(java.lang.String category) {
    this.category = category;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getDescription() {
    return description;
  }

  /**
   * @param description description or {@code null} for none
   */
  public PlaceRecord setDescription(java.lang.String description) {
    this.description = description;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getGrade() {
    return grade;
  }

  /**
   * @param grade grade or {@code null} for none
   */
  public PlaceRecord setGrade(java.lang.Integer grade) {
    this.grade = grade;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public PlaceRecord setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getLocation() {
    return location;
  }

  /**
   * @param location location or {@code null} for none
   */
  public PlaceRecord setLocation(java.lang.String location) {
    this.location = location;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getName() {
    return name;
  }

  /**
   * @param name name or {@code null} for none
   */
  public PlaceRecord setName(java.lang.String name) {
    this.name = name;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getOpening() {
    return opening;
  }

  /**
   * @param opening opening or {@code null} for none
   */
  public PlaceRecord setOpening(java.lang.String opening) {
    this.opening = opening;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getPrice() {
    return price;
  }

  /**
   * @param price price or {@code null} for none
   */
  public PlaceRecord setPrice(java.lang.String price) {
    this.price = price;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getTel() {
    return tel;
  }

  /**
   * @param tel tel or {@code null} for none
   */
  public PlaceRecord setTel(java.lang.String tel) {
    this.tel = tel;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getViews() {
    return views;
  }

  /**
   * @param views views or {@code null} for none
   */
  public PlaceRecord setViews(java.lang.Integer views) {
    this.views = views;
    return this;
  }

  @Override
  public PlaceRecord set(String fieldName, Object value) {
    return (PlaceRecord) super.set(fieldName, value);
  }

  @Override
  public PlaceRecord clone() {
    return (PlaceRecord) super.clone();
  }

}
