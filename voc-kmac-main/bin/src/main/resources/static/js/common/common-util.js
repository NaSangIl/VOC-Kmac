let ObjectUtil = {
    isEmpty: function(value) {
        if (value === null) return true
        if (typeof value === 'undefined') return true
        if (typeof value === 'string' && value === '') return true
        if (Array.isArray(value) && value.length < 1) return true
        if (typeof value === 'object' && value.constructor.name === 'Object' && Object.keys(value).length < 1 && Object.getOwnPropertyNames(value) < 1) return true
        if (typeof value === 'object' && value.constructor.name === 'String' && Object.keys(value).length < 1) return true // new String()
        return false;
    },
    default: function(value, defValue) {
        return this.isEmpty(value) ? defValue : value
    },
    copy: function(target, source){
        let tempTarget = JSON.parse(JSON.stringify(target));
        let tempSource = JSON.parse(JSON.stringify(source));
        Object.assign(tempTarget, tempSource);

        return tempTarget;
    }
}
let AjaxUtil = {
    get: function (url, param, callback, async, errCallback) {
		Util.showLoading();
        ObjectUtil.default(async, true);
        $.ajax({
            type:"GET",
            dataType:"JSON",
            url : url,
            data : param,
            contentType: "application/json",
            async: async,
            success : function(data) {
                console.log(data);
                if(data.messageCode == '0000') {
                    if(callback != null) callback(data);
                }
            },
            error : function(xhr) {
                _ajaxErrorProcess(xhr, errCallback);
            },
			beforeSend: function() {
				Util.showLoading();
			},
            complete: function() {
				Util.hideLoading();
            }
        });
    },
    post: function (url, param, callback, async, errCallback) {
		Util.showLoading();
        $.ajax({
            type:"POST",
            dataType:"JSON",
            url : url,
            data : param,
            contentType: "application/json",
            async: true,
            success : function(data) {
                console.log(data);
                if(data.messageCode == '0000') {
                    if(callback != null) callback(data);
                } else {
                    alert(data.message);
                }
            },
            error : function(xhr) {
                _ajaxErrorProcess(xhr, errCallback);
            },
			beforeSend: function() {
				Util.showLoading();
			},
            complete: function() {
				Util.hideLoading();
            }
        });
    }
}

/**
 * ajax 함수 공통 에러 처리
 * TODO 오류콜백 추가 => 결제 RuntimeException 상황에서도 처리해야함
 */
function _ajaxErrorProcess(xhr, errCallback) {
    //console.log('xhr',xhr);
    let msg = "";
    let errMessage = "";

    if (xhr.status === 0) {
        msg = 'No connection to the API server';
    } else if (xhr.status == 404) {
        msg = 'Requested page not found. [404]';
    } else if (xhr.status == 500) {
        msg = 'Internal Server Error [500]';
    } else if(!ObjectUtil.isEmpty(xhr.responseJSON) && !ObjectUtil.isEmpty(xhr.responseJSON.message)) {
        errMessage = xhr.responseJSON.message;
    }

    if(ObjectUtil.isEmpty(errMessage)) errMessage = msg + '\n시스템 오류입니다. 관리자에게 문의하세요.';
    alert(errMessage);
}

let StringUtil = {
    numberWithCommas: function(str, step) {
        if(typeof str === 'number') str = str.toString();
        let reg = new RegExp('\\B(?=(\\d{' + step + '})+(?!\\d))', 'g')
        return str.replace(reg, ",");
    }
}


