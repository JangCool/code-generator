/***
 * 페이지와 관련된 함수 정의
 *
 * page 변수는 rd.page 에서 접근가능하도록 설정하여 외부에서도 조작이 가능한 데이터는 page 변수 아래에 둔다
 * 모든 기능을 page 외부에서 조작이 가능하지 않도록 public, private 를 고려하여 개발해야함
 *
 * init 함수를 통해 흐름이 모두 보일 수 있도록 처리해야 함
 */
(function() {
	var _page = rd.object.createNestedObject(window, 'rd.page');

	var init = function() {

	};

	// ※ 관련 업무 로직 작성.

	/**
	 * DOM 초기화시 실행
	 */
	$(function() {
		init();
	});
})();
