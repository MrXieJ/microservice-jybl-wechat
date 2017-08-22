var URL = "http://mrxiej.ngrok.wendal.cn/api-wechat";

$(document).ready(function() {

	//加载医生信息
	$.ajax({
		type: "GET",
		url: URL + "/doctor/getdoctor?phone=138",
		dataType: "json",
		success: function(data) {
			$(".warn_div p").html(data.name);
		}
	});
	//加载服务包信息
	$.ajax({
		type: "GET",
		url: URL + "/doctor/getservice",
		dataType: "json",
		success: function(data) {
			if(data != null) {
				$("#p1").html("共" + data.length + "项 合计：");
				var money=0;
				$.each(data, function(n, obj) {

					var service = $("#div_service").clone(true);
					service.attr("id", "div_service" + n);
					$("form").prepend(service);
					$("#serviceinfo").attr("id", "serviceinfo" + n);
					$("#down").attr("id", "down" + n);

					service.find("label").html(obj.name);
					service.find(".count").html("次数 ：" + obj.count);
					service.find(".duration").html("期限 ：" + obj.duration + "个月");
					service.find(".infotwo").html("适用人群：" + obj.kind);
					service.find("p").html(obj.price + "元");
					money+=parseInt(obj.price);
					$("#serviceinfo" + n).show();
				});
				$("#p2").html(money+"元");
				$(".service_content label").css("background-image", "none");
				$(".service_content label").css("padding-left", "0px");
				$(".infoone").css("padding-left", "0px");
				$(".infotwo").css("padding-left", "0px");
				$(".div_service p").css("padding-left", "30px");
			}
		}
	});

})