/**
 * 이력/민감 VOC 그리드 옵션
 */
let GRID_OPTIONS_VOC = {
	columns: [
		{
			data: 'vocSeq', className: "select-checkbox",
			'render': function(data, type, row, meta) {
				return '<input type="radio" value="' + data + '" />';
			}
		},
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
 * VOC유형선택 그리드 옵션
 */
let GRID_OPTIONS_CUSTOMMST = {
	columns: [
		{
			data: 'customMstSeq', className: "select-checkbox",
			'render': function(data, type, full, meta) {
				return '<input type="radio" value="' + data + '" />';
			}
		},
		{ data: 'industryNm', className: "text-center" },
		{ data: 'customGrpCd', className: "text-center" },
		{ data: 'customGrpNm', className: "text-center" },
	],
};

/**
 * 사원찾기 그리드 옵션
 */
let GRID_OPTIONS_EMPL = {
	columns: [
		{
			data: 'userSeq', className: "select-checkbox",
			'render': function(data, type, full, meta) {
				return '<input type="radio" value="' + data + '" />';
			}
		},
		{ data: 'userId', className: "text-center" },
		{ data: 'userNm', className: "text-center" },
		{ data: 'titleNm', className: "text-center" },
		{ data: 'deptNm', className: "text-center" },
		{ data: 'employmentYn', className: "text-center" },
	],
};

let LoadGrid = {
	/**----------------------------------------------------------------------------------*
	 * 이력/민감 VOC 모달팝업 : LoadGrid.modalCommonVoc
	 * ---------------------------------------------------------------------------------*/
	modalCommonVoc: function(custParam) {
		$('#modalCommonVocForm').form('clear');
		$('#modalCommonVocForm').form('set.values', custParam);
		if (custParam.sensSpecYn == 'Y') $('#modalCommonVocForm').find('.chk-sensSpecYn').click();

		let gridOptions = $.extend(true, {}, GRID_OPTIONS_VOC);
		let url = '/kmacvoc/v1/voc/list';
		let param = $('#modalCommonVocForm').form('get.values');
		param.sensSpecYn = param.sensSpecYn == false ? '' : param.sensSpecYn;

		gridUtil.loadGrid("listDataTableModalCommonVoc", gridOptions, url, param);

		//더블클릭시 VOC 상세화면 오픈
		$('#listDataTableModalCommonVoc tbody').on('dblclick', 'tr', function(e) {
			let row = $("#listDataTableModalCommonVoc").DataTable().row($(this)).data();
			let win = window.open("/voc/vocdetailpop?vocSeq=" + row.vocSeq, "", "width=1600, height=950");
		});
	},
	/**----------------------------------------------------------------------------------*
	 * 회사지정 마스터코드 모달팝업 : LoadGrid.modalCommonCustommst
	 * ---------------------------------------------------------------------------------*/
	modalCommonCustommst: function() {
		let gridOptions = $.extend(true, {}, GRID_OPTIONS_CUSTOMMST);
		let url = '/kmacvoc/v1/custom/mst/list';
		let param = $('#modalCommonCustommstForm').form('get.values');
		param['useYn'] = "Y";
		gridUtil.loadGrid("listDataTableModalCommonCustom", gridOptions, url, param);
	},
	/**----------------------------------------------------------------------------------*
	 * 사원찾기 모달팝업 : LoadGrid.modalCommonEmpl
	 * ---------------------------------------------------------------------------------*/
	modalCommonEmpl: function() {
		let gridOptions = $.extend(true, {}, GRID_OPTIONS_EMPL);
		let url = '/kmacvoc/v1/user/list';
		let param = $('#modalCommonEmplForm').form('get.values');

		gridUtil.loadGrid("listDataTableModalCommonEmpl", gridOptions, url, param);
	},
}

$(function() {
	//----- VOC유형 이벤트 처리
	$('#modalCommonCustommst').find('.btn-search-custommst').on('click', function() { LoadGrid.modalCommonCustommst(); }); //조회
	$('#modalCommonCustommst').find('.btn-select-custommst').on('click', function() { copyCustom(); }); //선택

	// 엔터클릭 시, 조회버튼 클릭
	$("#modalCommonCustommst").find("input[type=text]").on('keydown', function(e) {
		if (e.key === 'Enter' || e.keyCode === 13) {
			e.preventDefault();
			LoadGrid.modalCommonCustommst();
		}
	})

	//----- 사원찾기 이벤트 처리
	$('#modalCommonEmpl').find('.btn-search-user').on('click', function() { LoadGrid.modalCommonEmpl(); }); //조회
	$('#modalCommonEmpl').find('.btn-select-empl').on('click', function() { saveAuthuser(); }); //선택

	// dropdown 생성 ----------------
	DropdownUtil.makeCodeList('EMPLOYMENT_YN', $('#modalCommonEmplForm').find('.d-employmentYn'));

	// 엔터클릭 시, 조회버튼 클릭
	$("#modalCommonEmpl").find("input[type=text]").on('keydown', function(e) {
		if (e.key === 'Enter' || e.keyCode === 13) {
			e.preventDefault();
			LoadGrid.modalCommonEmpl();
		}
	})
});
