$(function () {
    // 초기 설정 및 수행
    init();
    
});

/**
 * 초기 설정 및 수행 내용
 */
let init = function(){
	
	let $frm = $('.search-area');
	
	// dropbox data setting ----------------
    DropdownUtil.makeYearList($frm.find('.d-year'));        // 년도
    DropdownUtil.makeMonthList($frm.find('.d-month'));      // 월
    DropdownUtil.makeYearList($frm.find('.d-year2'));       // 년도
    DropdownUtil.makeMonthList($frm.find('.d-month2'));     // 월
    
    //공지사항 더보기
    $('#noticeMoreBtn').on('click', function(){ goPage('/bbs/bbslist') });
    
    setTimeout(function() {
		
        // 캐쉬값 존재 시 조회조건 셋팅
        let queryString = JSON.parse(localStorage.getItem("queryString"));
        let stYy,stMm,edYy,edMm;
        let date = new Date();
        date.setMonth(date.getMonth() -6);
        var delimiter = "";
        let bfday = date.getFullYear() + delimiter + ("0" + (date.getMonth()+1)).slice(-2) + delimiter + ("0" + date.getDate()).slice(-2);
    
    	let today = Util.getToday();
    
        stYy = bfday.substring(0,4);
        stMm = bfday.substring(4,6);
        edYy = today.substring(0,4);
        edMm = today.substring(4,6);
        
        $('.d-year').dropdown('set selected', stYy);
        $('.d-month').dropdown('set selected', stMm);
        $('.d-year2').dropdown('set selected', edYy);
        $('.d-month2').dropdown('set selected', edMm);
        
        searchData();
        
        //VOC현황 클릭
	    $('#allCntSum').on('click', function(){ fnVocMove('all') });
	    $('#complaintCntSum').on('click', function(){fnVocMove('01')});
	    $('#complimentCntSum').on('click', function(){fnVocMove('02')});
	    $('#suggestionCntSum').on('click', function(){fnVocMove('03')});
	    $('#inquiryCntSum').on('click', function(){fnVocMove('04')});
	    $('#sensSpecCntSum').on('click', function(){fnVocMove('sens')});
	    
	    
    }, 200);
}


function fnVocMove(callTp){
	
	//일반사용자인 경우 페이지 이동 제한.
    if($SessionInfo.getUserAuth().indexOf('900') > -1 && $SessionInfo.getUserAuth().length == 3){
		return false;
	}
	
	let stYy = $('#years').val();
    let stMm = $('#months').val();
    let edYy = $('#years2').val();
    let edMm = $('#months2').val();
    let companyCd = $SessionInfo.getCompanyCd();
    
    var regDtStart = stYy+ '-' +stMm + "-01"; 
    var regDtEnd = edYy+ '-' +edMm + "-01"; 
    
    regDtEnd = edYy+ '-' +edMm +"-"+Util.getLastday(regDtEnd);
	
	localStorage.setItem("regDtStart", regDtStart);
	localStorage.setItem("regDtEnd", regDtEnd);
	localStorage.setItem("companyCd", companyCd);
	
	//voc유형
	if(callTp !='sens' && callTp !='all'){
		localStorage.setItem("vocCaseCd", callTp);
		
	//민감
	}else if(callTp == 'sens'){
		localStorage.setItem("sensSpecYn", 'Y');
	}
		
	goPage('/voc/voclist');	
	
}

