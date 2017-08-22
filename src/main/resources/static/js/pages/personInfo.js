$(function() {
    var openid = getUrlParam('appid');
    var headimg = getUrlParam('headimg');
    $('#portait').attr('src', headimg);
    
    $.ajax({
        url: 'http://mrxiej.ngrok.wendal.cn/api-wechat/patientinfo/get',
        type: 'GET',
        data: {
            wechat_id: openid
        },
        beforeSend: function() {
            $.showLoading();
        },
        success: function(result, status, xhr) {
            if (!result || result.errorcode!='0') {
                alert('data is null');
            } else {
                var data = result.data;
                $('#name').val(data.name);
                $('#identify').val(data.id_card);
                $('#sex').val(data.sex);
                $('#age').val(data.age);
                $('#telephone').val(data.phone);
                $('#address').val(data.address);
                $('#detail').val(data.detailed_address);
            }
            console.info('success');
        },
        complete: function(status, xhr) {
            console.info('complete');
            $.hideLoading();
        }
    });

    // 返回
    $('header').on('click', '.return-op', function() {
        window.history.back(-1);
    });

    // 修改
    $('#modify').on('click', function() {
        var para = '?appid=' + openid + '&headimg=' + headimg;
        window.location.href = 'personSave.html' + para;
    });
});
