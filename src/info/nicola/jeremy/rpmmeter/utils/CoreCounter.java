package info.nicola.jeremy.rpmmeter.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

/**
 * This class is a helper class to count the number of cores on the device
 * @see http://stackoverflow.com/questions/7962155/how-can-you-detect-a-dual-core-cpu-on-an-android-device-from-code
 * @author Jeremy Nicola
 */
public class CoreCounter {

	private static int number=-1;
	
	public static int count(){
		if(number==-1){
		try{
			File dir=new File("/sys/devices/system/cpu/");
			File[] files=dir.listFiles(new FileFilter() {
				
				@Override
				public boolean accept(File pathname) {
					
					// Check if filename is "cpu"
					if(Pattern.matches("cpu[0-9]", pathname.getName())){
						return true;
					}
					return false;
				}
			});
			number=files.length;
			return files.length;
		}catch(Exception e){
			// Default: return 1 core
			number=1;
			return 1;
		}
		}else{
			return number;
		}
	}
}
