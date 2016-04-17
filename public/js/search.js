$(function search() {
    $("#search").keypress(function(e) {
        if(e.which === 13) {
            var text = $("#search").val();
            if (text.length > 0) {
                fetch('https://www.omdbapi.com/?s=' + text.trim() + '&page=1');
            }
        }
    });
});