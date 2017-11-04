$(function() {
    var wechat_id = getItem('wechat_id');
    var headimg = getItem('headimg');
    $('.weui-btn-area a').on('click', function() {
        generateReport();
    });

    $('header').on('click', '.top-left', function() {
        window.location.href='healthService.html?wechat_id='+wechat_id+'&headimg='+headimg;
    });

    // 生成风险报告
    function generateReport() {
        $.ajax({
            url: 'http://mrxiej.ngrok.wendal.cn/api-wechat/healthmanage/report/generate',
            type: 'POST',
            timeout: 5000,
            data: {
                wechat_id: wechat_id
            },
            beforeSend: function() {
                $.showLoading("正在生成风险评估报告,请稍候");
            },
            success: function(result, status, xhr) {
                if (result.errorcode != '0') {
                    if(result.errorcode=='1') {
                        $.alert('生成失败，请稍后重试');
                    }else if(result.errorcode=='2'){
                        $.alert('风险评估报告每三个月才能评估一次');
                    }else if(result.errorcode=='3'){
                        $.alert('请完善您的各项检查记录后再进行评估');
                    }else if(result.errorcode=='4'){
                        $.alert('抱歉，风险评估算法仅适用于35岁及以上的中老年人');
                    }
                } else {
                    setTimeout($.alert('成功生成您的风险评估报告',function () {
                        window.location.href = 'riskEvaluate_report.html?count=new';
                    }),0);
                }
            },
            error: function(xhr, status, error) {
                alert('网络慢，生成失败，请稍候重试');
            },
            complete: function(xhr, status) {
                $.hideLoading();
            }
        });
    }
});
