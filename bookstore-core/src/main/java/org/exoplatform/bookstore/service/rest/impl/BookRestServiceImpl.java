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
package org.exoplatform.bookstore.service.rest.impl;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.exoplatform.bookstore.exception.DataDuplicateException;
import org.exoplatform.bookstore.exception.DataNotFoundException;
import org.exoplatform.bookstore.model.Book;
import org.exoplatform.bookstore.service.rest.api.BookRestService;
import org.exoplatform.bookstore.storage.api.BookStorage;
import org.exoplatform.services.rest.resource.ResourceContainer;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Nov 24, 2011  
 */
@Path("bookstore")
public class BookRestServiceImpl implements ResourceContainer, BookRestService {
  
  private static final CacheControl cc;
  
  static {
    cc = new CacheControl();
    cc.setNoCache(true);
    cc.setNoStore(true);
  }

  private BookStorage bookStorage;
  
  public BookRestServiceImpl(BookStorage bookStorage) {
    this.bookStorage = bookStorage;
  }
  
  @GET
  @Path("/get/{bookId}")
  @Produces(MediaType.APPLICATION_JSON)
  @Override
  public Response getBook(@PathParam("bookId") String bookId) {
    Book book = bookStorage.findByIsbn(bookId);
    
    if(book == null) {
      return Response.status(Status.NO_CONTENT).build();
    }
    
    return Response.ok(book, MediaType.APPLICATION_JSON).cacheControl(cc).build();
  }

  @POST
  @Path("/add")
  @Override
  public Response insertBook(@FormParam("isbn") String isbn,
                             @FormParam("title") String title,
                             @FormParam("publisher") String publisher,
                             @FormParam("category") String category) {
    
    Book book = new Book(category, isbn, title, publisher, null, null);
    
    try {
      bookStorage.insert(book);
    } catch(DataDuplicateException e) {
      return Response.status(Status.CONFLICT).build();
    } catch(DataNotFoundException e) {
    }
    
    return Response.ok().cacheControl(cc).build();
  }
  
  @DELETE
  @Path("/delete/{bookId}")
  @Override
  public Response deleteBook(@PathParam("bookId") String bookId) {
    
    try {
      bookStorage.deleteBook(bookId);
    } catch(DataNotFoundException e) {
      return Response.status(Status.NOT_FOUND).build();
    }
    
    return Response.ok().cacheControl(cc).build();
  }

  @GET
  @Path("/get")
  @Produces(MediaType.APPLICATION_JSON)
  @Override
  public Response getAllBook() {
    List<Book> books = bookStorage.findAll();
    return Response.ok(books, MediaType.APPLICATION_JSON).cacheControl(cc).build();
  }

  @GET
  @Path("/get/category")
  @Produces(MediaType.APPLICATION_JSON)
  @Override
  public Response getAllCategory() {
    List<String> categories = bookStorage.getAllCategories();
    return Response.ok(categories, MediaType.APPLICATION_JSON).cacheControl(cc).build();
  }

  @GET
  @Path("/total")
  @Override
  public Response getNumberOfBooks() {
    return Response.ok(String.valueOf(bookStorage.findAll().size()), MediaType.APPLICATION_JSON)
                   .cacheControl(cc)
                   .build();
  }

  @GET
  @Path("/search/{keyword}")
  @Produces(MediaType.APPLICATION_JSON)
  @Override
  public Response searchBook(@PathParam("keyword") String key) {
    List<Book> books = bookStorage.findByTitle(key);
    return Response.ok(books, MediaType.APPLICATION_JSON).cacheControl(cc).build();
  }
}
