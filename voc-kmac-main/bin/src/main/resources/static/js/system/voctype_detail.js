let $Grid = null;
let $customType = "";   //[VOC_TYPE:VOC유형,VOC_ACT_TYPE:VOC처리유형}
$(function() {
	// 초기 설정 및 수행
	init();
});

/**
 * 초기 설정 및 수행 내용
 */
let init = function() {

	$customType = $('#registForm').find('#customType').val();
	let customMstSeq = localStorage.getItem("customMstSeq");

	// event 연결 ----------------
	$('.btn-wrap .btn-go-list').on('click', function() { goList(); });                       //회사지정코드그룹 목록
	$('.btn-wrap .btn-save-custommst').on('click', function() { saveMstData(); });           //회사지정코드그룹 저장
	$('.btn-wrap .btn-delt-custommst').on('click', function() { deleteMstData(); });         //회사지정코드그룹 삭제
	$('.btn-wrap .excl-download').on('click', function() { downloadExclTemplate(); });       //업로드양식 다운로드
	$('.btn-wrap .excl-upload').on('click', function() { uploadExclTemplate(); });           //엑셀업로드
	$('.btn-wrap .btn-save-custom').on('click', function() { popAddVoctype(); });            //회사지정코드 등록창 오픈
	$('.btn-wrap .btn-regi-custom1').on('click', function() { resetData('1'); });       //회사지정코드_대 신규등록
	$('.btn-wrap .btn-regi-custom2').on('click', function() { resetData('2'); });       //회사지정코드_중 신규등록
	$('.btn-wrap .btn-regi-custom3').on('click', function() { resetData('3'); });       //회사지정코드_소 신규등록
	$('.btn-wrap .btn-save-custom1').on('click', function() { saveData('1'); });        //회사지정코드_대 저장
	$('.btn-wrap .btn-save-custom2').on('click', function() { saveData('2'); });        //회사지정코드_중 저장
	$('.btn-wrap .btn-save-custom3').on('click', function() { saveData('3'); });        //회사지정코드_소 저장
	$('.btn-wrap .btn-delt-custom1').on('click', function() { deleteData('1'); });      //회사지정코드_대 삭제
	$('.btn-wrap .btn-delt-custom2').on('click', function() { deleteData('2'); });      //회사지정코드_중 삭제
	$('.btn-wrap .btn-delt-custom3').on('click', function() { deleteData('3'); });      //회사지정코드_소 삭제
	$('.btn-wrap .btn-refresh-custom').on('click', function() { loadGrid(customMstSeq); });  //새로고침
	$('.modal-custom').find('.close').on('click', function() { loadGrid(customMstSeq); });   //유형등록 팝업 닫힐경우 그리드 재조회

	// dropbox data setting ----------------
	DropdownUtil.makeCodeList('INDUSTRY_CD', $('#registForm').find('.d-industryCd'));  //업종
	DropdownUtil.makeCodeList('USE_YN', $('#registForm').find('.d-useYn')); //사용여부

	setTimeout(function() {
		if (customMstSeq > 0) searchMstData(customMstSeq);
		//localStorage.removeItem('customMstSeq');
		loadGrid(customMstSeq);
	}, 200);
}

/**
 * 회사지정코드 그리드 옵션
 */
let GRID_OPTIONS = {
	columns: [
		{data: 'customNm1', className: "text-center" },
		{data: 'customNm2', className: "text-center" },
		{data: 'customNm3', className: "text-center" },
		{data: 'modUserNm', className: "text-center" },
		{data: 'modDt', className: "text-center" },
	],
	scrollX: '100%',
	scrollXInner: '100%',
	//scrollXInner: '1800px',
};

/**
 * 회사지정코드 그리드 구성
 */
let loadGrid = function(customMstSeq) {
	if (customMstSeq == 0 || ObjectUtil.isEmpty(customMstSeq)) customMstSeq = -1;
	let gridOptions = $.extend(true, {}, GRID_OPTIONS);
	let url = '/kmacvoc/v1/custom/list';
	let param = { 'customMstSeq': customMstSeq, 'customType': $customType };
	$Grid = gridUtil.loadGrid("listDataTableCustom", gridOptions, url, param);
};

/**
 * 목록화면 이동
 */
let goList = function() {
	goPage("/system/voctypelist");
}

/**
 * 회사지정코드 마스터 데이터 조회
 */
let searchMstData = function(key) {
	AjaxUtil.get(
		'/kmacvoc/v1/custom/mst/' + key,
		{},
		function(result) {
			if (result && result.data) {
				let d = result.data;
				$('#registForm').form('clear');
				$('#registForm').form('set.values', d);
				$('.btn-delt-custommst').removeClass('blind');
				$('.histDiv').removeClass('blind');
			}
		}
	);
}

