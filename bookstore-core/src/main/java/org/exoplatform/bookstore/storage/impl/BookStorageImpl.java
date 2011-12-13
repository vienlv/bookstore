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
package org.exoplatform.bookstore.storage.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.jcr.SimpleCredentials;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.chromattic.api.Chromattic;
import org.chromattic.api.ChromatticBuilder;
import org.chromattic.api.ChromatticSession;
import org.chromattic.api.query.Query;
import org.chromattic.api.query.QueryBuilder;
import org.chromattic.api.query.QueryResult;
import org.chromattic.ext.ntdef.NTFile;
import org.chromattic.ext.ntdef.NTResource_Chromattic;
import org.chromattic.ext.ntdef.Resource;
import org.exoplatform.bookstore.chromattic.entity.BookEntity;
import org.exoplatform.bookstore.chromattic.entity.BookstoreEntity;
import org.exoplatform.bookstore.chromattic.entity.CategoryEntity;
import org.exoplatform.bookstore.chromattic.entity.CategoryRootEntity;
import org.exoplatform.bookstore.chromattic.lifecycle.BookChromatticSessionLifeCycle;
import org.exoplatform.bookstore.common.BookstoreConstants;
import org.exoplatform.bookstore.exception.DataDuplicateException;
import org.exoplatform.bookstore.exception.DataNotFoundException;
import org.exoplatform.bookstore.model.Book;
import org.exoplatform.bookstore.storage.api.BookStorage;
import org.exoplatform.bookstore.utils.BookstoreUtils;
import org.exoplatform.services.cache.CacheService;
import org.exoplatform.services.cache.ExoCache;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.social.core.storage.query.PropertyLiteralExpression;
import org.exoplatform.social.core.storage.query.WhereExpression;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Nov 14, 2011  
 */
public class BookStorageImpl implements BookStorage {
  
  /** LOG. */
  private static final Log LOG = ExoLogger.getLogger(BookStorage.class);
  
  /** Default categories of bookstore. */
  private static final String[] DEFAULT_CATEGORIES = { BookstoreConstants.CATEGORY_IT,
                                                       BookstoreConstants.CATEGORY_NOVEL,
                                                       BookstoreConstants.CATEGORY_SCIENTIFIC,
                                                       BookstoreConstants.CATEGORY_OTHERS };
  
  /** Chromattic object. */
  private Chromattic chromattic;
  
  /** ChromatticSession object. */
  private ChromatticSession chromatticSession;
  
  /** SimpleCredentials object. */
  private SimpleCredentials simpleCredentials;
  
  /** ExoCache object. */
  private ExoCache<Serializable, Book> exoCache;
  
  /**
   * Constructor.<br/>
   * 
   * @param cacheService
   */
  public BookStorageImpl(CacheService cacheService) {
    this.exoCache = cacheService.getCacheInstance(getClass().getName());
  }
  
  /**
   * Setter method for simpleCredentials field.<br/>
   * 
   * @param simpleCredentials
   */
  public void setCredentials(SimpleCredentials simpleCredentials) {
    this.simpleCredentials = simpleCredentials;
  }
  
  /**
   * Getter method for exoCache field.<br/>
   * 
   * @return
   */
  public ExoCache<Serializable, Book> getExoCache() {
    return exoCache;
  }
  
