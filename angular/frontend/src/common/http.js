/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */

angular.module('common.http', [])

  .service('httpRequest', function ($http, $q) {

    this.get = function (url, params, data) {
      return this.send('GET', url, params, data);
    };

    this.post = function (url, data, params) {
      return this.send('POST', url, data, params);
    };

    this.put = function (url, data) {
      return this.send('PUT', url, data);
    };

    this.del = function (url, params, data) {
      return this.send('DELETE', url, params, data);
    };

    this.send = function (method, url, data, params) {
      var deferred = $q.defer();

      $http({method: method, url: url, data: data, params: params})
        .success(function (data) {
          deferred.resolve(data);
        })

        .error(function (data, status) {

          if (status === 404 && !data) {
            deferred.resolve();
          } else {
            deferred.reject(data);
          }
        });

      return deferred.promise;
    };
  });
