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

import java.util.ArrayList;
import java.util.List;

import org.exoplatform.bookstore.common.BookstoreConstants;
import org.exoplatform.webui.core.model.SelectItemOption;
import org.exoplatform.webui.form.UIFormInputSet;
import org.exoplatform.webui.form.UIFormSelectBox;
import org.exoplatform.webui.form.UIFormStringInput;
import org.exoplatform.webui.form.UIFormUploadInput;
import org.exoplatform.webui.form.validator.MandatoryValidator;
import org.exoplatform.webui.form.validator.StringLengthValidator;

/**
 * Created by The eXo Platform SAS
 * Author : quangpld
 *          quangpld@exoplatform.com
 * Nov 17, 2011  
 */
public class UIBookInformation extends UIFormInputSet {
  
  /** Name of the ISBN text field. */
  public static final String TXT_ISBN = "txtIsbn";
  
  /** Name of the Book title text field. */
  public static final String TXT_TITLE = "txtTitle";
  
  /** Name of the Book publisher text field. */
  public static final String TXT_PUBLISHER = "txtPublisher";
  
  /** Name of the Book category select box. */
  public static final String CMB_CATEGORIES = "cmbCategories";
  
  /** Name of the Book image upload field. */
  public static final String UPL_IMAGE = "uplImage";

  /**
   * Default constructor.<br/>
   * 
   * @param name
   * @throws Exception 
   */
  public UIBookInformation(String name) throws Exception {
    super(name);
    
    // Create Book category select box.
    SelectItemOption<String> categoryNovel = new SelectItemOption<String>(BookstoreConstants.CATEGORY_NOVEL,
                                                                          BookstoreConstants.CATEGORY_NOVEL_VALUE);
    SelectItemOption<String> categoryScientific = new SelectItemOption<String>(BookstoreConstants.CATEGORY_SCIENTIFIC,
                                                                               BookstoreConstants.CATEGORY_SCIENTIFIC_VALUE);
    SelectItemOption<String> categoryIt = new SelectItemOption<String>(BookstoreConstants.CATEGORY_IT,
                                                                       BookstoreConstants.CATEGORY_IT_VALUE);
    SelectItemOption<String> categoryOthers = new SelectItemOption<String>(BookstoreConstants.CATEGORY_OTHERS,
                                                                           BookstoreConstants.CATEGORY_OTHERS_VALUE);
    
    List<SelectItemOption<String>> categoryList = new ArrayList<SelectItemOption<String>>();
    categoryList.add(categoryNovel);
    categoryList.add(categoryScientific);
    categoryList.add(categoryIt);
    categoryList.add(categoryOthers);
    
    UIFormSelectBox uiFormSelectBox = new UIFormSelectBox(CMB_CATEGORIES, CMB_CATEGORIES, categoryList);
    
    // Create book image upload field.
    UIFormUploadInput uiFormUploadInput = new UIFormUploadInput(UPL_IMAGE, UPL_IMAGE);
    uiFormUploadInput.setAutoUpload(true);
    
    // Add form input.
    addUIFormInput(new UIFormStringInput(TXT_ISBN, TXT_ISBN, null)
                   .addValidator(MandatoryValidator.class)
                   .addValidator(StringLengthValidator.class, 10, 14));
    
    addUIFormInput(new UIFormStringInput(TXT_TITLE, TXT_TITLE, null)
                   .addValidator(MandatoryValidator.class));
    
    addUIFormInput(uiFormSelectBox);
    
    addUIFormInput(new UIFormStringInput(TXT_PUBLISHER, TXT_PUBLISHER, null)
                   .addValidator(MandatoryValidator.class));
    
    addUIFormInput(uiFormUploadInput);
  }
}
