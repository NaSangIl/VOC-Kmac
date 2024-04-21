/*
* Gnb
*/
let menuDepth1 = document.querySelectorAll('.menu-depth1>li');
let menuDepth2Box = document.querySelectorAll('.menu-depth2');
let menuDepth2Boxes = [].map.call(menuDepth2Box, function(obj) {
	return obj;
});
let menuDepth2BoxMaxHeight = 0;
let subMenuBox = document.querySelector('.sub-depth-area');
let boxValues = [];


function acitveMenu() {
	menuDepth2Box.forEach((box) => {
		boxValues.push(box.offsetHeight);
	});
	menuDepth2BoxMaxHeight = Math.max.apply(null, boxValues);

	menuDepth2Boxes.map((box) => {
		box.style.height = menuDepth2BoxMaxHeight + "px";
		subMenuBox.style.height = menuDepth2BoxMaxHeight + "px";
		box.style.visibility = "visible";
	});

	$('.sub-depth-area').css('height', menuDepth2BoxMaxHeight);
}
//메뉴 비활성화 시 적용 스타일
function inactiveMenu() {
	menuDepth2Boxes.map((box) => {
		box.style.height = 0 + "px";
		box.style.visibility = "hidden";
		subMenuBox.style.height = 0 + "px";
	});

	$('.sub-depth-area').css('height', 0);
}

$(document).ready(function() {
	httpRequest = new XMLHttpRequest();
	httpRequest.onreadystatechange = () => {
		if (httpRequest.readyState === XMLHttpRequest.DONE) {
			if (httpRequest.status === 200) {
				document.getElementById("headerMenu").innerHTML = httpRequest.response;

				menuDepth1 = document.querySelectorAll('.menu-depth1>li');
				menuDepth2Box = document.querySelectorAll('.menu-depth2');
				menuDepth2Boxes = [].map.call(menuDepth2Box, function(obj) {
					return obj;
				});

				//const subMenuBox = document.querySelector('.sub-depth-area');

				menuDepth1.forEach((menu) => {
					menu.addEventListener('mouseover', (e) => {
						menu.classList.add("is-active");
						acitveMenu();
					});

					menu.addEventListener('mouseout', (e) => {
						menu.classList.remove("is-active");
						inactiveMenu();
					});
				});
			} else if (httpRequest.status === 500) {
				//alert('로그아웃 되었습니다.');
				// 캐쉬 초기화
				for (let i = localStorage.length - 1; i >= 0; i--) {
					let key = localStorage.key(i);
					localStorage.removeItem(key);
				}
				location.href = "/logout";
			} else {
				alert('시스템 오류입니다. 관리자에게 문의하세요.');
				// 캐쉬 초기화
				for (let i = localStorage.length - 1; i >= 0; i--) {
					let key = localStorage.key(i);
					localStorage.removeItem(key);
				}
				location.href = "/logout";
			}
		}
		
		//메뉴선택시 캐쉬 초기화
		$('a[id=subMenu]').on('click', function() { 
			var url = $(this).attr('url');
			cacheUt.clear(); 
			location.href = url;
		});
	};
	httpRequest.open('GET', '/main/menu');
	httpRequest.send();

	// logout
	$('.btn-logout').on('click', function() { logout(); });

});

let logout = function() {
	if (!confirm("로그아웃 하시겠습니까?")) return;

	// 캐쉬 초기화
	for (let i = localStorage.length - 1; i >= 0; i--) {
		let key = localStorage.key(i);
		localStorage.removeItem(key);
	}

	location.href = "/logout";
}

