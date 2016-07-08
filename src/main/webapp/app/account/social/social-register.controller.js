(function() {
    'use strict';

    angular
        .module('nearDocsApp')
        .controller('SocialRegisterController', SocialRegisterController);

    SocialRegisterController.$inject = ['$filter', '$stateParams'];

    function SocialRegisterController ($filter, $stateParams) {
        var vm = this;
        vm.provider = $stateParams.provider;
        vm.providerLabel = $filter('capitalize')(vm.provider);
        vm.success = $stateParams.success;
        vm.error = !vm.success;
    }
})();
