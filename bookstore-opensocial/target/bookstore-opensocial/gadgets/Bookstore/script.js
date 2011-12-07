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
  this.startIndex = 0;
  this.itemsPerPage = 5;
  this.viewer = null;
  this.viewerFriends = null;
  this.prefs = null;
  this.totalItems = 0;
  this.displayedItems = 0;
  this.totalPages = 0;
  this.currentPage = 1;
}

Bookstore.prototype.init = function() {
  eXo.social.viewerFriend = new Bookstore();
    this.start();
}

//Constants
Bookstore.FIRST = "first";
Bookstore.PREVIOUS = "previous";
Bookstore.NEXT = "next";
Bookstore.LAST = "last";

Bookstore.prototype.start = function() {
  this.setPrefs();
  this.loadFriends();
}

Bookstore.prototype.setPrefs = function() {
  this.prefs = new gadgets.Prefs();
  eXo.social.viewerFriend.itemsPerPage = this.prefs.getInt("itemsPerPage");
}

Bookstore.prototype.loadFriends = function() {
  var req = opensocial.newDataRequest();
  var opts = {};

  opts[opensocial.DataRequest.PeopleRequestFields.FIRST] = eXo.social.viewerFriend.startIndex;
  opts[opensocial.DataRequest.PeopleRequestFields.MAX] = eXo.social.viewerFriend.itemsPerPage;
  opts[opensocial.DataRequest.PeopleRequestFields.PROFILE_DETAILS] = [
      opensocial.Person.Field.AGE, opensocial.Person.Field.NAME,
      opensocial.Person.Field.GENDER,
      opensocial.Person.Field.PROFILE_URL,
      opensocial.Person.Field.THUMBNAIL_URL,
      opensocial.Person.Field.STATUS ];

  req.add(req.newFetchPersonRequest("VIEWER", opts), 'viewer');
  req.add(req.newFetchPeopleRequest("VIEWER_FRIENDS", opts), 'viewerFriends');
  req.send(onLoadFriends);

  function onLoadFriends(data) {
    if (!data.hadError()) {

      eXo.social.viewerFriend.viewer = data.get('viewer').getData();
      eXo.social.viewerFriend.viewerFriends = data.get('viewerFriends').getData();
      eXo.social.viewerFriend.totalItems = eXo.social.viewerFriend.viewerFriends.getTotalSize();
      eXo.social.viewerFriend.totalPages = Math.ceil(eXo.social.viewerFriend.totalItems/eXo.social.viewerFriend.itemsPerPage);
      eXo.social.viewerFriend.display();
    }
  }
}

Bookstore.prototype.display = function() {
  var viewerDisplay = "";
  var friendsDisplay = [];
  if (eXo.social.viewerFriend.viewer != null) {
    viewerDisplay = eXo.social.viewerFriend.viewer.getDisplayName();
  } else {
     alert("ERROR!!!")
  }

  var viewerEl = document.getElementById("viewer");
  var friendsEl = document.getElementById("friends");
  var totalPageEl = document.getElementById("totalPages");

  if (eXo.social.viewerFriend.totalItems > 0) {
    eXo.social.viewerFriend.viewerFriends
        .each( function(person) {
          if (person.getId()) {
            friendsDisplay.push('<li>' + person.getDisplayName()
                + "</li>");
          }
        });
    friendsEl.innerHTML = "<ul>" + friendsDisplay.join(" ") + "</ul>";
  } else {
    friendDisplay = viewerDisplay + " has no friends yet.";
    friendsEl.innerHTML = friendDisplay;
  }

  viewerEl.innerHTML = viewerDisplay;

  if (eXo.social.viewerFriend.totalPages > 1) {
    eXo.social.viewerFriend.registerPagingAction();
  }

  gadgets.window.adjustHeight();
}

Bookstore.prototype.registerPagingAction = function() {
  var allPageEl = this.getEl("page");
  allPageEl.innerHTML = this.setDisplayPaging(eXo.social.viewerFriend.currentPage);

  var allPages = [];
  var allPagesEl = this.getEl("pages");
  var totalpagesEl = _gel("totalPages");
  totalpagesEl.innerHTML = eXo.social.viewerFriend.totalPages;

  for (i=1; i<=eXo.social.viewerFriend.totalPages;i++) {
        var str="";

        if (i == eXo.social.viewerFriend.currentPage) {
          str = "<a href='#' class ='Number PageSelected' onclick='eXo.social.viewerFriend.toPage("+i+")'>" + i + "</a>";
        } else {
          str = "<a href='#' class ='Number' onclick='eXo.social.viewerFriend.toPage("+i+")'>" + i + "</a>";
        }

        allPages.push(str);
    }

    allPagesEl.innerHTML = allPages.join(" ");
}

