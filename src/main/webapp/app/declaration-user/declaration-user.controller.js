/**
 * Created by admin on 12/12/2016.
 */

(function() {
    'use strict';

    angular
        .module('petiteAnnonceKmerApp')
        .controller('DeclarationUserDialogController', DeclarationUserDialogController);

    DeclarationUserDialogController.$inject = ['$translate','$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'DeclarationUser', 'User', 'Localisation', 'Image','Country','Region', 'Auth', 'LoginService'];

    function DeclarationUserDialogController ($translate,$timeout, $scope, $stateParams, $uibModalInstance, $q, entity, DeclarationUser, User, Localisation, Image,Country,Region, Auth, LoginService) {
        var vm = this;

        vm.declaration = entity;
        vm.clear = clear;
        vm.save = save;
        vm.onSaveSuccess = onSaveSuccess;
        vm.onSaveError = onSaveError;

        // register field
        vm.doNotMatch = null;
        vm.error = null;
        vm.errorUserExists = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.registerAccount = {};
        vm.success = null;
        vm.isSave = false;




        vm.select1 = select1;
        vm.select2 = select2;
        vm.select3 = select3;
        vm.loadRegion = loadRegion;

         Country.query(function (data) {
             vm.countries = data;
             vm.localisation.country =vm.countries[0];
             vm.loadRegion();
        });
        vm.countryRegion = {};
        vm.localisation = {};




        vm.images = {};

        vm.images.principal = {}
        vm.images.image2 = {}
        vm.images.image3 = {}
        vm.hideUpload1 = false;
        vm.hideUpload2 = false;
        vm.hideUpload3 = false;


        function select1(file) {
            console.log(file);
            vm.hideUpload1 = true;
        }
        function select2(file) {
            console.log(file);
            vm.hideUpload2 = true;
        }

        function select3(file) {
            console.log(file);
            vm.hideUpload3 = true;
        }

        vm.citiesOptions = {
            country: 'cm',
            types: '(cities)'
        };
        vm.citiesOptionsDetail ='';



        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {

            console.log(vm.declaration);
            console.log(vm.localisation);
            /*DeclarationUser.saveDeclarationUser(
                vm.declaration,
                vm.localisation,
                [vm.images.principal,vm.images.image2,vm.images.image3]
            ).then(onSaveSuccess)
             .catch(onSaveError);*/
            vm.isSave= true;


        }

        function onSaveSuccess (data) {
          console.log("saved ok");
          console.log(data);
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function loadRegion() {
            if(vm.localisation.country) {
                vm.countryRegion = Region.getByCountry(
                    {countryId: vm.localisation.country.id}
                )
            }
        }

        function register () {
            console.log("SAVE register")

            if (vm.registerAccount.password !== vm.confirmPassword) {
                vm.doNotMatch = 'ERROR';
            } else {
                vm.registerAccount.langKey = $translate.use();
                vm.doNotMatch = null;
                vm.error = null;
                vm.errorUserExists = null;
                vm.errorEmailExists = null;

                Auth.createAccount(vm.registerAccount).then(function () {
                    vm.success = 'OK';

                    DeclarationUser.saveDeclarationUser(
                        vm.declaration,
                        vm.localisation,
                        vm.registerAccount,
                        [vm.images.principal,vm.images.image2,vm.images.image3]
                    ).then(onSaveSuccess)
                        .catch(onSaveError)

                }).catch(function (response) {
                    vm.success = null;
                    if (response.status === 400 && response.data === 'login already in use') {
                        vm.errorUserExists = 'ERROR';
                    } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                        vm.errorEmailExists = 'ERROR';
                    } else {
                        vm.error = 'ERROR';
                    }
                });
            }
        }
    }
})();
