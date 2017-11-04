/*
 * 获取url中的参数值
 *
 * @param
 * name: 参数的名称
 *
 * @return
 * 返回参数的值
 *
 */
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}


/****************************************/
/*             本地存储操作               */
/****************************************/
/*
 * 检查是否支持本地存储
 *
 */
function storageSupport() {
    try {
        return 'localStorage' in window && window['localStorage'] !== null;
    } catch (e) {
        return false;
    }
}

/*
 * 获取本地存储中关键值对应的值
 *
 * @param
 * key: 关键值
 *
 * @return
 * 返回关键值对应的值
 *
 */
function getItem(key) {
    return sessionStorage.getItem(key);
    // return localStorage.getItem(key);
}

/*
 * 获取本地存储中索引对应的关键值
 *
 * @param
 * index: 索引值
 *
 * @return
 * 返回索引值的关键值
 *
 */
function getItemKey(index) {
    return sessionStorage.key(index);
    // return localStorage.key(index);
}

/*
 * 获取本地存储的键值对数量
 *
 * @return
 * 返回键值对数量
 *
 */
function getItemLength() {
    return sessionStorage.length();
    // return localStorage.length();
}


/*
 * 移除本地存储中关键值对应的值
 *
 * @param
 * key: 关键值
 *
 * @return
 * 返回移除结果
 *
 */
function removeItem(key) {
    return sessionStorage.removeItem(key);
    // return localStorage.removeItem(key);
}

/*
 * 添加键值对到本地存储
 *
 * @param
 * key: 关键值
 * value: 数值
 *
 * @return
 * 返回添加结果
 *
 */
function setItem(key, value) {
    return sessionStorage.setItem(key, value);
    // return localStorage.setItem(key,value);
}

/*
 * 清除本地存储中的所有键值对
 *
 * @return
 * 返回清除结果
 *
 */
function clearItem() {
    return sessionStorage.clear();
    // return localStorage.clear();
}

/****************************************/
/*             Cookie操作               */
/****************************************/

/*
 * 添加Cookie
 *
 * @param
 * cname: cookie的名称
 * cvalue: cookie的值
 * exdays: cookie保存的时间
 *
 */
function setCookie(cname, cvalue, exdays) {
    var exdays = exdays ? exdays : 0;
    if (exdays > 0) {
        $.cookie(cname, cvalue, {
            path: '/',
            expires: exdays
        });
    } else {
        $.cookie(cname, cvalue, {
            path: '/'
        });
    }
}

/*
 * 读取Cookie
 *
 * @param
 * cname: cookie的名称
 *
 * @return
 * 返回cookie的值
 *
 */
function getCookie(cname) {
    var cvalue = $.cookie(cname);
    return cvalue;
}

/*
 * 检验Cookie
 *
 * @param
 * cname: cookie的名称
 *
 * @return
 * 返回true或false
 *
 */
function checkCookie(cname) {
    var cvalue = getCookie(cname);
    if (cvalue && cvalue != "") {
        return true;
    } else {
        return false;
    }
}

/*
 * 删除Cookie
 *
 * @param
 * cname: cookie的名称
 *
 * @return
 * 返回true或false
 *
 */
function delCookie(cname) {
    $.cookie(cname, null, {
        path: '/',
        expires: -1
    });
    if (checkCookie(cname)) {
        console.info("删除Cookie成功: " + cname);
        return true;
    } else {
        console.info("删除Cookie失败: " + cname);
        return false;
    }
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
            title + '</div><a id="mb_ico"></a><img id="mb_img" src="../image/gou1.png"/><div id="mb_msg">' +
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
                backgroundImage: 'url(../image/close.png)',
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
                if (typeof(callback) == 'function') {
                    callback();
                }
                // window.history.back(-1);
            });
        }
        //取消按钮事件
    var btnNo = function() {
        $("#mb_btn_no,#mb_ico").click(function() {
            $("#mb_box,#mb_con").remove();
        });
    }
})();

Date.prototype.Format = function(fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
