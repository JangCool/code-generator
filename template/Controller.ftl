package ${package};

<#if '${annotation}' == 'RestController'>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
import org.springframework.web.bind.annotation.RequestMapping;
import com.zen9.rivendell.core.controller.BaseController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@${annotation}
@RequestMapping("${requestMapping}")
public class ${controllerName}Controller extends BaseController {

//  ※ 컨트롤러 샘플입니다. 필요 없을 경우 제거 하세요.
//
//	@RequestMapping("/main")
//	public ModelAndView main(Request<?> request) throws Exception {
//		return forward("/sample/sample-mgmt/main");
//	}


}				