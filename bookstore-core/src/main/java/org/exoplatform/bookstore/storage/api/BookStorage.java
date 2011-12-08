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
package org.exoplatform.bookstore.storage.api;

import java.util.List;

import org.exoplatform.bookstore.exception.DataDuplicateException;
import org.exoplatform.bookstore.exception.DataNotFoundException;
import org.exoplatform.bookstore.model.Book;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Nov 14, 2011  
 */
public interface BookStorage {

  /**
   * Method used to insert book.<br/> 
   * 
   * @param book
   * @return Book inserted.
   * @throws DataDuplicateException, DataNotFoundException
   */
  public Book insert(Book book) throws DataDuplicateException, DataNotFoundException;
  
  /**
   * Method used to find book by id.<br/>
   * 
   * @param id
   * @return Book found by id.
   */
  public Book findById(String id);
  
  /**
   * Method used to find book by isbn.<br/>
   * 
   * @param isbn
   * @return Book found by isbn.
   */
  public Book findByIsbn(String isbn);
  
  /**
   * Method used to find book by title.<br/>
   * 
   * @param title
   * @return Book found by isbn.
   */
  public List<Book> findByTitle(String title);
  
  /**
   * Method used to find book by publisher.<br/>
   * 
   * @param publisher
   * @return List of result books.
   */
  public List<Book> findByPublisher(String publisher);
  
  /**
   * Method used to find all book.<br/>
   * 
   * @return List of books.
   */
  public List<Book> findAll();
  
  /**
   * Method used to update book.<br/>
   * 
   * @param book
   * @return Book updated.
   */
  public void updateBook(Book book) throws DataNotFoundException;

  /**
   * Method used to delete book by id.<br/>
   * 
   * @param id
   * @throws DataNotFoundException
   */
  public void deleteBook(String id) throws DataNotFoundException;

  /**
   * Method used to delete all book.<br/>
   */
  public void deleteAll(); 

  /**
   * Method used to check book exist.<br/>
   * 
   * @param isbn
   */
  public boolean isExists(String isbn);

  /**
   * Method used to get category list.<br/>
   * 
   * @return All categories.
   */
  public List<String> getAllCategories();
}
