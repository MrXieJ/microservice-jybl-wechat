$(function() {
    var wechat_id = getItem('wechat_id');

    // 测量时间
    var max = new Date().Format("yyyy-MM-dd");
    $(".measure-time").datetimePicker({
        title: '请选择测量时间',
        times: function() {
            return null;
        },
        max: max,
        onChange: function(picker, values, displayValues) {
        }
    });

    // 保存数据
    $('#submitBtn').on('click', function() {
        var exam = getExam();
        if (exam) {
            saveBioExam(exam);
        } else {
            $.alert('请将所有内容填写完整！')
        }
    });

    // 保存数据
    function saveBioExam(exam) {
        $.ajax({
            url: 'http://www.jiayibilin.com/api-wechat/healthmanage/biologycheck/saveorupdate',
            type: 'POST',
            timeout: 5000,
            data: {
                wechat_id: wechat_id,
                tch_time: exam.tch_time,
                fbg_time: exam.fbg_time,
                tg_time: exam.tg_time,
                hdl_time: exam.hdl_time,
                ldl_time: exam.ldl_time,
                tch: exam.tch,
                fbg: exam.fbg,
                tg: exam.tg,
                hdl: exam.hdl,
                ldl: exam.ldl
            },
            beforeSend: function() {
                $.showLoading();
            },
            success: function(result, status, xhr) {
                if (!result || result.errorcode != '0') {
                    $.alert('保存失败,请稍后尝试保存');
                } else {
                    $.alert("保存成功", function() {
                        window.history.back();
                    });
                }
            },
            error: function(xhr, status, error) {
                $.alert('请检查网络连接是否通畅');
            },

        });
    }

    // 获取数据
    function getExam() {
        var chordate_time = $('#chordate').find('.measure-time').val();
        var chordate_data = $('#chordate').find('.measure-data').val();
        var fastingBloodSugar_time = $('#fastingBloodSugar').find('.measure-time').val();
        var fastingBloodSugar_data = $('#fastingBloodSugar').find('.measure-data').val();
        var triglyceride_time = $('#triglyceride').find('.measure-time').val();
        var triglyceride_data = $('#triglyceride').find('.measure-data').val();
        var highLipoprotein_time = $('#highLipoprotein').find('.measure-time').val();
        var highLipoprotein_data = $('#highLipoprotein').find('.measure-data').val();
        var lowLipoprotein_time = $('#lowLipoprotein').find('.measure-time').val();
        var lowLipoprotein_data = $('#lowLipoprotein').find('.measure-data').val();

        if (chordate_time && chordate_data && fastingBloodSugar_time && fastingBloodSugar_data &&
            triglyceride_time && triglyceride_data && highLipoprotein_time &&
            highLipoprotein_data && lowLipoprotein_time && lowLipoprotein_data) {
            var exam = {
                tch_time: chordate_time,
                fbg_time: fastingBloodSugar_time,
                tg_time: triglyceride_time,
                hdl_time: highLipoprotein_time,
                ldl_time: lowLipoprotein_time,
                tch: chordate_data,
                fbg: fastingBloodSugar_data,
                tg: triglyceride_data,
                hdl: highLipoprotein_data,
                ldl: lowLipoprotein_data
            }
            return exam;
        } else {
            return null;
        }
    }
});
