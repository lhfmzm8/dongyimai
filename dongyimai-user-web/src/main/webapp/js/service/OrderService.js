app.service("OrderService", function ($http) {

    this.show = function () {
        return $http.get("/TbOrderController/show")
    }

    this.submit = function (order) {
        return $http.post("/TbOrderController/submit", order)
    }

})