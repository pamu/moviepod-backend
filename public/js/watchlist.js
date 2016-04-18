function watchlist(name, id) {
    $.ajax(
        url: "/addMovie",
        type: 'POST',
        dataType: 'application/json',
        data: {'name': name, 'imdb': id},
        success: function(result) {
                console.log('result: ' + JSON.stringify(result));
            })
}