let Util = {
    /**----------------------------------------------------------------------------------*
     * @Function    : Util.getToday(delimiter);
     * @Desc        : 현재날짜
     * @param       : delimiter : 구분자
     * ---------------------------------------------------------------------------------*/
    getToday : function(delimiter) {
        if(ObjectUtil.isEmpty(delimiter)) delimiter = "";
        let date = new Date();
        return date.getFullYear() + delimiter + ("0" + (date.getMonth()+1)).slice(-2) + delimiter + ("0" + date.getDate()).slice(-2);
    },
    /**----------------------------------------------------------------------------------*
     * @Function    : Util.numberFormat(price);
     * @Desc        : 금액을 천 단위로 (,)찍어서 리턴
     * @param       : text
     * ---------------------------------------------------------------------------------*/
    numberFormat : function(number) {
        if(ObjectUtil.isEmpty(number) || isNaN(number)) return number;
        return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    },
    /**----------------------------------------------------------------------------------*
     * @Function 	: Util.getTwoDigit(n);
     * @Desc 		: 숫자 2자리로 변환
     * @param 		: n - 변경할 숫자
     * ---------------------------------------------------------------------------------*/
    getTwoDigit : function(n) {
        return n > 9 ? "" + n : "0" + n;
    },
    /**----------------------------------------------------------------------------------*
     * @Function 	: Util.getCurrentMD();
     * @Desc 		: 메인/영화 페이지에서 표시하는 날짜 및 시간
     * ---------------------------------------------------------------------------------*/
    getCurrentMD : function(addDay) {
        //var week = new Array('일', '월', '화', '수', '목', '금', '토');
        var today = new Date();
        var pDate  = (addDay) ? (new Date(today.setDate(today.getDate() + (addDay) ))) : today;

        var month = Util.getTwoDigit(pDate.getMonth() + 1);
        var day   = Util.getTwoDigit(pDate.getDate());
        //var dayName = week[pDate.getDay()];
        //var hour = Util.getTwoDigit(pDate.getHours());
        //var minutes = Util.getTwoDigit(pDate.getMinutes());
        return month + "월" + day + "일";
        //var divider = "&#160;";
        //$(".time-wrap .day").html(month + "/" + day + divider + "(" + dayName + ")");
        //$(".time-wrap .time").text(hour + ":" + minutes);
    },
    /**----------------------------------------------------------------------------------*
     * @Function 	: Util.dateFormat(date, delimiter);
     * @Desc 		: 날짜형식 적용.
     * @param 		: date : 날짜값
     * @param 		: delimiter : 날짜값
     * ---------------------------------------------------------------------------------*/
    dateFormat : function(date, delimiter) {
        let d = date.replace(/[^0-9]/g, "");
        if(ObjectUtil.isEmpty(d) || d.length != 8) return date;
        if(ObjectUtil.isEmpty(delimiter)) delimiter = "-";

        return d.substring(0, 4) + delimiter + d.substring(4, 6) + delimiter + d.substring(6, 8);
    },
    /**----------------------------------------------------------------------------------*
     * @Function 	: Util.timeFormat(ev);
     * @Desc 		: 입력 format 지정한다.
     * @param 		: ev : 날짜값
     * ---------------------------------------------------------------------------------*/
    timeFormat : function(ev) {
        if(ObjectUtil.isEmpty(ev) || ev.length != 4) return ev;
        return ev.substring(0, 2) + ':' + ev.substring(2, 4);
    },
    /**----------------------------------------------------------------------------------*
     * @Function 	: Util.getLastday(date);
     * @Desc 		: 월의 마지막 날짜 가져오기
     * @param 		: date - 대상일자
     * ---------------------------------------------------------------------------------*/
    getLastday : function(date) {
        let d = date.replace(/[^0-9]/g, "");
        if(ObjectUtil.isEmpty(d) || d.length < 6) return date;
        let last = new Date(d.substring(0,4), d.substring(4,6), 0);
        return last.getDate();
    },
    /**----------------------------------------------------------------------------------*
     * @Function 	: Util.maxLengthCheck(object);
     * @Desc 		: Number 타입 maxlength 적용하기
     * @param 		: object - 대상 object
     * ---------------------------------------------------------------------------------*/
    maxLengthCheck : function(object){
	    if (object.value.length > object.maxLength){
	        object.value = object.value.slice(0, object.maxLength);
		}
    },
	showLoading : function(){
		$("#loadingBar").show();
	},
	hideLoading : function(){
		$("#loadingBar").hide();
	}
}

