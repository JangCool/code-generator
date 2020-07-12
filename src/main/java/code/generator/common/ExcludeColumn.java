package code.generator.common;

import code.generator.elements.children.global.ExcludeColumnElement;
import lombok.Data;

@Data
public class ExcludeColumn {
	
	/**
	 * Insert 쿼리에서 제외할 컬럼명 지정
	 */
	private String insert;
	
	/**
	 * Update 쿼리에서 제외할 컬러명 지정
	 */
	private String update;

	
	public ExcludeColumn(ExcludeColumnElement excludeColumnElement) {
		
		if(excludeColumnElement == null) {
			return;
		}
		
		this.insert =excludeColumnElement.getInsert();
		this.update =excludeColumnElement.getUpdate();
			
		
	}
	
}