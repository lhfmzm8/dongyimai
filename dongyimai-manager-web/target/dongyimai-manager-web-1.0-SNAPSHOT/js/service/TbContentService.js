//服务层
app.service("TbContentService",function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.post("/TbContentController/findAll");
	}
	//分页 
	this.findPage=function(pageNum,pageSize,tbContent){
		return $http.post("/TbContentController/findPage?pageNum="+pageNum+"&pageSize="+pageSize,tbContent);
	}
	//查询实体
	this.findOne=function(id){
		return $http.post("/TbContentController/findOne?id="+id);
	}
	//增加、修改
	this.save=function (tbContent) {
		let url;
		if(tbContent.id==null){
			url="/TbContentController/add";
		}else {
			url="/TbContentController/update"
		}
		return $http.post(url,tbContent);
	}
	//删除
	this.delete=function(ids){
		return $http.post("/TbContentController/delete?ids="+ids);
	}
});