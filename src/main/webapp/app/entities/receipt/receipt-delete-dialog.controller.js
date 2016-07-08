(function() {
    'use strict';

    angular
        .module('nearDocsApp')
        .controller('ReceiptDeleteController',ReceiptDeleteController);

    ReceiptDeleteController.$inject = ['$uibModalInstance', 'entity', 'Receipt'];

    function ReceiptDeleteController($uibModalInstance, entity, Receipt) {
        var vm = this;

        vm.receipt = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Receipt.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
