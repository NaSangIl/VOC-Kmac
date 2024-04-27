
/*
* datatables
*/
let $selectedRowData = {};

let gridUtil = {
    /**
     * Grid(DataTables) 생성 및 loading
     *
     * @param gridTableId Grid 대상이 되는 table 객체의 id
     * @param reqGridOptions Grid 요청 옵션
     *
     * @return grid  객체
     *
     */
    loadGrid : function(gridTableId, reqGridOptions, url, param){
        // grid 객체
        let $gridObj = null;

		try {
			Util.showLoading();
        if(typeof(gridTableId) == 'undefined') return ;

        let $gridTableObj = $("#"+gridTableId);
        if(!$gridTableObj.length) return;

        	$gridTableObj?.DataTable().destroy();

        // 기본 option
        let defaultGridOptions = {
            searching: false,
            serverSide: true,
            lengthChange: false,
            columnDefs: [
                {
                    orderable: false,
                    targets: "_all"
                },
            ],
            language: {
                "decimal" : "",
                "emptyTable" : "데이터가 없습니다.",
                "info" : "_START_ - _END_ (총 _TOTAL_ 건)",
                "infoEmpty" : "0건",
                "infoFiltered" : "(전체 _MAX_ 건 중 검색결과)",
                "infoPostFix" : "",
                "thousands" : ",",
                "lengthMenu" : "_MENU_ 개씩 보기",
                "loadingRecords" : "로딩중...",
                "processing" : "처리중...",
                "search" : "검색 : ",
                "zeroRecords" : "검색된 데이터가 없습니다.",
                "paginate" : {
                    "first" : "첫 페이지",
                    "last" : "마지막 페이지",
                    "next" : "다음",
                    "previous" : "이전"
                },
                "aria" : {
                    "sortAscending" : " :  오름차순 정렬",
                    "sortDescending" : " :  내림차순 정렬"
                }
            },
            ajax: {
                cache: false,
                url: url,
                type: 'get',
                dataType: 'json',
                contentType: "application/json",
                data: function (d) {
                    d = $.extend({}, d, param);
                    d.offset = d.start;
                    return d;
                },
                dataSrc : function(json){
                    json.recordsTotal = json.data.totalCount;
                    json.recordsFiltered = json.data.totalCount;
                    //console.log(json.data.list);
                    return json.data.list;
                },
				beforeSend: function() {
		           Util.showLoading();
		        },
		        complete: function() {
		            Util.hideLoading();
		        }
            },
            order: [],
            select: {style: 'single'},
            //buttons             : ["copy", "csv", "print"],
        }

        // [Start] 옵션 구성 ---------------------
        reqGridOptions = (reqGridOptions)? reqGridOptions : {};
        let gridOptions = $.extend(true, {}, defaultGridOptions,reqGridOptions);

        // footer에서 합계나 평균 처리할 옵션처리내용
        let footerCalculationOpt = gridOptions.footerCalculation;
        // 예시)
        //  footerCalculation : {
        //      targetSumColIndexs : [1,2,3,4,5,6],
        //      targetAvgColIndexs : [7,8,9,10,11],
        //      isNumberFormat  : true,
        //  },
        if(typeof(gridOptions.footerCallback) != 'function' && footerCalculationOpt != undefined && footerCalculationOpt != null){

            // footer의 각 cell의 class를 첫번 째 row와 동기화 (다중 row일 때 첫번째row에만 적용되는 문제 때문)
            let fn_foooterStyleSynch = function(api){
                // footer가 있을 때
                let $footerRows = $(api.table().footer()).find("tr");
                if($footerRows.length ){
                    let firstCellClassArry = [];
                    $footerRows.each(function(rowIdx, row){
                        // cell 단위 loop
                        $(row).find("th").each(function(cellIdx, cell){
                            // 첫번 째 row의 각 cell의 class 값을 저장
                            if(rowIdx == 0) firstCellClassArry.push($(cell).attr("class"));
                            // 저장된 각 cell의 class 내용을 2번째 row의 cell 들에 적용
                            else $(cell).attr("class", firstCellClassArry[cellIdx]);
                        });
                    });
                }
            };

            // footer의 계산 항목 표시 설정 함수
            let fn_footerCalculationProcess = function ( api, row, data, start, end, display ) {
                // Remove the formatting to get integer data for summation
                var intVal = function ( i ) {
                    return typeof i === 'string' ?
                        i.replace(/[\$,]/g, '')*1 :
                        typeof i === 'number' ?
                            i : 0;
                };
                // 대상 column index 배열 (합계 또는 평균의 계산 표시 대상 column의 index 배열)
                var coltosumIdxs    = footerCalculationOpt.targetSumColIndexs;
                var coltoavgIdxs    = footerCalculationOpt.targetAvgColIndexs;
                // footer가 있을 때
                let $footerRows = $(api.table().footer()).find("tr");

                // ---- 합계 계산
                for (let colIdx in coltosumIdxs){
                    let total     = api.column( coltosumIdxs[colIdx] ).data().reduce( function (a, b) {return intVal(a) + intVal(b);}, 0 );
                    // Total over this page
                    let pageTotal = api.column( coltosumIdxs[colIdx], { page: 'current'} ).data().reduce( function (a, b) {return intVal(a) + intVal(b);}, 0 );
                    // Footer에 값 적용
                    let $row = $footerRows;
                    let viewData = pageTotal;    // 합계

                    // Update footer : numberFormat 적용
                    let realViewData = (!ObjectUtil.isEmpty(footerCalculationOpt.isNumberFormat) && footerCalculationOpt.isNumberFormat)? Util.numberFormat(viewData) : viewData;
                    let $cellObj = $row.find("th");
                    let intColEqIdx = parseInt(coltosumIdxs[colIdx]) - $cellObj.eq(0).attr('colspan') + 1;
                    $cellObj.eq(intColEqIdx).html( realViewData );
                }

                // ---- 평균 계산
                for (let colIdx in coltoavgIdxs){
                    // ---- 평균 계산
                    let total     = api.column( coltoavgIdxs[colIdx] ).data().reduce( function (a, b) {return intVal(a) + intVal(b);}, 0 );
                    let pageTotal = api.column( coltoavgIdxs[colIdx], { page: 'current'} ).data().reduce( function (a, b) {return intVal(a) + intVal(b);}, 0 );
                    let avg = ( (end != null && end != 0)? pageTotal / end : 0 ).toFixed(1);

                    // Footer에 값 적용
                    let $row = $footerRows;
                    let viewData = avg;     // 평균

                    // Update footer : numberFormat 적용
                    let realViewData = (!ObjectUtil.isEmpty(footerCalculationOpt.isNumberFormat) && footerCalculationOpt.isNumberFormat)? Util.numberFormat(viewData) : viewData;
                    let $cellObj = $row.find("th");
                    let intColEqIdx = parseInt(coltoavgIdxs[colIdx]) - $cellObj.eq(0).attr('colspan') + 1;
                    $cellObj.eq(intColEqIdx).html( realViewData );
                }
            }   // end of 'fn_footerCalculationProcess(...)'

            //---------- footerCallback 설정 start
            gridOptions.footerCallback = function (row, data, start, end, display ) {
                var api = this.api();

                // footer 항목의 스타일 설정
                fn_foooterStyleSynch(api);

                // 계산 항목 처리
                fn_footerCalculationProcess(api, row, data, start, end, display);
            }   // end of 'gridOptions.footerCallback' callback 정의
            //---------- footerCallback 설정 end
        }
        // [End] 옵션 구성 ---------------------

        //------
        if(gridOptions.drawCallback == "vocType"){
			gridOptions.drawCallback = function(){
				drawVocType(this.api());         
        	}
        }else if(gridOptions.drawCallback == "vocActType"){
			gridOptions.drawCallback = function(){
				drawVocActType(this.api());         
        	}	
	
		}else{
			gridOptions.drawCallback = function(){}
		}
        

        //---- ** grid 실제 생성
        $gridObj = $gridTableObj.DataTable(gridOptions);

        //테이블 클릭 이벤트
        let $DataTable = $gridTableObj.DataTable();
        $DataTable.on( 'select', function ( e, dt, type, indexes ) {
            if ( type === 'row' ) {
                $selectedRowData = $DataTable.rows( indexes ).data()[0];
            }
        });

        return $gridObj;
		}catch(e) {
			Util.hideLoading();
	        return $gridObj;
		}
    }
}

