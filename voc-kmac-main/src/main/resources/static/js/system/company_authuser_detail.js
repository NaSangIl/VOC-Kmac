let $Grid = null;
let $authSeq;
let $companyCd;
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
	$('.btn-wrap .btn-go-list').on('click', function() { goList(); });               //목록
	$('.btn-wrap .btn-add-authuser').on('click', function() { popAddAuthuser(); });  //추가
	$('.btn-save').on('click', function() { saveData(); });
	$('.btn-wrap .btn-dtl').on('click', function() { popDtlAuth(); });
	$('.btn-delt').on('click', function() { deleteData(); });

	$authSeq = localStorage.getItem("authSeq");
	$companyCd = $SessionInfo.getCompanyCd();
	searchData($authSeq);

	setTimeout(function() {
		loadGrid();
	}, 300);

}

/**
 * 그리드 옵션
 */
let GRID_OPTIONS = {
	columns: [
		{
			data: 'authSeq', className: "text-center",
			'render': function(data, type, full, meta) {
				return meta.row + 1;
			}
		},
		{ data: 'userId', className: "text-center" },
		{ data: 'userNm', className: "text-center" },
		{ data: 'titleNm', className: "text-left" },
		{ data: 'deptNm', className: "text-center" },
		{
			data: 'userSeq',
			'render': function(data, type, full, meta) {
				return "<div class='btn-wrap'><button class='ui button btn-black-line btn-delt-comments ml_5' onclick='deleteAuthuser(" + data + ")'>삭제</button></div>";
			}
		},
	],
	pageLength:5,
};

/**
 * Grid 구성
 */
let loadGrid = function() {
	let gridOptions = $.extend(true, {}, GRID_OPTIONS);
	let url = '/kmacvoc/v1/authuser/list';
	let param = { 
		  'authSeq': $authSeq
		, 'companyCd': $companyCd
	 };

	$Grid = gridUtil.loadGrid("listDataTableAuthuser", gridOptions, url, param);
};

/**
 * 목록화면 이동
 */
let goList = function() {
	goPage('/system/companyauthuserlist');
}

/**
 * 데이터 조회
 */
let searchData = function(authSeq) {
	AjaxUtil.get(
		'/kmacvoc/v1/auth/' + authSeq,
		{},
		function(result) {
			if (result && result.data) {
				let d = result.data;
				$('#registForm').form('clear');
				$('#registForm').form('set.values', d);
			}
		}
	);
}

/**
 * 권한사용자 추가를 위한 팝업 오픈
 */
let popAddAuthuser = function() {
	$('#modalCommonEmplForm').find('#companyCd').val($companyCd);
	LoadGrid.modalCommonEmpl();
	$('#modalCommonEmpl').modal('show');
}

/**
 * 데이터 저장
 */
let saveAuthuser = function() {
	let row = $.map($('#listDataTableModalCommonEmpl').DataTable().row('.selected').data(), function(item) {
		return item;
	});

	var table = $('#listDataTableAuthuser').DataTable();
	var columnData = table.column('5')?.data()?.toArray(); // 'columnName'을 실제 열 이름으로 변경
	var selKey = row[8];

	if (columnData.includes(selKey)) {
	    alert("이미 등록된 사용자입니다.")
		return false;
	}

	let param = { 'authSeq': $authSeq, 'companyCd': $companyCd, 'userSeq': selKey };
	let url = '/kmacvoc/v1/authuser/add';

	AjaxUtil.post(
		url,
		JSON.stringify(param),
		function(result) {
			if (result && result.messageCode == '0000') {
				alert(result.data.rtnMessage);
				$('#modalCommonEmpl').modal('hide');
				loadGrid(); // grid 조회
			}
		}
	);
}

/**
 * 데이터 삭제
 */
let deleteAuthuser = function(userSeq) {

	if (!confirm('권한사용자정보를 삭제하시겠습니까?')) return;

	let param = { 'authSeq': $authSeq, 'userSeq': userSeq };
	let url = '/kmacvoc/v1/authuser/remove';

	AjaxUtil.post(
		url,
		JSON.stringify(param),
		function(result) {
			if (result && result.messageCode == '0000') {
				loadGrid(); // grid 조회
			}
		}
	);
}
