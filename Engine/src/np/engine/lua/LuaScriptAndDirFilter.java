package np.engine.lua;

import java.io.File;
import java.io.FilenameFilter;

import javax.swing.filechooser.FileFilter;


public class LuaScriptAndDirFilter implements FilenameFilter {

	@Override
	public boolean accept(File arg0, String arg1) {
		File file = new File(arg0.getAbsolutePath() + "/" + arg1);
		return (arg1.endsWith(".lua") || file.isDirectory());
	}


}
