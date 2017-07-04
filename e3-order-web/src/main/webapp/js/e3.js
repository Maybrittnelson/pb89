var E3 = E3MALL = {
	checkLogin : function() {
		var _ticket = $.cookie("E3_TOKEN");
		if (!_ticket) {
			return;
		}
		$
				.ajax({
					url : "http://localhost:8088/sso/token/" + _ticket,
					 dataType : "jsonp",
					type : "GET",
					success : function(data) {
						if (data.status == 200) {
							var username = data.data.username;
							var html = username
									+ "，欢迎来到宜立方！<a href=\"http://localhost:8082/user/logout.html\" class=\"link-logout\">[退出]</a>";
							$("#loginbar").html(html);
						}
					}
				});
	}
}

//var obj ={"status":200,"msg":"OK","data":{"id":51,"username":"hm5001","password":"698d51a19d8a121ce581499d7b701668","phone":"15015050550","email":null,"created":1498270085000,"updated":1498270085000}};

$(function() {
	// 查看是否已经登录，如果已经登录查询登录信息
	E3.checkLogin();
});