import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.Vector;

public class GeneralFileFilter extends FileFilter{
	String			description;
	Vector<String>	extensions;
	int				index;

	GeneralFileFilter(){
		description = "";
		extensions = new Vector<String>();
		index = 0;
	}

	void setDescription(String descIn){
		if (description.equals(""))
			description = descIn;
		else
			description += ", " + descIn;
	}

	public String getDescription(){
		return description;
	}

	void addExtension(String extIn){
		extensions.add(extIn);
	}

	public boolean accept(File fIn){
		String fileName, ext;

		fileName = fIn.getName();
		int mid = fileName.lastIndexOf(".");
		ext = fileName.substring(mid + 1, fileName.length());

		for (int i = 0; i < extensions.size(); ++i)
			if (ext.equals(extensions.elementAt(i)))
				return true;

		return false;
	}
}