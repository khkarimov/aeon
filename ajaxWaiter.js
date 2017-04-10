var aeon = {
    ajaxCounter: 0,
    ajaxTimeout: 18000
};
(function(originalCreateElement) {
    document.createElement = function(element) {
        var domElement = originalCreateElement.call(document, element);
        if (element === 'script') {
            var timeout = setTimeout(function() {
                $('#counter').html(--aeon.ajaxCounter);
            }, aeon.ajaxTimeout);
            $('#counter').html(++aeon.ajaxCounter);
            domElement.onload = function() {
                clearTimeout(timeout);
                $('#counter').html(--aeon.ajaxCounter);
            };
        }
        return domElement;
    }
})(document.createElement);
