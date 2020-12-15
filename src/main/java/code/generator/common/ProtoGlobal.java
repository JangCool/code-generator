package code.generator.common;

public class ProtoGlobal {
	
	
	private static BaseProtoPath basePath;


	public static void init(code.generator.elements.protobuf.global.GlobalElement globalElement) {
		
		if(globalElement == null) {
			
			Log.println("");
			Log.debug("global element가 지정되어 있지 않아 기본 값으로 처리 됩니다.");
			Log.println("");
			
			basePath = new BaseProtoPath(null);
		}else {
			basePath = new BaseProtoPath(globalElement.getBasePath());
		}
		
	}

	public static BaseProtoPath getBaseProtoPath() {
		return basePath;
	}

}