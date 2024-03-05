let $Grid        = null;

$(function () {
    // 초기 설정 및 수행
    init();
});

/**
 * 초기 설정 및 수행 내용
 */
let init = function(){
    let $frm = $('#searchForm');

    // event 연결 ----------------
    $('.btn-search').on('click', function(){ loadGrid(); });

    // 조회 일자 조건 컨트롤
    $('.ui.radio').on('click', function(){
        $frm.find('.d-year').dropdown('clear');
        $frm.find('.d-month').dropdown('clear');
        $frm.find('#regDtStart').val('');
        $frm.find('#regDtEnd').val('');

        if($frm.find('.chk-year').hasClass('checked')) {
            $frm.find('.yearDiv').removeClass('blind');
            $frm.find('.monthDiv').addClass('blind');
            $frm.find('.dayDiv').addClass('blind');
        } else if($frm.find('.chk-month').hasClass('checked')) {
            $frm.find('.yearDiv').removeClass('blind');
            $frm.find('.monthDiv').removeClass('blind');
            $frm.find('.dayDiv').addClass('blind');
        } else if($frm.find('.chk-day').hasClass('checked')) {
            $frm.find('.yearDiv').addClass('blind');
            $frm.find('.monthDiv').addClass('blind');
            $frm.find('.dayDiv').removeClass('blind');
        }
    });

    // dropbox data setting ----------------
    DropdownUtil.makeCodeList('RCPT_CHNN_CD', $frm.find('.d-rcptChnnCd'));     //접수채널코드
    DropdownUtil.makeYearList($frm.find('.d-year'));        // 년도
    DropdownUtil.makeMonthList($frm.find('.d-month'));      // 월
    DropdownUtil.makeCompList($frm.find('.d-companyCd'));   // 회사코드
    makeCodeVocType($('#searchForm'));  //VOC유형코드

    setTimeout(function() {
        let y = Util.getToday().substring(0,4);
        $('.d-year').dropdown('set selected', y);
        loadGrid();
    }, 200);
}

/**
 * 그리드 옵션
 */
let numberRender = $.fn.dataTable.render.number(',');   // 숫자 3자리마다 ',' 적용
let GRID_OPTIONS = {
    columns     : [
        { data: 'periodType',       className: "text-center"   },
        { data: 'totalCnt',         className: "text-right"   ,render: numberRender},
        { data: 'totalYoyCnt',      className: "text-right"   ,render: numberRender},
        { data: 'complimentCnt',    className: "text-right"   ,render: numberRender},
        { data: 'complimentRate',   className: "text-right"   },
        { data: 'complimentYoyCnt', className: "text-right"   ,render: numberRender},
        { data: 'complaintCnt',     className: "text-right"   ,render: numberRender},
        { data: 'complaintRate',    className: "text-right"   },
        { data: 'complaintYoyCnt',  className: "text-right"   ,render: numberRender},
        { data: 'suggestionCnt',    className: "text-right"   ,render: numberRender},
        { data: 'suggestionRate',   className: "text-right"   },
        { data: 'suggestionYoyCnt', className: "text-right"   ,render: numberRender},
        { data: 'inquiryCnt',       className: "text-right"   ,render: numberRender},
        { data: 'inquiryRate',      className: "text-right"   },
        { data: 'inquiryYoyCnt',    className: "text-right"   ,render: numberRender},
    ],
    paging: false,
    dom: 'Bftrip',
    // footer에 합계와 평균 내용을 추가하기 위한 custom option 항목
    footerCalculation   : {
        targetSumColIndexs : [1,2,3,5,6,8,9,11,12,14],
        targetAvgColIndexs : [4,7,10,13],
        isNumberFormat  : true,
    },
    buttons: [
        {
            extend: 'excel',
            text: '엑셀다운로드',
            filename: '기간별VOC현황-' + Util.getToday(),
            title: '기간별VOC현황 : '+ Util.getToday(),
        },
    ] ,
};

/**
 * Grid 구성
 */
let loadGrid = function(){
    let $frm = $('#searchForm');
    let gridOptions = $.extend(true, {}, GRID_OPTIONS);
    let url = '/kmacvoc/v1/statistics/list/period';
    let param = $('#searchForm').form('get.values');

    if(ObjectUtil.isEmpty($frm.find('#companyCd').val())) {
        alert('회사를 선택해 주세요.'); return;
    }

    //조회일자조건 셋팅
    let regDtStart, regDtEnd;
    let year = $frm.find('#year').val();
    let month = $frm.find('#month').val();
    if($frm.find('.chk-year').hasClass('checked')) {
        if(ObjectUtil.isEmpty(year)) {
            alert('등록년도를 입력해 주세요.'); return;
        }
        regDtStart = year + '-01-01';
        regDtEnd = year + '-12-31';
    } else if($frm.find('.chk-month').hasClass('checked')) {
        if(ObjectUtil.isEmpty(year)) {
            alert('등록년도를 입력해 주세요.'); return;
        }
        if(ObjectUtil.isEmpty(month)) {
            alert('등록월을 입력해 주세요.'); return;
        }
        regDtStart = year + '-' + month + '-01';
        let last = new Date(year, month, 0);
        let day = last.getDate();
        regDtEnd = year + '-' + month + '-' + day;
    } else if($frm.find('.chk-day').hasClass('checked')) {
        regDtStart = $frm.find('#regDtStart').val();
        regDtEnd = $frm.find('#regDtEnd').val();
        if(ObjectUtil.isEmpty(regDtStart)) {
            alert('등록일을 입력해 주세요.'); return;
        }
        if(ObjectUtil.isEmpty(regDtEnd)) {
            alert('등록일을 입력해 주세요.'); return;
        }
    }
    param.regDtStart = regDtStart;
    param.regDtEnd = regDtEnd;
    console.log('param',param);

    $Grid = gridUtil.loadGrid("listDataTablePeriod", gridOptions, url, param);
};

