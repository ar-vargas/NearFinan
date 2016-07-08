(function() {
    'use strict';

    angular
        .module('nearDocsApp')
        .controller('NearsoftnianController', NearsoftnianController);

    NearsoftnianController.$inject = ['$scope', '$state', 'Nearsoftnian'];

    function NearsoftnianController ($scope, $state, Nearsoftnian) {
        var vm = this;
        
        vm.nearsoftnians = [];

        loadAll();

        function loadAll() {
            Nearsoftnian.query(function(result) {
                vm.nearsoftnians = result;
            });
        }
    }
})();
