package code.generator.make;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import code.generator.common.Log;
import code.generator.common.ProtoGlobal;
import code.generator.elements.ProtoConfigurationElement;
import code.generator.elements.protobuf.ProtobufElement;
import code.generator.elements.protobuf.ProtobufsElement;
import code.generator.util.UtilsText;

public class Protobuf {
	
	protected ProtoConfigurationElement configuration;

	public Protobuf(ProtoConfigurationElement configurationElement) {
		this.configuration = configurationElement;
	}
	

	public void generator() throws Exception {
		generator(null);
	}

	public void generator(String fileName) throws Exception {

		
		List<ProtobufsElement> protobufsList = configuration.getProtobufs();
		if(protobufsList == null) {
			return;
		}
		
   		int protobufsListSize = protobufsList.size();
   		if(protobufsListSize > 0 ) {
	   			
	   		Log.println("");
	   		Log.debug("==================================== Protobuf Generator ======================================");
	   		Log.debug("지금 Protobuf 파일 생성을 시작 합니다.  ");
	   		Log.debug("");
	   		Log.debug("java_out = " + ProtoGlobal.getBaseProtoPath().getJavaOut());
	   		Log.debug("js_out = " + ProtoGlobal.getBaseProtoPath().getJsOut());
   		
   		}
   		
		for (int i = 0; i < protobufsListSize; i++) {
			
			ProtobufsElement protobufsElement = protobufsList.get(i);
			List<ProtobufElement> protobufs = protobufsElement.getProtobuf();
			
	   		Log.debug("================================================================================================");
			for (ProtobufElement protobuf : protobufs) {
		   		Log.debug("name						= " + protobuf.getName());
				
		   		ProcessBuilder builder = new ProcessBuilder();
		   		
		   		List<String> command =  new ArrayList<>();
		   		
		   		command.add(ProtoGlobal.getBaseProtoPath().getProtoc() + File.separator + "protoc");
		   		
		   		command.add("--proto_path=D:\\project\\hamonica\\workspace\\code-generator\\protobuf");

		   		if(!UtilsText.isBlank(ProtoGlobal.getBaseProtoPath().getJavaOut()) && protobufsElement.isJava()) {
			   		command.add("--java_out="+ProtoGlobal.getBaseProtoPath().getJavaOut());
		   		}
		   		
		   		if(!UtilsText.isBlank(ProtoGlobal.getBaseProtoPath().getJsOut()) && protobufsElement.isJs()) {
			   		command.add("--js_out="+ProtoGlobal.getBaseProtoPath().getJsOut());
		   		}
		   		
		   		command.add("proto/"+protobuf.getName()+".proto");
		   		
		   		builder.command(command);
		   		
//		   		builder.directory(new File(ProtoGlobal.getBaseProtoPath().getProtoc()));
		        Process process = builder.start();
		        Executors.newSingleThreadExecutor().submit(new StreamGobbler(process.getInputStream(), System.out::println));
		        Executors.newSingleThreadExecutor().submit(new StreamGobbler(process.getErrorStream(), System.out::println));
		        int exitCode = process.waitFor();
		        
		        assert exitCode == 0;
			}
	   		Log.debug("================================================================================================");

		}
	}
	
    class StreamGobbler implements Runnable {
        private InputStream inputStream;
        private Consumer<String> consumer;

        public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
        }

        
        public void run() {
            try {
                new BufferedReader(new InputStreamReader(inputStream, "utf-8")).lines()
                        .forEach(consumer);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }
}
