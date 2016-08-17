/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
describe('httpRequest tests', function () {
  beforeEach(module('common.http'));
  var httpService, $scope, $q, $httpBackend;

  beforeEach(inject(function (httpRequest, $rootScope, _$q_, _$httpBackend_) {
    httpService = httpRequest;
    $scope = $rootScope.$new();
    $q = _$q_;
    $httpBackend = _$httpBackend_;
  }));

  it('send get request without params', function () {
    var promise = httpService.get('/login');
    $httpBackend.when('GET', '/login')
      .respond('success');
    promise.then(function (data) {
      expect(data).toBe('success');
    });
    $httpBackend.flush();
  });

  it('send get request with params', function () {
    $httpBackend.when('GET', '/login', {username: 'Petar'})
      .respond('hi Petar');

    var promise = httpService.get('/login', {username: 'Petar'});

    promise.then(function (data) {
      expect(data).toEqual('hi Petar');
    });
    $httpBackend.flush();
  });

  it('send get request, status===404', function () {
    $httpBackend.when('GET', '/login', {username: 'Petar'})
      .respond(404, 'Page not found');

    var promise = httpService.get('/login', {username: 'Petar'});
    promise.then(function (data) {
    }, function (error) {
      expect(error).toEqual('Page not found');
    });

    $httpBackend.flush();
  });

  it('send get request, status===404 and no message', function () {
    $httpBackend.when('GET', '/login', {username: 'Petar'})
      .respond(404);

    var promise = httpService.get('/login', {username: 'Petar'});

    promise.then(function (data) {
    }, function (error) {
      expect(error).toEqual(undefined);
    });

    $httpBackend.flush();
  });


  it('send post request', function () {
    $httpBackend.when('POST', '/login', {username: 'Petar'})
      .respond('hi Petar');

    httpService.post('/login', {username: 'Petar'}).then(function (data) {
      expect(data).toEqual('hi Petar');
    });

    $httpBackend.flush();
  });

  it('send post request, status===404', function () {
    $httpBackend.when('POST', '/login', {username: 'Petar'})
      .respond(404, 'Page not found');

    httpService.post('/login', {username: 'Petar'}).then(function (data) {
    }, function (error) {
      expect(error).toEqual('Page not found');
    });

    $httpBackend.flush();
  });

  it('send post request, status===404 and no message', function () {
    $httpBackend.when('POST', '/login', {username: 'Petar'})
      .respond(404);

    httpService.post('/login', {username: 'Petar'}).then(function (data) {
    }, function (error) {
      expect(error).toEqual(undefined);
    });

    $httpBackend.flush();
  });

  it('send put request', function () {
    $httpBackend.when('PUT', '/register', {username: 'Petar'})
      .respond('success');

    httpService.put('/register', {username: 'Petar'}).then(function (data) {
      expect(data).toEqual('success');
    });
    $httpBackend.flush();
  });

  it('send put request, status===404', function () {
    $httpBackend.when('PUT', '/register', {username: 'Petar'})
      .respond(404, 'Page not found');

    httpService.put('/register', {username: 'Petar'}).then(function (data) {
    }, function (error) {
      expect(error).toEqual('Page not found');
    });

    $httpBackend.flush();
  });

  it('send put request, status===404 and no message', function () {
    $httpBackend.when('PUT', '/register', {username: 'Petar'})
      .respond(404);

    httpService.put('/register', {username: 'Petar'}).then(function (data) {
    }, function (error) {
      expect(error).toEqual(undefined);
    });

    $httpBackend.flush();
  });

  it('send del request', function () {
    $httpBackend.when('DELETE', '/remove', {username: 'Petar'})
      .respond('success');

    httpService.del('/remove', {username: 'Petar'}).then(function (data) {
      expect(data).toEqual('success');
    });
    $httpBackend.flush();
  });

  it('send del request, status===404', function () {
    $httpBackend.when('DELETE', '/delete', {username: 'Petar'})
      .respond(404, 'Page not found');

    httpService.del('/delete', {username: 'Petar'}).then(function (data) {
    }, function (error) {
      expect(error).toEqual('Page not found');
    });

    $httpBackend.flush();
  });

  it('send del request, status===404 and no message', function () {
    $httpBackend.when('DELETE', '/delete', {username: 'Petar'})
      .respond(404);

    httpService.del('/delete', {username: 'Petar'}).then(function (data) {
    }, function (error) {
      expect(error).toEqual(undefined);
    });

    $httpBackend.flush();
  });
});
