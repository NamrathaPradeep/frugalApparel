package exceptions;

public class ShoppingDbFailure extends Exception {
	private static final long serialVersionUID = 1L;
	public static final int STMT_FAILED = 0;
	public static final int INVALID_USER = 1;
	public static final int DUPLICATE_USER = 2;	
	public static final int DUPLICATE_CARD = 5;
	
	private int failureReason;
	
	public ShoppingDbFailure(int failureReason) {
		this.failureReason = failureReason;
	}
	
	public ShoppingDbFailure(int failureReason, String msg) {
		super(msg);
		this.failureReason = failureReason;
	}
	
	public int getFailureReason() {
		return failureReason;
	}
	
	public String getReasonStr() {
		switch (failureReason) {
		case STMT_FAILED:
			return "Failure Executing Statement";
		case INVALID_USER:
			return "Invalid user id";
		case DUPLICATE_USER:
			return "Duplicate user id";			
		case DUPLICATE_CARD:
			return "Duplicate card";
		default:
			return "Unknown Reason";
		}
	}

}
