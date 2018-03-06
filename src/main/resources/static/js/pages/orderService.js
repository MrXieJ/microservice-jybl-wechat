$(function() {
    var wechat_id = getItem('wechat_id');

    $('.weui-tab__bd').on('click', '.button_sp_area .weui-btn', function() {
        var $service = $(this).closest('.weui-media-box');
        var $id = $service.children().first();
        var $serviceName = $service.find('.service-title .service-name');
        var $servicePrice = $service.find('.service-title .service-price');
        var $serviceTime = $service.find('.service-content .service-time');
        var $serviceTotalTimes = $service.find('.service-content .service-total-times');
        var $serviceRemainTimes = $service.find('.service-content .service-remain-times');
        var id = $id.attr('data-id');
        console.info(id + ' ' + $serviceName.html() + ' ' + $servicePrice.html() + ' ' + $serviceTime.html() +
            ' ' + $serviceTotalTimes.html() + ' ' + $serviceRemainTimes.html());
        $.confirm({
            title: '购买',
            text: '是否要再次购买该服务？',
            onOK: function() {
                buyServiceagain(id);
            },
            onCancel: function() {
                //点击取消后的回调函数
            }
        });
    });

    getBoughtServices();

    // 获取已购买的服务包
    function getBoughtServices() {
        $.ajax({
            url: 'http://www.jiayibilin.com/api-wechat/patientinfo/service/get',
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
                if (!result || result.errorcode != '0') {
                    $.alert('加载失败',function () {
                        window.history.back();
                    })
                } else if(data.length==0) {
                    $.alert({
                        title: '提示',
                        text: '您尚未购买任何服务.',
                        onOK: function () {
                            window.history.back();
                        }
                    });
                }else{
                    parseBoughtServices(data);
                }
            },
            error: function(xhr, status, error) {
                $.alert('网络不给力',function () {
                    window.history.back();
                })
            },
            complete: function(xhr, status) {
                $.hideLoading();
            }
        });
    }

    // 解析服务数据
    function parseBoughtServices(services) {
        if (services && services.length > 0) {
            $.each(services, function(index, service) {
                var time = "";
                var duration = parseInt(service.duration);
                var remain = parseInt(service.left_count);
                var purchaseTime = service.purchased_time;
                var strTime = purchaseTime.toString();
                strTime = strTime.substr(0,10);
                var timearray =  strTime.split('-');
                var now= new Date(timearray[0], timearray[1]-1, timearray[2]);
                time += now.Format("yyyy-MM-dd") + ' 至 ';
                var expireDay = new Date(now.getTime()+duration*24*60*60*1000);
                time += expireDay.Format("yyyy-MM-dd");
                service.time = time;
                addOrderService('allOrderServices', service);
                if (service.indent_status== 1) {
                    addOrderService('inUseOrderService', service);
                } else if(service.indent_status== 99 || service.indent_status== 2){
                    addOrderService('completedOrderdService', service);
                }
            });
        }

        $('.service-remain-times').each(function() {
            var $parent = $(this).closest('.service-content');
            var $total = $parent.find('.service-total-times');
            var $remain = $parent.find('.service-remain-times').parent('p');
            var $btn = $parent.find('.button_sp_area');
            var total = parseInt($total.html());
            var remain = parseInt($(this).html());
            if (remain == 0) {
                $remain.addClass('text-red');
                $btn.removeClass('unvisible');
            } else if (total == remain) {
                $remain.addClass('text-green');
            } else if (total > remain) {
                $remain.addClass('text-blue');
            }
        })
    }

    //重复购买服务
    function buyServiceagain(serviceId) {
        $.ajax({
            url: 'http://www.jiayibilin.com/api-wechat/patientinfo/service/buyagain',
            type: 'POST',
            timeout: 5000,
            data: {
                wechat_id: wechat_id,
                serviceid: serviceId
            },
            beforeSend: function() {
                $.showLoading('请稍等');
            },
            success: function(result, status, xhr) {
                if (!result || result.errorcode != '0') {
                    $.alert('加载失败',function () {
                        window.history.back();
                    })
                } else {
                    $.alert({
                        title: '操作成功',
                        text: '您已成功再次购买该服务，可以重新进入页面查看服务信息.',
                        onOK: function () {
                            window.history.back();
                        }
                    });
                }
            },
            error: function(xhr, status, error) {
                $.alert('网络不给力',function () {
                    window.history.back();
                })
            },
            complete: function(xhr, status) {
                $.hideLoading();
            }
        });
    }

    // 添加服务
    function addOrderService(id, service) {
        var str = '<div class="weui-media-box weui-media-box_text">' +
            '<p hidden="hidden" data-id="' + service.serviceid + '"></p>' +
            '<div class="flex-r service-title">' +
            '<div class="title-left">' +
            '<p class="title-ellipsis service-name">' + service.name +
            '</p>' +
            '</div>' +
            '<div class="title-right">' +
            '<p class="text-red text-right text-ellipsis">' +
            '<span class="service-price">' + service.price + '</span>元' +
            '</p>' +
            '</div>' +
            '</div>' +
            '<p class="weui-media-box__desc service-content">' +
            '服务期: <span class="service-time">' + service.time + '</span>' +
            '</p>' +
            '<div class="flex-r flex-align-center flex-justify-between service-content">' +
            '<div class="flex-r">' +
            '<p>总次数: <span class="service-total-times">' + service.sum_count + '</span>次</p>' +
            '<p>' +
            '剩余次数: <span class="service-remain-times">' + service.left_count + '</span>次' +
            '</p>' +
            '</div>' +
            '<div class="button_sp_area flex-r flex-align-center unvisible">' +
            '<a href="javascript:;" class="weui-btn btn-default-mini btn-default-color">' +
            '再次购买' +
            '</a>' +
            '</div>' +
            '</div>' +
            '</div>';
        $('#' + id).append(str);
    }

});
