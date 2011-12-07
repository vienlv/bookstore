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

import java.io.InputStream;

import org.exoplatform.bookstore.BookUtils;
import org.exoplatform.bookstore.common.BookstoreConstants;
import org.exoplatform.bookstore.exception.DataNotFoundException;
import org.exoplatform.bookstore.model.Book;
import org.exoplatform.bookstore.storage.api.BookStorage;
import org.exoplatform.bookstore.utils.BookstoreUtils;
import org.exoplatform.upload.UploadResource;
import org.exoplatform.web.application.ApplicationMessage;
import org.exoplatform.webui.application.WebuiRequestContext;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.core.UIApplication;
import org.exoplatform.webui.core.UIPopupWindow;
import org.exoplatform.webui.core.lifecycle.UIFormLifecycle;
import org.exoplatform.webui.event.Event;
import org.exoplatform.webui.event.EventListener;
import org.exoplatform.webui.form.UIFormInputSet;
import org.exoplatform.webui.form.UIFormTabPane;
import org.exoplatform.webui.form.UIFormUploadInput;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Nov 21, 2011  
 */
@ComponentConfig(
  lifecycle = UIFormLifecycle.class,
  template = "system:/groovy/webui/form/UIFormTabPane.gtmpl",
  events = {
    @EventConfig(listeners = UIBookEditForm.EditActionListener.class)
  }                 
)
public class UIBookEditForm extends UIFormTabPane {
  
  /** BookBean. */
  private Book book;

  /**
   * Default constructor.<br/>
   * 
   * @throws Exception
   */
  public UIBookEditForm() throws Exception {
    super("UIBookEditForm");
    UIFormInputSet uiBookInformation = new UIBookInformation("UIBookInformation");
    addChild(uiBookInformation);
    setActions(new String[]{"Edit"});
    setSelectedTab(1);
  }
  
  /**
   * Getter method for book field.<br/>
   * 
   * @return Book
   */
  public Book getBook() {
    return book;
  }

  /**
   * Setter method for book field.<br/>
   * 
   * @param book
   */
  public void setBook(Book book) {
    this.book = book;
  }
  
  /**
   * Listener for edit book action.<br/>
   */
  public static class EditActionListener extends EventListener<UIBookEditForm> {

    @Override
    public void execute(Event<UIBookEditForm> event) throws Exception {
      UIBookEditForm uiBookEditForm = event.getSource();
      UIBookInformation uiBookInformation = uiBookEditForm.getChild(UIBookInformation.class);
      UIFormUploadInput uiFormUploadInput = uiBookInformation.getUIInput(UIBookInformation.UPL_IMAGE);
      BookStorage bookStorage =  BookUtils.getBookService();
      
      Book book = new Book();
      book.setId(bookStorage.findByIsbn(uiBookEditForm.getBook().getIsbn()).getId());
      book.setIsbn(uiBookInformation.getUIStringInput(UIBookInformation.TXT_ISBN).getValue());
      book.setTitle(uiBookInformation.getUIStringInput(UIBookInformation.TXT_TITLE).getValue());
      book.setPublisher(uiBookInformation.getUIStringInput(UIBookInformation.TXT_PUBLISHER).getValue());
      book.setCategory(BookstoreUtils.getCategoryValue(uiBookInformation.getUIFormSelectBox(UIBookInformation.CMB_CATEGORIES).getValue()));
      
      UploadResource uploadResource = uiFormUploadInput.getUploadResource();
      if(uploadResource != null) {
        book.setImageMimeType(uploadResource.getMimeType());
      }
      
      InputStream inputStream = uiFormUploadInput.getUploadDataAsStream();
      if(inputStream != null) {
        book.setImageBytes(new byte[inputStream.available()]);
        inputStream.read(book.getImageBytes());
      }
      
      try {
        bookStorage.updateBook(book);
      } catch(DataNotFoundException e) {
        WebuiRequestContext ctx = event.getRequestContext();
        UIApplication uiApplication = ctx.getUIApplication();
        uiApplication.addMessage(new ApplicationMessage(BookstoreConstants.MSG_BOOK_NOT_FOUND, null, ApplicationMessage.WARNING));
      }
      
      UIPopupWindow uiPopupWindow = uiBookEditForm.getParent();
      uiPopupWindow.setShow(false);
      BookUtils.updateWorkingSpace();
    }
  }
  
  /**
   * Method used to fill Book information.<br/>
   */
  public void fillBookInformation() {
    UIBookInformation uiBookInformation = getChild(UIBookInformation.class);
    uiBookInformation.getUIStringInput(UIBookInformation.TXT_ISBN).setValue(book.getIsbn());
    uiBookInformation.getUIStringInput(UIBookInformation.TXT_TITLE).setValue(book.getTitle());
    uiBookInformation.getUIStringInput(UIBookInformation.TXT_PUBLISHER).setValue(book.getPublisher());
  }
}
