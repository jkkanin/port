myRequests.factory('MyRequestsService', function($http, $q) {

    var service = {};
    service.getMyRequests = function() {
        return $http({
            method: 'GET',
            url: 'data/myrequests.json'
        });
    };
    return service;
});