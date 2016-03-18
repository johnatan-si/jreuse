package Util;

public class Log {

	private static String lastMsg = "";
	private static int repetitions = 1;
	
	public static void log(String msg) {
		Util.write("log.log", msg + "\r\n", true);
	}
	
	public static void logError(String msg, Exception e) {
		
		if(lastMsg.equals(msg)) {
			repetitions++;
		} else {
			if(lastMsg != "") {
				Util.write("logError.log", lastMsg + " " + (repetitions == 1 ? "" : repetitions) + "\r\n", true);
			}
			lastMsg = msg;
			repetitions = 1;
			if(e != null) {
				Util.writeError("logError.log", msg + "\r\n", true, e);
				lastMsg = "";
			}
		}
	}
	
	public static void closeLog() {
		if(lastMsg != "") {
			Util.write("logError.log", lastMsg + " " + (repetitions == 1 ? "" : repetitions) + "\r\n", true);
			lastMsg = "";
			repetitions = 0;
		}
	}
	
	public static void logRemovedClass(String msg) {
		Util.write("removedClasses.log", msg + "\r\n", true);
	}
}
