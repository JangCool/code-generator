package code.generator.vo;

public class BaseVO {

	private String business;
	private String defaultPackage;
	private String suffixPackage;

	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	
	public String getDefaultPackage() {
		return defaultPackage;
	}
	
	public void setDefaultPackage(String defaultPackage) {
		this.defaultPackage = defaultPackage;
	}
	
	public String getSuffixPackage() {
		return suffixPackage;
	}
	
	public void setSuffixPackage(String suffixPackage) {
		this.suffixPackage = suffixPackage;
	}

}
