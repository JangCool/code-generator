package ${package};
<#if proxyTargetClass?string("y","n") == "y">
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ${serviceName}Service {
<#else>
public interface ${serviceName}Service {
</#if>

}
