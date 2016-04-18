function watchlist(name, id) {
    $.post("/addMovie", {'name': name, 'imdb': id}, function(result) {
        console.log('result: ' + JSON.stringify(result));
    })
}