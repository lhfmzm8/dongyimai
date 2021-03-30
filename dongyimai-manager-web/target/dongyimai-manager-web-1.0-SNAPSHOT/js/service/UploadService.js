app.service("UploadService", function ($http) {
    this.uploadFile = function () {
        let formData = new FormData();
        formData.append("file", file.files[0]);
        return $http({
            method: 'POST',
            url: "/UploadController/upload",
            data: formData,
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity
        })
    }
})