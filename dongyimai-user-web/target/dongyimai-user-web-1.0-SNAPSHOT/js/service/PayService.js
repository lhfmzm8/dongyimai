app.service("PayService", function ($http) {

    this.pay = function () {
        return $http.get("/PayController/pay")
    }

})