/**
 * 회사지정코드 마스터 데이터 저장
 */
let saveMstData = function() {

	let $frm = $('#registForm');
	if (ObjectUtil.isEmpty($frm.find('#customGrpNm').val())) {
		alert('VOC 유형 그룹명을 입력해 주세요.');
		$frm.find('#customGrpNm').focus();
		return;
	}
	if (ObjectUtil.isEmpty($frm.find('#industryCd').val())) {
		alert('업종을 선택해 주세요.');
		return;
	}

	if (ObjectUtil.isEmpty($frm.find('#useYn').val())) {
		alert('사용여부를 선택해 주세요.');
		return;
	}

	if (ObjectUtil.isEmpty($frm.find('#customDesc').val())) {
		alert('VOC 유형 설명을 입력해 주세요.');
		$frm.find('#customGrpNm').focus();
		return;
	}

	if (!confirm('저장하시겠습니까?')) return;

	let formData = $('#registForm').serializeObject();
	let url = formData.customMstSeq == '0' ? '/kmacvoc/v1/custom/mst/add' : '/kmacvoc/v1/custom/mst/modify';

	AjaxUtil.post(
		url,
		JSON.stringify(formData),
		function(result) {
			if (result && result.messageCode == '0000') {
				alert(result.data.rtnMessage);
				//저장후 목록을 이동하지 않고 상세화면 처리로 변경됨
				localStorage.setItem("customMstSeq", result.data.rtnKey);
				goPage("/system/voctypedetail");
			}
		}
	);
}

/**
 * 회사지정코드 마스터 데이터 삭제
 */
let deleteMstData = function() {
	let rowCnt = $('#listDataTableCustom').DataTable().rows().count();
	let msg2 = rowCnt > 0 ? " 상세 VOC 유형도 삭제됩니다." : ""; //하위 VOC유형목록에 데이터가 존재할 경우만 표시
	if (!confirm('VOC 유형 그룹 정보를 삭제하시겠습니까? ' + msg2)) return;

	let customMstSeq = $('#registForm').find('#customMstSeq').val();
	let url = '/kmacvoc/v1/custom/mst/remove/' + customMstSeq;

	AjaxUtil.post(
		url,
		{},
		function(result) {
			if (result && result.messageCode == '0000') {
				alert(result.data.rtnMessage);
				goList();
			}
		}
	);
}

/**
 * 등록/수정을 위한 상세팝업 오픈
 */
let popAddVoctype = function() {
	initDataInfo = true;
	$('#selectCustomCode1').html('');
	$('#selectCustomCode2').html('');
	$('#selectCustomCode3').html('');
	resetData('1');
	searchData('1');
	$('.ui.modal-custom').modal('show');
}

/**
 * 회사지정코드 데이터 조회
 */
