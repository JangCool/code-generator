//package code.generator.vo;
//
//import code.generator.util.UtilsText;
//
//public class JspVO extends BaseVO{
//
//	@Override
//	public String getPkg() {
//		
//		String returnValue = super.getPkg();
//		
//		if (!UtilsText.isBlank(returnValue) && !UtilsText.isBlank(getBusiness())) {
//			returnValue = returnValue.concat("/").concat(getBusiness());
//		} else if (UtilsText.isBlank(returnValue) && !UtilsText.isBlank(getBusiness())) {
//			returnValue = getBusiness();
//		}
//		
//		if (!UtilsText.isBlank(returnValue) && !UtilsText.isBlank(getSuffixPkg())) {
//			returnValue = returnValue.concat("/").concat(getSuffixPkg());
//		} else if (UtilsText.isBlank(returnValue) && !UtilsText.isBlank(getSuffixPkg())) {
//			returnValue = getSuffixPkg();
//		}
//		
//		
//		return returnValue;
//	}
//}