let searchData = function() {
		
	// button 연결 ----------------
    $('.btn-search').on('click', function(){ searchData(); });
    
	let companyCd = $SessionInfo.getCompanyCd();
    let stYy = $('#years').val();
    let stMm = $('#months').val();
    let edYy = $('#years2').val();
    let edMm = $('#months2').val();

    if(ObjectUtil.isEmpty(stYy) || ObjectUtil.isEmpty(stMm)
    || ObjectUtil.isEmpty(edYy) || ObjectUtil.isEmpty(edMm)){
        alert('기간을 선택해 주세요.');
        return;
    }
    
    let regDtStart = stYy + '-' + (ObjectUtil.isEmpty(stMm) ? '01' : stMm) + '-' + '01';
    
    let regDtEnd = edYy + '-' + (ObjectUtil.isEmpty(edMm) ? '12' : edMm) + '-' + '01';

    let param = {'companyCd':companyCd, 'regDtStart':regDtStart, 'regDtEnd':regDtEnd};
    
    localStorage.setItem("queryString", JSON.stringify({'stYy':stYy, 'stMm':stMm, 'edYy':edYy, 'edMm':edMm}));

    //VOC현황 조회
    AjaxUtil.get(
        '/kmacvoc/v1/main/voc-progress',
        param,
        function(result){
            if(result && result.data){
                
                let dataList = result.data;
                
                
                //메인 차트
    			mainLineChart(result.data);
    			
                var allCntSum = 0;
                var complaintCntSum = 0;
                var complimentCntSum = 0;
                var suggestionCntSum = 0;
                var inquiryCntSum = 0;
                var sensSpecCntSum = 0;
                
                for(var i=0; i<dataList.length; i++){
					allCntSum += dataList[i].allCnt; // 전체 합계
					complaintCntSum += dataList[i].complaintCnt; // 불만 합계
					complimentCntSum += dataList[i].complimentCnt; // 칭찬 합계
					suggestionCntSum += dataList[i].suggestionCnt; // 제안 합계
					inquiryCntSum += dataList[i].inquiryCnt; // 문의 합계
					sensSpecCntSum += dataList[i].sensSpecCnt; // 민감/특이 합계
				}
				
				$('#allCntSum').text(Util.numberFormat(allCntSum));
				$('#complaintCntSum').text(Util.numberFormat(complaintCntSum));
				$('#complimentCntSum').text(Util.numberFormat(complimentCntSum));
				$('#suggestionCntSum').text(Util.numberFormat(suggestionCntSum));
				$('#inquiryCntSum').text(Util.numberFormat(inquiryCntSum));
				$('#sensSpecCntSum').text(Util.numberFormat(sensSpecCntSum));
				
                
            }
        }
    );	
	
	//VOC처리현황 조회
    AjaxUtil.get(
        '/kmacvoc/v1/main/voc-ActProgress',
        param,
        function(result){
            if(result && result.data){
                let dataList = result.data;
                $('#actTime').text(dataList.actTime);
                $('#nonActCnt').text(dataList.nonActCnt);
                $('#actRate').text(dataList.actRate);
                
               	$('#nonActCnt').on('click', function(){
					fnVocMove('sens');
				});
            }
        }
    );	
    
	//VOC유형별 접수현황 조회 (대분류)
    AjaxUtil.get(
        '/kmacvoc/v1/main/voc-TypeStates1',
        param,
        function(result){
            if(result && result.data){
                let dataList = result.data;
                
                //VOC유형별 접수현황 조회 (대분류)-차트생성
    			mainBarChartFn1(dataList, regDtStart, regDtEnd);
    			
    			$('#vocTypeNm1').text(dataList[0].vocTypeNm1);
    			//VOC유형별 접수현황 조회 (중분류)
    			mainBarChartFn2(dataList[0].vocTypeCd1, regDtStart, regDtEnd);
            }
        }
    );	    
	
	
	//VOC처리유형별 접수현황 조회 (대분류)
    AjaxUtil.get(
        '/kmacvoc/v1/main/voc-ActTypeStates1',
        param,
        function(result){
            if(result && result.data){
                let dataList = result.data;
    			mainBarChartFn4(dataList, regDtStart, regDtEnd);
    			
    			$('#vocActTypeNm1').text(dataList[0].vocActTypeNm1);
    			mainBarChartFn5(dataList[0].vocActTypeCd1, regDtStart, regDtEnd);
            }
        }
    );	 	
    

	//공지사항 목록 조회
    AjaxUtil.get(
        '/kmacvoc/v1/main/mian-List',
        param,
        function(result){
            if(result && result.data){
                let dataList = result.data;
    			
    			var sHtml = "";
    			
    			for(var i=0; i<dataList.length; i++){
					sHtml += '<li>';
	                sHtml += '    <p class="notice-title"><a href="javascript:fnNoticeDtlPop('+dataList[i].bbsSeq+');" id="noticePopup">'+dataList[i].title+'</a></p>';
	                sHtml += '    <div class="notice-info">';
	                sHtml += '        <span class="notice-writer">'+dataList[i].regUserNm+'</span>';
	                sHtml += '        <span class="notice-date">'+dataList[i].regDt+'<span>';
	                sHtml += '    </div>';
	                sHtml += '</li>';	
				}
    			$('#noticeList').html(sHtml);
            }
        }
    );	 	    
}

