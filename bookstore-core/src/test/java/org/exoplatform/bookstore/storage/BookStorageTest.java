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
package org.exoplatform.bookstore.storage;

import java.util.List;

import javax.jcr.SimpleCredentials;

import org.exoplatform.bookstore.common.BookstoreConstants;
import org.exoplatform.bookstore.exception.DataDuplicateException;
import org.exoplatform.bookstore.exception.DataNotFoundException;
import org.exoplatform.bookstore.model.Book;
import org.exoplatform.bookstore.storage.api.BookStorage;
import org.exoplatform.bookstore.storage.impl.BookStorageImpl;
import org.exoplatform.container.StandaloneContainer;

import junit.framework.TestCase;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Nov 14, 2011  
 */
public class BookStorageTest extends TestCase {

  protected static BookStorage bookStorage;
  
  protected static StandaloneContainer standaloneContainer;
  
  private static final String TEST_CONFIGURATION = "/conf/portal/test-configuration.xml";
  
  private static final String LOGIN_CONFIGURATION = "/conf/portal/login.conf";
  
  static {
    initContainer();
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    assertNotNull(bookStorage);
    bookStorage.deleteAll();
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testBookStorage() throws DataDuplicateException, DataNotFoundException {
    
    Book bookBean = new Book(BookstoreConstants.CATEGORY_NOVEL,
                             "978-0439574280",
                             "Adventures Of Sherlock Holmes",
                             "Scholastic Paperbacks",
                             "image/gif",
                             new byte[] {0, 1});
    Book returnBookBean = bookStorage.insert(bookBean);
    
    assertNotNull(returnBookBean);
    assertNotNull(returnBookBean.getId());
    assertEquals(bookBean.getIsbn(), returnBookBean.getIsbn());
    assertEquals(bookBean.getTitle(), returnBookBean.getTitle());
    assertEquals(bookBean.getPublisher(), returnBookBean.getPublisher());
  }
  
  public void testFindAll() throws DataDuplicateException, DataNotFoundException {
    
    Book bookBean = new Book(BookstoreConstants.CATEGORY_NOVEL,
                             "978-0439574280",
                             "Adventures Of Sherlock Holmes",
                             "Scholastic Paperbacks",
                             "image/gif",
                             new byte[] {0, 1});
    bookStorage.insert(bookBean);
    bookBean = new Book(BookstoreConstants.CATEGORY_NOVEL,
                        "978-1897093610",
                        "Treasure Island",
                        "Qualitas Classics",
                        "image/gif",
                        new byte[] {0, 1});
    bookStorage.insert(bookBean);
    
    List<Book> bookList = bookStorage.findAll();
    assertNotNull(bookList);
    assertEquals(2, bookList.size());
  }
  
  public void testFindByTitle() throws DataDuplicateException, DataNotFoundException {
    
    Book bookBean = new Book(BookstoreConstants.CATEGORY_NOVEL,
                             "978-0439574280",
                             "Adventures Of Sherlock Holmes",
                             "Scholastic Paperbacks",
                             "image/gif",
                             new byte[] {0, 1});
    bookStorage.insert(bookBean);
    bookBean = new Book(BookstoreConstants.CATEGORY_NOVEL,
                        "978-1897093610",
                        "Treasure Island",
                        "Qualitas Classics",
                        "image/gif",
                        new byte[] {0, 1});
    bookStorage.insert(bookBean);
    bookBean = new Book(BookstoreConstants.CATEGORY_NOVEL,
                        "978-1257245711",
                        "The Merry Adventures of Robin Hood",
                        "Public Domain Books",
                        "image/gif",
                        new byte[] {0, 1});
    bookStorage.insert(bookBean);
    
    List<Book> bookBeanList = bookStorage.findByTitle("Adventures");
    assertNotNull(bookBeanList);
    assertEquals(2, bookBeanList.size());
  }
  
  public void testIsExist() throws DataDuplicateException, DataNotFoundException {
    
    Book bookBean = new Book(BookstoreConstants.CATEGORY_NOVEL,
                             "978-0439574280",
                             "Adventures Of Sherlock Holmes",
                             "Scholastic Paperbacks",
                             "image/gif",
                             new byte[] {0, 1});
    bookBean = bookStorage.insert(bookBean);
    Book newBookBean = new Book(BookstoreConstants.CATEGORY_NOVEL,
                        "978-1897093610",
                        "Treasure Island",
                        "Qualitas Classics",
                        "image/gif",
                        new byte[] {0, 1});
    newBookBean = bookStorage.insert(newBookBean);
    
    assertTrue(bookStorage.isExists(bookBean.getIsbn()));
    assertTrue(bookStorage.isExists(newBookBean.getIsbn()));
  }
  
  public void testUpdateBook() throws DataDuplicateException, DataNotFoundException {
    
    Book bookBean = new Book(BookstoreConstants.CATEGORY_NOVEL,
                             "978-0439574280",
                             "Adventures Of Sherlock Holmes",
                             "Scholastic Paperbacks",
                             "image/gif",
                             new byte[] {0, 1});
    bookBean = bookStorage.insert(bookBean);
    
    Book updateBookBean = new Book(bookBean.getCategory(),
                                   bookBean.getIsbn(),
                                   bookBean.getTitle(),
                                   bookBean.getPublisher(),
                                   bookBean.getImageMimeType(),
                                   bookBean.getImageBytes());
    updateBookBean.setId(bookBean.getId());
    updateBookBean.setIsbn("8668668668");
    
    bookStorage.updateBook(updateBookBean);
    bookBean = bookStorage.findByIsbn("8668668668");
    assertNotNull(bookBean);
    assertEquals("8668668668", bookBean.getIsbn());
  }
  
  public void testDeleteBook() throws DataDuplicateException, DataNotFoundException {
    
    Book bookBean = new Book(BookstoreConstants.CATEGORY_NOVEL,
                             "978-0439574280",
                             "Adventures Of Sherlock Holmes",
                             "Scholastic Paperbacks",
                             "image/gif",
                             new byte[] {0, 1});
    bookBean = bookStorage.insert(bookBean);
    
    assertNotNull(bookBean);
    bookStorage.deleteBook(bookBean.getId());
    bookBean = bookStorage.findById(bookBean.getId());
    assertNull(bookBean);
  }
  
  public void testDeleteAll() throws DataDuplicateException, DataNotFoundException {
    
    Book bookBean = new Book(BookstoreConstants.CATEGORY_NOVEL,
                             "978-0439574280",
                             "Adventures Of Sherlock Holmes",
                             "Scholastic Paperbacks",
                             "image/gif",
                             new byte[] {0, 1});
    bookStorage.insert(bookBean);
    bookBean = new Book(BookstoreConstants.CATEGORY_NOVEL,
                        "978-1897093610",
                        "Treasure Island",
                        "Qualitas Classics",
                        "image/gif",
                        new byte[] {0, 1});
    bookStorage.insert(bookBean);
    
    List<Book> bookList = bookStorage.findAll();
    assertNotNull(bookList);
    assertEquals(2, bookList.size());
    
    bookStorage.deleteAll();
    bookList = bookStorage.findAll();
    assertEquals(0, bookList.size());
  }
  
  public void testFindById() throws DataDuplicateException, DataNotFoundException {
    
    Book book = new Book(BookstoreConstants.CATEGORY_NOVEL,
                             "978-0439574280",
                             "Adventures Of Sherlock Holmes",
                             "Scholastic Paperbacks",
                             "image/gif",
                             new byte[] {0, 1});
    book = bookStorage.insert(book);
    
    Book returnBookBean = bookStorage.findById(book.getId());
    assertNotNull(returnBookBean);
    assertEquals(book.getId(), returnBookBean.getId());
    assertEquals(book.getIsbn(), returnBookBean.getIsbn());
    assertEquals(book.getTitle(), returnBookBean.getTitle());
    assertEquals(book.getPublisher(), returnBookBean.getPublisher());
  }
  
  public void testFindByIsbn() throws DataDuplicateException, DataNotFoundException {
    
    Book book = new Book(BookstoreConstants.CATEGORY_NOVEL,
                             "978-0439574280",
                             "Adventures Of Sherlock Holmes",
                             "Scholastic Paperbacks",
                             "image/gif",
                             new byte[] {0, 1});
    book = bookStorage.insert(book);
    
    Book returnBookBean = bookStorage.findByIsbn(book.getIsbn());
    assertNotNull(returnBookBean);
    assertEquals(book.getId(), returnBookBean.getId());
    assertEquals(book.getIsbn(), returnBookBean.getIsbn());
    assertEquals(book.getTitle(), returnBookBean.getTitle());
    assertEquals(book.getPublisher(), returnBookBean.getPublisher());
  }
  
  private static void initContainer() {
    try {
      String jcrConfiguration = BookStorageTest.class.getResource(TEST_CONFIGURATION).toString();
      StandaloneContainer.addConfigurationURL(jcrConfiguration);

      String loginConfiguration = BookStorageTest.class.getResource(LOGIN_CONFIGURATION).toString();
      System.setProperty("java.security.auth.login.config", loginConfiguration);

      standaloneContainer = StandaloneContainer.getInstance();
      
      bookStorage = (BookStorageImpl) standaloneContainer.getComponentInstanceOfType(BookStorageImpl.class);
      ((BookStorageImpl) bookStorage).setCredentials(new SimpleCredentials(BookstoreConstants.USERNAME, BookstoreConstants.PASSWORD.toCharArray()));
    } catch (Exception e) {
      throw new RuntimeException("Failed to initialize standalone container: " + e.getMessage(), e);
    }
  }
}
