var aeon = {
    ajaxCounter: 0,
    ajaxTimeout: 18000
};

(function(originalCreateElement) {
    document.createElement = function(element) {
        var domElement = originalCreateElement.call(document, element);
        if (element === 'script') {
            var timeout = setTimeout(function() {
                --aeon.ajaxCounter;
            }, aeon.ajaxTimeout);
            ++aeon.ajaxCounter;
            domElement.onload = function() {
                clearTimeout(timeout);
                --aeon.ajaxCounter;
            };
        }
        return domElement;
    }
})(document.createElement);

(function(open) {
    XMLHttpRequest.prototype.open = function(method, url, async, user, pass) {
        ++aeon.ajaxCounter;
        var readyStateChange = function() {
            if (this.readyState === 4) {
                --aeon.ajaxCounter;
            }
        };
        this.addEventListener('readystatechange', readyStateChange, false);
        open.call(this, method, url, async, user, pass);
    };
})(XMLHttpRequest.prototype.open);
