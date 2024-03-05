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

    setTimeout(function() {
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

    $Grid = gridUtil.loadGrid("listDataTableBbs", gridOptions, url, param);
};

/**
 * 등록/수정을 위한 고객등록화면 이동
 */
let goAddBbs = function(){
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
    localStorage.setItem("backPage", '/bbs/bbslist');
    // 로그인 유저가 작성자인 경우 편집화면, 아닌 경우 조회용 화면 이동
    if($SessionInfo.getUserSeq() == $selectedRowData.regUserNo) goPage('/bbs/bbsdetail');
    else goPage('/bbs/bbsview');
}

