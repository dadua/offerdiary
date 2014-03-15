package com.socialbaba.games.pairmerge.ui.common;

public class LoggerFactory {
	
	public static String getTagForLogging() {
		Throwable t = new Throwable();
		StackTraceElement directCaller = t.getStackTrace()[1];
		return directCaller.getClassName();
	}

}
