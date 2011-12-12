var eXo = eXo || {};
eXo.social = eXo.social || {};

function Bookstore() {
  this.books = null;
  this.prefs = null;
  this.updateTime = 0;
}

Bookstore.prototype.init = function() {
	
  eXo.social.bookstore = new Bookstore();
  this.prefs = new gadgets.Prefs();
  this.updateTime = this.prefs.getInt("updateTime");
  var params = {};
  
  params[gadgets.io.RequestParameters.CONTENT_TYPE] = gadgets.io.ContentType.JSON;
	params[gadgets.io.RequestParameters.REFRESH_INTERVAL] = 0;
	gadgets.io.makeRequest('http://localhost:8080/rest-socialdemo/bookstore/get/', onLoadBooks, params);

	setInterval(function() {
		gadgets.io.makeRequest('http://localhost:8080/rest-socialdemo/bookstore/get/', onLoadBooks, params);
	}, this.updateTime*60*1000);

  function onLoadBooks(data) {
  	
  	var jsonData = data.data;
	  eXo.social.bookstore.books = jsonData;
	  eXo.social.bookstore.display(eXo.social.bookstore.books);
  }
}

Bookstore.prototype.display = function(data) {
	
	if(data == null) {
		return;
	}
  
  var bookEl = document.getElementById("books");
  var books = [];
  
  if(data.length > 0) {
  	for(var i=0; i<data.length; i++) {
    	books.push("<li>" + data[i].title + " (ISBN: " + data[i].isbn + "): " + data[i].publisher + " (" + data[i].category + ")" + "</li>");
    }

    bookEl.innerHTML = "<ul>" + books.join(" ") + "</ul>";
  } else {
  	bookEl.innerHTML = "There isn't any book yet";
  }

  gadgets.window.adjustHeight();
}
