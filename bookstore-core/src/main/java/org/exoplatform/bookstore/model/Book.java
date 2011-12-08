/*
 * Copyright (C) 2003-2011 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.exoplatform.bookstore.model;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Nov 14, 2011  
 */
public class Book {

  /** Id. */
  private String id;
  
  /** Book category. */
  private String category;
  
  /** Book ISBN. */
  private String isbn;
  
  /** Book title. */
  private String title;
  
  /** Book publisher. */
  private String publisher;
  
  /** Book image content. */
  private byte[] imageBytes;
  
  /** Book image mime-type. */
  private String imageMimeType;

  /**
   * Default constructor.<br/>
   */
  public Book() {
  }
  
  /**
   * Constructor.<br/>
   * 
   * @param id
   */
  public Book(String id) {
    this.id = id;
  }
  
  /**
   * Constructor.<br/>
   * 
   * @param category
   * @param isbn
   * @param title
   * @param publisher
   * @param imageMimeType
   * @param imageBytes
   */
  public Book(String category, String isbn, String title, String publisher, String imageMimeType, byte[] imageBytes) {
    
    this.category = category;
    this.isbn = isbn;
    this.title = title;
    this.publisher = publisher;
    this.imageMimeType = imageMimeType;
    this.imageBytes = imageBytes;
  }

  /**
   * Getter method for id field.<br/>
   * 
   * @return
   */
  public String getId() {
    return id;
  }

  /**
   * Setter method for id field.<br/>
   * 
   * @param id
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Getter method for category field.<br/>
   * 
   * @return
   */
  public String getCategory() {
    return category;
  }

  /**
   * Setter method for category field.<br/>
   * 
   * @param category
   */
  public void setCategory(String category) {
    this.category = category;
  }

  /**
   * Getter method for isbn field.<br/>
   * 
   * @return
   */
  public String getIsbn() {
    return isbn;
  }

  /**
   * Setter method for isbn field.<br/>
   * 
   * @param isbn
   */
  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  /**
   * Getter method for title field.<br/>
   * 
   * @return
   */
  public String getTitle() {
    return title;
  }

  /**
   * Setter method for title field.<br/>
   * 
   * @param title
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Getter method for publisher field.<br/>
   * 
   * @return
   */
  public String getPublisher() {
    return publisher;
  }

  /**
   * Setter method for publisher field.<br/>
   * 
   * @param publisher
   */
  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }
  
  /**
   * Getter method for imageBytes field.<br/>
   * 
   * @return
   */
  public byte[] getImageBytes() {
    return imageBytes;
  }

  /**
   * Setter method for imageBytes field.<br/>
   * 
   * @param imageBytes
   */
  public void setImageBytes(byte[] imageBytes) {
    this.imageBytes = imageBytes;
  }

  /**
   * Getter method for imageMimeType field.<br/>
   * 
   * @return
   */
  public String getImageMimeType() {
    return imageMimeType;
  }

  /**
   * Setter method for imageMimeType field.<br/>
   * 
   * @param imageMimeType
   */
  public void setImageMimeType(String imageMimeType) {
    this.imageMimeType = imageMimeType;
  }
}
