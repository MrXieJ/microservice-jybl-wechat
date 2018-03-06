$(function() {
    var wechat_id = getItem('wechat_id');
    var serviceIdList = getUrlParam('serviceIdList');
    var phone =getItem('phone');

    // 取消
    $('#cancel').on('click', function() {
        window.location.href = 'myDoctor.html';
    });

    //  确定
    $('#confirm').on('click', function() {
        var checkProcotol = $('#checkProcotol').is(':checked');
        if (checkProcotol) {
            $.ajax({
                url: 'http://www.jiayibilin.com/api-wechat/patientinfo/service/buy',
                type: 'POST',
                timeout: 5000,
                data: {
                    wechat_id: wechat_id,
                    servicelist: serviceIdList,
                    phone:phone
                },
                beforeSend: function() {
                    $.showLoading('请稍等');
                },
                success: function(result, status, xhr) {
                    if (result.errorcode != '0') {
                        if(result.errorcode=='1'){
                       $.alert('您已经有自己的私人医生，无法购买其他医生的服务', '提示',function () {
                            window.location.href = 'orderService.html';
                       });
                        }else if(result.errorcode=='2'){
                            $.alert('您还没有完善信息,请选择菜单栏个人服务完善个人信息后再购买服务', '提示',function () {
                                window.location.href = "javascript:WeixinJSBridge.call('closeWindow')";
                            });
                        }
                        else{
                        $.alert('购买失败，请稍候再试', '提示',function () {
                            window.location.href = 'orderService.html';
                        });
                        }
                    } else {
                        $.alert('购买成功', '提示',function () {
                            window.location.href = 'orderService.html';
                        });
					}

                },
                error: function(xhr, status, error) {
                    $.alert('网络慢', '提示',function () {
                        window.history.back();
                    });
                },
                complete: function(xhr, status) {
                    $.hideLoading();
                }
            });
        } else {
            $.alert('请您阅读并同意服务协议', '提示');
        }
    });

    getServiceOrder();


    // 获取服务订单
    function getServiceOrder() {
        $.ajax({
            url: 'http://www.jiayibilin.com/api-wechat/patientinfo/service/getbylist',
            type: 'GET',
            timeout: 5000,
            data: {
                servicelist: serviceIdList
            },
            beforeSend: function() {
                $.showLoading();
            },
            success: function(result, status, xhr) {
                if (!result || result.errorcode != '0') {
                    $.alert('加载失败，请重新进入',function () {
                        window.history.back();
                    });
                } else {
                    var data = result.data;
                    if (data && data.length > 0) {
                        var orders = 0;
                        var expense = 0;
                        $.each(data, function(index, service) {
                            addOrder(service);
                            orders++;
                            expense += parseInt(service.price);
                        });
                        $('#totalOrders').html(orders);
                        $('#totalExpense').html(expense);
                    }
                }
            },
            error: function(xhr, status, error) {
                $.alert('请检查您的网络是否通畅',function () {
                    window.history.back();
                })
            },
            complete: function(xhr, status) {
                $.hideLoading();
            }
        });
    }

    // 添加订单
    function addOrder(service) {
        var str = '<div class="weui-media-box weui-media-box_text">' +
            '<p hidden="hidden" data-id="' + service.id + '"></p>' +
            '<div class="flex-r service-title">' +
            '<div class="title-left">' +
            '<p class="title-ellipsis service-name">' + service.name + '</p>' +
            '</div>' +
            '<div class="title-right">' +
            '<p class="text-red text-right text-ellipsis">' +
            '<span class="service-price">' + service.price + '</span>元' +
            '</p>' +
            '</div>' +
            '</div>' +
            '<div class="flex-r service-content">' +
            '<p>总次数: <span class="service-count">' + service.count + '</span>次</p>' +
            '<p>' +
            '期限: <span class="service-duration">' + service.duration + '</span>天' +
            '</p>' +
            '</div>' +
            '<div class="flex-r service-content">' +
            '<p class="text-default">适用人群: <span class="service-kind">' +
            service.kind + '</span></p>' +
            '</div>' +
            '</div>';
        $('#orderList').append(str);
    }


});
