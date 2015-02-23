quickLinks.factory('QuickLinksService', function($http, $q) {

    var service = {};
    service.getLinks = function() {
        return $http({
            method: 'GET',
            url: 'data/quicklinks.json'
        });
    };
    return service;
});