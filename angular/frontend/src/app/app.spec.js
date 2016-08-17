describe('AppCtrl', function () {
  describe('isCurrentUrl', function () {
    var AppCtrl, scope, userdataGateway, deferred, currentUserProvider;

    beforeEach(module('bank'));

    beforeEach(inject(function ($q, $controller, $rootScope) {
      scope = $rootScope.$new();
      deferred = $q.defer();
      userdataGateway = {};
      currentUserProvider = {};
      AppCtrl = $controller('AppCtrl', {
        $scope: scope,
        userdataGateway: userdataGateway,
        currentUserProvider: currentUserProvider
      });
    }));

    it('should load currentUser', inject(function () {
      var name = 'Krisko';
      userdataGateway.loadCurrentUser = jasmine.createSpy('loadCurrentUser').and.returnValue(deferred.promise);
      currentUserProvider.setCurrentUser = jasmine.createSpy('setCurrentUser');

      AppCtrl.fetchCurrentUser();

      expect(userdataGateway.loadCurrentUser).toHaveBeenCalled();

      deferred.resolve(name);
      scope.$digest();

      expect(currentUserProvider.setCurrentUser).toHaveBeenCalledWith(name);
    }));
  });
});
