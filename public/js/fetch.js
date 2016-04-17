function fetch(url) {
    showProgress();
    console.log("trying url " + url);
    $.get(url, function(data) {
        $("#movies").html('');
        var payload = data['Search'];
        console.log(JSON.stringify(payload));
        for(var i = 0; i < payload.length; i ++) {
                var movie = payload[i];
                console.log(JSON.stringify(movie));
                $("#movies").append(getCard(movie['imdbID'], movie['Title'], [movie['Year']], movie['Poster']));
        }
    }).done(function(data) {
        //console.log(JSON.stringify(data));
    }).fail(function(error) {
        console.log(JSON.stringify(error));
        $("#movies").html("error loading results, refresh to try again.");
    }).always(function() {
        console.log("network operation done");
    });
}