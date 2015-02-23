'use strict';

var dashboardModule = angular.module('Dashboard', []);

dashboardModule.config(function(dashboardProvider) {

    dashboardProvider
        .structure('6-6/6-3-3', {
            rows: [{
                columns: [{
                    styleClass: 'col-md-6'
                }, {
                    styleClass: 'col-md-6'
                }]
            }, {
                columns: [{
                    styleClass: 'col-md-6'
                }, {
                    styleClass: 'col-md-3'
                }, {
                    styleClass: 'col-md-3'
                }]
            }]
        });
});