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
package org.exoplatform.bookstore.chromattic.entity;

import org.chromattic.api.annotations.Create;
import org.chromattic.api.annotations.FormattedBy;
import org.chromattic.api.annotations.Id;
import org.chromattic.api.annotations.ManyToOne;
import org.chromattic.api.annotations.MappedBy;
import org.chromattic.api.annotations.Name;
import org.chromattic.api.annotations.OneToOne;
import org.chromattic.api.annotations.Owner;
import org.chromattic.api.annotations.Path;
import org.chromattic.api.annotations.PrimaryType;
import org.chromattic.api.annotations.Property;
import org.chromattic.ext.format.BaseEncodingObjectFormatter;
import org.chromattic.ext.ntdef.NTFile;
import org.exoplatform.bookstore.common.BookstoreConstants;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Nov 14, 2011  
 */
@PrimaryType(name = BookstoreConstants.NODETYPE_BOOK)
@FormattedBy(BaseEncodingObjectFormatter.class)
public abstract class BookEntity {

  /**
   * Returns the node id.<br/>
   *
   * @return the node id
   */
  @Id
  public abstract String getId();

  /**
   * Returns the node path.<br/>
   *
   * @return the node path
   */
  @Path
  public abstract String getPath();

  /**
   * Returns the node name.<br/>
   *
   * @return the node name
   */
  @Name
  public abstract String getName();

  /**
   * Returns the book isbn.<br/>
   *
   * @return the book isbn
   */
  @Property(name = BookstoreConstants.PROPERTY_ISBN)
  public abstract String getIsbn();
  public abstract void setIsbn(String isbn);
  
  /**
   * Returns the book title.<br/>
   *
   * @return the book title
   */
  @Property(name = BookstoreConstants.PROPERTY_TITLE)
  public abstract String getTitle();
  public abstract void setTitle(String title);
  
  /**
   * Returns the book publisher.<br/>
   *
   * @return the book publisher
   */
  @Property(name = BookstoreConstants.PROPERTY_PUBLISHER)
  public abstract String getPublisher();
  public abstract void setPublisher(String publisher);
  
  /**
   * Returns the book image.<br/>
   * 
   * @return the book image
   */
  @MappedBy(BookstoreConstants.NODETYPE_IMAGE)
  @OneToOne
  @Owner
  public abstract NTFile getImage();
  public abstract void setImage(NTFile image);
  
  /**
   * Returns the category.<br/>
   *
   * @return the category
   */
  @ManyToOne
  @MappedBy(BookstoreConstants.NODETYPE_BOOK)
  public abstract CategoryEntity getCategory(); 
  public abstract void setCategory(CategoryEntity categoryEntity);
  
  /**
   * Create book image.<br/>
   * 
   * @return the image
   */
  @Create
  public abstract NTFile createImage();
}
