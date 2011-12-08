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
package org.exoplatform.bookstore.storage.cache;

import junit.framework.TestCase;

import org.exoplatform.bookstore.common.BookstoreConstants;
import org.exoplatform.bookstore.model.Book;
import org.exoplatform.bookstore.storage.BookStorageTest;
import org.exoplatform.container.StandaloneContainer;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Dec 5, 2011  
 */
public class CachedBookStorageTest extends TestCase {

  protected static CachedBookStorage cachedBookStorage;
  
  protected static BookstoreStorageCacheService cacheService;
  
  protected static StandaloneContainer standaloneContainer;
  
  private static final String TEST_CONFIGURATION = "/conf/portal/test-configuration.xml";
  
  private static final String LOGIN_CONFIGURATION = "/conf/portal/login.conf";
  
  static {
    initContainer();
  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    cachedBookStorage.deleteAll();
  }

  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  
  public void testInsertBook() throws Exception {
    assertEquals(0, cacheService.getExoBookCache().getCacheSize());
    
    Book book = new Book(BookstoreConstants.CATEGORY_NOVEL,
                             "978-0439574280",
                             "Adventures Of Sherlock Holmes",
                             "Scholastic Paperbacks",
                             "image/gif",
                             new byte[] {0, 1});
    cachedBookStorage.insert(book);
    
    assertEquals(1, cacheService.getExoBookCache().getCacheSize());
    assertEquals(0, cacheService.getExoBooksCache().getCacheSize());
  }
  
  public void testFindById() throws Exception {
    
    assertEquals(0, cacheService.getExoBookCache().getCacheSize());
    
    Book book = new Book(BookstoreConstants.CATEGORY_NOVEL,
                         "978-0439574280",
                         "Adventures Of Sherlock Holmes",
                         "Scholastic Paperbacks",
                         "image/gif",
                         new byte[] {0, 1});
    book = cachedBookStorage.insert(book);
    
    assertEquals(1, cacheService.getExoBookCache().getCacheSize());
    
    cacheService.getExoBookCache().clearCache();
    assertEquals(0, cacheService.getExoBookCache().getCacheSize());
    cachedBookStorage.findById(book.getId());
    assertEquals(1, cacheService.getExoBookCache().getCacheSize());
  }
  
  public void testUpdateBook() throws Exception {
    
    assertEquals(0, cacheService.getExoBookCache().getCacheSize());
    
    Book book = new Book(BookstoreConstants.CATEGORY_NOVEL,
                         "978-0439574280",
                         "Adventures Of Sherlock Holmes",
                         "Scholastic Paperbacks",
                         "image/gif",
                         new byte[] {0, 1});
    book = cachedBookStorage.insert(book);
    
    assertEquals(1, cacheService.getExoBookCache().getCacheSize());
    book.setTitle("New title");
    
    cachedBookStorage.updateBook(book);
    assertEquals(0, cacheService.getExoBookCache().getCacheSize());
  }
  
  public void testFindByIsbn() throws Exception {
    
    assertEquals(0, cacheService.getExoBookCache().getCacheSize());
    assertEquals(0, cacheService.getExoBookIndexCache().getCacheSize());
    
    Book book = new Book(BookstoreConstants.CATEGORY_NOVEL,
                         "978-0439574280",
                         "Adventures Of Sherlock Holmes",
                         "Scholastic Paperbacks",
                         "image/gif",
                         new byte[] {0, 1});
    book = cachedBookStorage.insert(book);
    
    assertEquals(1, cacheService.getExoBookCache().getCacheSize());
    assertEquals(0, cacheService.getExoBookIndexCache().getCacheSize());
    
    cacheService.getExoBookCache().clearCache();
    assertEquals(0, cacheService.getExoBookCache().getCacheSize());
    assertEquals(0, cacheService.getExoBookIndexCache().getCacheSize());
    cachedBookStorage.findByIsbn("978-0439574280");
    assertEquals(1, cacheService.getExoBookCache().getCacheSize());
    assertEquals(1, cacheService.getExoBookIndexCache().getCacheSize());
  }
  
  public void testFindByTitle() throws Exception {
    
    assertEquals(0, cacheService.getExoBookCache().getCacheSize());
    
    Book book1 = new Book(BookstoreConstants.CATEGORY_NOVEL,
                         "978-0439574280",
                         "Adventures Of Sherlock Holmes",
                         "Scholastic Paperbacks",
                         "image/gif",
                         new byte[] {0, 1});
    book1 = cachedBookStorage.insert(book1);
    
    assertEquals(1, cacheService.getExoBookCache().getCacheSize());

    assertEquals(0, cacheService.getExoBooksCache().getCacheSize());
    cachedBookStorage.findByTitle("Adventures Of Sherlock Holmes");
    assertEquals(1, cacheService.getExoBooksCache().getCacheSize());

    Book book2 = new Book(BookstoreConstants.CATEGORY_NOVEL,
                         "978-11222311251",
                         "Web Development",
                         "eXo Platform SEA",
                         "image/gif",
                         new byte[] {0, 1});
    book2 = cachedBookStorage.insert(book2);

    assertEquals(0, cacheService.getExoBooksCache().getCacheSize());
  }
  
  private static void initContainer() {
    try {
      String jcrConfiguration = BookStorageTest.class.getResource(TEST_CONFIGURATION).toString();
      StandaloneContainer.addConfigurationURL(jcrConfiguration);

      String loginConfiguration = BookStorageTest.class.getResource(LOGIN_CONFIGURATION).toString();
      System.setProperty("java.security.auth.login.config", loginConfiguration);

      standaloneContainer = StandaloneContainer.getInstance();
      
      cachedBookStorage = (CachedBookStorage) standaloneContainer.getComponentInstance(CachedBookStorage.class);     
      cacheService = (BookstoreStorageCacheService) standaloneContainer.getComponentInstanceOfType(BookstoreStorageCacheService.class);
    } catch (Exception e) {
      throw new RuntimeException("Failed to initialize standalone container: " + e.getMessage(), e);
    }
  }
}
