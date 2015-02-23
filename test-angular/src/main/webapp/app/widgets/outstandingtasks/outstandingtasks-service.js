outstandingTasks.factory('OutstandingTasksService', function($http, $q) {

    var service = {};
    service.getOutstandingTasks = function() {
        return $http({
            method: 'GET',
            url: 'data/outstandingtasks.json'
        });
    };
    return service;
});