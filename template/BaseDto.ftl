package ${package}.base;

import lombok.Data;
import java.io.Serializable;
import com.zen9.rivendell.core.bean.BaseBean;

/**
 * 테이블 명  : ${tableName}
 * 설명        : ${desc}
 */
@Data
public class Base${fileName} extends BaseBean implements Serializable {

<#if model??>
	<#list model as fieldMap>	
    /**
     * 이 필드는 Code Generator를 통하여 생성 되었습니다.
     * 설명 : ${fieldMap.comment}
     */
	private ${fieldMap.javaType} ${fieldMap.field};
	
	</#list>
</#if>
}