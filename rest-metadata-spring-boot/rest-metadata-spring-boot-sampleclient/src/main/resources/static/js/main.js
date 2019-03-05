
var app = angular.module('metadataApp', ['RestMetadata']);

app.controller('IndexCtrl', function ($scope, $http, $compile, uiForm) {

    $scope.init = function() {
    };

    $scope.load = function() {

        var rootContext = '';

        $http.get(rootContext + 'restmetadata/get/1/metadata').then(function(resp) {
            uiForm.buildForm(resp.data, rootContext, $scope, "#form-content");
        });
    };
})