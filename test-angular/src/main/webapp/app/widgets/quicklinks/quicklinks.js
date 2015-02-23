'use strict';

var quickLinks = angular.module('QuickLinks', ['adf.provider']);

quickLinks.config(function(dashboardProvider) {
    dashboardProvider
        .widget('quicklinks', {
            title: 'Quick Links',
            description: 'Displays quick links',
            templateUrl: 'app/widgets/quicklinks/quicklinks.html',
            controllerAs: 'quickLinksCtrl',
            controller: 'QuickLinksController'
        });
})