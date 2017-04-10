(function(open) {
    XMLHttpRequest.prototype.open = function(method, url, async, user, pass) {
        console.log('open');
        $('#counter').html(++aeon.ajaxCounter);
        var readyStateChange = function() {
            if (this.readyState === 4) {
                $('#counter').html(--aeon.ajaxCounter);
            }
        };

        this.addEventListener('readystatechange', readyStateChange, false);

        open.call(this, method, url, async, user, pass);
    };
})(XMLHttpRequest.prototype.open);
