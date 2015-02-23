'use strict';

var app = angular.module('app', ['adf', 'ngRoute', 'LocalStorageModule', 'ui.grid', 'Common', 'Dashboard', 'LeaveBalance', 'QuickLinks', 'OutstandingTasks', 'MyRequests']);

app.config(function($routeProvider, localStorageServiceProvider) {

    localStorageServiceProvider.setPrefix('adf');

    $routeProvider.when('/', {
        templateUrl: 'app/dashboard/dashboard.html',
        controller: 'DashboardController'
    }).otherwise({
        redirectTo: '/'
    });
});

app.run(function($rootScope, $http) {
	$http({
		method : 'GET',
		url : '/ofs-portal/services/users/userdetails'
	}).success(function(data) {
		$rootScope.user = data;
	}).error(function() {
		$rootScope.user = {'loginName':'ofsguest', 'userName':'OFS Guest'};
	});
});
