/**
 * Created by admin on 04/12/2016.
 */

(function() {
    'use strict';

    /** . */
    var DeclarationList = {
        templateUrl: 'app/components/util/declaration-list/declaration-list.component.html',
        controller: DeclarationListController,
        controllerAs: 'vm',
        bindings: {
            regionName: '<', // region key
            regionId: '<' // region name
        }
    };

    // register component in app
    angular
        .module('petiteAnnonceKmerApp')
        .component('declarationList', DeclarationList);

    // injection in controller
    DeclarationListController.$inject = ['Declaration','AlertService','$uibModal'];


    /**
     * Controller which handle initial admission request:
     *  - display request
     *  - display a form to select BRC
     *
     * @constructor
     */
    function DeclarationListController(Declaration,AlertService,$uibModal) {
        var vm = this;

        /** Initialisation data controller. */
        vm.initDataController = initDataController;

        /** onChanges bindings. */
        vm.$onChanges = onChanges;


        /**
         * Init data in controller.
         */
        function initDataController() {
            console.log("DeclarationListController");
            console.log(vm.regionId);
            console.log(vm.regionName);
                Declaration.query({
                        IdRegion:vm.regionId
                    }
                    , onSuccess, onError);
                function onSuccess(data, headers) {
                    vm.declarations = data;
                }
                function onError(error) {
                    AlertService.error(error.data.message);
                }

        }

        /**
         * Performs when bindings changes.
         * Change read only flag.
         */
        function onChanges() {
            vm.initDataController();
        }


        function viewDeclaration() {
            $uibModal.open({
                animation: true,
                component: 'admissionRequestForm', // component support since UI Boostrap 2.1.0
                size: 'lg',
                resolve: {
                    admissionRequest: vm.admissionRequest
                }
            });
        }

    }

})();

