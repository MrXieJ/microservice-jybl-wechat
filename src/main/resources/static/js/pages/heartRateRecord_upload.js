$(function() {
    var wechat_id = getItem('wechat_id');

    // 获取心率记录
    function getHeartRateRecord() {
        $.ajax({
            url: 'http://www.jiayibilin.com/api-wechat/healthmanage/bloodcheck/get',
            type: 'GET',
            timeout: 5000,
            data: {
                wechat_id: wechat_id,
                timearea: 'week',
                time: 'morning'
            },
            beforeSend: function() {
                $.showLoading();
            },
            success: function(result, status, xhr) {
                if (!result || result.errorcode != '0') {
                    $.alert('数据加载失败,请重新进入',function () {
                        window.history.back();
                    });
                } else {
                    if (result.errorcode == '0') {
                        window.location.href = 'heartRate__record.html';
                    }
                }
            },
            error: function(xhr, status, error) {
                $.alert('请检查您的网络是否通畅',function () {
                    window.history.back();
                });
            },
            complete: function(xhr, status) {
                $.hideLoading();
            }
        });
    }

});
