
    function getCard(cardId, title, tags, poster) {

        var html = '';

        for(i = 0; i < tags.length; i ++) {
            html = html + ('<div class="chip" style="margin: 2px">' +  tags[i] + '</div>');
        }

             return  '<div id="card_' + cardId + '" class="col s6 m3">' +
                        '<div class="card medium">' +
                            '<div class="card-image">' +
                                '<img style="width: 100%" height="200" src="' + poster + '">' +
                             '</div>' +
                            '<div class="card-content deep-purple-text">' +
                                '<div id="title_' + cardId + ' class="truncate"">' + title + '</div>' +
                                '<div>' + html + '</div>' +
                                '<i id="' + cardId + '"class="material-icons">library_add</i>' +
                            '</div>' +
                            '<div class="card-action" id="card_action_' + cardId + '">' +
                                '<a id="link_' + cardId + '" href="https://www.imdb.com/title/' + cardId + '">' + 'IMDB' + '</a>' +
                            '</div>' +
                        '</div>' +
                    '</div>'
    }
