// if( !checkCookie("msgDetailCookie") || getCookie("msgDetailCookie")==1 ) {
//     setCookie("msgDetailCookie",0,1);
// }else {
//     window.location.reload();
//     setCookie("msgDetailCookie",1,1);
// }


var pbt;

$(function () {
    var id = getUrlParam("id");
    setItem("message_id",id);
    setItem("healthService",true);

    //页面是否需要刷新
    var mesDetail = getItem("mesDetail");
    if(mesDetail){
        removeItem("mesDetail");
        location.reload();
    }
    //获取一个留言板及其回复
    $.ajax({
        url: 'http://mrxiej.ngrok.wendal.cn/api-wechat/patientinfo/messageboard/getone',
        type: 'GET',
        timeout: 5000,
        data: {
            id: id
        },
        // beforeSend: function() {
        //     $.showLoading();
        // },
        success: function(result, status, xhr) {
            var data = result.data;
            if (data && data.length > 0) {
                $.each(data, function (index, messageBoard) {
                    if(index==0){
                        addMessageBoard(messageBoard);
                    }else{
                        addMessageBoardReply(messageBoard);
                    }
                });
            }
        },
        error: function(xhr, status, error) {
            $.alert("请检查网络是否通畅",function () {
            })
        },
        // complete: function(status, xhr) {
        //
        //     $.hideLoading();
        // }
    });

    //添加留言第一个信息
    function addMessageBoard(messageBoard) {
        setItem("reply_id",messageBoard.id);
        setItem("phone",messageBoard.phone);
        var str = ' <div class="weui-flex title-area">'+
            '<div class="weui-flex__item weui-flex">'+
            '<div class="patient-name">'+messageBoard.name+'</div>'+
            '<div class="patient-sex-age">'+ messageBoard.sex+','+messageBoard.age+'岁'+'</div>'+
        '</div>'+
        '<div class="msg-time">'+messageBoard.datetime.split(".")[0]+'</div>'+
        '</div>'+
        '<div class="weui-flex text-area">'+
            '<div>描述:</div><div class="weui-flex__item description">'+messageBoard.content+'</div>'+
        '</div>';

        if(messageBoard.picture!=null && messageBoard.picture!="") {
           pbt=messageBoard.picture;
           str+='<div class="weui-flex img-area">'+
               '<div class="title">病情详细资料:</div>'+
               '<div id="img_container" class="weui-flex img-container">'+
               '<div class="weui-uploader__file"><img onclick="bigPicture(pbt);" src="' + messageBoard.picture + '"></div>' +
            '</div>'+
                '</div>';

        }

        $('.container').append(str);
    }





    //添加留言板回复信息
    function addMessageBoardReply(messageBoard) {
        var str = '<div class="weui-flex title-area">'+messageBoard.name+'回复</div>'+
            '<div class="reply-area">'+
            '<div class="weui-flex reply-info">'+
            '<div class="doc-avatar"><img style="border-radius: 50%" src="'+messageBoard.head_pic+'"></div>'+
            '<div class="weui-flex text-container">'+
            '<div class="doc-name">'+messageBoard.name+'</div>'+
            '<div class="reply-time">'+messageBoard.datetime.split(".")[0]+'</div>'+
        '</div>'+
        '</div>'+
        '<div class="reply-content">'+messageBoard.content+'</div>'+
        '</div>';
        $('.container').append(str);
    }

    //点击回复按钮
    $('.btn').on('click',function () {
        window.location.href="msgBoardReply.html";
    })

})