$(function() {
    var wechat_id = getItem('wechat_id');

    $('header').on('click', '.top-right', function() {
        window.location.href = 'heartRateRecord_save.html';
    });

    var config = getConfig();
    var ctx = document.getElementById("myChart").getContext('2d');
    var myChart = new Chart(ctx, config);
    var date = 'month';
    var time = 'morning';

    // 查找患者是否有心率记录
    function findHeartRateRecord() {
        $.ajax({
            url: 'http://mrxiej.ngrok.wendal.cn/api-wechat/healthmanage/bloodcheck/find',
            type: 'GET',
            timeout: 5000,
            data: {
                wechat_id: wechat_id,
            },
            beforeSend: function() {
                $.showLoading();
            },
            success: function(result, status, xhr) {
                if (result.data=='nothing') {
                    window.location.href = 'heartRateRecord_upload.html';
                } else {
                    time = result.data;
                    if(time=='evening'){
                        $('#meassure-evening').removeClass('btn-color-gray');
                        $('#meassure-evening').siblings('a').addClass('btn-color-gray');
                    }
                    getHeartRateRecord(date, time);
                    $.hideLoading();
                }
            },
            error: function(xhr, status, error) {
                $.alert("请检查您的网络是否通畅",function () {
                    window.history.back();
                });
            },
            complete: function(xhr, status) {
                $.hideLoading();
            }
        });
    }
    findHeartRateRecord();

    $('#record-week').on('click', function() {
        if ($(this).hasClass('btn-color-gray')) {
            date = 'week';
            $(this).removeClass('btn-color-gray');
            $(this).siblings('a').addClass('btn-color-gray');
            getHeartRateRecord(date, time);
        }
    });

    $('#record-month').on('click', function() {
        if ($(this).hasClass('btn-color-gray')) {
            date = 'month';
            $(this).removeClass('btn-color-gray');
            $(this).siblings('a').addClass('btn-color-gray');
            getHeartRateRecord(date, time);
        }
    });

    $('#meassure-morning').on('click', function() {
        if ($(this).hasClass('btn-color-gray')) {
            time = 'morning';
            $(this).removeClass('btn-color-gray');
            $(this).siblings('a').addClass('btn-color-gray');
            getHeartRateRecord(date, time);
        }
    });

    $('#meassure-evening').on('click', function() {
        if ($(this).hasClass('btn-color-gray')) {
            time = 'evening';
            $(this).removeClass('btn-color-gray');
            $(this).siblings('a').addClass('btn-color-gray');
            getHeartRateRecord(date, time);
        }
    });

    // 获取心率记录
    function getHeartRateRecord(timearea, time) {
        $.ajax({
            url: 'http://mrxiej.ngrok.wendal.cn/api-wechat/healthmanage/bloodcheck/get',
            type: 'GET',
            timeout: 5000,
            data: {
                wechat_id: wechat_id,
                timearea: timearea,
                time: time
            },
            beforeSend: function() {
                $.showLoading();
            },
            success: function(result, status, xhr) {
                if (!result || result.errorcode != '0') {
                    $.alert("加载血压心率信息时出现了一点问题，请稍后再试", function() {
                        window.location.href='heartRateRecord.html';
                    });
                } else{
                    if(result.data=='') {
                        $.alert("没有找到相应的记录,可以前往上传数据", function() {
                            myChart.options.scales.xAxes[0].display = true;
                            myChart.data.labels = [];
                            myChart.data.datasets[0].data = [];
                            myChart.data.datasets[1].data = [];
                            var ss = new Date().getTime();
                            var length= 6;
                            if (timearea=='month'){
                                length = 29;
                            }
                            for(var i=0;i<length;i++){
                                ss=ss-86400000;
                                myChart.data.labels.push (ss);
                            }
                            myChart.update();
                            $('#normalBP').html(0);
                            $('#hidesideBP').html(0);
                            $('#maxHighBP').html(0);
                            $('#minLowBP').html(0);
                            $('#avgBP').html(0);
                            $('#maxBP').html(0);
                            $('#avgRate').html(0);
                            $.hideLoading();
                        });
                    }else {
                            var data = result.data;
                            parseRecord(data);
                            $.hideLoading();
                        }
                    }
            },
            error: function(xhr, status, error) {
                $.alert("网络慢",function () {
                    window.history.back();
                });
            },
            complete: function(xhr, status) {
                $.hideLoading();
            }
        });
    }

    function parseRecord(data) {
        if (data)  {
            myChart.options.scales.xAxes[0].display = true;
            myChart.data.labels = [];
            myChart.data.datasets[0].data = [];
            myChart.data.datasets[1].data = [];
            for(var i=0;i<data.length;i++){
                var dt = new Date(data[i].date).getTime();
                myChart.data.labels.push(dt);
                myChart.data.datasets[0].data.push(data[i].diastolic_pressure);
                myChart.data.datasets[1].data.push(data[i].systolic_pressure);
            }
            myChart.update();

            //填充底部数据
            var normalCount = 0;
            var highCount = 0;
            var averageHigh = 0;
            var averageLow = 0;
            var maxHigh = 0;
            var maxLow = 0;
            var averageRhr = 0;
            var len = data.length;
            for(var i=0;i<len;i++) {
                if (parseFloat(data[i].diastolic_pressure) < 90 && parseFloat(data[i].systolic_pressure) < 140) {
                    normalCount++;
                } else {
                    highCount++;
                };
                averageHigh=add(averageHigh,parseFloat(data[i].systolic_pressure));
                averageLow=add(averageLow,parseFloat(data[i].diastolic_pressure));
                averageRhr=add(averageRhr,parseFloat(data[i].rhr));
                if(parseFloat(data[i].systolic_pressure)>maxHigh){
                    maxHigh=parseFloat(data[i].systolic_pressure);
                };
                if(parseFloat(data[i].diastolic_pressure)>maxLow){
                    maxLow=parseFloat(data[i].diastolic_pressure);
                };
            }
            averageHigh=div(averageHigh,parseFloat(len)).toFixed(2);
            averageLow=div(averageLow,parseFloat(len)).toFixed(2);
            averageRhr=div(averageRhr,parseFloat(len)).toFixed(2);

            if(maxHigh>=140){
                $('.maxHighBP').removeClass('btn-default-danger').removeClass('btn-default-success');
                $('.maxHighBP').html('高').addClass('btn-default-danger');
            }else if(maxHigh>=120) {
                $('.maxHighBP').removeClass('btn-default-danger').removeClass('btn-default-success');
                $('.maxHighBP').html('偏高').addClass('btn-default-warning');
            }else {
                $('.maxHighBP').removeClass('btn-default-danger').removeClass('btn-default-success');
                $('.maxHighBP').html('正常').addClass('btn-default-success');
            }
            if(maxLow>=90){
                $('.minLowBP').removeClass('btn-default-danger').removeClass('btn-default-success');
                $('.minLowBP').html('高').addClass('btn-default-danger');
            }else if(maxLow>=80){
                $('.minLowBP').removeClass('btn-default-danger').removeClass('btn-default-success');
                $('.minLowBP').html('偏高').addClass('btn-default-warning');
            }else{
                $('.minLowBP').removeClass('btn-default-danger').removeClass('btn-default-success');
                $('.minLowBP').html('正常').addClass('btn-default-success');
            }
            $('#normalBP').html(normalCount);
            $('#hidesideBP').html(highCount);
            $('#maxHighBP').html(maxHigh);
            $('#minLowBP').html(maxLow);
            $('#avgBP').html(averageLow);
            $('#maxBP').html(averageHigh);
            $('#avgRate').html(averageRhr);
            if(averageRhr>120){
                $.alert('心率过快，请务必联系您的医生或到医院就医');
            }else if(averageRhr>=100){
                $.alert('心率过快，若身体不适请及时就医');
            }else if(averageRhr>=80){
                $.alert('心率未达标，请注意调整治疗');
            }else if(averageRhr<45){
                $.alert('心率缓慢，请务必联系您的医生或到医院就医');
            };

        }
    }

    //浮点运算
    function add(a, b) {
        var c, d, e;
        try {
            c = a.toString().split(".")[1].length;
        } catch (f) {
            c = 0;
        }
        try {
            d = b.toString().split(".")[1].length;
        } catch (f) {
            d = 0;
        }
        return e = Math.pow(10, Math.max(c, d)), (mul(a, e) + mul(b, e)) / e;
    }

    function sub(a, b) {
        var c, d, e;
        try {
            c = a.toString().split(".")[1].length;
        } catch (f) {
            c = 0;
        }
        try {
            d = b.toString().split(".")[1].length;
        } catch (f) {
            d = 0;
        }
        return e = Math.pow(10, Math.max(c, d)), (mul(a, e) - mul(b, e)) / e;
    }

    function mul(a, b) {
        var c = 0,
            d = a.toString(),
            e = b.toString();
        try {
            c += d.split(".")[1].length;
        } catch (f) {}
        try {
            c += e.split(".")[1].length;
        } catch (f) {}
        return Number(d.replace(".", "")) * Number(e.replace(".", "")) / Math.pow(10, c);
    }

    function div(a, b) {
        var c, d, e = 0,
            f = 0;
        try {
            e = a.toString().split(".")[1].length;
        } catch (g) {}
        try {
            f = b.toString().split(".")[1].length;
        } catch (g) {}
        return c = Number(a.toString().replace(".", "")), d = Number(b.toString().replace(".", "")), mul(c / d, Math.pow(10, f - e));
    }


    // 获取图表配置
    function getConfig() {
        var config = {
            type: 'line',
            data: {
                labels: null,
                datasets: [{
                    fill: false,
                    label: '收缩压',
                    data: null,
                    borderColor: 'rgba(54, 162, 235, 1)',
                    borderWidth: 1
                }, {
                    fill: false,
                    label: '舒张压',
                    data: null,
                    borderColor: 'rgba(255,99,132,1)',
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    xAxes: [{
                        display: false,
                        type: 'time',
                        time: {
                            unit: 'day',
                            displayFormats: {
                                day: 'l'
                            }
                        }
                    }],
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                },
                legend: {
                    display: true,
                    position: 'bottom'
                },
                tooltips: {
                    callbacks: {
                        title: function(tooltipItem, data) {
                            var date = tooltipItem[0].xLabel;
                            return new Date(date).Format('yyyy/MM/dd');
                        }
                    }
                }
            }
        };
        return config;
    }

});
