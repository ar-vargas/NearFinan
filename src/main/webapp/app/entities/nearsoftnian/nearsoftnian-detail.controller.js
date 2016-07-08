(function() {
    'use strict';

    angular
        .module('nearDocsApp')
        .controller('NearsoftnianDetailController', NearsoftnianDetailController);

    NearsoftnianDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Nearsoftnian', 'Receipt'];

    function NearsoftnianDetailController($scope, $rootScope, $stateParams, entity, Nearsoftnian, Receipt) {
        var vm = this;

        vm.nearsoftnian = entity;

        var unsubscribe = $rootScope.$on('nearDocsApp:nearsoftnianUpdate', function(event, result) {
            vm.nearsoftnian = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
