function watchlist(name, id) {
    $.ajax(
        url: "/addMovie",
        type: 'POST',
        dataType: 'application/json',
        data: {'userId': $("#id").text(), 'name': name, 'imdb': id},
        success: function(result) {
                console.log('result: ' + JSON.stringify(result));
            })
}