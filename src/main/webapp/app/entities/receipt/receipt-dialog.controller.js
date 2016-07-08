(function() {
    'use strict';

    angular
        .module('nearDocsApp')
        .controller('ReceiptDialogController', ReceiptDialogController);

    ReceiptDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Receipt', 'Nearsoftnian'];

    function ReceiptDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Receipt, Nearsoftnian) {
        var vm = this;

        vm.receipt = entity;
        vm.clear = clear;
        vm.save = save;
        vm.nearsoftnians = Nearsoftnian.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.receipt.id !== null) {
                Receipt.update(vm.receipt, onSaveSuccess, onSaveError);
            } else {
                Receipt.save(vm.receipt, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('nearDocsApp:receiptUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