Bookstore.prototype.setDisplayPaging = function(currentPage) {
  var rtnHTML="";
  var totalPage = eXo.social.viewerFriend.totalPages;
  var lastPageTag = this.createTag("last", "Icon NextTopPageIcon","eXo.social.viewerFriend.lastPage()");
  var nextPageTag = this.createTag("next", "Icon NextPageIcon","eXo.social.viewerFriend.nextPage()");
  var previousPageTag = this.createTag("previous", "Icon LastPageIcon","eXo.social.viewerFriend.previousPage()");
  var firstPageTag = this.createTag("first", "Icon LastTopPageIcon","eXo.social.viewerFriend.firstPage()");
  var previousDisTag = this.createTag("previous", "Icon DisableLastPageIcon","void()");
  var firstDisTag = this.createTag("first", "Icon DisableLastTopPageIcon","void()");
  var lastDisTag = this.createTag("last", "Icon DisableNextTopPageIcon","void()");
  var nextDisTag = this.createTag("next", "Icon DisableNextPageIcon","void()");
  var pagesTag = "<div id='pages'></div>";

  if (totalPage == 1) {
    rtnHTML= this.createHTML(firstDisTag, previousDisTag, pagesTag, nextDisTag, lastDisTag);
  } else if ((currentPage > 1) && (currentPage < totalPage)) {
    rtnHTML= this.createHTML(firstPageTag, previousPageTag, pagesTag, nextPageTag, lastPageTag);
  } else if (currentPage == 1) {
    rtnHTML= this.createHTML(firstDisTag, previousDisTag, pagesTag, nextPageTag, lastPageTag);
  } else if (currentPage == totalPage) {
    rtnHTML= this.createHTML(firstPageTag, previousPageTag, pagesTag, nextDisTag, lastDisTag);
  }

  return rtnHTML;
}

Bookstore.prototype.createHTML = function(last, next, pages, previous, first) {
  var HTML="";
  HTML+= "<a class='TotalPages'>Total pages:</a>";
  HTML+="<a class='PagesTotalNumber' id='totalPages'></a>";

  HTML+= last;
  HTML+= next;
  HTML+= pages;
  HTML+= previous;
  HTML+= first;

  return HTML;
}

Bookstore.prototype.createTag = function(id, cls, action) {
  return "<a  id='" +id+"' class='"+cls+"' href='#' onclick='"+action+"'> </a>";
}

Bookstore.prototype.getEl = function (elId) {
    var el = document.getElementById(elId);
    if (el) {
        return el;
    } else {
        alert("element: " + elId + " not found!");
    }
}

Bookstore.prototype.firstPage = function() {
    if (eXo.social.viewerFriend.currentPage != 1) {
      eXo.social.viewerFriend.currentPage = 1;
      this.toPage(eXo.social.viewerFriend.currentPage);
  }
}

Bookstore.prototype.lastPage = function() {
    if (eXo.social.viewerFriend.currentPage != eXo.social.viewerFriend.totalPages) {
        eXo.social.viewerFriend.currentPage = eXo.social.viewerFriend.totalPages;
        this.toPage(eXo.social.viewerFriend.currentPage);
    }
}

Bookstore.prototype.nextPage = function() {
    if (eXo.social.viewerFriend.currentPage < eXo.social.viewerFriend.totalPages) {
        eXo.social.viewerFriend.currentPage += 1;
        this.toPage(eXo.social.viewerFriend.currentPage);
    }
}

Bookstore.prototype.previousPage = function() {
    if (eXo.social.viewerFriend.currentPage > 1) {
        eXo.social.viewerFriend.currentPage -= 1;
        this.toPage(eXo.social.viewerFriend.currentPage);
    }
}

Bookstore.prototype.toPage = function(pageNum) {
    if ((pageNum > 0) && (pageNum <= eXo.social.viewerFriend.totalPages)) {
        eXo.social.viewerFriend.currentPage = pageNum;
        eXo.social.viewerFriend.startIndex = (eXo.social.viewerFriend.currentPage - 1)*eXo.social.viewerFriend.itemsPerPage;
        eXo.social.viewerFriend.start();
    }
}
