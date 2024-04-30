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
        if($frm.find('.chk-year').hasClass('checked')) {
            $frm.find('.yearDiv').removeClass('blind');
            $frm.find('.monthDiv').addClass('blind');
            $frm.find('.dayDiv').addClass('blind');
            $('th[id=txtTitle]').html('전년대비');
        } else if($frm.find('.chk-month').hasClass('checked')) {
            $frm.find('.yearDiv').addClass('blind');
            $frm.find('.monthDiv').removeClass('blind');
            $frm.find('.dayDiv').addClass('blind');
            $('th[id=txtTitle]').html('전년대비');
        } else if($frm.find('.chk-day').hasClass('checked')) {
            $frm.find('.yearDiv').addClass('blind');
            $frm.find('.monthDiv').addClass('blind');
            $frm.find('.dayDiv').removeClass('blind');
            $('th[id=txtTitle]').html('전년대비');
        }
    });

    // dropbox data setting ----------------
    DropdownUtil.makeCodeList('RCPT_CHNN_CD', $frm.find('.d-rcptChnnCd'));     //접수채널코드
    DropdownUtil.makeCompList($frm.find('.d-companyCd'));   // 회사코드
    makeCodeVocType($('#searchForm'));  //VOC유형코드

    if($SessionInfo.getUserAuth().indexOf('100') < 0 && $SessionInfo.getUserAuth().indexOf('000') < 0) {
		$frm.find('#companyArea').hide();	
	}
	
	//달력설정
	initCalendar();
	
	
    setTimeout(function() {
    	//년도 셋팅   
        let yearDate = new Date();
        yearDate.setFullYear(yearDate.getFullYear() -3);	//년도별 기본값 3년전
    
        $frm.find('#regYyStart').val(yearDate.getFullYear());
	    $frm.find('#regYyEnd').val(Util.getToday().substring(0,4));
    
    	//월별 셋팅
    	let monthDate = new Date();
        monthDate.setMonth(monthDate.getMonth() -12);
        
        var delimiter = "-";
        let bfMonth = monthDate.getFullYear() + delimiter + ("0" + (monthDate.getMonth()+1)).slice(-2);
        
        $frm.find('#regYmStart').val(bfMonth);
	    $frm.find('#regYmEnd').val(Util.getToday().substring(0,4) + "-" + Util.getToday().substring(4,6));
	    

    	//일별 셋팅
    	let dayDate = new Date();
        dayDate.setDate(dayDate.getDate() - 30);
        
        var delimiter = "-";
        let bfDay = dayDate.getFullYear() + delimiter + ("0" + (dayDate.getMonth()+1)).slice(-2) + delimiter + ("0" + dayDate.getDate()).slice(-2);
        
        $frm.find('#regDtStart').val(bfDay);
	    $frm.find('#regDtEnd').val(Util.getToday().substring(0,4) + "-" + Util.getToday().substring(4,6) + "-" + Util.getToday().substring(6,8));	    	
	    
        loadGrid();
    }, 200);
}

let initCalendar = function(){
	
	//년도별 달력(시작)
	$('#yearPickerStart').calendar({
        type: 'year',
	    formatter: {
	        year: 'YYYY' 
	    },
        startCalendar: $('#yearPickerEnd')
        
    });
    
    //년도별 달력(종료)
	$('#yearPickerEnd').calendar({
        type: 'year',
	    formatter: {
	        year: 'YYYY' 
	    },
        startCalendar: $('#yearPickerStart')
        
    });
    
	
	//월별 달력(시작)
	$('#monthPickerStart').calendar({
        type: 'month',
	    formatter: {
	        month: 'YYYY-MM' 
	    },
        startCalendar: $('#monthPickerEnd'),
        text: {
            months: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],
            monthsShort: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],
        }
        
    });
    
    //월별 달력(종료)
	$('#monthPickerEnd').calendar({
        type: 'month',
	    formatter: {
	        month: 'YYYY-MM'
	    },
        startCalendar: $('#monthPickerStart'),
        text: {
            months: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],
            monthsShort: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],
        }
        
    });    
}
/**
 * 그리드 옵션
 */
