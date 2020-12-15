package code.generator.make;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import code.generator.common.Global;
import code.generator.common.Log;
import code.generator.elements.ConfigurationElement;
import code.generator.elements.jdbc.ServiceElement;
import code.generator.elements.jdbc.ServicesElement;
import code.generator.util.UtilsText;

public class MakeService extends BaseMake{
	
	public MakeService() {
		super();
	}
	
	public MakeService(ConfigurationElement configurationElement) {
		super(configurationElement);
	}

	@Override
	public void generator() throws Exception {
		generator(null);
	}
	
	@Override
	public void generator(String fileName) throws Exception {

		
		List<ServicesElement> servicesList = getConfiguration().getServices();
		if(servicesList == null) {
			return;
		}
		
   		int servicesListSize = servicesList.size();
   		if(servicesListSize > 0 ) {
	   			
	   		Log.println("");
	   		Log.debug("==================================== Service Generator ======================================");
	   		Log.debug("지금 Service 파일 생성을 시작 합니다.  ");
	   		Log.debug("");
	   		Log.debug("path source = " + Global.getBasePath().getSource());
   		
   		}
   		
		for (int i = 0; i < servicesListSize; i++) {
			
			ServicesElement services = servicesList.get(i);
			List<ServiceElement> serviceList = services.getService();
			
	   		Log.debug("================================================================================================");
	   		Log.debug("business						= " + services.getBusiness());
	   		Log.debug("package						= " + services.getServicePackage());
	   		Log.debug("suffix-package 				= " + services.getSuffixPackage());
	   		Log.debug("proxy-target-class			= " + services.isProxyTargetClass());
	   		Log.debug("================================================================================================");
			
			
			for (ServiceElement service : serviceList) {
				
		   		String serviceName	= service.getName();
				
				String folder = UtilsText.concat(new File(Global.getBasePath().getSource()).getAbsolutePath(), File.separator, services.getServicePath());
				String path = UtilsText.concat(folder,File.separator,serviceName,"Service.java");
				
				String folderImpl = UtilsText.concat(new File(Global.getBasePath().getSource()).getAbsolutePath(),File.separator,services.getServicePath());		
				String pathImpl = UtilsText.concat(folder,File.separator,serviceName,"ServiceImpl.java");
				
				
				Map<String, Object> data = new HashMap<>();
				data.put("serviceName"			, serviceName);
				data.put("package"				, services.getServicePackage());
				data.put("proxyTargetClass"		, services.isProxyTargetClass());
				
				writeTemplate("Service", folder, path, data); 

				if(!services.isProxyTargetClass()) {
					writeTemplate("ServiceImpl", folderImpl, pathImpl, data);						
				}
			}
		}
	}
}