let UIUtil = {
    /**----------------------------------------------------------------------------------*
     * @Function    : UIUtil.loadDatepicker(jqueryObj);
     * @Desc        : datepicker 적용
     * @param       : $targetObjects - jquery 객체
     * 예) UIUtil.loadDatepicker($("#selectedDt"));
     * ---------------------------------------------------------------------------------*/
    loadDatepicker : function($targetObjects, addOptions){

        let defaultDatepickerOptions = {
            //달력 날짜 형태
              dateFormat: 'yy.mm.dd'
            //빈 공간에 현재월의 앞뒤월의 날짜를 표시
            , showOtherMonths: true
            // 월- 년 순서가아닌 년도 - 월 순서
            , showMonthAfterYear:true
            //option값 년 선택 가능
            , changeYear: true
            //option값  월 선택 가능
            , changeMonth: true
            //button:버튼을 표시하고,버튼을 눌러야만 달력 표시 ^ both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시
            , showOn: "both"
            //버튼 이미지 경로
            , buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif"
            //버튼 이미지만 깔끔하게 보이게함
            , buttonImageOnly: true
            //버튼 호버 텍스트
            , buttonText: "선택"
            //달력의 년도 부분 뒤 텍스트
            , yearSuffix: "년"
            //달력의 월 부분 텍스트
            , monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
            //달력의 월 부분 Tooltip
            , monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
            //달력의 요일 텍스트
            , dayNamesMin: ['일','월','화','수','목','금','토']
            //달력의 요일 Tooltip
            , dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일']
            //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
            , minDate: "-5Y"
            //최대 선택일자(+1D:하루후, -1M:한달후, -1Y:일년후)
            , maxDate: "+5y"
        };

        let dpOptions = (addOptions && !ObjectUtil.isEmpty(addOptions)) ? $.extend(true, {}, defaultDatepickerOptions, addOptions) : defaultDatepickerOptions;

        if($targetObjects.length){
            $targetObjects.datepicker(dpOptions);
        }
    },
}

let goMain = function() {
    $(location).attr('href', '/main');
}

let goPage = function(url) {
    $(location).attr('href', url);
}

