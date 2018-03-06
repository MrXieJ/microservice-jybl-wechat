$(function() {
    var wechat_id = getUrlParam('wechat_id');
    var headimg = getUrlParam('headimg');

    // 保存在本地
    setItem('wechat_id', wechat_id);
    setItem('headimg', headimg);

    //页面是否需要刷新
    var healthService = getItem("healthService");
    if(healthService){
        removeItem("healthService");
        location.reload();
    }
     getUnreadMessage();
    // getMessageBoard();
    //判断是否填写个人信息
    $.ajax({
        url: 'http://www.jiayibilin.com/api-wechat/patientinfo/get',
        type: 'GET',
        timeout: 30000,
        data: {
            wechat_id: wechat_id
        },
        beforeSend: function() {
            $.showLoading();
        },
        success: function(result, status, xhr) {
            if (result.errorcode!="0") {
                if(result.errorcode=="10004"){
                    $.alert({
                        title: '提示',
                        text: '数据加载失败， 请稍后再试',
                        onOK: function () {
                            window.location.href = "javascript:WeixinJSBridge.call('closeWindow')";
                        }
                    });
                }else {
                    $.alert({
                        title: '提示',
                        text: '当前页面需要完善个人信息，请完善个人信息后访问。',
                        onOK: function () {
                            window.location.href = "javascript:WeixinJSBridge.call('closeWindow')";
                        }
                    });
                }
            }else{
            $('.circle').attr("src",getUrlParam("headimg"));
            }
        },
        error: function(xhr, status, error) {
            $.alert({
                title: '提示',
                text: '网络不给力',
                onOK: function () {
                    window.location.href = "javascript:WeixinJSBridge.call('closeWindow')";
                }
            });
        },
        complete: function(status, xhr) {
            $.hideLoading();
        }
    });

    // 右上角
    $('header').on('click', '.top-right img', function() {
        var para = '?appid=' + wechat_id + '&headimg=' + headimg;
        window.location.href = 'personInfo.html' + para;
    });

    // 轮播图
    var mySwiper = new Swiper('.swiper-container', {
        direction: 'horizontal',
        loop: true,
        height:document.body.clientWidth*0.8,
        autoplay: 3000,
        autoplayDisableOnInteraction: false,
        roundLengths: true,
        pagination: '.swiper-pagination',
    });


function getUnreadMessage() {
    var num=0;
    //获取未读医生群发消息数量
    $.ajax({
        url: 'http://www.jiayibilin.com/api-wechat/patientinfo/groupreceiving/unread',
        type: 'GET',
        timeout: 5000,
        data: {
            wechat_id: wechat_id
        },
        beforeSend: function() {
            $.showLoading();
        },
        success: function(result, status, xhr) {
            var data = result.data;
            if (data > 0) {
               num+=data;
            }
            //获取未读消息数量
            $.ajax({
                url: 'http://www.jiayibilin.com/api-wechat/healthmanage/messageremind/unread/getnumber',
                type: 'GET',
                timeout: 5000,
                data: {
                    wechat_id: wechat_id
                },
                beforeSend: function() {
                    $.showLoading();
                },
                success: function(result, status, xhr) {
                    var data = result.data;
                    if (data > 0) {
                        num+=data;
                    }
                    if (num > 0) {
                        $('.number-red-dot').html(num).removeClass('unvisible');
                    } else {
                        $('.number-red-dot').addClass('unvisible');
                    }
                },
                error: function(xhr, status, error) {
                    $.alert("请检查网络是否通畅",function () {
                    })
                },
                complete: function(status, xhr) {

                    $.hideLoading();
                }
            });

        },
        error: function(xhr, status, error) {
            $.alert("请检查网络是否通畅",function () {
            })
        },
        complete: function(status, xhr) {

            $.hideLoading();
        }
    });
}


    //获取医生回复
    $.ajax({
        url: 'http://www.jiayibilin.com/api-wechat/healthmanage/messageremind/unread/getnumber',
        type: 'GET',
        timeout: 5000,
        data: {
            wechat_id: wechat_id
        },
        beforeSend: function() {
            $.showLoading();
        },
        success: function(result, status, xhr) {
            var data = result.data;
            if (data > 0) {
                $('.number-red-dot').html(data).removeClass('unvisible');
            } else {
                $('.number-red-dot').addClass('unvisible');
            }
        },
        error: function(xhr, status, error) {
            $.alert("请检查网络是否通畅",function () {
            })
        },
        complete: function(status, xhr) {

            $.hideLoading();
        }
    });

    //获取最新留言板和回复
    function getMessageBoard() {
        $.ajax({
            url: 'http://www.jiayibilin.com/api-wechat/patientinfo/messageboard/getnewestmessagwe',
            type: 'GET',
            timeout: 5000,
            data: {
                wechat_id: wechat_id
            },
            beforeSend: function() {
                $.showLoading();
            },
            success: function(result, status, xhr) {
                var da = result.data;

                if (da && da.length > 0) {
                    $.each(da, function(index, messageBoard) {
                        addMessageBoard(messageBoard);
                    });
                } else{
                    var str = '<div class="weui-cell weui-cell_swiped nomessage">\n' +
                        '还没有任何留言\n' +
                        '</div>';
                    $('.weui-panel__bd').append(str);
                }

            },
            error: function(xhr, status, error) {
                $.alert("请检查网络是否通畅",function () {
                })
            },
            complete: function(status, xhr) {

                $.hideLoading();
            }
        });
    }
    $.ajax({
        url: 'http://www.jiayibilin.com/api-wechat/patientinfo/messageboard/getnewestmessagwe',
        type: 'GET',
        timeout: 5000,
        data: {
            wechat_id: wechat_id
        },
        beforeSend: function() {
            $.showLoading();
        },
        success: function(result, status, xhr) {
            var da = result.data;

            if (da && da.length > 0) {
                $.each(da, function(index, messageBoard) {
                    addMessageBoard(messageBoard);
                });
            } else{
                var str = '<div class="weui-cell weui-cell_swiped nomessage">\n' +
                    '还没有任何留言\n' +
                    '</div>';
                $('.weui-panel__bd').append(str);
            }

        },
        error: function(xhr, status, error) {
            $.alert("请检查网络是否通畅",function () {
            })
        },
        complete: function(status, xhr) {

            $.hideLoading();
        }
    });
//添加留言板
    function addMessageBoard(messageboard) {
        if(!messageboard){
            return;
        }
        var str =  '<div class="weui-cell weui-cell_swiped"  id="'+messageboard.id+'">'+
            '<a class="weui-cell__bd chat-wrapper" ontouchstart="return touchstartF(event,this);"  ontouchmove="return touchmoveF(event,this);" ontouchend="return touchendF(event,this,'+messageboard.id+');"  >'+
            '<div class="chat-box-head">'+
            '<img class="chat-head-img" style="border-radius: 50%" src="'+messageboard.head_pic+'" alt="" />';
        if(messageboard.sender==1 && messageboard.isread==0){
            str+= '<span class="red-dot"></span>';
        }
           str+= '</div>'+
            '<div class="chat-box-tail"  >'+
            '<div class="flex-r flex-align-center flex-justify-between">'+
            '<p class="chat-person-name" style="font-size: 18px">'+messageboard.name+'</p>'+
            '<p class="chat-latest-time">'+messageboard.datetime.split(".")[0]+'</p>'+
        '</div>'+
        '<div class="chat-message">'+
            messageboard.content+
    '</div>'+
        '</div>'+
        '</a>'+
        '<div class="weui-cell__ft" onclick="return toDelete(event,this,'+messageboard.id+');">'+
            '<a class="weui-swiped-btn weui-swiped-btn_warn" href="javascript:">删除</a>'+
            '</div>'+
            '</div>';
        $('.weui-panel__bd').append(str);
    }


});
