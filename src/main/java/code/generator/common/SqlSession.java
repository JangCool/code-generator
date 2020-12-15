package code.generator.common;

import code.generator.elements.jdbc.global.SqlSessionElement;
import lombok.Data;

@Data
public class SqlSession {
	
	/**
	 * Generator에서 이용할 템플릿 경로
	 */
	private String name = Const.DEFAULT_SQL_SESSION;
	
	public SqlSession(SqlSessionElement sqlSessionElement) {
		
		if(sqlSessionElement == null) {
			return;
		}
		
		this.name = sqlSessionElement.getName();
			
		
	}
	
}