function fetch(url) {
    showProgress();
    $.get(url, function(data) {
        $("#movies").html('');
        var payload = data['Search'];
        for(var i = 0; i < payload.length; i ++) {
                var movie = payload[i];
                console.log(JSON.stringify(movie));
                $("#movies").append(getCard(movie['imdbID'], movie['Title'], [movie['Year']], movie['Poster']));
        }
    }).done(function(data) {
        //console.log(JSON.stringify(data));
    }).fail(function(error) {
        $("#movies").html("error loading results, refresh to try again.");
    }).always(function() {
        console.log("network operation done");
    });
}