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

    // dropbox data setting ----------------
    DropdownUtil.makeCodeList('RCPT_CHNN_CD', $frm.find('.d-rcptChnnCd'));     //접수채널코드
    DropdownUtil.makeCompList($frm.find('.d-companyCd'));   // 회사코드
    makeCodeVocType($('#searchForm'));      //VOC유형코드
    makeCodeVocActType($('#searchForm'));   //처리유형코드

    if($SessionInfo.getUserAuth().indexOf('100') < 0 && $SessionInfo.getUserAuth().indexOf('000') < 0) {
		$frm.find('#companyArea').hide();	
	}

    setTimeout(function() {

		let date = new Date();
        date.setMonth(date.getMonth() -12);
        
        var delimiter = "-";
        let bfday = date.getFullYear() + delimiter + ("0" + (date.getMonth()+1)).slice(-2) + delimiter + ("0" + date.getDate()).slice(-2);
        		
        $('#searchForm').find('#regDtStart').val(bfday);
        $('#searchForm').find('#regDtEnd').val(Util.getToday('-'));
        loadGrid();
    }, 200);
}

/**
 * 그리드 옵션
 */
let numberRender = $.fn.dataTable.render.number(',');   // 숫자 3자리마다 ',' 적용
let columns = [
        { data: 'actPeriod',       className: "text-center"   },
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
	];
	
let GRID_OPTIONS = {
    columns     : columns,
    paging: false,
    dom: 'Bftrip',
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
        
        
        //------------------- 칭찬 -------------------
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
    buttons             : [
        {
            extend: 'excel',
            text: '엑셀다운로드',
            filename: '처리기간별VOC현황-' + Util.getToday(),
            title: '처리기간별VOC현황 : '+ Util.getToday(),
        },
    ] ,
};

/**
 * Grid 구성
 */
let loadGrid = function(){
    let $frm = $('#searchForm');

    if(ObjectUtil.isEmpty($frm.find('#companyCd').val())) {
        alert('회사를 선택해 주세요.'); return;
    }
    if(ObjectUtil.isEmpty($frm.find('#regDtStart').val()) || ObjectUtil.isEmpty($frm.find('#regDtEnd').val())) {
        alert('등록일을 선택해 주세요.'); return;
    }

    let gridOptions = $.extend(true, {}, GRID_OPTIONS);
    let url = '/kmacvoc/v1/statistics/list/actperiod';
    let param = $('#searchForm').form('get.values');

    param.regDtStart = $frm.find('#regDtStart').val();
    param.regDtEnd = $frm.find('#regDtEnd').val();

    console.log('param',param);

    $Grid = gridUtil.loadGrid("listDataTableActperiod", gridOptions, url, param);
};

