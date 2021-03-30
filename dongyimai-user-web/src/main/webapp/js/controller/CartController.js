app.controller("CartController", function ($scope, $controller, CartService) {

    $controller("BaseController", {$scope: $scope});
    $scope.cart = {checked: false, sellers: [], nums: {}}

    $scope.add = function (id, num) {
        angular.forEach($scope.cart.sellers, function (seller) {
            angular.forEach(seller.orders, function (order) {
                if (order.id === id) {
                    if (num + $scope.cart.nums[id] > order.num) {
                        $scope.cart.nums[id] = order.num;
                        alert("超过限购数量");
                    } else {
                        CartService.add(id, num, $scope.username).success(function (response) {
                            if (response.success) {
                                $scope.cart.nums[id] += num;
                                order.totalFee = $scope.cart.nums[id] * order.price;
                                $scope.account();
                            } else
                                alert(response.message);
                        })
                    }
                }
            })
        })
    }

    $scope.delete = function (id) {
        let ids = [];
        if (id === null)
            ids = $scope.keys;
        else
            ids.push(id)
        angular.forEach(ids, function (id) {
            let sellerIndex = [];
            angular.forEach($scope.cart.sellers, function (seller) {
                let orderIndex = [];
                angular.forEach(seller.orders, function (order) {
                    if (order.id === id) {
                        $scope.cart.nums.orderIndex.push(seller.orders.indexOf(order));
                    }
                })
                angular.forEach(orderIndex, function (index) {
                    seller.orders.splice(index, 1);
                })
                if (seller.orders.length === 0)
                    sellerIndex.push($scope.cart.sellers.indexOf(seller));
            })
            angular.forEach(sellerIndex, function (index) {
                $scope.cart.sellers.splice(index, 1);
            })
        })
        // CartService.delete(ids, $scope.cart.sellers, $scope.username).success(function (response) {
        //     if (!response.success)
        //         alert(response.message);
    }

    $scope.show = function () {
        CartService.show().success(function (response) {
            $scope.cart = response;
            angular.forEach($scope.cart.sellers, function (seller) {
                $scope.orderNum += seller.orders.length;
                angular.forEach(seller.orders, function (order) {
                    order.totalFee = $scope.cart.nums[order.id] * order.price;
                    order.checked = false;
                })
            })
        })
    }

    $scope.account = function () {
        $scope.totalMoney = 0.00;
        angular.forEach($scope.keys, function (key) {
            angular.forEach($scope.cart.sellers, function (seller) {
                angular.forEach(seller.orders, function (order) {
                    if (key === order.id)
                        $scope.totalMoney += order.totalFee;
                })
            })
        })
    }

    $scope.checkCart = function ($event) {
        let checked = $event.target.checked;
        $scope.cart.checked = checked;
        angular.forEach($scope.cart.sellers, function (seller) {
            if (seller.checked !== checked) {
                $scope.checkSeller($event, seller);
            }
        })
    }

    $scope.checkSeller = function ($event, seller) {
        let checked = $event.target.checked;
        seller.checked = checked;
        angular.forEach(seller.orders, function (order) {
            if (order.checked !== checked) {
                $scope.checkOrder($event, seller, order)
            }
        })
    }

    $scope.checkOrder = function ($event, seller, order) {
        order.checked = $event.target.checked;
        $scope.updateKeys($event, order.id);
        let tmp = true;
        angular.forEach(seller.orders, function (order) {
            if (order.checked === false || order.checked === undefined)
                tmp = false;
        })
        seller.checked = tmp;
        $scope.checkAll();
    }

    $scope.checkAll = function () {
        let tmp = true;
        angular.forEach($scope.cart.sellers, function (seller) {
            angular.forEach(seller.orders, function (order) {
                if (order.checked === false || order.checked === undefined)
                    tmp = false;
            })
        })
        $scope.cart.checked = tmp;
    }

    $scope.commitNum = function ($event, id) {
        if ($event.keyCode === 13) {
            let num = $event.target.value - $scope.cart.nums[id];
            $scope.add(id, num);
        }
    }

    $scope.toOrder = function () {
        if ($scope.cart.username === undefined || $scope.cart.username === "anonymousUser")
            $scope.login();
        else {
            CartService.toOrder($scope.cart).success(function (response) {
                if (response.success)
                    location.href = "/getOrderInfo.html";
                else
                    alert(response.message);
            })
        }
    }

    $scope.test = function () {
        CartService.test().success(function () {
            location.reload();
        })
    }

})