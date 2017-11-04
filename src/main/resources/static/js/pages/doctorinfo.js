$(function() {
    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r != null) {
            return unescape(r[2]);
        }
        return null;
    }
    var phone = getUrlParam('phone');
    var wechat_id = getUrlParam('wechat_id');
    setItem('wechat_id', wechat_id);
    setItem('phone', phone);
    $.alert('想拥有您自己的私人医生需要购买医生提供的服务','提示');
    $(function() {
        $('header').on('click', '.top-left', function() {
            window.history.back();
        });
    });
    getDoctorInfo();
    getAllServices();
    // 获取医生信息
    function getDoctorInfo() {
        $.ajax({
            url: 'http://mrxiej.ngrok.wendal.cn/api-wechat/doctor/get',
            type: 'GET',
            timeout: 5000,
            data: {
                phone: phone
            },
            beforeSend: function() {
                $.showLoading();
            },
            success: function(result, status, xhr) {
                if (result.errorcode != '0') {
                        $.alert({
                            title: '提示',
                            text: '加载失败，请过一段时间再尝试，给您带来的不遍我们深感抱歉.',
                            onOK: function () {
                                javascript:WeixinJSBridge.call('closeWindow');
                            }
                        });

                } else {
                    var data = result.data;
                    if (data) {
                        if(data.head_pic!=null){
                            $('#portait').attr('src', data.head_pic);
                        }
                        $('#personName').html(data.name);
                        $('#personDepartment').html(data.department);
                        $('#personPost').html(data.title);
                        $('#personHospital').html(data.hospital);
                        $('#personAdept').html(data.adept);
                        $('#personExperience').html(data.experience);
                    }
                }
            },
            error: function(xhr, status, error) {
                $.alert('网络不通畅');
                javascript:WeixinJSBridge.call('closeWindow');
            },
            complete: function(xhr, status) {
                var result = getDoctor();
                var data = result.data;
                if (data) {
                    $('#personName').html(data.name);
                    $('#personDepartment').html(data.department);
                    $('#personPost').html(data.title);
                    $('#personHospital').html(data.hospital);
                    $('#personAdept').html(data.adept);
                    $('#personExperience').html(data.experience);
                }
                $.hideLoading();
            }
        });
    }

    // 加载服务包信息
    function getAllServices() {
        $.ajax({
            url: 'http://mrxiej.ngrok.wendal.cn/api-wechat/doctor/service/get',
            type: 'GET',
            timeout: 5000,
            beforeSend: function() {
                $.showLoading();
            },
            success: function(result, status, xhr) {
                if (!result || result.errorcode != '0' || !result.data) {
                    $.alert({
                        title: '提示',
                        text: '加载失败，请过一段时间再尝试，给您带来的不遍我们深感抱歉.',
                        onOK: function () {
                            javascript:WeixinJSBridge.call('closeWindow');
                        }
                    });
                } else {
                    var data = result.data;
                    if (data && data.length > 0) {
                        $.each(data, function(index, service) {
                            addService(service);
                        });
                    }
                    getBoughtServices();
                }
            },
            error: function(xhr, status, error) {
                $.alert("请检查您的网络是否通畅");
                javascript:WeixinJSBridge.call('closeWindow');
            },
            complete: function(xhr, status) {
                $.hideLoading();
            }
        });
    }

    // 获取已购买的服务包
    function getBoughtServices() {
        $.ajax({
            url: 'http://mrxiej.ngrok.wendal.cn/api-wechat/patientinfo/service/get',
            type: 'GET',
            timeout: 5000,
            data: {
                wechat_id: wechat_id
            },
            beforeSend: function() {
                $.showLoading();
            },
            success: function(result, status, xhr) {
                if (!result || result.errorcode != '0' || !result.data) {
                    $.alert({
                        title: '提示',
                        text: '加载失败，请过一段时间再尝试，给您带来的不遍我们深感抱歉.',
                        onOK: function () {
                            javascript:WeixinJSBridge.call('closeWindow');
                        }
                    });
                } else {
                    var data = result.data;
                    if (data && data.length > 0) {
                        $.each(data, function(index, service) {
                            var $this = $('p[data-id="' + service.serviceid + '"]');
                            if ($this) {
                                var $parent = $this.closest('.weui-media-box');
                                var $check = $parent.find('.title-left input[type="checkbox"]');
                                $check.attr('checked', 'true').attr('disabled', 'true');
                                $parent.find('.title-right .service-price').html('已购买');
                                $parent.find('.title-right .fold').addClass('unvisible');
                            }
                        });
                    }
                }
            },
            error: function(xhr, status, error) {
                $.alert("请检查您的网络是否通畅");
                javascript:WeixinJSBridge.call('closeWindow');
            },
            complete: function(xhr, status) {
                $.hideLoading();
            }
        });
    }

    // 收缩展开
    $('#serviceList').on('click', '.fold,.unfold', function() {
        var $this = $(this);
        var $parent = $this.closest('.weui-media-box');
        if ($this.hasClass('fold')) {
            $this.removeClass('fold').addClass('unfold');
            $parent.find('.service-detail').removeClass('unvisible');
        } else {
            $this.removeClass('unfold').addClass('fold');
            $parent.find('.service-detail').addClass('unvisible');
        }
    });

    // 购买服务
    $('#purchase').on('click', function() {
        var $parent = $('#serviceList');
        var $checked = $parent.find('input:enabled:checked');
        var num = $checked.length;
        if (num) {
            var serviceIdList = [];
            $checked.each(function(index) {
                var $data = $(this).closest('.weui-media-box__bd');
                var $dataId = $data.children().first();
                var id = $dataId.attr('data-id');
                serviceIdList.push(id);
            });
            var para = '?serviceIdList=' + serviceIdList.toString();
            window.location.href = 'myDoctor_order.html' + para;
        } else {
            $.alert('您还没选择需要购买的服务！');
        }
    });

    // 添加服务
    function addService(service) {
        if (!service) return;
        var str = '<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">' +
            '<div class="weui-media-box__bd">' +
            '<p hidden="hidden" data-id="' + service.id + '"></p>' +
            '<div class="service-title">' +
            '<div class="title-left">' +
            '<div class="cs-circle-check title-ellipsis">' +
            '<input id="check' + service.id + '" class="check" type="checkbox" />' +
            '<label for="check' + service.id + '" class="">' +
            service.name + '</label>' +
            '</div>' +
            '</div>' +
            '<div class="flex-r title-right">' +
            '<p class="service-price text-red text-right text-ellipsis weui-flex__item">' +
            service.price + '元</p>' +
            '<div class="single-arrow-down fold">' +
            '<span class="arrow-down"></span>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '<div class="flex-r service-detail unvisible">' +
            '<p>总次数: <span class="service-count">' + service.count + '</span></p>' +
            '<p>期限: <span class="service-duration">' + service.duration + '个月</span>' +
            '</p>' +
            '</div>' +
            '<div class="flex-r service-detail unvisible">' +
            '<p class="text-default">适用人群: <span class="service-kind">' +
            service.kind + '</span></p>' +
            '</div>' +
            '</div>' +
            '</a>';
        $('#serviceList').append(str);
    }

});