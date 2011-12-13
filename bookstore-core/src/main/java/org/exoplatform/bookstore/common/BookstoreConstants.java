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
package org.exoplatform.bookstore.common;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Nov 14, 2011  
 */
public class BookstoreConstants {

  /** exo:bookstore Node type. */
  public static final String NODETYPE_BOOKSTORE = "exo:bookstore";
  
  /** exo:categories Node type. */
  public static final String NODETYPE_CATEGORIES = "exo:categories";
  
  /** exo:category Node type. */
  public static final String NODETYPE_CATEGORY = "exo:category";
  
  /** exo:book Node type. */
  public static final String NODETYPE_BOOK = "exo:book";
  
  /** exo:image Node type. */
  public static final String NODETYPE_IMAGE = "exo:image";
  
  /** exo:bookstore Node name. */
  public static final String NODENAME_BOOKSTORE = "exo:bookstore";
  
  /** exo:categories Node name. */
  public static final String NODENAME_CATEGORIES = "exo:categories";
  
  /** exo:image Node name. */
  public static final String NODENAME_IMAGE = "exo:image";
  
  /** Novel category. */
  public static final String CATEGORY_NOVEL = "Novel";
  
  /** Scientific category. */
  public static final String CATEGORY_SCIENTIFIC = "Scientific";
  
  /** Information Technology category. */
  public static final String CATEGORY_IT = "Information Technology";
  
  /** Others category. */
  public static final String CATEGORY_OTHERS = "Others";
  
  /** exo:isbn Property isbn. */
  public static final String PROPERTY_ISBN = "exo:isbn";
  
  /** exo:title Property title. */
  public static final String PROPERTY_TITLE = "exo:title";
  
  /** exo:publisher Property publisher. */
  public static final String PROPERTY_PUBLISHER = "exo:publisher";
  
  /** jcr:content/jcr:mimeType Property mime type. */
  public static final String PROPERTY_MIME_TYPE = "jcr:content/jcr:mimeType";
  
  /** jcr:content/jcr:data Property jcr data. */
  public static final String PROPERTY_JCR_DATA = "jcr:content/jcr:data";
  
  /** jcr:uuid Property uuid. */
  public static final String PROPERTY_JCR_UUID = "jcr:uuid";
  
  /** jcr:path Property jcr path. */
  public static final String PROPERTY_JCR_PATH = "jcr:path";
  
  /** Username used to log into repository. */
  public static final String USERNAME_ROOT = "root";
  
  /** Pass used to log into bookstore repository. */
  public static final String PASSWORD = "exo";
  
  /** Username used to log into social repository. */
  public static final String PASSWORD_SOCIAL = "gtn";
  
  /** Category novel value. */
  public static final String CATEGORY_NOVEL_VALUE = "1";
  
  /** Category scientific value. */
  public static final String CATEGORY_SCIENTIFIC_VALUE = "2";
  
  /** Category information technology value. */
  public static final String CATEGORY_IT_VALUE = "3";
  
  /** Category others value. */
  public static final String CATEGORY_OTHERS_VALUE = "4";
  
  public static final String JCR_ROOT_PATH_FOR_REST = "/rest-socialdemo/jcr/repository/social/";
  public static final String SLASH = "/";
  
  public static final String FIND_BY_ID = "findId";
  public static final String FIND_BY_ISBN = "findIsbn";
  public static final String FIND_BY_TITLE = "findTitle";
  public static final String FIND_BY_PUBLISHER = "findPublisher";
  
  public static final String MSG_BOOK_ALREADY_EXIST = "Book %s <ISBN: %s> has already existed";
  public static final String MSG_CATEGORY_DOES_NOT_EXIST = "Category %s is not existed";
  public static final String MSG_INSERT_BOOK_SUCCESSFUL = "Book %s <ISBN: %s - Publisher: %s> has been inserted";
  public static final String MSG_UPDATE_BOOK_SUCCESSFUL = "Book %s <ISBN: %s - Publisher: %s> has been updated";
  public static final String MSG_DELETE_BOOK_SUCCESSFUL = "Book %s <ISBN: %s - Publisher: %s> has been deleted";
  public static final String MSG_SEARCH_BOOK_FAIL = "Fail to search book";
  public static final String MSG_DELETE_BOOK_FAIL = "Fail to delete book";
  public static final String MSG_BOOK_NOT_FOUND = "Book not found";
}
