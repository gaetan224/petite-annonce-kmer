(function() {
    'use strict';

    angular
        .module('petiteAnnonceKmerApp')
        .controller('RegionDeclarationController', RegionDeclarationController);

    RegionDeclarationController.$inject = ['AlertService','Declaration','region', 'ParseLinks','paginationConstants', 'pagingParams','$state'];

    function RegionDeclarationController (AlertService, Declaration, region, ParseLinks,paginationConstants, pagingParams,$state) {
        var vm = this;
        vm.regionId = region.regionId;
        vm.regionName = region.regionName;
        vm.declarations = [];
        vm.loadAll = loadAll;

        vm.loadPage = loadPage;
        vm.transition = transition;

        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.itemsPerPage = paginationConstants.itemsPerPage;

        vm.loadAll();

        function loadAll() {
            console.log("ddd");
            Declaration.getAllDeclarationsByRegion({
                    IdRegion:vm.regionId,
                        page: pagingParams.page - 1,
                        size: vm.itemsPerPage,
                        sort: sort()
                }
                , onSuccess, onError);

        }


        function sort() {
            var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
            if (vm.predicate !== 'id') {
                result.push('id');
            }
            return result;
        }


        function onSuccess(data, headers) {
            vm.links = ParseLinks.parse(headers('link'));
            vm.totalItems = headers('X-Total-Count');
            vm.queryCount = vm.totalItems;
            vm.declarations = data;
            vm.page = pagingParams.page;
        }

        function onError(error) {
            AlertService.error(error.data.message);
        }


        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                regionId: vm.regionId,
                regionName: vm.regionName,
                search: vm.currentSearch
            });
        }
    }
})();
