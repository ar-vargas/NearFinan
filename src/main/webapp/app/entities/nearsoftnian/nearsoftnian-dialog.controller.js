(function() {
    'use strict';

    angular
        .module('nearDocsApp')
        .controller('NearsoftnianDialogController', NearsoftnianDialogController);

    NearsoftnianDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Nearsoftnian', 'Receipt'];

    function NearsoftnianDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Nearsoftnian, Receipt) {
        var vm = this;

        vm.nearsoftnian = entity;
        vm.clear = clear;
        vm.save = save;
        vm.receipts = Receipt.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.nearsoftnian.id !== null) {
                Nearsoftnian.update(vm.nearsoftnian, onSaveSuccess, onSaveError);
            } else {
                Nearsoftnian.save(vm.nearsoftnian, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('nearDocsApp:nearsoftnianUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
