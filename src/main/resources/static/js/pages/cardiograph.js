$(function() {
    var wechat_id = getItem('wechat_id');
    // console.info('wechat_id = ' + wechat_id);

    // 时间选择
    $("#measureTime").datetimePicker({
        title: '请选择测量时间',
        times: function() {
            return null;
        },
        onChange: function(picker, values, displayValues) {
            // console.log(values);
        }
    });

    // 输入字数显示
    $('#remark').on('input',function(){
      var count = $(this).val().length;
      $('#count').html(count);
    })

    // 保存数据
    $('#submitBtn').on('click', function() {
        var diograph = getDiograph();
        if (diograph) {
            $.showLoading();
            uploadDiograph(diograph)
        } else {
            $.alert('请上传图片以及填写时间！')
        }
    });

    // 获取心电图数据
        function getDiograph() {
            var diogram = "暂时没有获取图片";
            var date = $('#measureTime').val();
            var remark = $('#remark').val();
            if (diogram && date) {
                return {
                    diogram: diogram,
                    date: date,
                    remark: remark
                }
            } else {
                return null;
            }
        }

    // 上传心电图
    function uploadDiograph(diograph){
        // 暂时没有实现

        var file = $('#uploaderInput')[0].files[0];

        var ext = /\.[^\.]+/.exec($('#uploaderInput').val());

        var storeAs = 'diograph/' + new Date().getTime() + ext;

        OSS.urllib.request("http://www.jiayibilin.com/api-stsserver/requestSTS",
            {method: 'GET'},
            function (err, response) {
                if (err) {
                    return alert("上传失败");
                }

                try {
                    result = JSON.parse(response);

                    var client = new OSS.Wrapper({
                        accessKeyId: result.AccessKeyId,
                        accessKeySecret: result.AccessKeySecret,
                        stsToken: result.SecurityToken,
                        endpoint: 'oss-cn-shenzhen.aliyuncs.com',
                        bucket: 'jybl-photo'
                    });
                } catch (e) {
                    $.hideLoading();
                    $.alert('上传图片异常');
                }

                client.multipartUpload(storeAs, file).then(function (result) {
                    diograph.diogram = result.res.requestUrls[0].split('?')[0];
                    saveDiograph(diograph, client, storeAs);
                }).catch(function (err) {
                    $.hideLoading();
                    $.alert('上传图片异常');
                });
            })

    }

    // 保存心电图数据
    function saveDiograph(diograph, client, storeAs) {
        $.ajax({
            url: 'http://www.jiayibilin.com/api-wechat/healthmanage/cardiogram/save',
            type: 'POST',
            timeout: 5000,
            data: {
                wechat_id: wechat_id,
                cardiogram: diograph.diogram,
                date: diograph.date,
                remark: diograph.remark
            },
            success: function(result, status, xhr) {
                if (!result || result.errorcode != '0') {
                    client.delete(storeAs);
                    $.alert('保存失败');
                } else {
                    $.alert('上传保存成功！', function(){
                        window.history.back();
                    });
                }
            },
            error: function(xhr, status, error) {
                client.delete(storeAs);
                $.alert('网络不通畅');
            },
            complete: function(xhr, status) {
                $.hideLoading();
            }
        });
    }
});
