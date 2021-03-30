app.controller("PayController", function ($scope, $controller, PayService) {

    $controller("BaseController", {$scope: $scope})

    $scope.pay = function () {
        PayService.pay().success(function (response) {
            $scope.totalFee = response.total_amount;
            $scope.outTradeNo = response.out_trade_no;
            new QRious({element: $("#qr")[0], size: 250, level: "Q", value: response.qrcode});
        })
    }
})