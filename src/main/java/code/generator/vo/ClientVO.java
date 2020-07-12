//package code.generator.vo;
//
//import code.generator.util.UtilsText;
//
//public class ClientVO extends BaseVO{
//
//	private String clientName;
//	private String clientUrl;
//	
//	@Override
//	public String getPkg() {
//		
//		String returnValue = super.getPkg();
//		
//		if(!UtilsText.isBlank(getBusiness())){
//			returnValue = returnValue.concat(".").concat(getBusiness());
//		}
//		
//		if(!UtilsText.isBlank(getSuffixPkg())){
//			returnValue = returnValue.concat(".").concat(getSuffixPkg());
//		}
//		
//		
//		return returnValue;
//	}
//
//	public String getClientName() {
//		return clientName;
//	}
//
//	public void setClientName(String clientName) {
//		this.clientName = clientName;
//	}
//
//	public String getClientUrl() {
//		return clientUrl;
//	}
//
//	public void setClientUrl(String clientUrl) {
//		this.clientUrl = clientUrl;
//	}
//}
