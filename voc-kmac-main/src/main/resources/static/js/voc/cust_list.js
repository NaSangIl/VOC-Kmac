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
    $('.btn-wrap .btn-add').on('click', function(){ goAddCust(); });
    $('.btn-wrap .btn-dtl').on('click', function(){ goDtlCust(); });

    //더블클릭시 상세화면 이동
    $('#listDataTableCust tbody').on('dblclick', 'tr', function (e) {
        goDtlCust();
    });

    // dropbox data setting ----------------
    DropdownUtil.makeCompList($('#searchForm').find('.d-companyCd'));

    setTimeout(function() {
        loadGrid();
    }, 200);
}

/**
 * 그리드 옵션
 */
let GRID_OPTIONS = {
    columns     : [
        { data: 'custNo',   className: "text-center"   },
        { data: 'custNm',   className: "text-center"   },
        { data: 'telNo',   className: "text-center"   },
        { data: 'emailAddr',   className: "text-center"   },
        { data: 'regDt',   className: "text-center"   },
        { data: 'vocCnt',   className: "text-center"   },
    ],
};

/**
 * Grid 구성
 */
let loadGrid = function(){
    let gridOptions = $.extend(true, {}, GRID_OPTIONS);
    let url = '/kmacvoc/v1/customer/list';
    let param = $('#searchForm').form('get.values');
    //날짜포맷 변경되어 별도 셋팅
    param.regDtStart = $('#searchForm').find('#regDtStart').val();
    param.regDtEnd = $('#searchForm').find('#regDtEnd').val();
    param.vocCntSortYn = param.vocCntSortYn == false ? '' : "Y";
    
    $Grid = gridUtil.loadGrid("listDataTableCust", gridOptions, url, param);
};

/**
 * 등록/수정을 위한 고객등록화면 이동
 */
let goAddCust = function(){
	localStorage.removeItem('custSeq');
    goPage('/voc/custdetail');
}

/**
 * 회사 상세화면 이동
 */
let goDtlCust =  function(){
    localStorage.setItem("custSeq", $selectedRowData.custSeq);
    goPage('/voc/custdetail');
}

