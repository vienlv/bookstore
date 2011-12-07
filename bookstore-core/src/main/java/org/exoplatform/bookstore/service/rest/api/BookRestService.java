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
package org.exoplatform.bookstore.service.rest.api;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Nov 24, 2011  
 */
@Path("bookstore")
public interface BookRestService {

  @GET
  @Path("/get/{bookId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getBook(@PathParam("bookId") String bookId);
  
  @POST
  @Path("/add")
  public Response insertBook(@FormParam("isbn") String isbn,
                             @FormParam("title") String title,
                             @FormParam("publisher") String publisher,
                             @FormParam("category") String category);
  
  @DELETE
  @Path("/delete/{bookId}")
  public Response deleteBook(@PathParam("bookId") String bookId);
  
  @GET
  @Path("/get")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllBook();
  
  @GET
  @Path("/get/category")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllCategory();
  
  @GET
  @Path("/total")
  public Response getNumberOfBooks();
  
  @GET
  @Path("/search/{keyword}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response searchBook(@PathParam("keyword") String key);
}