/* 통계 > VOC 유형별 현황 셀머지 */
let drawVocType = function(api) {
	var rows = api.rows( {page:'current'} ).nodes(),
		settings = {
            "COLUMN_THEME0" : 0,
            "COLUMN_THEME1" : 1,
            "COLUMN_THEME2" : 2
		};

	 	if(rows.length > 0){
			 
	        mergeCells(rows, settings.COLUMN_THEME0);
	        mergeCells(rows, settings.COLUMN_THEME1);
	        mergeCells(rows, settings.COLUMN_THEME2);
		}	
};
/* 통계 > VOC 처리유형별 현황 셀머지 */
let drawVocActType = function(api) {
	var rows = api.rows( {page:'current'} ).nodes(),
		settings = {
            "COLUMN_THEME0" : 0,
            "COLUMN_THEME1" : 1,
		};

	 	if(rows.length > 0){
			 
	        mergeCells(rows, settings.COLUMN_THEME0);
	        mergeCells(rows, settings.COLUMN_THEME1);
		}	
};

let mergeCells = function(rows, rowIndex) {

    var last = null,
        currentRow = null,
        k = null,
        gNum = 0,
        refLine = null;

    	rows.each( function (line, i) {
        currentRow = line.childNodes[rowIndex];

        if ( last === currentRow.innerText ) {
            currentRow.setAttribute('style', 'display: none');
            ++k;

            return; //leave early
        }

        last = currentRow.innerText;

        if ( i > 0 ) {
            rows[refLine].childNodes[rowIndex].rowSpan = ++k;
            ++gNum;
        }

        k = 0; refLine = i;          
    });

    // for the last group
    rows[refLine].childNodes[rowIndex].rowSpan = ++k;
};