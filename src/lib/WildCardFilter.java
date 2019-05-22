package lib;

import java.io.File;
import java.io.FileFilter;

/**
 * A wild card filter implementation. Implements the FileFilter interface and its accept method,
 * so that files can be chosen based on a given match pattern
 * @author andre
 *
 */
public class WildCardFilter implements FileFilter {

	private String matchPattern;
	
	public WildCardFilter(String toMatch) {
		matchPattern = toMatch;
	}
	
	@Override
	public boolean accept(File pathname) {
		String str = pathname.getName();
		if(!str.contains(".")) {
			return false;
		}
		else {
			String[] splitStr = str.split(".");
			String extension = splitStr[splitStr.length - 1];
			if(extension.equals(matchPattern)) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}
