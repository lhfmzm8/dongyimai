app.service("CartService", function ($http) {

    this.add = function (id, num, username) {
        return $http.post("/CartController/add", {id, num, username})
    }

    this.delete = function (ids, sellers, username) {
        return $http.post("/CartController/delete", {ids, sellers, username})
    }

    this.show = function () {
        return $http.get("/CartController/show")
    }

    this.toOrder = function (cart) {
        return $http.post("/CartController/toOrder", cart)
    }

    this.test=function () {
        return $http.get("/CartController/test")
    }

})