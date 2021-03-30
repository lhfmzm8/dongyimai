app.controller("OrderController", function ($scope, $controller, OrderService) {

    $controller("BaseController", {$scope: $scope})

    $scope.show = function () {
        OrderService.show().success(function (response) {
            $scope.order = response;
            $scope.order.totalFee = 0.00;
            $scope.order.postFee = 0.00;
            $scope.order.paymentType = "1";
            $scope.order.num = 0;
            for (let i = 0; i < $scope.order.addresses.length; i++) {
                if ($scope.order.addresses[i].isDefault === "1") {
                    $scope.order.address = $scope.order.addresses[i];
                    break;
                }
            }
            angular.forEach($scope.order.sellers, function (seller) {
                angular.forEach(seller.orders, function (order) {
                    $scope.order.totalFee += order.totalFee;
                    $scope.order.num += 1;
                })
            })
            $scope.order.payment = $scope.order.totalFee + $scope.order.postFee;
        })
    }

    $scope.submit = function () {
        OrderService.submit($scope.order).success(function (response) {
            if (response.success) {
                //页面跳转
                if ($scope.order.paymentType === '1') {//如果是支付宝支付，跳转到支付页面
                    location.href = "pay.html";
                } else {//如果货到付款，跳转到提示页面
                    location.href = "paysuccess.html";
                }
            } else {
                alert(response.message);	//也可以跳转到提示页面
            }

        })
    }

})