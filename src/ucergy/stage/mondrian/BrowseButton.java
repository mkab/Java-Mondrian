/**
 * 
 */
package ucergy.stage.mondrian;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;



/**
 * @author mkab
 *
 */
public class BrowseButton implements ActionListener {

	private final MondrianConfigGUI m;

	public BrowseButton(MondrianConfigGUI _m) {
		m = _m;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		System.out.println(evt.getActionCommand());

		JFileChooser filechooser = new JFileChooser();
		filechooser.setFileFilter(new MyFilter());
		int result = filechooser.showOpenDialog(m);
		
		//get the absolute path of the selected file
		if(result == JFileChooser.APPROVE_OPTION) {
			m.getJtfXml().setText(filechooser.getSelectedFile().getAbsolutePath());
		}
	}

}

/**
 * File filter to filter out only directories and XML files 
 */
class MyFilter extends javax.swing.filechooser.FileFilter {

	public boolean accept(File file) {
		if(file.isDirectory()) {
			return true;
		}

		String filename = file.getName();
		return filename.endsWith(".xml");
	}

	public String getDescription() {
		return "*.xml";
	}
}