/* 공지사항 상세 */
function fnNoticeDtlPop(bbsSeq){
    window.open("/bbs/bbsviewpop?bbsSeq="+bbsSeq, "", "width=1020, height=855");
}

//메인 차트
function mainLineChart(dataList) {
	
	var ymTp = new Array();	//기준년월
	var complaintCnt = new Array();	//불만
	var complimentCnt = new Array();	//칭찬
	var suggestionCnt = new Array();	//제안
	var inquiryCnt = new Array();	//문의
	
	//데이터 분할
	for(var i=0; i<dataList.length; i++){
		ymTp.push(dataList[i].ymTp);
		complaintCnt.push(dataList[i].complaintCnt);
		complimentCnt.push(dataList[i].complimentCnt);
		suggestionCnt.push(dataList[i].suggestionCnt);
		inquiryCnt.push(dataList[i].inquiryCnt);
	}
	
    //차트 색상 지정
    Highcharts.setOptions({
         colors: [' #e15858','#5f72f6','#18b69c','#f2bb24']
    });
  
    Highcharts.chart('mainLineChart', {
        chart: {
            type: 'line',
            height:'380px',
            margin: [50, 0, 60, 30],
            backgroundColor:'rgba(255, 255, 255, 0)',
        },
        title: {
            text: ''
        },
        subtitle: {
            text: ''
        },
        credits: {enabled: false},
        legend: {
            align: 'right',
            verticalAlign: 'top',
            x: 15,
            y: 0,
            itemStyle: {
                color: '#333',
                fontSize: '15px',
                fontWeight:'400'
            },
            symbolWidth: 13,
            symbolHeight: 13,
            symbolPadding: 5,
        },
        xAxis: {
            categories: ymTp,
            labels: {
                  y:23,
                  x:0,
                align: 'center',
                 style: {
                    color:' #888',
                    fontSize: '13px',
                    fontWeight:'400'
                 },
             },
             lineColor:'#e0e0e0',
        },
        yAxis: {
            title: {
                align: 'high',
                offset: 0,
                text: '(건)',
                rotation: 0,
                x: -5,
                y: -10,
                style: {
                    color:'#bbb',
                    fontSize: '13px',
                    fontWeight:'400'
                 },
            },
            min: 0,
            max: 40,
            tickInterval:5,
            lineWidth: 1,
            lineColor: '#e0e0e0',
            gridLineColor:'#e0e0e0',
            labels: {
                x:-8,
                y:8,
                style: {
                    color:'#bbb',
                    fontSize: '13px',
                    fontWeight:'400'
                 },
            },
           
        },
        plotOptions: {
            line: {
                marker: {
                    symbol: "circle",
                    radius: 4.5,
                    lineColor: "#fff",
                    lineWidth: 2,
                },
                dataLabels: {
                    enabled: false
                },
                lineWidth: 3
            }
        },
        tooltip: {
            format: '<span style="color:#333; font-size:13px; font-weight:bold; margin-bottom:10px;">{key}</span><br><br>'+
            '<span style="color:#333; font-size:13px">{point.y}<span>'
        },
        series: [{
            name: '불만',
            data: complaintCnt,
            legend: {
                itemStyle: {
                    color: 'red',
                    fontSize: '15px',
                    fontWeight:'400'
                },
            },
            legendSymbol: 'rectangle',
        }, {
            name: '칭찬',
            data: complimentCnt,
            legendSymbol: 'rectangle',
        }, {
            name: '제안',
            data: suggestionCnt,
            legendSymbol: 'rectangle',
        }, {
            name: '문의',
            data: inquiryCnt,
            legendSymbol: 'rectangle',
        }]
    });
        
}
function mainBarChartFn1(dataList, regDtStart, regDtEnd) {
	
	var vocTypeCd1 = new Array();	//1Lev유형코드
	var vocTypeNm1 = new Array();	//1Lev유형명
	var receiptCnt = new Array();	//접수건수
	
	//데이터 분할
	for(var i=0; i<dataList.length; i++){
		vocTypeCd1.push(dataList[i].vocTypeCd1);
		vocTypeNm1.push(dataList[i].vocTypeNm1);
		receiptCnt.push(dataList[i].receiptCnt);
	}
	
    Highcharts.setOptions({
        colors: ['#4e6091']
    });

    Highcharts.chart('mainBarChart1', {
        chart: {
            type: 'column',
            height:'340px',
            margin: [30, 0, 30, 30],
            backgroundColor:'rgba(255, 255, 255, 0)',
        },
        title: {
            text: ''
        },
        subtitle: {
            text: ''
        },
        credits: {enabled: false},
        legend: {enabled:false},
        xAxis: {
            categories: vocTypeNm1,
            labels: {
                  y:23,
                  x:0,
                align: 'center',
                 style: {
                    color:' #888',
                    fontSize: '13px',
                    fontWeight:'400'
                 },
             },
             lineColor:'#e0e0e0',
        },
        yAxis: {
            title: {
                align: 'high',
                offset: 0,
                text: '(건)',
                rotation: 0,
                x: -5,
                y: -10,
                style: {
                    color:'#bbb',
                    fontSize: '13px',
                    fontWeight:'400'
                 },
            },
            min: 0,
            tickInterval:10,
            lineWidth: 1,
            lineColor: '#e0e0e0',
            gridLineWidth:0,
            labels: {
                x:-8,
                y:8,
                style: {
                    color:'#bbb',
                    fontSize: '13px',
                    fontWeight:'400',
                 },
            },
           
        },
        plotOptions: {
            line: {
                marker: {
                    symbol: "circle",
                    radius: 4.5,
                    lineColor: "#fff",
                    lineWidth: 2,
                },
                dataLabels: {
                    enabled: false
                },
                lineWidth: 3
            }
        },
        tooltip: {
            format: '<span style="color:#333; font-size:13px; font-weight:bold; margin-bottom:10px;">{key}</span><br><br>'+
            '<span style="color:#333; font-size:13px">{point.y} 건<span>'
        },
        series: [{
            maxPointWidth: 34,
            data: receiptCnt,
            dataLabels: {
                enabled: true,
                colorByPoint: true,
                rotation: -0,
                color: '#4e6091',
                align: 'center',
                y: 1, 
                style: {
                    fontSize: '14px',
                    fontWeight:'500',
                }
            },
            events:{  // 이벤트
				click: function ( event ){ // 클릭이벤트 
				
					$('#vocTypeNm1').text(vocTypeNm1[event.point.index]);
					//중분류 VOC유형별 접수현황 조회
					mainBarChartFn2(vocTypeCd1[event.point.index], regDtStart, regDtEnd);
				}
        	}
        }]
    });
}

