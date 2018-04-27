(function() {
    'use strict';

    angular
        .module('practiseApp')
        .controller('Company_MasterDeleteController',Company_MasterDeleteController);

    Company_MasterDeleteController.$inject = ['$uibModalInstance', 'entity', 'Company_Master'];

    function Company_MasterDeleteController($uibModalInstance, entity, Company_Master) {
        var vm = this;

        vm.company_Master = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Company_Master.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