let DropdownUtil = {
    /**----------------------------------------------------------------------------------*
     * @Function    : DropdownUtil.makeCodeList(codeType, obj);
     * @Desc        : dropdown용 공통코드 생성
     * @param       : codeType - 코드유형, obj - dropdown object
     * ---------------------------------------------------------------------------------*/
    makeCodeList : function(codeType, obj) {
        let param = {"codeType": codeType};

        AjaxUtil.get(
            '/kmacvoc/v1/code/dropdown/list',
            param,
            function(result){
                if(result && result.data){
                    $(obj).dropdown({
                        values: result.data,
                        clearable: true
                    });
                }
            }
        );
    },
    /**----------------------------------------------------------------------------------*
     * @Function    : DropdownUtil.makeCompList(obj);
     * @Desc        : dropdown용 회사코드 생성
     * @param       : obj - dropdown object
     * ---------------------------------------------------------------------------------*/
    makeCompList : function(obj, changeEvent) {
        let param = {"useYn": "Y", "offset": -1};

        AjaxUtil.get(
            '/kmacvoc/v1/company/list',
            param,
            function(result){
                if(result && result.data.list){
                    result.data.list.map(function(item) {
                        item.value = item.companyCd;
                        item.name = item.companyNm;
                    });
					let compDropOption = {
                        values: result.data.list,
                        clearable: true,
					};
					if(!!changeEvent){
						compDropOption.onChange = function(v, t, $choice) { return changeEvent(v, t, $choice);}
					}
                    $(obj).dropdown(compDropOption);

                    //회사코드 default setting
                    $(obj).dropdown('set selected', $SessionInfo.getCompanyCd());

                    //시스템운영자, 시스템관리자가 아닌경우, 회사선택 불가능하도록 처리
                    if($SessionInfo.getUserAuth().indexOf('100') < 0 && $SessionInfo.getUserAuth().indexOf('000') < 0) {
                        $(obj).addClass('disabled');
                    }
                }
            }
        );
    },
    /**----------------------------------------------------------------------------------*
     * @Function    : DropdownUtil.makeAuthList(obj);
     * @Desc        : dropdown용 권한코드 생성
     * @param       : obj - dropdown object
     * ---------------------------------------------------------------------------------*/
    makeAuthList : function(obj) {
        let param = {"useYn": "Y", "offset": -1};

        AjaxUtil.get(
            '/kmacvoc/v1/auth/list',
            param,
            function(result){
                if(result && result.data.list){
                    result.data.list.map(function(item) {
                        item.value = item.authSeq;
                        item.name = item.authNm;
                    });
					let authDropOption = {
                        values: result.data.list,
                        clearable: true,
					};
                    $(obj).dropdown(authDropOption);
                }
            }
        );
    },
    /**----------------------------------------------------------------------------------*
     * @Function    : DropdownUtil.makeActUserList(companyCd, obj);
     * @Desc        : dropdown용 권한코드 생성
     * @param       : companyCd - 회사코드
     * @param       : obj - dropdown object
     * ---------------------------------------------------------------------------------*/
    makeActUserList : function(companyCd, obj) {

        AjaxUtil.get(
            '/kmacvoc/v1/user/act/list/'+(!!companyCd ? companyCd : $SessionInfo.getCompanyCd()),
            {},
            function(result){
                if(result && result.data){
                    $(obj).dropdown({
                        values: result.data,
                        clearable: true,
						position: { my: "left top", at: "left bottom" },
                    });
                }
            }
        );
    },
    /**----------------------------------------------------------------------------------*
     * @Function    : DropdownUtil.makeYearList(obj);
     * @Desc        : dropdown용 년도 생성
     * @param       : obj - dropdown object
     * ---------------------------------------------------------------------------------*/
    makeYearList : function(obj) {
        let year_list = [], year_data = {};
        for(let i=2023; i<2030; i++) {
            year_data = {};
            year_data.name=i;
            year_data.value=i;
            year_list.push(year_data);
        }

        $(obj).dropdown({
            values: year_list,
            clearable: true
        });
    },
    /**----------------------------------------------------------------------------------*
     * @Function    : DropdownUtil.makeMonthList(obj);
     * @Desc        : dropdown용 월 생성
     * @param       : obj - dropdown object
     * ---------------------------------------------------------------------------------*/
    makeMonthList : function(obj) {
        let month_list = [], month_data = {};
        for(let i=1; i<13; i++) {
            month_data = {};
            month_data.name = i<10 ? '0'+i : i;
            month_data.value = i<10 ? '0'+i : i;
            month_list.push(month_data);
        }

        $(obj).dropdown({
            values: month_list,
            clearable: true
        });
    },
    /**----------------------------------------------------------------------------------*
     * @Function    : DropdownUtil.makeDayList(obj);
     * @Desc        : dropdown용 일 생성
     * @param       : obj - dropdown object
     * ---------------------------------------------------------------------------------*/
    makeDayList : function(obj) {
        let day_list = [], day_data = {};
        for(let i=1; i<32; i++) {
            day_data = {};
            day_data.name = i<10 ? '0'+i : i;
            day_data.value = i<10 ? '0'+i : i;
            day_list.push(day_data);
        }

        $(obj).dropdown({
            values: day_list,
            clearable: true
        });
    },
}

/**
 * 세션정보
 */
let $SessionInfo = {
    getUserSeq: function() {
        return $('.header-info').find('#loginUserSeq').val();
    },
    getCompanyCd: function() {
        return $('.header-info').find('#loginCompanyCd').val();
    },
    getUserId: function() {
        return $('.header-info').find('#loginUserId').val();
    },
    getUserAuth: function() {
        return $('.header-info').find('#loginUserAuth').val();
    }
}