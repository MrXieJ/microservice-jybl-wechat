/*
 * 获取url中的参数值
 *
 * @param
 * name: 参数的名称
 *
 * @return
 * 返回参数的值
 *
 */
function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) return unescape(r[2]);
	return null;
}
