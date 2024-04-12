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
            $('th[id=txtTitle]').html('전년대비');
        } else if($frm.find('.chk-month').hasClass('checked')) {
            $frm.find('.yearDiv').removeClass('blind');
            $frm.find('.monthDiv').removeClass('blind');
            $frm.find('.dayDiv').addClass('blind');
            $('th[id=txtTitle]').html('전월대비');
        } else if($frm.find('.chk-day').hasClass('checked')) {
            $frm.find('.yearDiv').addClass('blind');
            $frm.find('.monthDiv').addClass('blind');
            $frm.find('.dayDiv').removeClass('blind');
            $('th[id=txtTitle]').html('전일대비');
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
        { data: 'complimentRate',   className: "text-right"   ,render:function (data, type, row, meta) {return (data * 100).toFixed(1) + '%'}},
        { data: 'complimentYoyCnt', className: "text-right"   ,render: numberRender},
        { data: 'complaintCnt',     className: "text-right"   ,render: numberRender},
        { data: 'complaintRate',    className: "text-right"   ,render:function (data, type, row, meta) {return (data * 100).toFixed(1) + '%'}},
        { data: 'complaintYoyCnt',  className: "text-right"   ,render: numberRender},
        { data: 'suggestionCnt',    className: "text-right"   ,render: numberRender},
        { data: 'suggestionRate',   className: "text-right"   ,render:function (data, type, row, meta) {return (data * 100).toFixed(1) + '%'}},
        { data: 'suggestionYoyCnt', className: "text-right"   ,render: numberRender},
        { data: 'inquiryCnt',       className: "text-right"   ,render: numberRender},
        { data: 'inquiryRate',      className: "text-right"   ,render:function (data, type, row, meta) {return (data * 100).toFixed(1) + '%'}},
        { data: 'inquiryYoyCnt',    className: "text-right"   ,render: numberRender},
    ],
    paging: false,
    dom: 'Bftrip',
/*    
    // footer에 합계와 평균 내용을 추가하기 위한 custom option 항목
    footerCalculation :{
        targetSumColIndexs : [1,2,3,5,6,8,9,11,12,14],
        targetAvgColIndexs : [4,7,10,13],
        isNumberFormat  : true,
    },
*/    
    footerCallback: function (row, data, start, end, display ) {
		var api = this.api(), data;
        var totalCnt = 0;
        var totalYoyCnt = 0;
        
        var complimentCnt = 0;
        var complimentYoyCnt = 0;
        var complimentRate = 0;
        var complaintCnt = 0;
        var complaintYoyCnt = 0;
        var complaintRate = 0;
        var suggestionCnt = 0;
        var suggestionYoyCnt = 0;
        var suggestionRate = 0;
        var inquiryCnt = 0;
        var inquiryYoyCnt = 0;
        var inquiryRate = 0;
        
        //전체 접수 합계
        api.column(1, {search:'applied'}).data().each(function(data,index){
            totalCnt += parseFloat(data);
        });
        $(api.column(1).footer()).html(totalCnt.toLocaleString());
        
        //전체 전년대비 합계
        api.column(2, {search:'applied'}).data().each(function(data,index){
            totalYoyCnt += parseFloat(data);
        });
        $(api.column(2).footer()).html(totalYoyCnt.toLocaleString());
        
        //칭찬 접수 합계
        api.column(3, {search:'applied'}).data().each(function(data,index){
            complimentCnt += parseFloat(data);
        });
        $(api.column(3).footer()).html(complimentCnt.toLocaleString());
                
        //칭찬 접수 합계
        api.column(5, {search:'applied'}).data().each(function(data,index){
            complimentYoyCnt += parseFloat(data);
        });
        $(api.column(5).footer()).html(complimentYoyCnt.toLocaleString());        
        
        //칭찬 비율 평균
        if(totalCnt == 0){
			complimentRate = 0
		}else{
	        complimentRate = (complimentCnt / totalCnt).toFixed(3) * 100;
		}        
        $(api.column(4).footer()).html(complimentRate+"%");
        
        
        //불만 접수 합계
        api.column(6, {search:'applied'}).data().each(function(data,index){
            complaintCnt += parseFloat(data);
        });
        $(api.column(6).footer()).html(complaintCnt.toLocaleString());
                
        //불만 접수 합계
        api.column(8, {search:'applied'}).data().each(function(data,index){
            complaintYoyCnt += parseFloat(data);
        });
        $(api.column(8).footer()).html(complaintYoyCnt.toLocaleString());        
        
        //불만 비율 평균
        if(totalCnt == 0){
			complaintRate = 0
		}else{
	        complaintRate = (complaintCnt / totalCnt).toFixed(3) * 100;
		}
        $(api.column(7).footer()).html(complaintRate+"%");
        
        
        //제안 접수 합계
        api.column(9, {search:'applied'}).data().each(function(data,index){
            suggestionCnt += parseFloat(data);
        });
        $(api.column(9).footer()).html(suggestionCnt.toLocaleString());
                
        //제안 접수 합계
        api.column(11, {search:'applied'}).data().each(function(data,index){
            suggestionYoyCnt += parseFloat(data);
        });
        $(api.column(11).footer()).html(suggestionYoyCnt.toLocaleString());        
        
        //제안 비율 평균
        if(totalCnt == 0){
			suggestionRate = 0;
		}else{
	        suggestionRate = (suggestionCnt / totalCnt).toFixed(3) * 100;
		}
        $(api.column(10).footer()).html(suggestionRate+"%");     
        
        
        //문의 접수 합계
        api.column(12, {search:'applied'}).data().each(function(data,index){
            inquiryCnt += parseFloat(data);
        });
        $(api.column(12).footer()).html(inquiryCnt.toLocaleString());
                
        //문의 접수 합계
        api.column(14, {search:'applied'}).data().each(function(data,index){
            inquiryYoyCnt += parseFloat(data);
        });
        $(api.column(14).footer()).html(inquiryYoyCnt.toLocaleString());        
        
        //문의 비율 평균
        if(totalCnt == 0){
			inquiryRate = 0;
		}else{
	        inquiryRate = (inquiryCnt / totalCnt).toFixed(3) * 100;
		}
        $(api.column(13).footer()).html(inquiryRate+"%");                          
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
    
	if($frm.find('.chk-year').hasClass('checked')){
		param.periodType = "Y";	
	}else if($frm.find('.chk-month').hasClass('checked')){
		param.periodType = "M";	
	}else if($frm.find('.chk-day').hasClass('checked')){
		param.periodType = "D";	
	}
    param.regDtStart = regDtStart;
    param.regDtEnd = regDtEnd;
    console.log('param',param);

    $Grid = gridUtil.loadGrid("listDataTablePeriod", gridOptions, url, param);
};

