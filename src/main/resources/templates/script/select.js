function loadProvince(regionId){
    $("#id_provSelect").html("");
    $("#id_provSelect").append("<option value=''>请选择省份</option>");
    var jsonStr = getAddress(regionId,0);
    for(var k in jsonStr) {
        $("#id_provSelect").append("<option value='"+k+"'>"+jsonStr[k]+"</option>");
    }
    if(regionId.length!=6) {
        $("#id_citySelect").html("");
        $("#id_citySelect").append("<option value=''>请选择城市</option>");
        $("#id_areaSelect").html("");
        $("#id_areaSelect").append("<option value=''>请选择区域</option>");
    } else {
        $("#id_provSelect").val(regionId.substring(0,2)+"0000");
        loadCity(regionId);
    }
}

function loadCity(regionId){
    $("#id_citySelect").html("");
    $("#id_citySelect").append("<option value=''>请选择城市</option>");
    if(regionId.length!=6) {
        $("#id_areaSelect").html("");
        $("#id_areaSelect").append("<option value=''>请选择区域</option>");
    } else {
        var jsonStr = getAddress(regionId,1);
        for(var k in jsonStr) {
            $("#id_citySelect").append("<option value='"+k+"'>"+jsonStr[k]+"</option>");
        }
        if(regionId.substring(2,6)=="0000") {
            $("#id_areaSelect").html("");
            $("#id_areaSelect").append("<option value=''>请选择区域</option>");
        } else {
            $("#id_citySelect").val(regionId.substring(0,4)+"00");
            loadArea(regionId);
        }
    }
}

function loadArea(regionId){
    $("#id_areaSelect").html("");
    $("#id_areaSelect").append("<option value=''>请选择区域</option>");
    if(regionId.length==6) {
        var jsonStr = getAddress(regionId,2);
        for(var k in jsonStr) {
            $("#id_areaSelect").append("<option value='"+k+"'>"+jsonStr[k]+"</option>");
        }
        if(regionId.substring(4,6)!="00") {$("#id_areaSelect").val(regionId);}
    }
}

function getAddress(regionId,type) {
    var array = {};
    if(type==0) {//省级列表
        var obj = address['0'];
        for(var x in obj) {
            var key = obj[x][0];
            var value = obj[x][1];
            array[key] = value;
        }
    } else if(type==1) {//市级列表
        var str = regionId.substring(0,2);
        var obj = address['1'];
        for(var x in obj) {
            var key = obj[x][0];
            var value = obj[x][1];
            if(key.substring(0,2)==str) {array[key] = value;}
        }
    } else if(type==2) {//区县级列表
        var str = regionId.substring(0,4);
        var obj = address['2'];
        for(var x in obj) {
            var key = obj[x][0];
            var value = obj[x][1];
            if(key.substring(0,4)==str) {array[key] = value;}
        }
    }
    return array;
}

function bfunction() {
    var objj = address['0'];
    var obj=doucument.getElementById("id_provSelect");
    var index=obj.selectedIndex;
    obj.options[index].value=obj.options[index].text;
    obj.options[index].value=objj[obj.options[index].value];


}