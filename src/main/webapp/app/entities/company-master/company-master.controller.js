(function() {
    'use strict';

    angular
        .module('practiseApp')
        .controller('Company_MasterController', Company_MasterController);

    Company_MasterController.$inject = ['Company_Master'];

    function Company_MasterController(Company_Master) {

        var vm = this;

        vm.company_Masters = [];

        loadAll();

        function loadAll() {
            Company_Master.query(function(result) {
                vm.company_Masters = result;
                vm.searchQuery = null;
            });
        }
    }
})();
