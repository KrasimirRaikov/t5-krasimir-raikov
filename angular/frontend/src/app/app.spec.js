describe('AppCtrl', function () {
  describe('isCurrentUrl', function () {
    var AppCtrl, scope, userdataGateway, deferred, currentUserProvider;

    beforeEach(module('bank'));

    beforeEach(inject(function ($q, $controller, $rootScope) {
      scope = $rootScope.$new();
      deferred = $q.defer();
      userdataGateway = {};
      currentUserProvider = {};
      growl = {};
      cookies = {};
      state = {};
      logoutGateway = {};
      AppCtrl = $controller('AppCtrl', {
        $scope: scope,
        userdataGateway: userdataGateway,
        currentUserProvider: currentUserProvider,
        logoutGateway: logoutGateway,
        growl: growl,
        $cookies: cookies,
        $state: state
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
    it('should logout user', function () {
      var sessionId = '1';
      logoutGateway.logout = jasmine.createSpy('logout').and.returnValue(deferred.promise);
      cookies.remove = jasmine.createSpy('remove cookie');
      state.go = jasmine.createSpy('state go');
      growl.success = jasmine.createSpy('growl success');
      currentUserProvider.setCurrentUser = jasmine.createSpy('setCurrentUser');

      AppCtrl.logoutUser();

      deferred.resolve(sessionId);
      scope.$digest();

      expect(logoutGateway.logout).toHaveBeenCalled();
      expect(state.go).toHaveBeenCalledWith('login');
      expect(growl.success).toHaveBeenCalledWith('successful logout');
    });

    it('be unable to logout', function () {
      var errorMessage = 'we were unable to log you out';
      logoutGateway.logout = jasmine.createSpy('logout').and.returnValue(deferred.promise);
      growl.error = jasmine.createSpy('growl error');

      AppCtrl.logoutUser();

      deferred.reject(errorMessage);
      scope.$digest();

      expect(logoutGateway.logout).toHaveBeenCalled();
      expect(growl.error).toHaveBeenCalledWith(errorMessage);
    });
  });
});
