/**
 * 
 */
package ucergy.stage.mondrian;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.jdom2.JDOMException;

/**
 * @author mkab
 *
 */
public class ButtonsAction implements ActionListener {

	private final MondrianConfigGUI m;

	public ButtonsAction(MondrianConfigGUI _m) {
		m = _m;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		System.out.println(evt.getActionCommand());



		/**If button OK is pressed*/
		if(evt.getActionCommand().equalsIgnoreCase("ok")) {

			File f = new File(m.getJtfXml().getText());

			String passwd = new String(m.getJpfPasswd().getPassword());

			MondrianData data = null;
			MondrianConfig mc = null;

			try{
				data = new MondrianData(m.getJtfHost().getText(),
						m.getJtfDbName().getText(),
						(String) m.getComboConType().getSelectedItem(), 
						f.getAbsolutePath(),
						Integer.parseInt(m.getJtfPort().getText()),
						m.getJtfUser().getText(), passwd, 
						m.getMdxTextArea().getText()
						);

				//get Tomcat's installation directory
				String path = m.getJtfTomcat().getText();

				mc = new MondrianConfig(data);

				//update *.jsp files
				mc.updateMondrianProperties(path + "/webapps/mondrian/WEB-INF/queries/");

				//update datasources.xml
				mc.updateXmlProperties(path + "/webapps/mondrian/WEB-INF/");

			} catch (IOException e) {
				e.printStackTrace();
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(m, 
						"Entrer le numero de port (un entier)", "Port error", 
						JOptionPane.ERROR_MESSAGE);
			} catch (XmlFileException e) {
				JOptionPane.showMessageDialog(m, e.getError(), 
						"XML file error", JOptionPane.ERROR_MESSAGE);
			} catch (JDOMException e) {
				e.printStackTrace();
			} finally {
				data = null;
				mc   = null;
			}

		}


		if(evt.getActionCommand().equalsIgnoreCase("detect")) {
			String path = EnvFactory.determineTomcatDir();
			m.getJtfTomcat().setText(path);
		}


		/**If button reset is pressed*/
		if(evt.getActionCommand().equalsIgnoreCase("reset")) {
			m.getJtfDbName().setText("");
			m.getJtfHost().setText("");
			m.getJtfPort().setText("");
			m.getJtfUser().setText("");
			m.getJtfTomcat().setText("");
			m.getJtfXml().setText("");
			m.getJtfInfo().setText("");
			m.getJpfPasswd().setText("");
			m.getMdxTextArea().setText("");
			m.getComboConType().setSelectedItem("");
		}
	}

}
