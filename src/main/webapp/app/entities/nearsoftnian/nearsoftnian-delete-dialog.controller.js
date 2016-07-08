(function() {
    'use strict';

    angular
        .module('nearDocsApp')
        .controller('NearsoftnianDeleteController',NearsoftnianDeleteController);

    NearsoftnianDeleteController.$inject = ['$uibModalInstance', 'entity', 'Nearsoftnian'];

    function NearsoftnianDeleteController($uibModalInstance, entity, Nearsoftnian) {
        var vm = this;

        vm.nearsoftnian = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Nearsoftnian.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
