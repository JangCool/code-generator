package code.generator.common;

import lombok.Data;

@Data
public class BaseProtoPath {
	
	/**
	 * Generator에서 이용할 템플릿 경로
	 */
	private String protoc  = "";
	
	/**
	 * Generator를 이용하여 classapth에  위치할 Java파일 경로
	 */
	private String javaOut = "";

	/**
	 * Generator를 이용하여 javascript에  위치할 정적파일 경로
	 */
	private String jsOut = "";
	
	
	public BaseProtoPath(code.generator.elements.protobuf.global.BasePathElement basePathElement) {
		
		if(basePathElement == null) {
			return;
		}
		
		this.protoc = basePathElement.getProtoc();
		this.javaOut = basePathElement.getJavaOut();
		this.jsOut = basePathElement.getJsOut();
	}
	
}