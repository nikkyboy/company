(function() {
    'use strict';

    angular
        .module('practiseApp')
        .controller('Product_MasterController', Product_MasterController);

    Product_MasterController.$inject = ['Product_Master'];

    function Product_MasterController(Product_Master) {

        var vm = this;

        vm.product_Masters = [];

        loadAll();

        function loadAll() {
            Product_Master.query(function(result) {
                vm.product_Masters = result;
                vm.searchQuery = null;
            });
        }
    }
})();
