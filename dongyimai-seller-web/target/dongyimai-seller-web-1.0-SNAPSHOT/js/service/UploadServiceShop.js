app.service("UploadServiceShop", function ($http) {
    this.uploadFile = function () {
        let formData = new FormData();
        formData.append("file", file.files[0]);
        return $http({
            method: 'POST',
            url: "/UploadControllerShop/upload",
            data: formData,
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity
        })
    }
})