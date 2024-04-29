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

	let stYy,stMm,edYy,edMm;
	let date = new Date();
    var delimiter = "-";
	
    date.setMonth(date.getMonth() -6);
    let bfday = date.getFullYear() + delimiter + ("0" + (date.getMonth()+1)).slice(-2) + delimiter + ("0" + date.getDate()).slice(-2);
	
	$('#searchForm').find('#regDtStart').val(bfday);
	
	let today = Util.getToday();
	
	edYy = today.substring(0,4);
    edMm = today.substring(4,6);
    edDd = today.substring(6,8);
        
	$('#searchForm').find('#regDtEnd').val(edYy+"-"+edMm+"-"+edDd);
	
	setTimeout(function() {
		
		if(!ObjectUtil.isEmpty(localStorage.getItem("companyCd"))){
			$('#searchForm').find('.d-companyCd').dropdown('set selected', localStorage.getItem("companyCd"));
		}	
		
	    if(!ObjectUtil.isEmpty(localStorage.getItem("custNm"))){
			$('#searchForm').find('#custNm').val(localStorage.getItem("custNm"));
		}	
		
		if(!ObjectUtil.isEmpty(localStorage.getItem("vocCaseCd"))){
			$('#searchForm').find('.d-vocCaseCd').dropdown('set selected', localStorage.getItem("vocCaseCd"));
		}	
		
		if(!ObjectUtil.isEmpty(localStorage.getItem("vocTypeCd1"))){
			$('#searchForm').find('.d-vocTypeCd1').dropdown('set selected', localStorage.getItem("vocTypeCd1"));
		}	

		if(!ObjectUtil.isEmpty(localStorage.getItem("vocTypeCd2"))){
			$('#searchForm').find('.d-vocTypeCd2').dropdown('set selected', localStorage.getItem("vocTypeCd2"));
		}
		
		if(!ObjectUtil.isEmpty(localStorage.getItem("vocTypeCd3"))){
			$('#searchForm').find('.d-vocTypeCd3').dropdown('set selected', localStorage.getItem("vocTypeCd3"));
		}		
		
		if(!ObjectUtil.isEmpty(localStorage.getItem("rcptChnnCd"))){
			$('#searchForm').find('.d-rcptChnnCd').dropdown('set selected', localStorage.getItem("rcptChnnCd"));
		}			

		if(!ObjectUtil.isEmpty(localStorage.getItem("regUserNm"))){
			$('#searchForm').find('#regUserNm').val(localStorage.getItem("regUserNm"));
		}

		if(!ObjectUtil.isEmpty(localStorage.getItem("vocTitle"))){
			$('#searchForm').find('#vocTitle').val(localStorage.getItem("vocTitle"));
		}			

		if(!ObjectUtil.isEmpty(localStorage.getItem("vocActTypeCd1"))){
			$('#searchForm').find('.d-vocActTypeCd1').dropdown('set selected', localStorage.getItem("vocActTypeCd1"));
		}	

		if(!ObjectUtil.isEmpty(localStorage.getItem("vocActTypeCd2"))){
			$('#searchForm').find('.d-vocActTypeCd2').dropdown('set selected', localStorage.getItem("vocActTypeCd2"));
		}			
		
		if(!ObjectUtil.isEmpty(localStorage.getItem("vocActUserNm"))){
			$('#searchForm').find('#vocActUserNm').val(localStorage.getItem("vocActUserNm"));
		}		
		
		if(!ObjectUtil.isEmpty(localStorage.getItem("vocStatusCd"))){
			$('#searchForm').find('.d-vocStatusCd').dropdown('set selected', localStorage.getItem("vocStatusCd"));
		}								

		if(!ObjectUtil.isEmpty(localStorage.getItem("regDtStart"))){
			$('#searchForm').find('#regDtStart').val(localStorage.getItem("regDtStart"));
		}		
		
		if(!ObjectUtil.isEmpty(localStorage.getItem("regDtEnd"))){
			$('#searchForm').find('#regDtEnd').val(localStorage.getItem("regDtEnd"));
		}		
		
		if(!ObjectUtil.isEmpty(localStorage.getItem("vocActDtStart"))){
			$('#searchForm').find('#vocActDtStart').val(localStorage.getItem("vocActDtStart"));
		}				

		if(!ObjectUtil.isEmpty(localStorage.getItem("vocActDtEnd"))){
			$('#searchForm').find('#vocActDtEnd').val(localStorage.getItem("vocActDtEnd"));
		}					

		if(!ObjectUtil.isEmpty(localStorage.getItem("sensSpecYn"))){
			$('#searchForm').find('#sensSpecYn').prop('checked',true);
		}		

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
	param.sensSpecYn = param.sensSpecYn == false ? '' : "Y";
	param.delYn = param.delYn == false ? '' : "Y";
	
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
	
	//조회값 세션 저장
	localStorage.setItem('companyCd', 		$('#searchForm').find('#companyCd').val());
    localStorage.setItem('custNm',          $('#searchForm').find('#custNm').val());
	localStorage.setItem('vocCaseCd',       $('#searchForm').find('#vocCaseCd').val());
	localStorage.setItem('vocTypeCd1',      $('#searchForm').find('#vocTypeCd1').val());
	localStorage.setItem('vocTypeCd2',      $('#searchForm').find('#vocTypeCd2').val());
	localStorage.setItem('vocTypeCd3',      $('#searchForm').find('#vocTypeCd3').val());
	localStorage.setItem('rcptChnnCd',      $('#searchForm').find('#rcptChnnCd').val());
	//localStorage.setItem('sourceCd',        $('#searchForm').find('#sourceCd').val());
	localStorage.setItem('regUserNm',       $('#searchForm').find('#regUserNm').val());
	localStorage.setItem('vocTitle',        $('#searchForm').find('#vocTitle').val());
	localStorage.setItem('vocActTypeCd1',   $('#searchForm').find('#vocActTypeCd1').val());
	localStorage.setItem('vocActTypeCd2',   $('#searchForm').find('#vocActTypeCd2').val());
	localStorage.setItem('vocActUserNm',    $('#searchForm').find('#vocActUserNm').val());
	localStorage.setItem('vocStatusCd',     $('#searchForm').find('#vocStatusCd').val());
	localStorage.setItem('regDtStart',      $('#searchForm').find('#regDtStart').val());
	localStorage.setItem('regDtEnd',        $('#searchForm').find('#regDtEnd').val());
	localStorage.setItem('vocActDtStart',   $('#searchForm').find('#vocActDtStart').val());
	localStorage.setItem('vocActDtEnd',     $('#searchForm').find('#vocActDtEnd').val());
	localStorage.setItem('sensSpecYn',      $('#searchForm').find('#sensSpecYn').val());
	
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