function mainBarChartFn2(vocTypeCd1, regDtStart, regDtEnd) {
	
	let companyCd = $SessionInfo.getCompanyCd();
    let param = {'companyCd':companyCd, 'regDtStart':regDtStart, 'regDtEnd':regDtEnd, 'vocTypeCd1':vocTypeCd1};
    
	//VOC처리현황 조회
    AjaxUtil.get(
        '/kmacvoc/v1/main/voc-TypeStates2',
        param,
        function(result){
            if(result && result.data){
                let dataList = result.data;
    			
				var vocTypeCd2 = new Array();	//2Lev유형코드
				var vocTypeNm2 = new Array();	//2Lev유형명
				var receiptCnt = new Array();	//접수건수
				
				//데이터 분할
				for(var i=0; i<dataList.length; i++){
					vocTypeCd2.push(dataList[i].vocTypeCd2);
					vocTypeNm2.push(dataList[i].vocTypeNm2);
					receiptCnt.push(dataList[i].receiptCnt);
				}
				
    			Highcharts.setOptions({
			        colors: ['#b9c1d8']
			    });
			
			    Highcharts.chart('mainBarChart2', {
			        chart: {
			            type: 'column',
			            height:'315px',
			            margin: [30, 0, 30, 30],
			            backgroundColor:'rgba(255, 255, 255, 0)',
			        },
			        title: {
			            text: ''
			        },
			        subtitle: {
			            text: ''
			        },
			        credits: {enabled: false},
			        legend: {enabled:false},
			        xAxis: {
			            categories: vocTypeNm2,
			            labels: {
			                  y:23,
			                  x:0,
			                align: 'center',
			                 style: {
			                    color:' #888',
			                    fontSize: '13px',
			                    fontWeight:'400'
			                 },
			             },
			             lineColor:'#e0e0e0',
			        },
			        yAxis: {
			            title: {
			                align: 'high',
			                offset: 0,
			                text: '(건)',
			                rotation: 0,
			                x: -5,
			                y: -10,
			                style: {
			                    color:'#bbb',
			                    fontSize: '13px',
			                    fontWeight:'400'
			                 },
			            },
			            min: 0,
			            tickInterval:5,
			            lineWidth: 1,
			            lineColor: '#e0e0e0',
			            gridLineWidth:0,
			            labels: {
			                x:-8,
			                y:8,
			                style: {
			                    color:'#bbb',
			                    fontSize: '13px',
			                    fontWeight:'400'
			                 },
			            },
			           
			        },
			        plotOptions: {
			            line: {
			                marker: {
			                    symbol: "circle",
			                    radius: 4.5,
			                    lineColor: "#fff",
			                    lineWidth: 2,
			                },
			                dataLabels: {
			                    enabled: false
			                },
			                lineWidth: 3
			            }
			        },
			        tooltip: {
			            format: '<span style="color:#333; font-size:13px; font-weight:bold; margin-bottom:10px;">{key}</span><br><br>'+
			            '<span style="color:#333; font-size:13px">{point.y} 건<span>'
			        },
			        series: [{
			            maxPointWidth: 34,
			            data: receiptCnt,
			            dataLabels: {
			                enabled: true,
			                colorByPoint: true,
			                rotation: -0,
			                color: '#6e7998',
			                align: 'center',
			                y: 1, 
			                style: {
			                    fontSize: '14px',
			                    fontWeight:'500',
			                    textOutline: false 
			                }
			            },
			            events:{  // 이벤트
							click: function ( event ){ // 클릭이벤트 
							
								$('#vocTypeNm2').text(vocTypeNm2[event.point.index]);
								//중분류 VOC유형별 접수현황 조회
								mainBarChartFn3(vocTypeCd2[event.point.index], regDtStart, regDtEnd);
							}
			        	}
			        }]
			    });
    			

    			if(vocTypeCd2.length > 0){
					//소분류 VOC유형별 접수현황 조회
					$('#vocTypeNm2').text(vocTypeNm2[0]);
					mainBarChartFn3(vocTypeCd2[0], regDtStart, regDtEnd);	
				}
            }
        }
    );
}


