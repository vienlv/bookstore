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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Dec 5, 2011  
 */
public class BookFilter {

  /** Book ISBN. */
  private String isbn;
  
  /** Book title. */
  private String title;
  
  /** Book publisher. */
  private String publisher;
  
  /** Excluded book list. */
  private List<Book> excludedBookList;
  
  /**
   * Default constructor.<br/>
   */
  public BookFilter() {
    this.isbn = "";
    this.title = "";
    this.publisher = "";
    this.excludedBookList = new ArrayList<Book>();
  }

  /**
   * Getter method for isbn.<br/>
   * 
   * @return Book isbn.
   */
  public String getIsbn() {
    return isbn;
  }

  /**
   * Setter method for isbn.<br/>
   * 
   * @param isbn
   */
  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  /**
   * Getter method for title.<br/>
   * 
   * @return Book title.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Setter method for title.<br/>
   * 
   * @param title
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Getter method for publisher.<br/>
   * 
   * @return Book publisher.
   */
  public String getPublisher() {
    return publisher;
  }

  /**
   * Setter method for publisher.<br/>
   * 
   * @param publisher
   */
  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  /**
   * Getter method for excludedBookList.<br/>
   * 
   * @return Excluded book list.
   */
  public List<Book> getExcludedBookList() {
    return excludedBookList;
  }

  /**
   * Setter method for excludedBookList.<br/>
   * 
   * @param excludedBookList
   */
  public void setExcludedBookList(List<Book> excludedBookList) {
    this.excludedBookList = excludedBookList;
  }
}
