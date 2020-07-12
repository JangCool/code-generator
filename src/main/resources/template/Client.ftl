package ${package};

import org.springframework.cloud.openfeign.FeignClient;

import kr.co.abcmart.constant.Const;
import kr.co.abcmart.zconfiguration.feign.FeignConfig;

@FeignClient(contextId = "${name}Client", name = "${clientName}", <#if '${clientUrl}' != ''> url = "${clientUrl}",</#if> configuration = FeignConfig.class)
public interface ${name}Client {

}