  /**
   * Setter method for exoCache field.<br/>
   * 
   * @param exoCache
   */
  public void setExoCache(ExoCache<Serializable, Book> exoCache) {
    this.exoCache = exoCache;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Book insert(Book book) throws DataDuplicateException, DataNotFoundException {
    
    if (book == null) {
      return null;
    }
    
    // Check existing book.
    if (isExists(book.getIsbn())) {
      throw new DataDuplicateException(String.format(BookstoreConstants.MSG_BOOK_ALREADY_EXIST, book.getTitle(), book.getIsbn()));
    }
    
    // Get CategoryEntity object.
    CategoryEntity categoryEntity = _getCategory(book.getCategory());
    
    // Check existing category.
    if (categoryEntity == null) {
      throw new DataNotFoundException(String.format(BookstoreConstants.MSG_CATEGORY_DOES_NOT_EXIST, book.getCategory()));
    }

    // Create BookEntity object.
    BookEntity bookEntity = categoryEntity.createBook();
    categoryEntity.getBooks().put(book.getIsbn(), bookEntity);
    bookEntity.setCategory(categoryEntity);
    bookEntity.setIsbn(book.getIsbn());
    bookEntity.setTitle(book.getTitle());
    bookEntity.setPublisher(book.getPublisher());
    
    // Set book image.
    if (book.getImageMimeType() != null && book.getImageBytes() != null) {
      
      NTFile image = bookEntity.getImage();
      
      if (image == null) {
        image = bookEntity.createImage();
        bookEntity.setImage(image);
      }
      
      image.setContentResource(new Resource(book.getImageMimeType(), null, book.getImageBytes()));
    }
    
    // Save session.
    chromatticSession.save();
    
    // Write log.
    LOG.info(String.format(BookstoreConstants.MSG_INSERT_BOOK_SUCCESSFUL, bookEntity.getTitle(), bookEntity.getIsbn(), bookEntity.getPublisher()));
    
    return _getBookFromBookEntity(bookEntity);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Book findById(String id) {
    
    return _getBookFromBookEntity(_findById(id));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Book findByIsbn(String isbn) {
    
    List<Book> bookList = _find(BookstoreConstants.FIND_BY_ISBN, isbn);
    return bookList.size() == 1 ? bookList.get(0) : null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Book> findByTitle(String title) {
    
    return _find(BookstoreConstants.FIND_BY_TITLE, title);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Book> findByPublisher(String publisher) {
    
    return _find(BookstoreConstants.FIND_BY_PUBLISHER, publisher);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Book> findAll() {
    
    return _find(null, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateBook(Book book) throws DataNotFoundException {
    
    // Find BookEntity by book id.
    BookEntity bookEntity = _findById(book.getId());
    
    if (bookEntity == null) {
      throw new DataNotFoundException(BookstoreConstants.MSG_SEARCH_BOOK_FAIL);
    }
    
    // Declare and initialize CategoryEntity object.
    CategoryEntity categoryEntity = _getCategory(book.getCategory());
    
    if (categoryEntity == null) {
      throw new DataNotFoundException(String.format(BookstoreConstants.MSG_CATEGORY_DOES_NOT_EXIST, book.getCategory()));
    }
    
    // Update BookEntity object.
    bookEntity.setIsbn(book.getIsbn());
    bookEntity.setTitle(book.getTitle());
    bookEntity.setPublisher(book.getPublisher());
    bookEntity.setCategory(categoryEntity);
    
    if(book.getImageBytes() != null) {
      
      NTFile image = bookEntity.getImage();
      
      if (image == null) {
        image = bookEntity.createImage();
        bookEntity.setImage(image);
      }
      
      image.setContentResource(new Resource(book.getImageMimeType(), null, book.getImageBytes()));
    }
    
    // Save session.
    chromatticSession.save();
    
    // Write log.
    LOG.info(String.format(BookstoreConstants.MSG_UPDATE_BOOK_SUCCESSFUL, bookEntity.getTitle(), bookEntity.getIsbn(), bookEntity.getPublisher()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteBook(String id) throws DataNotFoundException {
    
    // Find BookEntity by book id.
    BookEntity bookEntity = _findById(id);
    
    if (bookEntity == null) {
      throw new DataNotFoundException(BookstoreConstants.MSG_SEARCH_BOOK_FAIL);
    }
    
    Book book = _getBookFromBookEntity(bookEntity);
    
    // Delete book.
    bookEntity.getCategory().getBooks().remove(bookEntity.getName());
    
    // Save session.
    chromatticSession.save();
    
    // Write log.
    LOG.info(String.format(BookstoreConstants.MSG_DELETE_BOOK_SUCCESSFUL, book.getTitle(), book.getIsbn(), book.getPublisher()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteAll() {

    // Get all categories.
    List<CategoryEntity> categories = getCategories();
    
    Map<String, BookEntity> bookMap = null;
    Collection<BookEntity> books = null;
    
    // Loop through all categories
    for (CategoryEntity categoryEntity : categories) {
      
      bookMap = categoryEntity.getBooks();
      books = bookMap.values();
      
      // Loop through all books in current cateogry
      for (BookEntity bookEntity : books) {
        
        // Delete book.
        bookEntity.getCategory().getBooks().remove(bookEntity.getName());
      }
    }
    
    // Save session.
    chromatticSession.save();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isExists(String isbn) {
    
    if (isbn == null) {
      return false;
    }
    
    // Get all categories.
    List<CategoryEntity> categories = getCategories();
    
    Map<String, BookEntity> bookMap = null;
    Collection<BookEntity> books = null;
    
    // Loop through all categories
    for (CategoryEntity categoryEntity : categories) {
      
      bookMap = categoryEntity.getBooks();
      books = bookMap.values();
      
      // Loop through all books in current cateogry
      for (BookEntity bookEntity : books) {
        
        if (isbn.equals(bookEntity.getIsbn())) {
          return true;
        }
      }
    }
    
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getAllCategories() {
    
    // Get all categories.
    List<CategoryEntity> categories = getCategories();
    
    // Declare and initialize return list.
    List<String> returnList = new ArrayList<String>();
    
    // Loop through all categories
    for (CategoryEntity categoryEntity : categories) {
      returnList.add(categoryEntity.getName());
    }
    
    return returnList;
  }

  /**
   * Method used to initialize environments.<br/>
   */
  private void _init() {
    
    // Declare and Create the ChromatticBuilder object.
    ChromatticBuilder chromatticBuilder = ChromatticBuilder.create();
    
    // Add Mapping Entity to the ChromatticBuilder object.
    chromatticBuilder.add(BookstoreEntity.class);
    chromatticBuilder.add(CategoryRootEntity.class);
    chromatticBuilder.add(CategoryEntity.class);
    chromatticBuilder.add(BookEntity.class);
    
    // Set the value of the ChromatticBuilder.SESSION_LIFECYCLE_CLASSNAME option.
    chromatticBuilder.setOptionValue(ChromatticBuilder.SESSION_LIFECYCLE_CLASSNAME, BookChromatticSessionLifeCycle.class.getName());
    
    // Create the Chromattic object.
    chromattic = chromatticBuilder.build();
    
    // Get the ChromatticSession object.
    chromatticSession = _getSession();
    
    // Get the BookstoreEntity object (exo:bookstore root node).
    BookstoreEntity bookstoreEntity = _getBookstoreEntity();
    
    // If exo:bookstore root node doesn't exist, insert the exo:bookstore root node and default category nodes.
    if (bookstoreEntity == null) {
      
      // Insert the exo:bookstore root node.
      bookstoreEntity = chromatticSession.insert(BookstoreEntity.class, BookstoreConstants.NODENAME_BOOKSTORE);
      
      // Create the CategoryRootEntity object  (exo:categories node)
      CategoryRootEntity categoryRootEntity = bookstoreEntity.createCategoryRoot();      
      
      // Set the exo:bookstore root node as the parent node of exo:categories node.
      categoryRootEntity.setBookstore(bookstoreEntity);
      bookstoreEntity.setCategoryRoot(categoryRootEntity);
      
      // Declare the CategoryEntity object (exo:category node).
      CategoryEntity categoryEntity = null;
      
      // Create default categories of the bookstore.
      for (String category : DEFAULT_CATEGORIES) {
        
        // Create the CategoryEntity (exo:category node) from the CategoryRootEntity node (exo:categories node).
        categoryEntity = categoryRootEntity.createCategory();
        categoryRootEntity.getCategories().put(category, categoryEntity);
        categoryEntity.setCategoryRoot(categoryRootEntity);

        // Save session.
        chromatticSession.save();
      }
    }
  }
  
  /**
   * Method used to get ChromatticSession object.<br/>
   * 
   * @return ChromatticSession object.
   */
  private ChromatticSession _getSession() {
    
    // If Chromattic object is null, initialize environment.
    if (chromattic == null) {
      _init();
    }
    
    if(simpleCredentials == null) {
      simpleCredentials = new SimpleCredentials(BookstoreConstants.USERNAME_ROOT, BookstoreConstants.PASSWORD.toCharArray());
    }
    
    // Return ChromatticSession object.
    return chromattic.openSession(simpleCredentials);
  }
  
  /**
   * Method used to get the BookstoreEntity (exo:bookstore root node).<br/>
   * 
   * @return BookstoreEntity (exo:bookstore root node)
   */
  private BookstoreEntity _getBookstoreEntity() {
    
    if(chromatticSession == null) {
      _init();
    }
    
    return chromatticSession.findByPath(BookstoreEntity.class, BookstoreConstants.NODENAME_BOOKSTORE);
  }
  
  /**
   * Method used to find book by condition.<br/>
   * 
   * @param findBy
   * @param key
   * @return
   */
  private List<Book> _find(String findBy, String key) {
    
    List<Book> bookList = new ArrayList<Book>();
    
    try {
      
      if(chromatticSession == null) {
        _init();
      }
      
      QueryBuilder<BookEntity> builder = _getSession().createQueryBuilder(BookEntity.class);
      WhereExpression whereExpression = new WhereExpression();
      whereExpression.like(new PropertyLiteralExpression<String>(String.class, BookstoreConstants.PROPERTY_JCR_PATH), "/exo:bookstore/%");
      
      if(BookstoreConstants.FIND_BY_ISBN.equals(findBy)) {
        
        whereExpression.and().like(new PropertyLiteralExpression<String>(String.class, BookstoreConstants.PROPERTY_ISBN),
                                   BookstoreUtils.appendPercentCharacter(key));
      } else if(BookstoreConstants.FIND_BY_TITLE.equals(findBy)) {
        
        whereExpression.and().like(new PropertyLiteralExpression<String>(String.class, BookstoreConstants.PROPERTY_TITLE),
                                   BookstoreUtils.appendPercentCharacter(key));
      } else if(BookstoreConstants.FIND_BY_PUBLISHER.equals(findBy)) {
        
        whereExpression.and().like(new PropertyLiteralExpression<String>(String.class, BookstoreConstants.PROPERTY_PUBLISHER),
                                   BookstoreUtils.appendPercentCharacter(key));
      } else if(BookstoreConstants.FIND_BY_ID.equals(findBy)) {
        
        whereExpression.and().equals(new PropertyLiteralExpression<String>(String.class, BookstoreConstants.PROPERTY_JCR_UUID), key);
      }
      
      Query<BookEntity> query = StringUtils.isEmpty(whereExpression.toString()) ? builder.get()
                                                                                : builder.where(whereExpression.toString()).get();
      
      QueryResult<BookEntity> queryResult = query.objects();
      
      while(queryResult.hasNext()) {
        
        BookEntity bookEntity = queryResult.next();
        Book book = new Book();
        book.setId(bookEntity.getId());
        book.setCategory(bookEntity.getCategory().getName());
        book.setIsbn(bookEntity.getIsbn());
        book.setTitle(bookEntity.getTitle());
        book.setPublisher(bookEntity.getPublisher());
        
        NTFile bookImage = bookEntity.getImage();
        
        if(bookImage != null) {
          
          book.setImageMimeType(bookImage.getContentResource().getMimeType());
          book.setImageBytes(IOUtils.toByteArray(((NTResource_Chromattic) bookImage.getContent()).getData()));
        }
        
        bookList.add(book);
      }
    } catch (Exception e) {
      LOG.error(BookstoreConstants.MSG_SEARCH_BOOK_FAIL, e);
    }
    
    return bookList;
  }
  
  /**
   * Method used to get CategoryEntity object (exo:category node).<br/>
   * 
   * @param category Category needs to get.
   * @return CategoryEntity object (exo:category node).
   */
  private CategoryEntity _getCategory(String category) {
    
    // Initialize environment.
    if (chromattic == null) {
      _init();
    }
    
    // Get the BookstoreEntity object (exo:bookstore root node).
    BookstoreEntity bookstoreEntity = _getBookstoreEntity();
    
    // Get the Map object contains all categories.
    Map<String, CategoryEntity> categories = bookstoreEntity.getCategoryRoot().getCategories();
    
    // Return the CategoryEntity object (exo:category node).
    return categories.get(category);
  }
  
  /**
   * Method used to get List<CategoryEntity> object contains all categories.<br/>
   * 
   * @return
   */
  private List<CategoryEntity> getCategories() {
    
    // If Chromattic object is null
    if (chromattic == null) {
      _init();
    }
    
    // Get the BookstoreEntity object (exo:bookstore root node).
    BookstoreEntity bookstoreEntity = _getBookstoreEntity();
    
    // Get the Map object contains all categories.
    Map<String, CategoryEntity> categories = bookstoreEntity.getCategoryRoot().getCategories();
    Collection<CategoryEntity> returnCategories = categories.values();
    
    if (returnCategories == null) {
      return null;
    }
    
    return new ArrayList<CategoryEntity>(returnCategories);
  }
  
  /**
   * Method used to get BookEntity object (exo:book node) by its id.<br/>
   * 
   * @param id The id needs to get BookEntity object (exo:book node).
   * @return BookEntity object (exo:book node).
   */
  private BookEntity _findById(String id) {
    
    // Initialize environment.
    if (chromattic == null) {
      _init();
    }
    
    return chromatticSession.findById(BookEntity.class, id);
  }
  
  /**
   * Method used to convert BookEntity to BookBean object.<br/>
   * 
   * @param bookEntity BookEntity object used to covert to BookBean object.
   * @return BookBean object from BookEntity parameter.
   */
  private Book _getBookFromBookEntity(BookEntity bookEntity) {
    
    // If BookEntity object is null
    if (bookEntity == null) {
      return null;
    }
    
    // Convert BookEntity parameter to BookBean object.
    Book bookBean = new Book();
    bookBean.setId(bookEntity.getId());
    bookBean.setCategory(bookEntity.getCategory().getName());
    bookBean.setIsbn(bookEntity.getIsbn());
    bookBean.setTitle(bookEntity.getTitle());
    bookBean.setPublisher(bookEntity.getPublisher());
    
    NTFile image = bookEntity.getImage();
    
    if(image != null && image.getContentResource() != null) {
      bookBean.setImageMimeType(image.getContentResource().getMimeType());
      bookBean.setImageBytes(image.getContentResource().getData());
    }
    
    // Return BookBean object.
    return bookBean;
  }
}
