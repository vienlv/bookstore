/**
  Copyright (C) 2003-2007 eXo Platform SAS. This program is free
  software; you can redistribute it and/or modify it under the terms of
  the GNU Affero General Public License as published by the Free
  Software Foundation; either version 3 of the License, or (at your
  option) any later version. This program is distributed in the hope
  that it will be useful, but WITHOUT ANY WARRANTY; without even the
  implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
  PURPOSE. See the GNU General Public License for more details. You
  should have received a copy of the GNU General Public License along
  with this program; if not, see<http://www.gnu.org/licenses/>.
*/
/**
  * Created by The eXo Platform SARL
  * Author : hoat_le
  *          hoatlevan@gmail.com
  * Jun 17, 2009
  */

var eXo = eXo || {};
eXo.social = eXo.social || {};

function Bookstore() {
  this.isbn = null;
  this.title = null;
  this.publisher = null;
  this.category = null;
}

Bookstore.prototype.init = function() {
  eXo.social.bookstore = new Bookstore();
  var params = {};
  params[gadgets.io.RequestParameters.CONTENT_TYPE] = gadgets.io.ContentType.JSON;
  gadgets.io.makeRequest('http://localhost:8080/rest-socialdemo/bookstore/get/', onLoadBooks, params);
  
  function onLoadBooks(data) {
  	
  	var jsonData = data.data;
	  eXo.social.bookstore.isbn = jsonData[0].isbn;
	  eXo.social.bookstore.title = jsonData[0].title;
	  eXo.social.bookstore.publisher = jsonData[0].publisher;
	  eXo.social.bookstore.category = jsonData[0].category;
	  eXo.social.bookstore.display();
  }
}

Bookstore.prototype.display = function() {
  var viewerDisplay = "";
  if (eXo.social.bookstore.isbn != null) {
    viewerDisplay = eXo.social.bookstore.isbn;
  } else {
     alert("ERROR!!!")
  }

  var viewerEl = document.getElementById("viewer");

  viewerEl.innerHTML = viewerDisplay;

  gadgets.window.adjustHeight();
}
