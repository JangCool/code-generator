package ${package}.base;

import java.util.List;
import java.lang.Object;
import ${model};


/**
 * 테이블 명 : ${tableName}
 * 설명       : ${desc}
 *
 * ※ 절대 수정 금지. 해당 파일은 code generator 작동 시 내용을 전부 덮어 씌우게 됩니다. 
 *
 */

public interface Base${fileName}Dao {

    /**
     * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
	
	${fileName} selectByPrimaryKey(${fileName} ${field}) throws Exception;
	
    /**
     * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
	List<${fileName}> select(${fileName} ${field}) throws Exception;
	
    /**
     * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
	int insert(${fileName} ${field}) throws Exception;
	
    /**
     * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
	int update(${fileName} ${field}) throws Exception;
	
	 /**
     * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
	int delete(${fileName} ${field}) throws Exception;


}
