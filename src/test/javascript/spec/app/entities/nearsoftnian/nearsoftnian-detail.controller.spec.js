'use strict';

describe('Controller Tests', function() {

    describe('Nearsoftnian Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockNearsoftnian, MockReceipt;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockNearsoftnian = jasmine.createSpy('MockNearsoftnian');
            MockReceipt = jasmine.createSpy('MockReceipt');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Nearsoftnian': MockNearsoftnian,
                'Receipt': MockReceipt
            };
            createController = function() {
                $injector.get('$controller')("NearsoftnianDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'nearDocsApp:nearsoftnianUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
