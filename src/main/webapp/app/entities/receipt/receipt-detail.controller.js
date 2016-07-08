(function() {
    'use strict';

    angular
        .module('nearDocsApp')
        .controller('ReceiptDetailController', ReceiptDetailController);

    ReceiptDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Receipt', 'Nearsoftnian'];

    function ReceiptDetailController($scope, $rootScope, $stateParams, entity, Receipt, Nearsoftnian) {
        var vm = this;

        vm.receipt = entity;

        var unsubscribe = $rootScope.$on('nearDocsApp:receiptUpdate', function(event, result) {
            vm.receipt = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
