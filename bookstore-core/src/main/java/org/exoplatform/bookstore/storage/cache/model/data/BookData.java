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
package org.exoplatform.bookstore.storage.cache.model.data;

import org.exoplatform.bookstore.model.Book;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Dec 5, 2011  
 */
public class BookData implements CacheData<Book> {

  /** serialVersionUID. */
  private static final long serialVersionUID = 7881085182390085595L;

  /** Book id. */
  private final String id;

  /** Book isbn. */
  private final String isbn;

  /** Book title. */
  private final String title;

  /** Book publisher. */
  private final String publisher;
  
  /** Book category. */
  private final String category;
  
  /** Book image mime-type. */
  private final String imageMimeType;
  
  /** Book image contents. */
  private final byte[] imageBytes;
  
  /**
   * Constructor.<br/>
   * 
   * @param book
   */
  public BookData(Book book) {
    
    if(book != null) {
      
      this.id = book.getId();
      this.isbn = book.getIsbn();
      this.title = book.getTitle();
      this.publisher = book.getPublisher();
      this.category = book.getCategory();
      this.imageMimeType = book.getImageMimeType();
      this.imageBytes = book.getImageBytes();
    } else {
      
      this.id = null;
      this.isbn = null;
      this.title = null;
      this.publisher = null;
      this.category = null;
      this.imageMimeType = null;
      this.imageBytes = null;
    }
  }

  /**
   * Getter method for id.<br/>
   * 
   * @return Book id.
   */
  public String getId() {
    return id;
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
   * Getter method for title.<br/>
   * 
   * @return Book title.
   */
  public String getTitle() {
    return title;
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
   * Getter method for category.<br/>
   * 
   * @return Book category.
   */
  public String getCategory() {
    return category;
  }
  
  /**
   * Getter method for imageMimeType.<br/>
   * 
   * @return Book image mime-type.
   */
  public String getImageMimeType() {
    return imageMimeType;
  }
  
  /**
   * Getter method for imageBytes.<br/>
   * 
   * @return Book image content.
   */
  public byte[] getImageBytes() {
    return imageBytes;
  }
  
  /**
   * {@inheritDoc}
   */
  public Book build() {

    if (id == null) {
      return null;
    }

    return new Book(category, isbn, title, publisher, imageMimeType, imageBytes);
  }
}
