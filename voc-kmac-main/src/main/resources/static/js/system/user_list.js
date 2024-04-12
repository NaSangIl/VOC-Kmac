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

    // dropbox data setting ----------------
    DropdownUtil.makeCompList($('#searchForm').find('.d-companyCd'));   //회사코드
    DropdownUtil.makeCodeList('EMPLOYMENT_YN', $('#searchForm').find('.d-employmentYn'));  //재직여부

	
	
    // event 연결 ----------------
    $('.btn-search').on('click', function(){ loadGrid(); });
    $('.btn-wrap .btn-add').on('click', function(){ goAddUser(); });     // 등록
    $('.btn-wrap .btn-dtl').on('click', function(){ goDtlUser(); });     // 상세

    //더블클릭시 상세화면 이동
    $('#listDataTableUser tbody').on('dblclick', 'tr', function (e) {
        goDtlUser();
    });

    setTimeout(function() {
		$('#searchForm').find('.d-employmentYn').dropdown('set selected', 'Y');
        loadGrid();
    }, 200);
}

/**
 * 그리드 옵션
 */
let GRID_OPTIONS = {
    columns     : [
        { data: 'companyNm',className: "text-center"   },
        { data: 'userId',   className: "text-center"   },
        { data: 'userNm',   className: "text-center"   },
        { data: 'deptNm',   className: "text-center"   },
        { data: 'titleNm',  className: "text-center"   },
        { data: 'telNo',   className: "text-center"   },
        { data: 'emailAddr',   className: "text-center"   },
        { data: 'employmentYn',   className: "text-center"   },
        { data: 'regUserNm',   className: "text-center" },
        { data: 'regDt',   className: "text-center" },
        { data: 'modUserNm',   className: "text-center" },
        { data: 'modDt',   className: "text-center" },
    ],
    scrollX: '100%',
    scrollXInner: '1800px',
};

/**
 * Grid 구성
 */
let loadGrid = function(){
    let gridOptions = $.extend(true, {}, GRID_OPTIONS);
    let url = '/kmacvoc/v1/user/list';
    let param = $('#searchForm').form('get.values');

    $Grid = gridUtil.loadGrid("listDataTableUser", gridOptions, url, param);
};

/**
 * 등록/수정을 위한 사용자 등록화면 이동
 */
let goAddUser = function(){
	localStorage.removeItem('userSeq');
    goPage('/system/userdetail');
}

/**
 * 사용자 상세화면 이동
 */
let goDtlUser =  function(){
    localStorage.setItem("userSeq", $selectedRowData.userSeq);
    goPage('/system/userdetail');
}
