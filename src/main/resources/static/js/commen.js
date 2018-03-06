apiready = function(){
    api.setStatusBarStyle({
        style: 'dark'
    });
    var header =  $api.byId('header');
    $api.fixStatusBar(header);
    var headerPos = $api.offset(header);
    var wrap= $api.byId('wrap');
    $api.css(wrap,'margin-top:'+headerPos.h+'px');
}
$(function() {
    FastClick.attach(document.body);
});

function jump(x, y) {
    api.openWin(
        {
            name: x,
            url: y,
        }
    );
}

function back() {
    api.closeWin();

}