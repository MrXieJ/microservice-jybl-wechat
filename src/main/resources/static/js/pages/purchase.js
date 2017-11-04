var URL = "http://mrxiej.ngrok.wendal.cn/api-wechat";

//获取url中的参数
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r != null) {
		return unescape(r[2]);
	}
	return null;
}

var servicelist = getUrlParam("chk_value");
var wechat_id = getUrlParam("wechat_id");
var phone = getUrlParam("phone");



$(document).ready(function() {

	//加载医生信息
	$.ajax({
		type: "GET",
		url: URL + "/doctor/get?phone="+phone,
		dataType: "json",
		success: function(alldata) {
			var data = alldata.data;
			if(data!=null){
			$(".warn_div p").html(data.name);
			}else{
				$.alert("加载失败，请重新进入",function () {
					window.history.back();
                })
			}

		}
	});
	//加载服务包信息

	$.ajax({
		type: "GET",
		url: URL + "/patientinfo/service/getbylist",
		data: {
			servicelist: servicelist,
		},
		traditional: true,
		dataType: 'json',
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		},
		success: function(alldata) {
			var data = alldata.data;
			if(data != null) {
				$("#p1").html("共" + data.length + "项 合计：");
				var money = 0;
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
					money += parseInt(obj.price);
					$("#serviceinfo" + n).show();
				});
				$("#p2").html(money + "元");
				$(".service_content label").css("background-image", "none");
				$(".service_content label").css("padding-left", "0px");
				$(".infoone").css("padding-left", "0px");
				$(".infotwo").css("padding-left", "0px");
				$(".div_service p").css("padding-left", "30px");
			}else{
				$.alert("信息加载失败",function () {
					window.history.back();
                })
			}
		},
		error:function () {
			$.alert("请检查您的网络是否通畅",function () {
				window.history.back();
            })
        }
	});

})

//点击确定按钮

function ack() {
	$.ajax({
		type: "POST",
		data: {
			wechat_id: wechat_id,
			servicelist: servicelist,
		},
		url: URL + "/patientinfo/service/buy",
		dataType: "json",
		success: function(alldata) {
			if(alldata.errorcode='0'){
                $.msgbox.Alert("提示", "购买成功！");
			}else{
				$.alert("购买失败，请稍后再试",{

				})
			}
		}
	});


	

}

//弹窗事件
(function() {
	$.msgbox = {
		Alert: function(title, msg) {
			GenerateHtml("alert", title, msg);
			btnOk(); //alert只是弹出消息，因此没必要用到回调函数callback  
			btnNo();
		}
	}
	//生成Html  
	var GenerateHtml = function(type, title, msg) {
		var _html = "";
		_html = '<div id="mb_box"></div><div id="mb_con"><div id="mb_tit">' +
			title + '</div><a id="mb_ico"></a><img id="mb_img" src="../image/gou1@2x.png"/><div id="mb_msg">' +
			msg + '</div><div id="mb_btnbox"><input id="mb_btn_ok" type="button" value="确定" /></div></div>'

		//必须先将_html添加到body，再设置Css样式  
		$("body").append(_html);
		//生成Css  
		GenerateCss();
	}

	//生成Css  
	var GenerateCss = function() {
		$("#mb_box").css({
			width: '100%',
			height: '100%',
			zIndex: '99999',
			position: 'fixed',
			filter: 'Alpha(opacity=60)',
			backgroundColor: 'black',
			top: '0',
			left: '0',
			opacity: '0.6'
		});
		$("#mb_con").css({
			textAlign: 'center',
			zIndex: '999999',
			width: '318px',
			position: 'fixed',
			backgroundColor: 'White',
			borderRadius: '5px'
		});
		$("#mb_tit").css({
			height: '43px',
			width: '100%',
			lineHeight: '43px',
			textAlign: 'center',
			display: 'block',
			fontSize: '15px',
			color: '#FFFFFF',
			backgroundColor: '#20A6AC',
			borderRadius: '5px 5px 0 0',
			fontWeight: 'bold'
		});
		$("#mb_msg").css({
			padding: '15px',
			lineHeight: '20px',
			color: '#333333',
			fontSize: '15px'
		});
		$("#mb_ico").css({
			display: 'block',
			position: 'absolute',
			right: '-10px',
			top: '-10px',
			backgroundImage: 'url(../image/close@2x.png)',
			backgroundSize: '25px 25px',
			width: '25px',
			height: '25px',
			textAlign: 'center',
			lineHeight: '16px',
			cursor: 'pointer',
			fontFamily: '微软雅黑'
		});
		$("#mb_img").css({
			paddingTop: '25px',
			width: '50px',
			height: '50px'
		});
		$("#mb_btnbox").css({
			margin: '10px 0 10px 0',
			textAlign: 'center'
		});
		$("#mb_btn_ok,#mb_btn_no").css({
			width: '88px',
			height: '32px',
			color: '#FFFFFF',
			border: 'none'
		});
		$("#mb_btn_ok").css({
			backgroundColor: '#20A6AC',
			borderRadius: '5px',
		});
		$("#mb_btn_no").css({
			backgroundColor: 'gray',
			marginLeft: '20px'
		});
		//右上角关闭按钮hover样式  
		$("#mb_ico").hover(function() {
			$(this).css({
				backgroundColor: 'Red',
				color: 'White'
			});
		}, function() {
			$(this).css({
				backgroundColor: '#DDD',
				color: 'black'
			});
		});
		var _widht = document.documentElement.clientWidth; //屏幕宽  
		var _height = document.documentElement.clientHeight; //屏幕高  
		var boxWidth = $("#mb_con").width();
		var boxHeight = $("#mb_con").height();
		//让提示框居中  
		$("#mb_con").css({
			top: (_height - boxHeight) / 2 + "px",
			left: (_widht - boxWidth) / 2 + "px"
		});
	}
	//确定按钮事件  
	var btnOk = function(callback) {
		$("#mb_btn_ok").click(function() {
			$("#mb_box,#mb_con").remove();
			if(typeof(callback) == 'function') {
				callback();
			}
			window.history.back();
		});
	}
	//取消按钮事件  
	var btnNo = function() {
		$("#mb_btn_no,#mb_ico").click(function() {
			$("#mb_box,#mb_con").remove();
		});
	}
})();