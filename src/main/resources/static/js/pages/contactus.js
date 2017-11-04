$(function() {
    var wechat_id = getUrlParam('wechat_id');
    $('#suggestsub').on('click', function() {
        $.confirm({
            title: '保存',
            text: '感谢您的宝贵意见，确定提交吗？',
            onOK: function() {
                var $suggesttion = $('#suggesttion');
                if ($suggesttion.val()!='') {
                    var suggest = {
                        wechat_id: wechat_id,
                        content: $suggesttion.val(),};
                    suggestsub(suggest, function () {
                    });
                }
                else{
                    $.alert("请填写您的建议内容后再提交");
                }


            },
            onCancel: function() {
                //点击取消后的回调函数
            }
        });
    });

    /**
     * 提交建议
     * @para
     * url: 保存路径
     * person: 保存对象
     * calback: 回调函数
     *
     */
    function suggestsub(suggest, calback) {
        $.ajax({
            url: 'http://mrxiej.ngrok.wendal.cn/api-wechat/patientinfo/contact/suggest',
            type: 'POST',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(suggest),
            success: function(result, status, xhr) {
                if (result.errorcode == "0") {
                    $.alert('我们已经收到您的建议，感谢您对我们工作的支持',function () {
                        $('#suggesttion').val('');
                    });
                }else if(result.errorcode=="1"){
                    $.alert('每天提的建议不能超过三次，感谢您对我们工作的支持');;
                }else{
                    $.alert('建议提交出现了点问题，请稍候再试')
                }
            },
            error: function (xhr, status, error) {
                setTimeout(function () {
                    $.alert("网络慢",function () {
                    })
                },0)
            },
            complete: function(status, xhr) {
                $.hideLoading();
            }
        });
    }

});
