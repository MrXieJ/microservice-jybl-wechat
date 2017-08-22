//加载信息
var URL = "http://mrxiej.ngrok.wendal.cn/api-wechat";
//获取url中的参数
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); //匹配目标参数
	if(r != null) return unescape(r[2]);
	return null; //返回参数值
}

var url = window.location.href;
var phone = getUrlParam(url);

$(document).ready(function() {
	//加载医生信息
	$.ajax({
		type: "GET",
		url: URL + "/doctor/getdoctor?phone=138",
		dataType: "json",
		success: function(data) {
			if(data.name == null) {
				$("#topname").html("未填写");
				$("#midname").html("未填写");
			} else {
				$("#topname").html(data.name);
				$("#midname").html(data.name);
			}

			var dept;
			if(data.department == null && data.title == null) {
				dept = "未填写 | 未填写";
			} else if(data.department == null && data.title != null) {
				dept = "未填写 | " + data.title;
			} else if(data.department != null && data.title == null) {
				dept = data.department + " | 未填写";
			} else {
				dept = data.department + " | " + data.title;
			}
			$(".department").html(dept);

			if(data.hospital == null) {
				$(".address").html("未填写");
			} else {
				$(".address").html(data.hospital);
			}

			if(data.adept == null) {
				$("#adept").html("未填写");
			} else {
				$("#adept").html(data.adept);
			}

			if(data.experience == null) {
				$("#experience").html("未填写");
			} else {
				$("#experience").html(data.experience);
			}
			if(data.head_pic != null) {
				$(".div_img img").attr("src", data.head_pic);
			}
			if(data.QRcode_pic != null) {
				$(".div_qrcode img").attr("src", data.QRcode_pic);
			}
		}
	});
//加载服务包信息
	$.ajax({
		type: "GET",
		url: URL + "/doctor/getservice",
		dataType: "json",
		success: function(data) {
			if(data != null) {
				$.each(data, function(n, obj) {
					var service = $("#div_service").clone(true);
					service.attr("id", "div_service" + n);
					$("form").prepend(service);
					$("#serviceinfo").attr("id", "serviceinfo" + n);
					$("#down").attr("id", "down" + n);

					service.find("label").html(obj.name);
					service.find(".count").html("次数 ：" + obj.count);
					service.find(".duration").html("期限 ：" + obj.duration+"个月");
					service.find(".infotwo").html("适用人群：" + obj.kind);
					service.find("p").html(obj.price + "元");
				});
			}
		}
	});

})
//下拉点击事件
function down(obj) {
	var th = $(obj);
	var id = th.parent().attr("id").toString();
	var n = id.charAt(id.length - 1);
	if(th.children("img").attr("src") == "../image/Shape-11@2x.png") {
		th.children("img").attr("src", "../image/Shape-12@2x.png");
		$("#serviceinfo"+n).show();
	} else {
		th.children("img").attr("src", "../image/Shape-11@2x.png");
		$("#serviceinfo"+n).hide();
	}

}

//function dataLoad() {
//	var xmlhttp;
//	if(window.XMLHttpRequest) {
//		xmlhttp = new XMLHttpRequest(); 
//	} else {
//		xmlhttp = new ActiveXObject();
//	}
//	xmlhttp.onreadystatechange = function() {
//		if(xmlhttp.readyState == 4 && xmlhttp.status == 200) {
//			document.getElementsByClassName("name")[0].innerHTML = "haha";
//			document.getElementsByClassName("department")[0].innerHTML = "外科";
//			document.getElementsByClassName("title")[0].innerHTML = "主刀医生";
//			document.getElementsByClassName("address")[0].innerHTML = "广州市天河";
//		}
//	}
//	xmlhttp.open("GET","",true);
//	xmlhttp.send();

//}