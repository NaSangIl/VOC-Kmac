let $Grid        = null;

$(function () {
    // 초기 설정 및 수행
    init();
});

/**
 * 초기 설정 및 수행 내용
 */
let init = function(){
    // 초기 화면 구성 ----------------
    
    // event 연결 ----------------
    $('.btn-search').on('click', function(){ loadGrid(); });
    $('.btn-wrap .btn-add').on('click', function(){ goAddBbs(); });
    $('.btn-wrap .btn-dtl').on('click', function(){ goDtlBbs(); });

    //더블클릭시 상세화면 이동
    $('#listDataTableBbs tbody').on('dblclick', 'tr', function (e) {
        goDtlBbs();
    });

    // dropbox data setting ----------------
    DropdownUtil.makeCompList($('#searchForm').find('.d-companyCd'));
	
	//시스템운영자, 시스템관리자인 경우
    if ($SessionInfo.getUserAuth().indexOf('100') > -1 || $SessionInfo.getUserAuth().indexOf('000') > -1) {
		$('#companyArea', $('#searchForm')).removeClass('blind');
		$('.btn-add').removeClass('blind');
	
	
	//관리자인 경우
	}else if($SessionInfo.getUserAuth().indexOf('200') > -1 ){
		
		$('#companyArea', $('#searchForm')).addClass('blind');
			
	//그외
	}else{
		$('#companyArea', $('#searchForm')).addClass('blind');
		$('.btn-add').addClass('blind');
	}
    
    
    
    setTimeout(function() {
		if(!ObjectUtil.isEmpty(localStorage.getItem("companyCd"))){
			$('#searchForm').find('.d-companyCd').dropdown('set selected', localStorage.getItem("companyCd"));
		}	
		
		if(!ObjectUtil.isEmpty(localStorage.getItem("title"))){
			$('#searchForm').find('#title').val(localStorage.getItem("title"));
		}	

		if(!ObjectUtil.isEmpty(localStorage.getItem("regDtStart"))){
			$('#searchForm').find('#regDtStart').val(localStorage.getItem("regDtStart"));
		}
		
		if(!ObjectUtil.isEmpty(localStorage.getItem("regDtEnd"))){
			$('#searchForm').find('#regDtEnd').val(localStorage.getItem("regDtEnd"));
		}
				
        loadGrid();
    }, 200);
}

/**
 * 그리드 옵션
 */
let GRID_OPTIONS = {
    columns     : [
        { data: 'bbsSeq',    className: "select-checkbox",
            'render': function (data, type, full, meta) {
                return '<input type="radio" value="'+data+'" />';
            }
        },
        { data: 'bbsSeq',       className: "text-center"   },
        { data: 'title',        className: "text-center"   },
        { data: 'regUserNm',    className: "text-center"   },
        { data: 'regDt',        className: "text-center"   },
        { data: 'hit',          className: "text-center"   },
        { data: 'commentsCnt',  className: "text-center"   },
    ],
};

/**
 * Grid 구성
 */
let loadGrid = function(){
    let gridOptions = $.extend(true, {}, GRID_OPTIONS);
    let url = '/kmacvoc/v1/bbs/list';
    let param = $('#searchForm').form('get.values');
    //날짜포맷 변경되어 별도 셋팅
    param.regDtStart = $('#searchForm').find('#regDtStart').val();
    param.regDtEnd = $('#searchForm').find('#regDtEnd').val();

	localStorage.setItem("companyCd", $('#searchForm').find('#companyCd').val());
	localStorage.setItem("title", $('#searchForm').find('#title').val());
	localStorage.setItem("regDtStart", $('#searchForm').find('#regDtStart').val());
	localStorage.setItem("regDtEnd", $('#searchForm').find('#regDtEnd').val());
	
    $Grid = gridUtil.loadGrid("listDataTableBbs", gridOptions, url, param);
};

/**
 * 등록/수정을 위한 고객등록화면 이동
 */
let goAddBbs = function(){
	localStorage.setItem("bbsSeq", 0);
	localStorage.setItem("companyCd", $('#searchForm').find('#companyCd').val());
    goPage('/bbs/bbsdetail');
}

/**
 * 회사 상세화면 이동
 */
let goDtlBbs =  function(){
    if($selectedRowData.bbsSeq == undefined) {
        alert('목록을 선택해 주세요.');
        return;
    }
    localStorage.setItem("bbsSeq", $selectedRowData.bbsSeq);
    localStorage.setItem("companyCd", $('#searchForm').find('#companyCd').val());
	localStorage.setItem("title", $('#searchForm').find('#title').val());
	localStorage.setItem("regDtStart", $('#searchForm').find('#regDtStart').val());
	localStorage.setItem("regDtEnd", $('#searchForm').find('#regDtEnd').val());
	
    localStorage.setItem("backPage", '/bbs/bbslist');
    
    goPage('/bbs/bbsview');
}

