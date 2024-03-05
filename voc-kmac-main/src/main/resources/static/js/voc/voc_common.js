
/**
 * VOC유형 dropbox 생성 및 이벤트 처리
 */
let makeCodeVocType = function(obj, companyCd) {
	let vocTypeList1 = [];
	let vocTypeList2 = [];
	let vocTypeList3 = [];
	let param1 = { 'customType': 'VOC_TYPE', 'companyCd': !!companyCd ? companyCd : $SessionInfo.getCompanyCd(), 'customLevel': 1 };
	let param2 = { 'customType': 'VOC_TYPE', 'companyCd': !!companyCd ? companyCd : $SessionInfo.getCompanyCd(), 'customLevel': 2 };
	let param3 = { 'customType': 'VOC_TYPE', 'companyCd': !!companyCd ? companyCd : $SessionInfo.getCompanyCd(), 'customLevel': 3 };

	AjaxUtil.get('/kmacvoc/v1/company/custom/code/list', param1,
		function(result) {
			if (result && result.data) {
				result.data.list.map(function(item) {
					item.value = item.customCd;
					item.name = item.customNm;
				});
				vocTypeList1 = result.data.list;
			}
		}, false
	);
	AjaxUtil.get('/kmacvoc/v1/company/custom/code/list', param2,
		function(result) {
			if (result && result.data) {
				result.data.list.map(function(item) {
					item.value = item.customCd;
					item.name = item.customNm;
				});
				vocTypeList2 = result.data.list;
			}
		}, false
	);
	AjaxUtil.get('/kmacvoc/v1/company/custom/code/list', param3,
		function(result) {
			if (result && result.data) {
				result.data.list.map(function(item) {
					item.value = item.customCd;
					item.name = item.customNm;
				});
				vocTypeList3 = result.data.list;
			}
		}, false
	);

	obj.find('.d-vocTypeCd1').dropdown({
		values: vocTypeList1,
		clearable: true,
		onChange: function(v, t, $choice) {
			let list2 = vocTypeList2.filter(data => data.upperCustomCd === v);
			let displayTxt1 = '';
			makeCodeVocType3([], displayTxt1, obj);

			if (v == '') {
			} else {
				let displayObj = vocTypeList1.filter(data => data.value === v);
				displayTxt1 = displayObj[0].name;
			}
			if (obj.find('#vocTypeDisplay') != undefined) obj.find('#vocTypeDisplay').val(displayTxt1);
			makeCodeVocType2(list2, vocTypeList3, displayTxt1, obj);
		}
	});
}
let makeCodeVocType2 = function(list2, vocTypeList3, displayTxt1, obj) {
	obj.find('.d-vocTypeCd2').dropdown({
		values: list2,
		clearable: true,
		onChange: function(v, t, $choice) {
			let list3 = vocTypeList3.filter(data => data.upperCustomCd === v);
			let displayTxt2 = '';
			if (v == '') {
				displayTxt2 = displayTxt1;
			} else {
				let displayObj = list2.filter(data => data.value === v);
				displayTxt2 = displayTxt1 + '>' + displayObj[0].name;
			}
			if (obj.find('#vocTypeDisplay') != undefined) obj.find('#vocTypeDisplay').val(displayTxt2);
			makeCodeVocType3(list3, displayTxt2, obj);
		}
	});
}
let makeCodeVocType3 = function(list3, displayTxt2, obj) {
	obj.find('.d-vocTypeCd3').dropdown({
		values: list3,
		clearable: true,
		onChange: function(v, t, $choice) {
			let displayTxt3 = '';
			if (v === '') {
				displayTxt3 = displayTxt2;
			} else {
				let displayObj = list3.filter(data => data.value === v);
				displayTxt3 = displayTxt2 + '>' + displayObj[0].name;
			}
			if (obj.find('#vocTypeDisplay') != undefined) obj.find('#vocTypeDisplay').val(displayTxt3);
		}
	});
}


/**
 * 처리유형 dropbox 생성 및 이벤트 처리
 */
let makeCodeVocActType = function(obj, companyCd) {
	let vocActTypeList1 = [];
	let vocActTypeList2 = [];
	let param1 = { 'customType': 'VOC_ACT_TYPE', 'companyCd': !!companyCd ? companyCd : $SessionInfo.getCompanyCd(), 'customLevel': 1 };
	let param2 = { 'customType': 'VOC_ACT_TYPE', 'companyCd': !!companyCd ? companyCd : $SessionInfo.getCompanyCd(), 'customLevel': 2 };

	AjaxUtil.get('/kmacvoc/v1/company/custom/code/list', param1,
		function(result) {
			if (result && result.data) {
				result.data.list.map(function(item) {
					item.value = item.customCd;
					item.name = item.customNm;
				});
				vocActTypeList1 = result.data.list;
			}
		}, false
	);
	AjaxUtil.get('/kmacvoc/v1/company/custom/code/list', param2,
		function(result) {
			if (result && result.data) {
				result.data.list.map(function(item) {
					item.value = item.customCd;
					item.name = item.customNm;
				});
				vocActTypeList2 = result.data.list;
			}
		}, false
	);

	obj.find('.d-vocActTypeCd1').dropdown({
		values: vocActTypeList1,
		clearable: true,
		onChange: function(v, t, $choice) {
			let list2 = vocActTypeList2.filter(data => data.upperCustomCd === v);
			makeCodeVocActType2(list2, obj);
		}
	});
}
let makeCodeVocActType2 = function(list2, obj) {
	obj.find('.d-vocActTypeCd2').dropdown({
		values: list2,
		clearable: true
	});
}
