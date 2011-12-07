function init() {
l  oadFriends();
}

function loadFriends() {
  var req = opensocial.newDataRequest();
  req.add(req.newFetchPersonRequest('VIEWER'), 'viewer');
  req.add(req.newFetchPeopleRequest('VIEWER_FRIENDS'), 'viewerFriends');
  req.send(onLoadFriends);
}

function onLoadFriends(data) {
  var viewer = data.get('viewer').getData();
  var viewerFriends = data.get('viewerFriends').getData();

  html = new Array();
  html.push('<ul>');
  viewerFriends.each(function(person) {
    html.push('<li>' + person.getDisplayName() + "</li>");
  });
  html.push('</ul>');
  document.getElementById('friends').innerHTML = html.join('');
  document.getElementById('friends').innerHTML = viewer;
  document.getElementById('friends').innerHTML = viewer;
}
