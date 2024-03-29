<#assign TableOperation=statics["code.generator.make.TableOperation"]>
<#assign UtilsText=statics["code.generator.util.UtilsText"]>

package ${package}.base;

import java.util.List;
import java.lang.Object;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import ${model};


/**
 * 테이블 명 : ${name}
 * 설명       : ${desc}
 *
 * ※ 절대 수정 금지. 해당 파일은 code generator 작동 시 내용을 전부 덮어 씌우게 됩니다. 
 *
 */
 

public interface Base${fileName}Dao {

    /**
     * 이 findByPrimaryKey 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
    ${findByPrimaryKey}
	${fileName} findByPrimaryKeyParam(<#if pkColumns??><#list pkColumns as column><#if column_index gt 0 > ,</#if>@Param("${UtilsText.convert2CamelCase('${column.COLUMN_NAME}')}") ${TableOperation.javaType('${column.DATA_TYPE}')} ${UtilsText.convert2CamelCase('${column.COLUMN_NAME}')}</#list></#if>) throws Exception;

    /**
     * 이 findByPrimaryKey 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
    ${findByPrimaryKey}
	${fileName} findByPrimaryKey(${fileName} ${field}) throws Exception;

    /**
     * 이 findAll 메소드는 Code Generator를 통하여 생성 되었습니다.
     * !!!! 데이터 양이 많지 않은 경우에만 사용하시기 바랍니다. !!!!
     */
    ${findAll}
	List<${fileName}> findAll() throws Exception;
	
    /**
     * <pre>
     * 이 findBy 메소드는 Code Generator를 통하여 생성 되었습니다.
     * 데이터 조회 건이 0개나 1개 이상일 경우 사용합니다.
     * </pre>
     */
     ${find}
	List<${fileName}> find(${fileName} ${field}) throws Exception;
	
    /**
     * <pre>
     * 이 findBy 메소드는 Code Generator를 통하여 생성 되었습니다.
     * 데이터 조회 건이 0개나 1개일 경우 사용합니다. 1개이상 데이터가 조회되면 오류가 발생합니다.
     * </pre>
     */
     ${find}
	${fileName} findOne(${fileName} ${field}) throws Exception;
		
    /**
     * 이 select 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
    ${insert}
	int insert(${fileName} ${field}) throws Exception;
	
    /**
     * 이 updateByPrimaryKey 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
    ${updateByPrimaryKey}
	int updateByPrimaryKey(${fileName} ${field}) throws Exception;
	
    /**
     * 이 updateByPrimaryKey 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
    ${update}
	int update(${fileName} ${field}) throws Exception;
	
	 /**
     * 이 deleteByPrimaryKey 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
    ${deleteByPrimaryKey} 
	int deleteByPrimaryKeyParam(<#if pkColumns??><#list pkColumns as column><#if column_index gt 0 > ,</#if>@Param("${UtilsText.convert2CamelCase('${column.COLUMN_NAME}')}") ${TableOperation.javaType('${column.DATA_TYPE}')} ${UtilsText.convert2CamelCase('${column.COLUMN_NAME}')}</#list></#if>) throws Exception;

	/**
     * 이 deleteByPrimaryKey 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
    ${deleteByPrimaryKey} 
	int deleteByPrimaryKey(${fileName} ${field}) throws Exception;
	
	/**
     * 이 delete 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
    ${delete} 
	int delete(${fileName} ${field}) throws Exception;
	
	/**
     * 이 deleteAll 메소드는 Code Generator를 통하여 생성 되었습니다.
     */
    ${deleteAll} 
	int deleteAll() throws Exception;
	

}
