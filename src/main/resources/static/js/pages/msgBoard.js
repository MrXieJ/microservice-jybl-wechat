
// var strcookie = document.cookie;
// console.log(strcookie);
// var arrcookie = strcookie.split("=");
// var statuscookie = arrcookie[1];
// if(statuscookie == "" || statuscookie == "0"){
//     //retset flag
//     document.cookie="statuscookie1=1";
// }else{
//     window.location.reload();
//     document.cookie="statuscookie1=0";
// }

$(function () {
var  wechat_id = getItem('wechat_id');
var phone=getItem("phone");
    getPatientInfo();


//获取患者信息
function getPatientInfo() {
    $.ajax({
        url:'http://mrxiej.ngrok.wendal.cn/api-wechat/patientinfo/get',
        type: 'GET',
        timeout: 5000,
        data: {
            wechat_id: wechat_id
        },
        success: function(result, status, xhr) {
            if (!result || result.errorcode != '0' || !result.data) {
                if(result.errorcode=='10003') {
                    $.alert({
                        title: '提示',
                        text: '获取患者信息时，所查询的患者不存在',
                        onOK: function () {
                            window.history.back();
                        }
                    });
                }
            } else if(result.errorcode=='10004') {
                $.alert({
                    title: '提示',
                    text: '获取患者信息失败',
                    onOK: function () {
                        window.history.back();
                    }
                });
            } else{
                var data = result.data;
                if (data) {
                    if(data.name!=null) {
                        name=data.name;
                        sex=data.sex;
                        age=data.age;
                        $('.patient-info').html('就诊人:' + data.name);
                    }
                }
            }
        },
        error: function(xhr, status, error) {
            $.alert('请检查网络是否通畅',function () {
                window.history.back();
            });
        }
        // complete: function(xhr, status) {
        //     $.hideLoading();
        // }
    });
}


// 上传留言板图片
$('.submit-btn').on('click', function(){

    var file = $('#uploaderInput')[0].files[0];
 console.log(file);
    if(file!=undefined){
        var ext = /\.[^\.]+/.exec($('#uploaderInput').val());

        var storeAs = 'messageboard/' + new Date().getTime() + ext;

        OSS.urllib.request("http://125.216.243.114:2004/requestSTS",
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
                    $.alert({
                        title: '提示',
                        text: '上传图片出现异常',
                        onOK: function () {
                            window.history.back();
                        }
                    });
                }
                client.multipartUpload(storeAs, file).then(function (result) {
                    var picture = result.res.requestUrls[0].split('?')[0];
                    saveMessageBoard(picture, client, storeAs);
                }).catch(function (err) {
                    $.hideLoading();
                    $.alert({
                        title: '提示',
                        text: '上传数据出现异常',
                        onOK: function () {
                            window.history.back();
                        }
                    });
                });
            })
    }else{

            var da = {
                "wechat_id":wechat_id,
                "phone":phone,
                "sender":0,
                "content":$('#text_area').val()
            }

            $.ajax({
                url:'http://mrxiej.ngrok.wendal.cn/api-wechat/patientinfo/messageboard/set',
                type: 'POST',
                timeout: 5000,
                contentType: 'application/json;charset=utf-8',
                data: JSON.stringify(da),
                beforeSend: function() {
                    $.showLoading();
                },
                success: function(result, status, xhr) {
                    if (!result || result.errorcode != '0') {
                        $.alert('留言失败');
                    }else{
                        $.hideLoading();
                        $.alert({
                            title: '提示',
                            text: '留言成功',
                            onOK: function () {
                                window.history.back();
                            }
                        });

                    }
                },
                error: function(xhr, status, error) {
                    $.alert('网络不通畅');
                },
                complete: function(xhr, status) {
                    $.hideLoading();
                }
            });

    }


});

//上传留言板信息
function saveMessageBoard(picture, client, storeAs) {

    var da = {
        "wechat_id":wechat_id,
        "phone":phone,
        "sender":0,
        "content":$('#text_area').val(),
        "picture":picture
    }

    $.ajax({
        url:'http://mrxiej.ngrok.wendal.cn/api-wechat/patientinfo/messageboard/set',
        type: 'POST',
        timeout: 5000,
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify(da),
        beforeSend: function() {
            $.showLoading();
        },
        success: function(result, status, xhr) {
            if (!result || result.errorcode != '0') {
                client.delete(storeAs);
                $.alert('保存图片失败');
            }else{
                $.hideLoading();
                $.alert({
                    title: '提示',
                    text: '留言成功',
                    onOK: function () {
                        window.history.back();
                    }
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

$('header').on('click', '.top-left', function() {
    window.location.href=document.referrer;
});

// 缩略图预览
// document.querySelector('#uploaderCustomFiles').addEventListener('click', function(e){
//     var target = e.target;
//
//     while(!target.classList.contains('weui-uploader__file') && target){
//         target = target.parentNode;
//     }
//     if(!target) return;
//
//     var url = target.getAttribute('style') || '';
//     var id = target.getAttribute('data-id');
//
//     if(url){
//         url = url.match(/url\((.*?)\)/)[1].replace(/"/g, '');
//     }
//     var gallery = weui.gallery(url, {
//         onDelete: function(){
//             weui.confirm('确定删除该图片？', function(){
//                 var index;
//                 for (var i = 0, len = uploadCustomFileList.length; i < len; ++i) {
//                     var file = uploadCustomFileList[i];
//                     if(file.id == id){
//                         index = i;
//                         break;
//                     }
//                 }
//                 if(index !== undefined) uploadCustomFileList.splice(index, 1);
//
//                 target.remove();
//                 gallery.hide();
//             });
//         }
//     });
// });
});