let initDataInfo = true;
let searchData = function(flag) {
	if (flag == '3' && $customType == 'VOC_ACT_TYPE') return; //처리유형은 대/소만 존재

	let $frm = $('#customRegistForm' + flag);

	let param = {};
	param.customMstSeq = $('#registForm').find('#customMstSeq').val();
	param.customLevel = flag;
	param.customType = $customType;

	let upperCustomCd2 = $('#customRegistForm1').find('#customCd').val();
	let upperCustomCd3 = $('#customRegistForm2').find('#customCd').val();

	if (flag == '1') {
		resetData('2');
		resetData('3');
	} else if (flag == '2') {
		resetData('3');
		param.upperCustomCd = upperCustomCd2;
	} else if (flag == '3') {
		param.upperCustomCd = upperCustomCd3;
		//if(flag == '3') param.upperCustomCd = upperCustomCd3 == '' ? '-1' : upperCustomCd3;
	}

	if (flag != '1' && !param.upperCustomCd) return false;
	AjaxUtil.get(
		'/kmacvoc/v1/custom/code/list',
		param,
		function(result) {
			if (result && result.data && result.data.list) {
				let list = result.data?.list;
				let obj = '#selectCustomCode' + flag;
				$(obj).html('');
				list.map((d) => {
					$(obj).append("<option value='" + d.customCd + "' onclick='javascript:setData(" + flag + "," + JSON.stringify(d) + ")'>" + d.customNm + "</option>");
				});

				if (0 < list?.length) {
					if (initDataInfo) {
						$(obj + " option:eq(0)").prop("selected", true);
						$(obj + " option:eq(0)").click();
					} else {
						if (flag == '1') {
							$('#selectCustomCode2').html('');
							$('#selectCustomCode3').html('');
							$('#vocTypeEditPop .btn-regi-custom1').removeClass("disabled");
							$('#vocTypeEditPop .btn-regi-custom2').addClass("disabled");
							$('#vocTypeEditPop .btn-regi-custom3').addClass("disabled");
							searchData('2');
						} else if (flag == '2') {
							$('#vocTypeEditPop .btn-regi-custom1').removeClass("disabled");
							$('#vocTypeEditPop .btn-regi-custom2').removeClass("disabled");
							$('#vocTypeEditPop .btn-regi-custom3').addClass("disabled");
							$('#selectCustomCode3').html('');
							searchData('3');
						}
					}
					$('#vocTypeEditPop .btn-regi-custom' + flag).removeClass("disabled");
				} else {
					if (flag == '1') {
						resetData('1');
						$('#vocTypeEditPop .btn-regi-custom1').removeClass("disabled");
						$('#vocTypeEditPop .btn-regi-custom2').addClass("disabled");
						$('#vocTypeEditPop .btn-regi-custom3').addClass("disabled");
						$('#selectCustomCode2').html('');
						$('#selectCustomCode3').html('');
					} else if (flag == '2') {
						$('#vocTypeEditPop .btn-regi-custom1').removeClass("disabled");
						$('#vocTypeEditPop .btn-regi-custom2').removeClass("disabled");
						$('#vocTypeEditPop .btn-regi-custom3').addClass("disabled");
						$('#selectCustomCode3').html('');
					} else if (flag == '3') {
						$('#vocTypeEditPop .btn-regi-custom3').removeClass("disabled");
					}
				}

			}
		}
	);
}

/**
 * 신규등록 : 데이터 리셋
 */
let resetData = function(flag) {
	if (flag == '3' && $customType == 'VOC_ACT_TYPE') return; //처리유형은 대/소만 존재

	let $frm = $('#customRegistForm' + flag);

	$frm.find('#customType').val($customType);
	$frm.find('#customSeq').val('0');
	$frm.find('#upperCustomCd').val('');
	$frm.find('#customCd').val('');
	$frm.find('#customNm').val('');
	$frm.find('#dispOrder').val('');

	$('#vocTypeEditPop').find('input').attr("readonly", true);
	$frm.find('input').attr("readonly", false);
	$('#vocTypeEditPop [class*="btn-save-custom"]').hide();
	$('#vocTypeEditPop').find('.btn-save-custom' + flag).show();
	$('#vocTypeEditPop [class*="btn-delt-custom"]').hide();
}

/**
 * 회사지정코드 데이터 셋팅
 */
let setData = function(flag, d) {
	if (flag == '3' && $customType == 'VOC_ACT_TYPE') return; //처리유형은 대/소만 존재

	let $frm = $('#customRegistForm' + flag);
	$frm.form('set.values', d);

	initDataInfo = false;
	if (flag == '1') {
		resetData('2');
		searchData('2');
	} else if (flag == '2') {
		resetData('3');
		searchData('3');
	}

	$('#vocTypeEditPop').find('input').attr("readonly", true);
	$frm.find('input').attr("readonly", false);

	$('#vocTypeEditPop [class*="btn-save-custom"]').hide();
	$('#vocTypeEditPop').find('.btn-save-custom' + flag).show();
	$('#vocTypeEditPop [class*="btn-delt-custom"]').hide();
	$('#vocTypeEditPop').find('.btn-delt-custom' + flag).show();
}

/**
 * 회사지정코드 데이터 저장
 */
