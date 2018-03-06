$(function() {
    var wechat_id = getItem('wechat_id');

    // 测量时间
    var selectdate;
    var selecttime;
    var max = new Date().Format("yyyy-MM-dd");
    var now = max + ' 晨起';
    selectdate=max;
    selecttime=now;
    $('#measureTime').datetimePicker({
        title: '请选择测量时间',
        times: function() {
            return [{
                values: ['晨起', '睡前']
            }];
        },
        value: now,
        max: max,
        onChange: function(picker, values, displayValues) {
            selectdate=values[0]+'-'+values[1]+'-'+values[2];
            selecttime = values[3];
        }
    });

    // 展开收缩
    var winH = $(window).height();
    var categorySpace = 10;
    $('.js_category').on('click', function() {
        var $this = $(this),
            $inner = $this.next('.js_categoryInner'),
            $page = $this.parents('.page'),
            $parent = $(this).parent('li');
        var innerH = $inner.data('height');
        bear = $page;

        if (!innerH) {
            $inner.css('height', 'auto');
            innerH = $inner.height();
            $inner.removeAttr('style');
            $inner.data('height', innerH);
        }

        if ($parent.hasClass('js_show')) {
            $parent.removeClass('js_show');
            $this.find('.unfold').html('展开').removeClass('unfold').addClass('fold');
        } else {
            // 列表组互斥
            // $parent.siblings().removeClass('js_show');

            $parent.addClass('js_show');
            if (this.offsetTop + this.offsetHeight + innerH > $page.scrollTop() + winH) {
                var scrollTop = this.offsetTop + this.offsetHeight + innerH - winH + categorySpace;

                if (scrollTop > this.offsetTop) {
                    scrollTop = this.offsetTop - categorySpace;
                }

                $page.scrollTop(scrollTop);
            }
            $this.find('.fold').html('收起').removeClass('fold').addClass('unfold');
        }
    });

    $('#submitBtn').on('click', function() {
        var record1 = getRecord('firstMeasure');
        var record2 = getRecord('secondMeasure');
        var record3 = getRecord('threeMeasure');
        if (record1 && record2 && record3) {
            $.confirm({
                title: '保存',
                text: '确定要保存数据吗？',
                onOK: function() {
                    var systolic_mid=(parseInt(record1.systolic_pressure)+parseInt(record2.systolic_pressure)+parseInt(record3.systolic_pressure))/3;
                    var diastolic_mid=(parseInt(record1.diastolic_pressure)+parseInt(record2.diastolic_pressure)+parseInt(record3.diastolic_pressure))/3;
                    var restingHR_mid=(parseInt(record1.rhr)+parseInt(record2.rhr)+parseInt(record3.rhr))/3;
                    var info = {
                        sys_mid: systolic_mid,
                        dia_mid: diastolic_mid,
                        rest_mid: restingHR_mid,
                    };
                    saveheartrateRecord(info);
                },
                onCancel: function() {

                }
            });
        } else {
            $.alert("为了获得最准确的健康分析结果，请您将数据填写完整","医生提示");
        }
    });

    // 保存血压心率数据
    function saveheartrateRecord(info) {
        $.ajax({
            url: 'http://www.jiayibilin.com/api-wechat/healthmanage/bloodcheck/saveorupdate',
            type: 'POST',
            timeout: 5000,
            data: {
                wechat_id: wechat_id,
                date: selectdate,
                time: selecttime,
                systolic_pressure: info.sys_mid,
                diastolic_pressure: info.dia_mid,
                rhr: info.rest_mid,
            },
            beforeSend: function() {
                $.showLoading();
            },
            success: function(result, status, xhr) {
                if (!result || result.errorcode != '0') {
                    $.alert("保存失败，请稍后再试",function () {

                    });
                } else {
                    window.location.href = 'heartRateRecord.html';
                }
            },
            error: function(xhr, status, error) {
                $.alert("请检查您的网络是否通畅");
            },
            complete: function(xhr, status) {
                $.hideLoading();
            }
        });
    }
    // 获取测量数据
    function getRecord(id) {
        var dateArr = $('#measureTime').val().split(" ");
        var date = dateArr[0];
        var time = dateArr[1];
        var systolic = $('#' + id).find('input.systolic_pressure').val();
        var diastolic = $('#' + id).find('input.diastolic_pressure').val();
        var restingHR = $('#' + id).find('input.restingHR').val();

        if (date && time && systolic && diastolic && restingHR) {
            var record = {
                date: date,
                time: time,
                systolic_pressure: systolic,
                diastolic_pressure: diastolic,
                rhr: restingHR
            };
            return record;
        } else {
            return null;
        }
    }
});
