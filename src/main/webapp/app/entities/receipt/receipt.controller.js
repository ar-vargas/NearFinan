(function() {
    'use strict';

    angular
        .module('nearDocsApp')
        .controller('ReceiptController', ReceiptController);

    ReceiptController.$inject = ['$scope', '$state', 'Receipt'];

    function ReceiptController ($scope, $state, Receipt) {
        var vm = this;
        
        vm.receipts = [];

        loadAll();

        function loadAll() {
            Receipt.query(function(result) {
                vm.receipts = result;
            });
        }
    }
})();
