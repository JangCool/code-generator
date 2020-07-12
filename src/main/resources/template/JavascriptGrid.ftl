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

	/************************************************************************************************************************************************************/
	/**																 								 															*/
	/**													 ※중요 샘플 소스를 활용하지 않으면 주석에 있는 내용 모두 제거한다. 														*/
	/**																								 															*/
	/************************************************************************** 샘플 시작 **************************************************************************
	  
	var init = function() {
		//그리드 실행
		_page.sampleGrid = createSampleGrid();
		
		// 이벤트 바인딩
		bindFormMajor();
		
		// 또는 DB에서 데이터를 읽어와 초기화 작업이 필요할 때 사용
		
		//초기화시 IE 지원을 위해 async, await키워드를 사용할 수 없기 떄문에 Callback함수를 이용하여 초기화 처리한다.
		var init = function() {
			
			rd.pageInit({
				url : "/system/menu-mgmt/init-data"
			},function(data){
				
				initData = data;
				//적용대상시스템코드 콤보 데이터로 변환.
				aplyTgtSysCdCombo = rd.object.convertDataToCombo(initData.aplyTgtSysCd);
	
				//프로그램 유형 코드 콤보 데이터로 변환.
				progTpCdCombo = rd.object.convertDataToCombo(initData.progTpCd);
				
				//적용대상 시스템 input 초기화.
				inputAplyTgtSysCd = rd.input.comboBox({
					data : aplyTgtSysCdCombo,
					id:"aplyTgtSysCd"
				});
					
				// 그리드 설정
				_page.menuTreeGrid = createMenuTree();
				
				_page.menuProgramGrid = createMenuProgramGrid();
				
				// 이벤트 바인딩
				bindFormMajor();
			});
		};
	};
	
	  ※ Sample 그룹코드 그리드 초기화
	var createSampleGrid = function() {

		// 그리드 초기화
		var grid = rd.grid.createGrid({
			id: 'gridCodeGroup',
			columns :[
				{ header: '샘플1',		id:'sampleValue1',		dataType: rd.grid.constant.DATA_TYPE.String, 		width : 100 },
				{ header: '샘플2',		id:'sampleValue2',		dataType: rd.grid.constant.DATA_TYPE.String, 		width : 100 }
			],
			properties : {
				form : "searchFormSample",
				url: '/system/sample-mgmt/read-sample-list',
				editable : true
			},
			events : {
				initialize : function(){
					console.log('grid: groupCode init');
				},
				endSave : function(result){
					console.log("endQuery result : ",result);
					//저장이 종료 되면 다시 조회 한다.
					grid.doQuery();
				},
				row: {		
					selectedRowChanged: function(prevRow, currentRow) {
						console.log("row 선택 변경 시 이벤트 발생.");
					}
				}
			}
		});

		// 그리드 버튼 이벤트
		var $gridButton = $("#sampleGridButton");
		
		$gridButton.find(".add-row").click(function() {
			grid.addRow();
		});
				
		$gridButton.find(".save").click(function() {
			grid.doSave({
				url: '/system/sample-mgmt/save-sample'
			});
		});

		return grid;
	};

	var bindFormMajor = function() {
		$("#search").click(function(e) {
			e.preventDefault();

			_page.sampleGrid.doQuery({
				url: '/system/code-mgmt/read-code-group-list'
			});
		});
	};
	
	**************************************************************************** 샘플 종료 ****************************************************************************/
	 
	var init = function() {
		
	};
	
	
	/**
	 * 로직 작성 시작.
	 */
	
	
	/**
	 * DOM 초기화시 실행
	 */
	init();
})();
