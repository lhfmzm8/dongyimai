//服务层
app.service("IndexService", function ($http) {
    this.showName = function () {
        return $http.post("/IndexController/showName");
    }
})