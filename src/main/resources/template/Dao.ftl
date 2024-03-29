package ${package};

import org.apache.ibatis.annotations.Mapper;
import ${package}.base.Base${fileName}Dao;
import ${model};

/**
 * <pre>
 * 테이블 명   : ${table.name}
 * 설명       : ${desc}
 *
 * --------------------------------------------------------------------------------------------------------------------
 * ※ 주의 
 * 	 - 기본 find, insert, update, delete 메소드는 Base${fileName}Dao 클래스에 구현 되어있습니다.
 * 	 - Base${fileName}Dao는 절대 수정 불가 하며 새로운 메소드 추가 하실 경우에는 해당 소스에서 작업 하시면 됩니다.
 * --------------------------------------------------------------------------------------------------------------------
 *</pre>
 */
@Mapper
public interface ${fileName}Dao extends Base${fileName}Dao {
	

}
