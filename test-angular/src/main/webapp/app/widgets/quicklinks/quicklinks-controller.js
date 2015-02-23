quickLinks.controller('QuickLinksController', function($scope, QuickLinksService) {

	var me = this;
	me.links = [];

    QuickLinksService.getLinks().success(function(data, status, headers, config) {
    	me.links = data;
    });
});