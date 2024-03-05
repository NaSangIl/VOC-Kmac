let $Grid = null;

$(function() {
	// 초기 설정 및 수행
	init();
});

/**
 * 초기 설정 및 수행 내용
 */
let init = function() {
	// 초기 화면 구성 ----------------

	// event 연결 ----------------
	$('.btn-search').on('click', function(e) { loadGrid(); });
	$('.btn-wrap .btn-add').on('click', function() { goAddVoc(); });
	$('.btn-wrap .btn-dtl').on('click', function() { goDtlVoc(); });

	//더블클릭시 상세화면 이동
	$('#listDataTableVoc tbody').on('dblclick', 'tr', function(e) {
		goDtlVoc();
	});


	// dropbox data setting ----------------
	DropdownUtil.makeCodeList('VOC_CASE_CD', $('#searchForm').find('.d-vocCaseCd'));       //VOC구분코드
	DropdownUtil.makeCodeList('RCPT_CHNN_CD', $('#searchForm').find('.d-rcptChnnCd'));     //접수채널코드
	DropdownUtil.makeCodeList('VOC_STATUS_CD', $('#searchForm').find('.d-vocStatusCd'));   //VOC상태코드

	// 회사코드
	DropdownUtil.makeCompList($('#searchForm').find('.d-companyCd'), function(v, t, $choice) {
		makeCodeVocType($('#searchForm'), v);      //VOC유형코드
		makeCodeVocActType($('#searchForm'), v);   //처리유형코드
	});

	makeCodeVocType($('#searchForm'), $SessionInfo.getCompanyCd());      //VOC유형코드
	makeCodeVocActType($('#searchForm'), $SessionInfo.getCompanyCd());   //처리유형코드

	setTimeout(function() {
		loadGrid();
	}, 200);
}

/**
 * 그리드 옵션
 */
let GRID_OPTIONS = {
	columns: [
		{ data: 'sensSpecYn', className: "text-center" },
		{ data: 'vocCaseNm', className: "text-center" },
		{
			data: 'vocTypeNm1', className: "text-center",
			render: function(data, type, row, meta) {
				return row.vocTypeNm1 + '>' + row.vocTypeNm2 + '>' + row.vocTypeNm3;
			}
		},
		{ data: 'rcptChnnNm', className: "text-center" },
		{ data: 'custNm', className: "text-center" },
		{ data: 'vocTitle', className: "text-center" },
		{ data: 'vocStatusNm', className: "text-center" },
		{ data: 'regUserNm', className: "text-center" },
		{ data: 'regDt', className: "text-center" },
		{
			data: 'vocActTypeNm1', className: "text-center",
			render: function(data, type, row, meta) {
				return ObjectUtil.isEmpty(row.vocActTypeNm2) ? row.vocActTypeNm1 : row.vocActTypeNm1 + '>' + row.vocActTypeNm2;
			}
		},
		{ data: 'vocActUserNm', className: "text-center" },
		{ data: 'vocActDt', className: "text-center" },
	],
	//scrollY             : '300px',
	//scrollCollapse      : true,
	scrollX: '100%',
	scrollXInner: '2100px',
};

/**
 * Grid 구성
 */
let loadGrid = function() {
	let gridOptions = $.extend(true, {}, GRID_OPTIONS);
	let url = '/kmacvoc/v1/voc/list';
	let param = $('#searchForm').form('get.values');

	//날짜포맷 변경되어 별도 셋팅
	param.regDtStart = $('#searchForm').find('#regDtStart').val();
	param.regDtEnd = $('#searchForm').find('#regDtEnd').val();
	param.vocActDtStart = $('#searchForm').find('#vocActDtStart').val();
	param.vocActDtEnd = $('#searchForm').find('#vocActDtEnd').val();
	param.sensSpecYn = param.sensSpecYn == false ? '' : param.sensSpecYn;
	$Grid = gridUtil.loadGrid("listDataTableVoc", gridOptions, url, param);
};

/**
 * 등록/수정을 위한 사용자상세팝업 오픈
 */
let goAddVoc = function() {
	if (ObjectUtil.isEmpty($('#searchForm').find('#companyCd').val())) {
		alert('회사를 선택해 주세요.');
		$('#searchForm').find('.d-companyCd').focus();
		return;
	}
	localStorage.setItem('companyCd', $('#searchForm').find('#companyCd').val());
	localStorage.removeItem('vocSeq');
	goPage('/voc/regist');
}

/**
 * 사용자상세 팝업
 */
let goDtlVoc = function() {
	localStorage.setItem("vocSeq", $selectedRowData.vocSeq);
	localStorage.setItem("companyCd", $selectedRowData.companyCd);
	goPage('/voc/vocdetail');
}

