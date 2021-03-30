app.service("SearchService", function ($http) {

    this.grouping = function (searchMap) {
        return $http.post("/SearchController/grouping", searchMap)
    }

    this.search = function (searchMap) {
        return $http.post("/SearchController/search", searchMap);
    }
})