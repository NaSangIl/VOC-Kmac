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
	if ($SessionInfo.getUserAuth().indexOf('100') < 0 && $SessionInfo.getUserAuth().indexOf('000') < 0) {
		$('#companyCdFild').hide();
	}
	// dropdown 생성 ----------------
	DropdownUtil.makeCompList($('.d-companyCd'));  // 회사코드

	// event 연결 ----------------
	$("#searchForm").find('.btn-search').on('click', function(e) { e.preventDefault(); loadGrid(); });

	//더블클릭시 권한사용자등록화면 이동
	$('#listDataTableAuth tbody').on('dblclick', 'tr', function(e) {
		goAddAuthuser();
	});

	setTimeout(function() {
		loadGrid();
	}, 300);
}

/**
 * 그리드 옵션
 */
let GRID_OPTIONS = {
	columns: [
		{ data: 'authNm', className: "text-center" },
		{ data: 'authDesc', className: "text-left" },
		{ data: 'authUserCnt', className: "text-center" },
	],
};

/**
 * Grid 구성
 */
let loadGrid = function() {
	if (ObjectUtil.isEmpty($('#searchForm').find('#companyCd').val())) {
		alert('회사를 선택해 주세요.');
		$('#searchForm').find('.d-companyCd').focus();
		return;
	}

	let gridOptions = $.extend(true, {}, GRID_OPTIONS);
	let url = '/kmacvoc/v1/auth/list';
	let param = $('#searchForm').form('get.values');

	gridUtil.loadGrid("listDataTableAuth", gridOptions, url, param);
};

/**
 * 권한사용자 상세 이동
 */
let goAddAuthuser = function() {
	localStorage.setItem("authSeq", $selectedRowData.authSeq);
	localStorage.setItem("companyCd", $('#searchForm').find('#companyCd').val());
	goPage('/system/authuserdetail');
}
