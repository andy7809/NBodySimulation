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
		if(pathname.getName().contains(matchPattern)) {
			return true;
		}
		return false;
	}
}
