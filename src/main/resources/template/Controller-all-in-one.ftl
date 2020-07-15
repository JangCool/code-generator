package ${package};

<#if '${annotation}' == 'RestController'>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.co.pionnet.butterfly.core.core.controller.BaseController;
import kr.co.pionnet.butterfly.core.core.request.Request;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@${annotation}
@RequestMapping("${requestMapping}")
public class ${controllerName}Controller extends BaseController {

//  ※ 컨트롤러 샘플입니다. 필요 없을 경우 제거 하세요.
//
//	@RequestMapping("/main")
//	public ModelAndView main(Request<?> request) throws Exception {
//		return forward("/sample/main");
//	}

	/**
	 * form 영역에 기본으로 설정되어야 할 데이터를 정의 및 호출 한다. 페이지 접속 시 바로 호출 한다.
	 *
	 * @param request com.zen9.rivendell.core.request.Request
	 * @throws Exception
	 *
	 * @since 
	 * @author 
	 */
	@RequestMapping("/init-data")
	<#if '${annotation}' == 'RestController'>
	public ResponseEntity<?> initData(Request<?> request) throws Exception {

		// - 기본으로 들어 있는 데이터
		// currentDate : yyyy-mm-dd 형식으로 출력,
		// currentDateTime : yyyy-mm-dd HH:mm 형식으로 출력
		Map<String, Object> initViewData = initViewData(request);

		// 샘플 예제.  지우고 사용 하세요.
		//initViewData.put("sampleCode", Common.getCode().getCodeList(CodeConst.SAMPLE1));

		return ResponseEntity.ok(initViewData);
	}
	<#else>
	public void initData(Request<?> request) throws Exception {
		// - 기본으로 들어 있는 데이터
		// currentDate : yyyy-mm-dd 형식으로 출력,
		// currentDateTime : yyyy-mm-dd HH:mm 형식으로 출력
		Map<String, Object> initViewData = initViewData(request);

		// 샘플 예제.  지우고 사용 하세요.
		//initViewData.put("sampleCode", Common.getCode().getCodeList(CodeConst.SAMPLE1));

		writeJson(request, initViewData);
	}
	</#if>
}				