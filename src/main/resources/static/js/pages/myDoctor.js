$(function() {
    var wechat_id = getItem('wechat_id');
    var headimg = getItem('headimg');
    var appId='';
    var timestamp='';
    var nonceStr='';
    var signature='';
    $(function() {
        $('header').on('click', '.top-left', function() {
            // window.location.href="healthService.html?wechat_id="+wechat_id+"&headimg="+headimg;
            window.history.go(-1);
        });
    });
    getMyDoctorInfo();
    getAllServices();

    // 获取患者关注的医生信息
    function getMyDoctorInfo() {
        $.ajax({
            url: 'http://mrxiej.ngrok.wendal.cn/api-wechat/patientinfo/doctor/get',
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
                    if(result.errorcode=='10006') {
                        $.alert({
                            title: '提示',
                            text: '您还未签约医生（扫描医生二维码购买医生服务即可签约）.',
                            onOK: function () {
                                window.history.back();
                            }
                        });
                    }
                } else {
                    var data = result.data;
                    if (data) {
                        if(data.head_pic!=null){
                            $('#portait').attr('src', data.head_pic);
                        }
                        setItem('phone',data.phone);
                        $('#personName').html(data.name);
                        $('#personDepartment').html(data.department);
                        $('#personPost').html(data.title);
                        $('#personHospital').html(data.hospital);
                        $('#personAdept').html(data.adept);
                        $('#personExperience').html(data.experience);
                        $('#a_2evaluate').attr('href', encodeURI('evaluateDoc.html?phone=' + data.phone + '&name=' + encodeURI(data.name) + '&avatar=' + encodeURI(data.head_pic)));
                        $('#share').attr('href',encodeURI( 'share.html?qrcode='+data.qrcode_pic+'&name='+data.name));

                    }
                }
            },
            error: function(xhr, status, error) {
                $.alert('请检查网络是否通畅',function () {
                    window.history.back();
                });
            },
            complete: function(xhr, status) {
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
                    $.alert('加载失败，请重新进入',function () {
                        window.history.back();
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
                $.alert("请检查网络是否通畅",function () {
                    window.history.back();
                });
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
                if (result.errorcode != '0') {
                    $.alert('加载失败,请重新进入',function () {
                        window.history.back();
                    });
                } else {
                    var data = result.data;
                    if (data && data.length > 0) {
                        $.each(data, function(index, service) {
                            if(service.indent_status==1){
                            var $this = $('p[data-id="' + service.service_id + '"]');
                            if ($this) {
                                var $parent = $this.closest('.weui-media-box');
                                var $check = $parent.find('.title-left input[type="checkbox"]');
                                $check.attr('checked', 'true').attr('disabled', 'true');
                                $parent.find('.title-right .service-price').html('已购买');
                                $parent.find('.title-right .fold').addClass('unvisible');
                            }
                            }
                        });
                    }
                }
            },
            error: function(xhr, status, error) {
                $.alert("请检查网络是否通畅",function () {
                    window.history.back();
                });
            },
            complete: function(xhr, status) {
                $.hideLoading();
            }
        });
    }
   //判断过期
    function comptime(beginTime,duration) {
        beginTime = beginTime.substring(0,beginTime.length-2);
        var endTime = getNowFormatDate();
        var a = (Date.parse(endTime) - Date.parse(beginTime));
        var durationTime = duration*24*3600*1000;
        a = a-durationTime;
        if (a < 0) {
            return 0;
        } else  {
            return 1;
        }
    }

    function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var seperator2 = ":";
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
        return currentdate;
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
                console.info(index + ' : ' + id)
                serviceIdList.push(id);
            });
            console.info(serviceIdList.toString());
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