let numberRender = $.fn.dataTable.render.number(',');   // 숫자 3자리마다 ',' 적용
let GRID_OPTIONS = {
    columns     : [
        { data: 'periodType',       className: "text-center"   },
        { data: 'totalCnt',         className: "text-right"   ,render: numberRender},
        { data: 'totalYoyCnt',      className: "text-right"   ,render:function (data, type, row, meta) {return (data).toFixed(1) + '%'}},
        { data: 'complimentCnt',    className: "text-right"   ,render: numberRender},
        { data: 'complimentRate',   className: "text-right"   ,render:function (data, type, row, meta) {return (data).toFixed(1) + '%'}},
        { data: 'complimentYoyCnt', className: "text-right"   ,render:function (data, type, row, meta) {return (data).toFixed(1) + '%'}},
        { data: 'complaintCnt',     className: "text-right"   ,render: numberRender},
        { data: 'complaintRate',    className: "text-right"   ,render:function (data, type, row, meta) {return (data).toFixed(1) + '%'}},
        { data: 'complaintYoyCnt',  className: "text-right"   ,render:function (data, type, row, meta) {return (data).toFixed(1) + '%'}},
        { data: 'suggestionCnt',    className: "text-right"   ,render: numberRender},
        { data: 'suggestionRate',   className: "text-right"   ,render:function (data, type, row, meta) {return (data).toFixed(1) + '%'}},
        { data: 'suggestionYoyCnt', className: "text-right"   ,render:function (data, type, row, meta) {return (data).toFixed(1) + '%'}},
        { data: 'inquiryCnt',       className: "text-right"   ,render: numberRender},
        { data: 'inquiryRate',      className: "text-right"   ,render:function (data, type, row, meta) {return (data).toFixed(1) + '%'}},
        { data: 'inquiryYoyCnt',    className: "text-right"   ,render:function (data, type, row, meta) {return (data).toFixed(1) + '%'}},
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
        
        if(data.length == 0){
			totalYoyCnt = 0
		}else{
			//전체 전년대비 증감율 평균
	        api.column(2, {search:'applied'}).data().each(function(data,index){
	            totalYoyCnt += parseFloat(data);
	        });
	        totalYoyCnt = (totalYoyCnt / data.length).toFixed(1) + '%';
		}    
        $(api.column(2).footer()).html(totalYoyCnt.toLocaleString());
                
        //칭찬 접수 합계
        api.column(3, {search:'applied'}).data().each(function(data,index){
            complimentCnt += parseFloat(data);
        });
        $(api.column(3).footer()).html(complimentCnt.toLocaleString());
                        
         //칭찬 비율 평균
        if(data.length == 0){
			complimentRate = 0
		}else{
			//칭찬 비율 평균
	        api.column(4, {search:'applied'}).data().each(function(data,index){
	            complimentRate += parseFloat(data);
	        });
	        complimentRate = (complimentRate / data.length).toFixed(1) + '%';
		}        
        $(api.column(4).footer()).html(complimentRate.toLocaleString());
        

        //칭찬 전년대비 증감율 평균
        if(data.length == 0){
			complimentYoyCnt = 0
		}else{
	        //칭찬 전년대비 증감율 평균
	        api.column(5, {search:'applied'}).data().each(function(data,index){
	            complimentYoyCnt += parseFloat(data);
	        });
			
	        complimentYoyCnt = (complimentYoyCnt / data.length).toFixed(1) + '%';
		}        
        $(api.column(5).footer()).html(complimentYoyCnt.toLocaleString());
        
        
        //------------------- 불만 -------------------
        //불만 접수 합계
        api.column(6, {search:'applied'}).data().each(function(data,index){
            complaintCnt += parseFloat(data);
        });
        $(api.column(6).footer()).html(complaintCnt.toLocaleString());
                

        //불만 비율 평균
        if(data.length == 0){
			complaintRate = 0
		}else{
	        //불만 비율 평균
	        api.column(7, {search:'applied'}).data().each(function(data,index){
	            complaintRate += parseFloat(data);
	        });			
	        complaintRate = (complaintRate / data.length).toFixed(1) + '%';
		}        
        $(api.column(7).footer()).html(complaintRate.toLocaleString());        
        
        //불만 전년대비 증감율 평균
        if(data.length == 0){
			complaintYoyCnt = 0
		}else{
	        //불만 전년대비 증감율 평균
	        api.column(8, {search:'applied'}).data().each(function(data,index){
	            complaintYoyCnt += parseFloat(data);
	        });			
	        complaintYoyCnt = (complaintYoyCnt / data.length).toFixed(1) + '%';
		}        
        $(api.column(8).footer()).html(complaintYoyCnt.toLocaleString());        
        
             
        //------------------- 제안 -------------------        
        //제안 접수 합계
        api.column(9, {search:'applied'}).data().each(function(data,index){
            suggestionCnt += parseFloat(data);
        });
        $(api.column(9).footer()).html(suggestionCnt.toLocaleString());
                
        //제안 비율 평균
        if(data.length == 0){
			suggestionRate = 0
		}else{
	        //제안 비율 평균
	        api.column(10, {search:'applied'}).data().each(function(data,index){
	            suggestionRate += parseFloat(data);
	        });			
	        suggestionRate = (suggestionRate / data.length).toFixed(1) + '%';
		}        
        $(api.column(10).footer()).html(suggestionRate.toLocaleString());        
        
        //제안 전년대비 증감율 평균
        if(data.length == 0){
			suggestionYoyCnt = 0
		}else{
	        //제안 전년대비 증감율 평균
	        api.column(11, {search:'applied'}).data().each(function(data,index){
	            suggestionYoyCnt += parseFloat(data);
	        });			
	        suggestionYoyCnt = (suggestionYoyCnt / data.length).toFixed(1) + '%';
		}        
        $(api.column(11).footer()).html(suggestionYoyCnt.toLocaleString());          
        
        
        //------------------- 문의 -------------------
        //문의 접수 합계
        api.column(12, {search:'applied'}).data().each(function(data,index){
            inquiryCnt += parseFloat(data);
        });
        $(api.column(12).footer()).html(inquiryCnt.toLocaleString());


        //문의 비율 평균
        if(data.length == 0){
			inquiryRate = 0
		}else{
	        //문의 비율 평균
	        api.column(13, {search:'applied'}).data().each(function(data,index){
	            inquiryRate += parseFloat(data);
	        });			
	        inquiryRate = (inquiryRate / data.length).toFixed(1) + '%';
		}        
        $(api.column(13).footer()).html(inquiryRate.toLocaleString());        
        
        //문의 전년대비 증감율 평균
        if(data.length == 0){
			inquiryYoyCnt = 0
		}else{
	        //문의 전년대비 증감율 평균
	        api.column(14, {search:'applied'}).data().each(function(data,index){
	            inquiryYoyCnt += parseFloat(data);
	        });			
	        inquiryYoyCnt = (inquiryYoyCnt / data.length).toFixed(1) + '%';
		}        
        $(api.column(14).footer()).html(inquiryYoyCnt.toLocaleString());                  
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
    let regYyStart = $frm.find('#regYyStart').val();
    let regYyEnd = $frm.find('#regYyEnd').val();
    
    let regYmStart = $frm.find('#regYmStart').val();
    let regYmEnd = $frm.find('#regYmEnd').val();
    
    if($frm.find('.chk-year').hasClass('checked')) {
        if(ObjectUtil.isEmpty(regYyStart) || ObjectUtil.isEmpty(regYyEnd)) {
            alert('등록년도를 입력해 주세요.'); return;
        }
        regDtStart = regYyStart + '-01-01';
        regDtEnd = regYyEnd + '-12-31';
        
        
    } else if($frm.find('.chk-month').hasClass('checked')) {
        if(ObjectUtil.isEmpty(regYmStart) || ObjectUtil.isEmpty(regYmEnd)) {
            alert('등록월을 입력해 주세요.'); return;
        }
        regDtStart = regYmStart + '-01';
        regDtEnd = regYmEnd + "-" + Util.getLastday(regYmEnd);
        
        
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

