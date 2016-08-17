/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
describe('bank.operations module test', function () {
    beforeEach(module('bank.operations'));

    var $controller;
    var $q;
    var $scope;

    beforeEach(inject(function (_$controller_, _$q_, _$rootScope_) {
        $controller = _$controller_;
        $q = _$q_;
        $scope = _$rootScope_.$new();
    }));

    it('deposit some funds', function () {

        var httpRequest = {};
        var operationsCtrl = $controller('OperationsCtrl', {$scope: $scope, httpRequest: httpRequest});
        $scope.amount = 234;
        httpRequest.post = function (url, params, data){
            var defered = $q.defer();
            var message = params.amount+' '+params.operation;
            defered.resolve(message);
            return defered.promise;
        };
        $scope.deposit();
        $scope.$apply();
        expect($scope.message).toEqual('234 Deposit');
    });

});
