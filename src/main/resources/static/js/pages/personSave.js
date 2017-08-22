$(function() {
    var openid = getUrlParam('appid');
    var headimg = getUrlParam('headimg');
    $('#portait').attr('src', headimg);
    var isUpdate = false;
    var saveUrl = 'http://mrxiej.ngrok.wendal.cn/api-wechat/patientinfo/save';
    var updateUrl = 'http://mrxiej.ngrok.wendal.cn/api-wechat/patientinfo/update';

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
            if (!result || result.errorcode != "0") {
                isUpdate = false;
            } else {
                isUpdate = true;
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

    // 阻止键盘弹出
    $('#sex').on('click', function(event) {
        document.activeElement.blur();
    });

    $("#sex").picker({
        title: "请选择您的性别",
        cols: [{
            textAlign: 'center',
            values: ['男', '女']
        }]
    });

    // 阻止键盘弹出
    $('#address').on('click', function(event) {
        document.activeElement.blur();
    });

    $('#address').cityPicker({
        title: "选择联系地址",
        showDistrict: true,
        onChange: function(picker, values, displayValues) {
            // console.log(values, displayValues);
        }
    });

    $('#submitBtn').on('click', function() {
        $.confirm({
            title: '保存',
            text: '确定要保存修改吗？',
            onOK: function() {
                var $name = $('#name');
                var $identify = $('#identify');
                var $sex = $('#sex');
                var $age = $('#age');
                var $telephone = $('#telephone');
                var $address = $('#address');
                var $detail = $('#detail');

                var person = {
                  wechat_id: openid,
                  name: $name.val(),
                  id_card: $identify.val(),
                  sex: $sex.val(),
                  age: $age.val(),
                  phone: $telephone.val(),
                  address: $address.val(),
                  detailed_address: $detail.val(),
                  headpic: headimg
                };

                if(isUpdate){
                  savePerson(updateUrl, person, function(){});
                }else{
                  savePerson(saveUrl, person, function(){});
                }
            },
            onCancel: function() {
                //点击取消后的回调函数
            }
        });
    });

    /**
    * 保存数据
    * @para
    * url: 保存路径
    * person: 保存对象
    * calback: 回调函数
    *
    */
    function savePerson(url, person, calback) {
        $.ajax({
            url: url,
            type: 'POST',
            data: {
                wechat_id: person.wechat_id,
                name: person.name,
                id_card: person.id_card,
                sex: person.sex,
                age: person.age,
                phone: person.phone,
                address: person.address,
                detailed_address: person.detailed_address,
                headpic: person.headpic
            },
            beforeSend: function() {
                $.showLoading('请稍候...');
            },
            success: function(result, status, xhr) {
                if (result.errorcode == "0") {
                  window.location.href = 'msg_success.html';
                }else{
                  alert('error');
                }
            },
            complete: function(status, xhr) {
                console.info('complete');
                $.hideLoading();
            }
        });
    }
});