let saveData = function(flag) {

	if (flag == '3' && $customType == 'VOC_ACT_TYPE') return; //처리유형은 대/소만 존재

	let msg;
	let $frm = $('#customRegistForm' + flag);

	if (ObjectUtil.isEmpty($frm.find('#customCd').val())) {
		msg = $customType == "VOC_TYPE" ? "VOC유형코드" : "VOC처리유형코드";
		alert(msg + ' 를 입력해 주세요.');
		$frm.find('#customCd').focus();
		return;
	}
	if (ObjectUtil.isEmpty($frm.find('#customNm').val())) {
		msg = $customType == "VOC_TYPE" ? "VOC유형코드명" : "VOC처리유형코드명";
		alert(msg + '을 입력해 주세요.');
		$frm.find('#customNm').focus();
		return;
	}
	if (ObjectUtil.isEmpty($frm.find('#dispOrder').val())) {
		alert('순서를 선택해 주세요.');
		$frm.find('#dispOrder').focus();
		return;
	}

	$frm.find('#customMstSeq').val($('#registForm').find('#customMstSeq').val());
	$frm.find('#customLevel').val(flag);
	if (flag == '2') {
		if (!ObjectUtil.isEmpty($('#customRegistForm1').find('#customCd').val())) {
			$frm.find('#upperCustomCd').val($('#customRegistForm1').find('#customCd').val());
		} else {
			alert('상위 코드를 선택해 주세요.');
			return;
		}
	}

	if (flag == '3') {
		if (!ObjectUtil.isEmpty($('#customRegistForm2').find('#customCd').val())) {
			$frm.find('#upperCustomCd').val($('#customRegistForm2').find('#customCd').val());
		} else {
			alert('상위 코드를 선택해 주세요.');
			return;
		}
	}

	if (!confirm('저장하시겠습니까?')) return;
	let formData = $frm.serializeObject();
	let url = formData.customSeq == '0' ? '/kmacvoc/v1/custom/add' : '/kmacvoc/v1/custom/modify';

	AjaxUtil.post(
		url,
		JSON.stringify(formData),
		function(result) {
			if (result && result.messageCode == '0000') {
				alert(result.data.rtnMessage);
				initDataInfo = true;
				searchData(flag);
				loadGrid(localStorage.getItem("customMstSeq"));
			}
		}
	);
}

/**
 * 회사지정코드 데이터 삭제
 */
let deleteData = function(flag) {
	if (flag == '3' && $customType == 'VOC_ACT_TYPE') return; //처리유형은 대/소만 존재

	let $frm = $('#customRegistForm' + flag);
	let customSeq = $frm.find('#customSeq').val();

	if (ObjectUtil.isEmpty(customSeq) || customSeq === "0") {
		alert('삭제할 데이터가 존재하지 않습니다.');
		return;
	}

	if (!confirm('삭제대상코드에 연결된 하위코드도 삭제됩니다. 삭제하시겠습니까?')) return;

	let url = '/kmacvoc/v1/custom/remove/' + customSeq;

	AjaxUtil.post(
		url,
		{},
		function(result) {
			if (result && result.messageCode == '0000') {
				resetData(flag);
				alert(result.data.rtnMessage);
				initDataInfo = true;
				searchData(flag);
				loadGrid(localStorage.getItem("customMstSeq"));
			}
		}
	);
}

/**
 * 업로드양식다운로드
 */
let downloadExclTemplate = function() {
	$(location).attr('href', '/kmacvoc/v1/custom/download/' + $customType);
}

function getFileExtension(filename) {
	return filename.split('.').pop().toLowerCase();
}

/**
 * Excel업로드
 */
let uploadExclTemplate = function() {
	let formData = new FormData($("#exclUploadForm")[0]);

	let f = $('#excelFile').val();
	if (ObjectUtil.isEmpty(f)) {
		alert("파일을 선택해 주세요.");
		return;
	}

	let fileExtension = getFileExtension($('#excelFile').val());
	if (fileExtension !== 'xlsx' && fileExtension !== 'xls' && fileExtension !== 'csv') {
		alert("엑셀파일만 업로드 가능합니다.");
		return;
	}

	let msg = $customType == "VOC_TYPE" ? " VOC유형을" : "VOC처리유형을";
	if (!confirm('엑셀업로드를 통해 ' + msg + ' 일괄등록 하시겠습니까? \n기존에 등록된 유형은 삭제됩니다.')) return;

	let customMstSeq = $('#registForm').find('#customMstSeq').val();
	formData.append('customType', $customType);
	formData.append('customMstSeq', customMstSeq);

	Util.showLoading();
	$.ajax({
		method: "POST",
		url: "/kmacvoc/v1/custom/batchReadExcel",
		data: formData,
		contentType: false,
		processData: false
	})
	.done(function(result) {
		let lv1Cnt = result?.data?.messageArgs?.[0];
		let lv2Cnt = result?.data?.messageArgs?.[1];
		let lv3Cnt = result?.data?.messageArgs?.[2];
		let dupCnt = result?.data?.messageArgs?.[3];
		if (result && result?.messageCode == '0000') {
			alert("VOC유형 (대) " + lv1Cnt +" 건, (중) " + lv2Cnt + " 건, (소) " + lv3Cnt + " 건 등록되었습니다.\n중복 데이터는 " + dupCnt + " 건 발생했습니다.");
			$('#excelFile').val("");
			loadGrid(customMstSeq);
		}else{
			alert(result?.message);
		}
		Util.hideLoading();
	}).fail(function(xhr, status, error) {
		alert("엑셀업로드가 실패되었습니다.");
		Util.hideLoading();
	});
}



