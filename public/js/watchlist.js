$(function watchlist(name, id) {
    $.post("/addMovie", {'name': name, 'imdb': id}, function(result) {
        console.log(JSON.stringify(result));
    })
});