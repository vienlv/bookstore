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

  private final String id;

  private final String isbn;

  private final String title;

  private final String publisher;
  
  private final String category;
  
  private final String imageMimeType;
  
  private final byte[] imageBytes;
  
  public BookData(Book book) {
    
    if (book != null) {
      this.id = book.getId();
      this.isbn = book.getIsbn();
      this.title = book.getTitle();
      this.publisher = book.getPublisher();
      this.category = book.getCategory();
      this.imageMimeType = book.getImageMimeType();
      this.imageBytes = book.getImageBytes();
    }
    else {
      this.id = null;
      this.isbn = null;
      this.title = null;
      this.publisher = null;
      this.category = null;
      this.imageMimeType = null;
      this.imageBytes = null;
    }
  }

  public String getId() {
    return id;
  }

  public String getIsbn() {
    return isbn;
  }

  public String getTitle() {
    return title;
  }

  public String getPublisher() {
    return publisher;
  }
  
  public String getCategory() {
    return category;
  }
  
  public String getImageMimeType() {
    return imageMimeType;
  }
  
  public byte[] getImageBytes() {
    return imageBytes;
  }
  
  public Book build() {

    if (id == null) {
      return null;
    }

    return new Book(category, isbn, title, publisher, imageMimeType, imageBytes);
  }
}
