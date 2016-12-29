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
            declarations: '<', // declarations to be displayed
        }
    };

    // register component in app
    angular
        .module('petiteAnnonceKmerApp')
        .component('declarationList', DeclarationList);


    // injection in controller
    DeclarationListController.$inject = ['AlertService','$uibModal','Image','DataUtils'];


    /**
     * Controller which handle initial admission request:
     *  - display request
     *  - display a form to select BRC
     *
     * @constructor
     */
    function DeclarationListController(AlertService,$uibModal,Image,DataUtils) {
        var vm = this;

        /** Initialisation data controller. */
        vm.initDataController = initDataController;

        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        /** onChanges bindings. */
        vm.$onChanges = onChanges;

        vm.settings = {
            width: 50,
            height: 50
        };


        /**
         * Init data in controller.
         */
        function initDataController() {
                    angular.forEach(vm.declarations, function (declaration) {
                        console.log(declaration);
                        declaration.images = Image.getByDeclaration(declaration);
                    });
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

