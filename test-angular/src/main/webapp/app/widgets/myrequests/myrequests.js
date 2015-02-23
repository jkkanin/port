'use strict';

var myRequests = angular.module('MyRequests', ['adf.provider']);

myRequests.config(function(dashboardProvider) {
    dashboardProvider
        .widget('myrequests', {
            title: 'My Requests',
            description: 'Display requests raised by the user',
            templateUrl: 'app/widgets/myrequests/myrequests.html',
            controller: 'MyRequestsController'
        });
});