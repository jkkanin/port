'use strict';

var outstandingTasks = angular.module('OutstandingTasks', ['adf.provider']);

outstandingTasks.config(function(dashboardProvider) {
    dashboardProvider
        .widget('outstandingtasks', {
            title: 'Outstanding Tasks',
            description: 'Display outstanding tasks of the employee',
            templateUrl: 'app/widgets/outstandingtasks/outstandingtasks.html',
            controller: 'OutstandingTasksController'
        });
});