$(function() {
    var wechat_id = getItem('wechat_id');
    var id = getUrlParam('id');


    var $detail = $('.notification-detail');
    var $title = $detail.find('[name="detail-title"]');
    var $dateTime = $detail.find('[name="detail-time"]');
    var $target = $detail.find('[name="detail-target"]');
    var $remark = $detail.find('[name="detail-remark"]');
    var $circle = $detail.find('[name="detail-circle"]');

    getnotificationDetail(id);
    // 获取消息详细内容
    function getnotificationDetail(id) {
        $.ajax({
            url: 'http://mrxiej.ngrok.wendal.cn/api-wechat/healthmanage/messageremind/getbyid',
            type: 'GET',
            timeout: 5000,
            data: {
                message_id: id
            },
            beforeSend: function() {
                $.showLoading();
            },
            success: function(result, status, xhr) {
                if (result.errorcode != '0') {
                    $.alert('加载失败',function () {
                        window.history.back();
                    });
                } else {
                    var data = result.data;
                    addContent(data);
                }
            },
            error: function(xhr, status, error) {
            },
            complete: function(xhr, status) {
                $.hideLoading();
            }
        });
    }

    // 添加消息内容
    function addContent(notification) {
       // $('[name="detail-time"]').html(notification.time);
        var str = '<div class="content-item">' +
            '<p class="title">' +
            '标题：<span name="detail-title">' + notification.title + '</span>' +
            '</p>' +
            '<p class="target text-default">' +
            '目标：<span name="detail-target">' + notification.target + '</span>' +
            '</p>' +
            '<p class="remark">' +
            '备注：<span name="detail-remark">' + notification.remark + '</span>' +
            '</p>' +
            '<p class="circle">' +
            '周期：<span name="detail-circle">' + notification.period +'天'+ '</span>' +
            '</p>' +
            '</div>';
        $('#contentList').append(str);
    }

});
