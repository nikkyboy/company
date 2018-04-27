(function() {
    'use strict';

    angular
        .module('practiseApp')
        .controller('Order_DetailsController', Order_DetailsController);

    Order_DetailsController.$inject = ['Order_Details'];

    function Order_DetailsController(Order_Details) {

        var vm = this;

        vm.order_Details = [];

        loadAll();

        function loadAll() {
            Order_Details.query(function(result) {
                vm.order_Details = result;
                vm.searchQuery = null;
            });
        }
    }
})();