function mainBarChartFn3(vocTypeCd2, regDtStart, regDtEnd) {
	
	let companyCd = $SessionInfo.getCompanyCd();
    let param = {'companyCd':companyCd, 'regDtStart':regDtStart, 'regDtEnd':regDtEnd, 'vocTypeCd2':vocTypeCd2};
    
	//VOC처리현황 조회
    AjaxUtil.get(
        '/kmacvoc/v1/main/voc-TypeStates3',
        param,
        function(result){
            if(result && result.data){
                let dataList = result.data;
    			
				var vocTypeCd3 = new Array();	//3Lev유형코드
				var vocTypeNm3 = new Array();	//3Lev유형명
				var receiptCnt = new Array();	//접수건수
				
				//데이터 분할
				for(var i=0; i<dataList.length; i++){
					vocTypeCd3.push(dataList[i].vocTypeCd3);
					vocTypeNm3.push(dataList[i].vocTypeNm3);
					receiptCnt.push(dataList[i].receiptCnt);
				}
					
			    Highcharts.setOptions({
			        colors: ['#c9c9c9']
			    });
			
			    Highcharts.chart('mainBarChart3', {
			        chart: {
			            type: 'column',
			            height:'315px',
			            margin: [30, 0, 30, 30],
			            backgroundColor:'rgba(255, 255, 255, 0)',
			        },
			        title: {
			            text: ''
			        },
			        subtitle: {
			            text: ''
			        },
			        credits: {enabled: false},
			        legend: {enabled:false},
			        xAxis: {
			            categories: vocTypeNm3,
			            labels: {
			                  y:23,
			                  x:0,
			                align: 'center',
			                 style: {
			                    color:' #888',
			                    fontSize: '13px',
			                    fontWeight:'400'
			                 },
			             },
			             lineColor:'#e0e0e0',
			        },
			        yAxis: {
			            title: {
			                align: 'high',
			                offset: 0,
			                text: '(건)',
			                rotation: 0,
			                x: -5,
			                y: -10,
			                style: {
			                    color:'#bbb',
			                    fontSize: '13px',
			                    fontWeight:'400'
			                 },
			            },
			            min: 0,
			            tickInterval:5,
			            lineWidth: 1,
			            lineColor: '#e0e0e0',
			            gridLineWidth:0,
			            labels: {
			                x:-8,
			                y:8,
			                style: {
			                    color:'#bbb',
			                    fontSize: '13px',
			                    fontWeight:'400'
			                 },
			            },
			           
			        },
			        plotOptions: {
			            line: {
			                marker: {
			                    symbol: "circle",
			                    radius: 4.5,
			                    lineColor: "#fff",
			                    lineWidth: 2,
			                },
			                dataLabels: {
			                    enabled: false
			                },
			                lineWidth: 3
			            }
			        },
			        tooltip: {
			            format: '<span style="color:#333; font-size:13px; font-weight:bold; margin-bottom:10px;">{key}</span><br><br>'+
			            '<span style="color:#333; font-size:13px">{point.y} 건<span>'
			        },
			        series: [{
			            maxPointWidth: 34,
			            data: receiptCnt,
			            dataLabels: {
			                enabled: true,
			                colorByPoint: true,
			                rotation: -0,
			                color: '#8a8a8a',
			                align: 'center',
			                y: 1, 
			                style: {
			                    fontSize: '14px',
			                    fontWeight:'500',
			                    textOutline: false 
			                }
			            }
			        }]
			    });
			}
        }
    );
}

	
function mainBarChartFn4(dataList, regDtStart, regDtEnd) {
	
	var vocActTypeCd1 = new Array();	//1Lev처리유형코드
	var vocActTypeNm1 = new Array();	//1Lev처리유형명
	var receiptCnt = new Array();		//접수건수
	
	//데이터 분할
	for(var i=0; i<dataList.length; i++){
		vocActTypeCd1.push(dataList[i].vocActTypeCd1);
		vocActTypeNm1.push(dataList[i].vocActTypeNm1);
		receiptCnt.push(dataList[i].receiptCnt);
	}
		
		
    Highcharts.setOptions({
        colors: ['#4e6091']
    });

    Highcharts.chart('mainBarChart4', {
        chart: {
            type: 'column',
            height:'340px',
            margin: [30, 0, 30, 30],
            backgroundColor:'rgba(255, 255, 255, 0)',
        },
        title: {
            text: ''
        },
        subtitle: {
            text: ''
        },
        credits: {enabled: false},
        legend: {enabled:false},
        xAxis: {
            categories: vocActTypeNm1,
            labels: {
                  y:23,
                  x:0,
                align: 'center',
                 style: {
                    color:' #888',
                    fontSize: '13px',
                    fontWeight:'400'
                 },
             },
             lineColor:'#e0e0e0',
        },
        yAxis: {
            title: {
                align: 'high',
                offset: 0,
                text: '(건)',
                rotation: 0,
                x: -5,
                y: -10,
                style: {
                    color:'#bbb',
                    fontSize: '13px',
                    fontWeight:'400'
                 },
            },
            min: 0,
            tickInterval:10,
            lineWidth: 1,
            lineColor: '#e0e0e0',
            gridLineWidth:0,
            labels: {
                x:-8,
                y:8,
                style: {
                    color:'#bbb',
                    fontSize: '13px',
                    fontWeight:'400',
                 },
            },
           
        },
        plotOptions: {
            line: {
                marker: {
                    symbol: "circle",
                    radius: 4.5,
                    lineColor: "#fff",
                    lineWidth: 2,
                },
                dataLabels: {
                    enabled: false
                },
                lineWidth: 3
            }
        },
        tooltip: {
            format: '<span style="color:#333; font-size:13px; font-weight:bold; margin-bottom:10px;">{key}</span><br><br>'+
            '<span style="color:#333; font-size:13px">{point.y} 건<span>'
        },
        series: [{
            maxPointWidth: 34,
            data: receiptCnt,
            dataLabels: {
                enabled: true,
                colorByPoint: true,
                rotation: -0,
                color: '#4e6091',
                align: 'center',
                y: 1, 
                style: {
                    fontSize: '14px',
                    fontWeight:'500',
                }
            },
            events:{  // 이벤트
				click: function ( event ){ // 클릭이벤트 
				
					$('#vocActTypeNm1').text(vocActTypeNm1[event.point.index]);
					//중분류 VOC유형별 접수현황 조회
					mainBarChartFn5(vocActTypeCd1[event.point.index],regDtStart, regDtEnd);
				}
			}
            
        }]
    });
}
function mainBarChartFn5(vocActTypeCd1, regDtStart, regDtEnd) {

	let companyCd = $SessionInfo.getCompanyCd();
    let param = {'companyCd':companyCd, 'regDtStart':regDtStart, 'regDtEnd':regDtEnd, 'vocActTypeCd1':vocActTypeCd1};
    
	//VOC처리현황 조회
    AjaxUtil.get(
        '/kmacvoc/v1/main/voc-ActTypeStates2',
        param,
        function(result){
            if(result && result.data){
                let dataList = result.data;
    			
				var vocActTypeCd2 = new Array();	//2Lev처리유형코드
				var vocActTypeNm2 = new Array();	//2Lev처리유형명
				var receiptCnt = new Array();		//접수건수
				
				//데이터 분할
				for(var i=0; i<dataList.length; i++){
					vocActTypeCd2.push(dataList[i].vocActTypeCd2);
					vocActTypeNm2.push(dataList[i].vocActTypeNm2);
					receiptCnt.push(dataList[i].receiptCnt);
				}
					
			    Highcharts.setOptions({
			        colors: ['#b9c1d8']
			    });
			
			    Highcharts.chart('mainBarChart5', {
			        chart: {
			            type: 'column',
			            height:'315px',
			            margin: [30, 0, 30, 30],
			            backgroundColor:'rgba(255, 255, 255, 0)',
			        },
			        title: {
			            text: ''
			        },
			        subtitle: {
			            text: ''
			        },
			        credits: {enabled: false},
			        legend: {enabled:false},
			        xAxis: {
			            categories: vocActTypeNm2,
			            labels: {
			                  y:23,
			                  x:0,
			                align: 'center',
			                 style: {
			                    color:' #888',
			                    fontSize: '13px',
			                    fontWeight:'400'
			                 },
			             },
			             lineColor:'#e0e0e0',
			        },
			        yAxis: {
			            title: {
			                align: 'high',
			                offset: 0,
			                text: '(건)',
			                rotation: 0,
			                x: -5,
			                y: -10,
			                style: {
			                    color:'#bbb',
			                    fontSize: '13px',
			                    fontWeight:'400'
			                 },
			            },
			            min: 0,
			            tickInterval:5,
			            lineWidth: 1,
			            lineColor: '#e0e0e0',
			            gridLineWidth:0,
			            labels: {
			                x:-8,
			                y:8,
			                style: {
			                    color:'#bbb',
			                    fontSize: '13px',
			                    fontWeight:'400'
			                 },
			            },
			           
			        },
			        plotOptions: {
			            line: {
			                marker: {
			                    symbol: "circle",
			                    radius: 4.5,
			                    lineColor: "#fff",
			                    lineWidth: 2,
			                },
			                dataLabels: {
			                    enabled: false
			                },
			                lineWidth: 3
			            }
			        },
			        tooltip: {
			            format: '<span style="color:#333; font-size:13px; font-weight:bold; margin-bottom:10px;">{key}</span><br><br>'+
			            '<span style="color:#333; font-size:13px">{point.y} 건<span>'
			        },
			        series: [{
			            maxPointWidth: 34,
			            data: receiptCnt,
			            dataLabels: {
			                enabled: true,
			                colorByPoint: true,
			                rotation: -0,
			                color: '#6e7998',
			                align: 'center',
			                y: 1, 
			                style: {
			                    fontSize: '14px',
			                    fontWeight:'500',
			                    textOutline: false 
			                }
			            }
			        }]
			    });
			}
        }
    );			    
}