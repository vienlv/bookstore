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

import java.util.ArrayList;
import java.util.List;

import org.exoplatform.bookstore.exception.DataDuplicateException;
import org.exoplatform.bookstore.exception.DataNotFoundException;
import org.exoplatform.bookstore.model.Book;
import org.exoplatform.bookstore.model.BookFilter;
import org.exoplatform.bookstore.storage.api.BookStorage;
import org.exoplatform.bookstore.storage.cache.loader.ServiceContext;
import org.exoplatform.bookstore.storage.cache.model.data.BookData;
import org.exoplatform.bookstore.storage.cache.model.data.ListBooksData;
import org.exoplatform.bookstore.storage.cache.model.key.BookFilterKey;
import org.exoplatform.bookstore.storage.cache.model.key.BookKey;
import org.exoplatform.bookstore.storage.cache.model.key.BookPrimaryKey;
import org.exoplatform.bookstore.storage.cache.model.key.ListBooksKey;
import org.exoplatform.services.cache.ExoCache;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Dec 5, 2011  
 */
public class CachedBookStorage implements BookStorage {
  
  /** . */
  private final ExoCache<BookKey, BookData> exoBookCache;
  
  /** . */
  private final ExoCache<ListBooksKey, ListBooksData> exoBooksCache;
  
  /** . */
  private final ExoCache<BookPrimaryKey, BookKey> exoBookIndexCache;
  
  /** . */
  private final FutureExoCache<BookKey, BookData, ServiceContext<BookData>> bookCache;
  
  /** . */
  private final FutureExoCache<ListBooksKey, ListBooksData, ServiceContext<ListBooksData>> booksCache;
  
  /** . */
  private final FutureExoCache<BookPrimaryKey, BookKey, ServiceContext<BookKey>> bookIndexCache;

  /** BookStorage object. */
  private final BookStorage bookStorage;
  
  /**
   * Constructor.<br/>
   * 
   * @param bookStorageImpl
   * @param cacheService
   */
  public CachedBookStorage(final BookStorage bookStorage, final BookstoreStorageCacheService cacheService) {
    
    this.bookStorage = bookStorage;
    
    this.exoBookCache = cacheService.getExoBookCache();
    this.exoBooksCache = cacheService.getExoBooksCache();
    this.exoBookIndexCache = cacheService.getExoBookIndexCache();
    this.bookCache = CacheType.BOOK.createFutureCache(exoBookCache);    
    this.booksCache = CacheType.BOOKS.createFutureCache(exoBooksCache);
    this.bookIndexCache = CacheType.BOOK_INDEX.createFutureCache(exoBookIndexCache);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Book insert(final Book book) throws DataDuplicateException, DataNotFoundException {

    Book returnBook = bookStorage.insert(book);
    BookKey bookKey = new BookKey(returnBook);
    exoBookCache.put(bookKey, new BookData(book));
    exoBooksCache.clearCache();
    
    return returnBook;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Book findById(final String id) {
    
    BookKey bookKey = new BookKey(new Book(id));
    Book book = bookCache.get(new ServiceContext<BookData>() {
      
      public BookData execute() {
        return new BookData(bookStorage.findById(id));
      }
    }, bookKey).build();
    
    return book;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Book findByIsbn(final String isbn) {
    
    BookPrimaryKey bookPrimaryKey = new BookPrimaryKey(isbn);
    BookKey bookKey = bookIndexCache.get(new ServiceContext<BookKey>() {
      
      public BookKey execute() {
        
        Book book = bookStorage.findByIsbn(isbn);
        
        if (book == null) {
          return null;
        }
        
        BookKey bookKey = new BookKey(book);
        exoBookCache.put(bookKey, new BookData(book));
        return bookKey;
      }
    }, bookPrimaryKey);
    
    if(bookKey != null) {
      return findById(bookKey.getId());
    }
    
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Book> findByTitle(final String title) {
    
    BookFilter bookFilter = new BookFilter();
    BookFilterKey key = new BookFilterKey(title, bookFilter);
    ListBooksKey listKey = new ListBooksKey(key, 0, 0);
    ListBooksData listData = booksCache.get(new ServiceContext<ListBooksData>() {
      
      public ListBooksData execute() {
        List<Book> books = bookStorage.findByTitle(title);
        return buildBooksData(books);
      }
    }, listKey);
    
    return buildBooks(listData);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Book> findByPublisher(String publisher) {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Book> findAll() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateBook(final Book book) throws DataNotFoundException {
    
    BookKey bookKey = new BookKey(new Book(book.getId()));
    exoBookCache.remove(bookKey);
    exoBooksCache.clearCache();
    
    bookStorage.updateBook(book);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteBook(final String id) throws DataNotFoundException {
    
    bookStorage.deleteBook(id);
    BookKey bookKey = new BookKey(new Book(id));
    exoBookCache.remove(bookKey);
    exoBooksCache.clearCache();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteAll() {
    bookStorage.deleteAll();
    exoBookCache.clearCache();
    exoBooksCache.clearCache();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isExists(String isbn) {
    // TODO Auto-generated method stub
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getAllCategories() {
    // TODO Auto-generated method stub
    return null;
  }
  
  /**
   * Build the book list from the caches Ids.
   *
   * @param data ids
   * @return books
   */
  private List<Book> buildBooks(ListBooksData data) {

    List<Book> books = new ArrayList<Book>();
    
    for (BookKey k : data.getIds()) {
      Book gotBook = findById(k.getId());
      books.add(gotBook);
    }
    
    return books;
  }

  /**
   * Build the book data list from the book list.
   *
   * @param books books
   * @return ids
   */
  private ListBooksData buildBooksData(List<Book> books) {

    List<BookKey> data = new ArrayList<BookKey>();
    
    for (Book i : books) {
      BookKey k = new BookKey(i);
      exoBookCache.put(k, new BookData(i));
      data.add(new BookKey(i));
    }
    
    return new ListBooksData(data);
  }
}
