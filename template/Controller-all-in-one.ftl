package ${package};

<#if '${annotation}' == 'RestController'>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.zen9.rivendell.core.controller.BaseController;
import com.zen9.rivendell.core.request.Request;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;

@Slf4j
@${annotation}
@RequestMapping("${requestMapping}/${snakeCaseName}")
public class ${controllerName}Controller extends BaseController {

	@RequestMapping("/main")
	public ModelAndView main(Request<?> request) throws Exception {
		return forward("/${business}/${snakeCaseName}/${snakeCaseName}-main");
	}
	
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
	public void initData(Request<?> request) throws Exception {

		// - 기본으로 들어 있는 데이터
		// currentDate : yyyy-mm-dd 형식으로 출력,
		// currentDateTime : yyyy-mm-dd HH:mm 형식으로 출력
		Map<String, Object> initViewData = initViewData(request);

		// 샘플 예제.  지우고 사용 하세요.
		//initViewData.put("sampleCode", Common.getCode().getCodeList(CodeConst.SAMPLE1));

		writeJson(request, initViewData);
	}
	
}				