/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
describe('bank.http module tests', function () {
    beforeEach(module('bank.http'));
    httpService = {};
    $scope = {};
    $q ={};
    $httpBackend = {};
    beforeEach(inject(function (httpRequest, _$rootScope_, _$q_, _$httpBackend_){
        httpService = httpRequest;
        $scope = _$rootScope_.$new();
        $q = _$q_;
        $httpBackend = _$httpBackend_;
    }));

    it('send get request without params', function () {
        $httpBackend.when('GET', '/foo')
            .respond('bar');

        httpService.get('/foo').then(function (data) {
            $scope.message = data;
        });

        $httpBackend.flush();
        $scope.$apply();
        expect($scope.message).toEqual('bar');
    });

    it('send get request with params', function(){
        $httpBackend.when('GET', '/foo', {username: 'Petar'})
            .respond('bar'+' Petar');

        httpService.get('/foo', {username: 'Petar'}).then(function (data) {
            $scope.message = data;
        });

        $httpBackend.flush();
        $scope.$apply();
        expect($scope.message).toEqual('bar Petar');
    });


    it('send post request', function(){
        $httpBackend.when('POST', '/foo', {username: 'Petar'})
            .respond('bar'+' Petar');

        httpService.post('/foo', {username: 'Petar'}).then(function (data) {
            $scope.message = data;
        });

        $httpBackend.flush();
        $scope.$apply();
        expect($scope.message).toEqual('bar Petar');
    });

    it('send put request', function(){
        $httpBackend.when('PUT', '/foo', {username: 'Petar'})
            .respond('success');

        httpService.put('/foo', {username: 'Petar'}).then(function (data) {
            $scope.message = data;
        });
        $httpBackend.flush();
        $scope.$apply();
        expect($scope.message).toEqual('success');
    });

    it('send del request', function () {
        $httpBackend.when('DELETE', '/foo', {username: 'Petar'})
            .respond('success');

        httpService.del('/foo', {username: 'Petar'}).then(function (data) {
            $scope.message = data;
        });
        $httpBackend.flush();
        $scope.$apply();
        expect($scope.message).toEqual('success');
    });
});
