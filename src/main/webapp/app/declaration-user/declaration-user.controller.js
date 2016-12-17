/**
 * Created by admin on 12/12/2016.
 */

(function() {
    'use strict';

    angular
        .module('petiteAnnonceKmerApp')
        .controller('DeclarationUserDialogController', DeclarationUserDialogController);

    DeclarationUserDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Declaration', 'User', 'Localisation', 'Image','Country','Region'];

    function DeclarationUserDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Declaration, User, Localisation, Image,Country,Region) {
        var vm = this;

        vm.declaration = entity;
        vm.clear = clear;
        vm.save = save;
        vm.onSaveSuccess = onSaveSuccess;
        vm.onSaveError = onSaveError;
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
        console.log("SAVE")
            console.log(vm.citiesOptionsDetail);
            console.log(vm.localisation.city);

        }

        function onSaveSuccess (result) {

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
    }
})();
