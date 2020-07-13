package code.generator.exception;

public class NotFoundPrimaryKey extends RuntimeException {
	
	public NotFoundPrimaryKey() {
		super();
	}
	
	public NotFoundPrimaryKey(String message) {
		super(message);
	}
	
	public NotFoundPrimaryKey(Throwable e) {
		super(e);
	}	

}
