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
package org.exoplatform.bookstore.webui;

import java.util.List;

import org.exoplatform.bookstore.BookUtils;
import org.exoplatform.bookstore.common.BookstoreConstants;
import org.exoplatform.bookstore.model.Book;
import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.core.UIPopupWindow;
import org.exoplatform.webui.core.lifecycle.UIFormLifecycle;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;
import org.exoplatform.webui.event.Event.Phase;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Nov 18, 2011  
 */
@ComponentConfig(
  lifecycle = UIFormLifecycle.class,
  template = "app:/groovy/bookstore/webui/UIBookList.gtmpl",
  events = {
      @EventConfig(listeners = UIBookList.EditBookActionListener.class, phase = Phase.DECODE),
      @EventConfig(listeners = UIBookList.DeleteBookActionListener.class,
                   confirm = "BookList.msg.confirm_book_delete")
  }
)
public class UIBookList extends UIBookstoreSection {
  
  /** UIPopupEditBook. */
  public static final String COMPONENT_ID_UI_POPUP_EDIT_BOOK = "UIPopupEditBook";
  
  /** List of books. */
  List<Book> bookList = null;

  /**
   * Default constructor.<br/>
   * 
   * @throws Exception
   */
  public UIBookList() throws Exception {
    UIPopupWindow uiPopupWindow = createUIComponent(UIPopupWindow.class, null, COMPONENT_ID_UI_POPUP_EDIT_BOOK);
    uiPopupWindow.setShow(false);
    uiPopupWindow.setWindowSize(400, 0);
    addChild(uiPopupWindow);
  }
  
  /**
   * Getter method for bookList field.<br/>
   * 
   * @return
   */
  public List<Book> getBookList() {
    
    if (bookList != null) {
      return bookList;
    }
    
    return BookUtils.getBookService().findAll();
  }

  /**
   * Setter method for bookList field.<br/>
   * 
   * @param bookList
   */
  public void setBookList(List<Book> bookList) {
    this.bookList = bookList;
  }
  
  /**
   * Method used to make Book image URL.<br/>
   * 
   * @return
   */
  public String buildImageURL(Book book) {
    
    if(book == null || book.getImageBytes() == null) {
      return null;
    }
    
    StringBuilder url = new StringBuilder();
    url.append(BookstoreConstants.JCR_ROOT_PATH_FOR_REST);
    url.append(BookstoreConstants.NODENAME_BOOKSTORE);
    url.append(BookstoreConstants.SLASH);
    url.append(BookstoreConstants.NODENAME_CATEGORIES);
    url.append(BookstoreConstants.SLASH);
    url.append(book.getCategory());
    url.append(BookstoreConstants.SLASH);
    url.append(book.getIsbn());
    url.append(BookstoreConstants.SLASH);
    url.append(BookstoreConstants.NODENAME_IMAGE);
    
    return encodeURL(url.toString());
  }
  
  private String encodeURL(String str) {
    
    return str.replaceAll(":", "%3A");
  }
  
  /**
   * Listener for edit book action.<br/>
   */
  public static class EditBookActionListener extends EventListener<UIBookList> {

    @Override
    public void execute(Event<UIBookList> event) throws Exception {
      WebuiRequestContext ctx = event.getRequestContext();
      String bookId = ctx.getRequestParameter("objectId");
      
      UIBookList uiBookList = event.getSource();
      UIPopupWindow uiPopupWindow = uiBookList.getChild(UIPopupWindow.class);
      
      UIBookEditForm uiBookEditForm = uiBookList.createUIComponent(UIBookEditForm.class, null, null);
      uiBookEditForm.setBook(BookUtils.getBookService().findById(bookId));
      uiBookEditForm.fillBookInformation();
      uiPopupWindow.setUIComponent(uiBookEditForm);
      uiPopupWindow.setWindowSize(500, 300);
      uiPopupWindow.setShow(true);
    }
  }
  
  /**
   * Listener for delete book action.<br/>
   */
  public static class DeleteBookActionListener extends EventListener<UIBookList> {

    @Override
    public void execute(Event<UIBookList> event) throws Exception {      
      WebuiRequestContext ctx = event.getRequestContext();
      String bookId = ctx.getRequestParameter("objectId");
      BookUtils.getBookService().deleteBook(bookId);
    }
  }
}
