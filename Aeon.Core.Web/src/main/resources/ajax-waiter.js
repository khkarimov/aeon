var aeon = {
    ajaxCounter: 0,
    ajaxJsonpElementTimeout: 18000
};

(function(open) {
    XMLHttpRequest.prototype.open = function(method, url, async, user, pass) {
        ++aeon.ajaxCounter;
        var readyStateChange = function() {
            if (this.readyState === 4) {
                --aeon.ajaxCounter;
            }
        };
        this.addEventListener('readystatechange', readyStateChange, false);

        if (arguments.length < 3) {
            async = true;
        }

        open.call(this, method, url, async, user, pass);
    };
})(XMLHttpRequest.prototype.open);
