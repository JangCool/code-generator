package code.generator.common;

import code.generator.elements.children.global.GlobalElement;

public class Global {
	
	private static SqlSession sqlSession;
	
	private static BasePackage basePackage;
	
	private static BasePath basePath;

	private static ExcludeColumn excludeColumn;

	public static void init(GlobalElement globalElement) {
		
		if(globalElement == null) {
			
			Log.println("");
			Log.debug("global element가 지정되어 있지 않아 기본 값으로 처리 됩니다.");
			Log.println("");
			
			sqlSession = new SqlSession(null);
			basePackage = new BasePackage(null);
			basePath = new BasePath(null);
			excludeColumn = new ExcludeColumn(null);
		}else {
			sqlSession = new SqlSession(globalElement.getSqlSession());
			basePackage = new BasePackage(globalElement.getBasePackage());
			basePath = new BasePath(globalElement.getBasePath());
			excludeColumn = new ExcludeColumn(globalElement.getExcludeColumn());
		}
		
	}

	public static SqlSession getSqlSession() {
		return sqlSession;
	}

	public static BasePackage getBasePackage() {
		return basePackage;
	}

	public static BasePath getBasePath() {
		return basePath;
	}

	public static ExcludeColumn getExcludeColumn() {
		return excludeColumn;
	}

}