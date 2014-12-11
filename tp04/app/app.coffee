angular.module 'app', []

.controller 'AppController', class
  constructor: (@$http) ->
    @gotoPage(1)

  gotoPage: (pageIndex) ->
    @$http.get("/page/#{pageIndex}").success (json) =>
      @page = json
