export function dateFormat(input, pattern = "") { // 在参数列表中 通过 pattern="" 来指定形参默认值，防止报错

	var dt = new Date(input);
	// 获取年月日
	var y = dt.getFullYear();
	var m = (dt.getMonth() + 1).toString().padStart(2, '0');
	var d = dt.getDate().toString().padStart(2, '0');

	// 如果 传递进来的字符串类型，转为小写之后，等于 yyyy-mm-dd，那么就返回 年-月-日
	// 否则，就返回  年-月-日 时：分：秒
	if (pattern.toLowerCase() === 'yyyy-mm-dd') {
		return `${y}-${m}-${d}`;
	} else {
		// 获取时分秒
		var hh = dt.getHours().toString().padStart(2, '0');
		var mm = dt.getMinutes().toString().padStart(2, '0');
		var ss = dt.getSeconds().toString().padStart(2, '0');
		return `${y}-${m}-${d} ${hh}:${mm}:${ss}`;
	}
}
