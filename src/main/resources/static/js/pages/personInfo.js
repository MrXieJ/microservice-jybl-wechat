$(function() {
    var wechat_id = getUrlParam('appid');
    var headimg = getUrlParam('headimg');
    if (headimg) {
        $('#portait').attr('src', headimg);
    }

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
                     window.location.href = 'msg_error.html';
            } else {
                var presult=result.data;
                $('#name').val(presult.name);
                $('#identify').val(presult.id_card);
                $('#sex').val(presult.sex);
                $('#age').val(presult.age);
                $('#telephone').val(presult.phone);
                $('#address').val(presult.address);
                $('#detail').val(presult.detailed_address);
            }
        },
        error: function(xhr, status, error) {
            $.alert("网络不给力",function () {
                javascript:WeixinJSBridge.call('closeWindow');
            })
        },
        complete: function(status, xhr) {
            $.hideLoading();
        }
    });

    // 修改
    $('#modify').on('click', function() {
        var para = '?appid=' + wechat_id + '&headimg=' + headimg;
        window.location.href = 'personSave.html' + para;
    });
});
