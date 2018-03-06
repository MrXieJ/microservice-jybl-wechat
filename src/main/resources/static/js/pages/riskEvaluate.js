$(function() {
    var wechat_id = getItem('wechat_id');
    var headimg= getItem('headimg');

    getEvaluateNumber();
    getAllReports();

    $('header').on('click', '.top-left', function() {
        window.location.href = 'healthService.html?wechat_id='+wechat_id+'&headimg='+headimg;
    });

    // 获取评估人数
    function getEvaluateNumber() {
        $.ajax({
            url: 'http://www.jiayibilin.com/api-wechat/healthmanage/report/getnumber',
            type: 'GET',
            timeout: 5000,
            beforeSend: function() {
                $.showLoading();
            },
            success: function(result, status, xhr) {
                if (result.errorcode != '0') {
                    $.alert('数据加载失败，请重新进入',function () {
                        window.history.back();
                    });
                } else {
                    var data = result.data;
                    $('#totalNumber').html(data);
                }
            },
            error: function(xhr, status, error) {
                $.alert('网络慢',function () {
                    window.history.back();
                });
            },
            complete: function(xhr, status) {
                $.hideLoading();
            }
        });
    }

    // 获取所有评估报告
    function getAllReports() {
        $.ajax({
            url: 'http://www.jiayibilin.com/api-wechat/healthmanage/report/getall',
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
                    $.alert('数据加载失败，请重新进入',function () {
                        window.history.back();
                    });
                } else {
                    var data = result.data;
                    parseEvaluate(data);
                }
            },
            error: function(xhr, status, error) {
                $.alert('网络慢',function () {
                    window.history.back();
                });
            },
            complete: function(xhr, status) {
                $.hideLoading();
            }
        });
    }

    // 解析评估数据
    function parseEvaluate(evaluates) {
        if (evaluates && evaluates.length > 0) {
            $.each(evaluates, function(index, evaluate) {
                addEvaluate(evaluate);
            });
        }
    }

    // 添加评估列表
    function addEvaluate(evaluate) {
        var str = '<a href="riskEvaluate_report.html?count=' + evaluate.count +
            '" class="weui-media-box weui-media-box_appmsg">' +
            '<div class="weui-media-box__hd">' +
            '<img class="weui-media-box__thumb" src="../image/chart-icon.png" alt="" />' +
            '</div>' +
            '<div class="weui-media-box__bd">' +
            '<h4 class="weui-media-box__title">' +"第"+evaluate.count+"次风险评估报告" + '</h4>' +
            '<p class="text-default">' + evaluate.time + '</p>' +
            '</div>' +
            '<div class="box-icon-right">' +
            '<img src="../image/Shape-1.png" alt="" />' +
            '</div>' +
            '</a>';
        $('#evaluateList').append(str);
    }

});
