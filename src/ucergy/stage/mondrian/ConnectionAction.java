/**
 * 
 */
package ucergy.stage.mondrian;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 * @author mkab
 *
 */
public class ConnectionAction implements ActionListener {

	private final MondrianConfigGUI m;

	public ConnectionAction(MondrianConfigGUI _m) {
		m = _m;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {
		System.out.println("Testing connection...");
		Connection connect = null;

		String passwd = new String(m.getJpfPasswd().getPassword());


		MondrianData data = null;
		MondrianConfig mc = null;

		try {

			data = new MondrianData(m.getJtfHost().getText(),
					m.getJtfDbName().getText(),
					(String) m.getComboConType().getSelectedItem(),
					Integer.parseInt(m.getJtfPort().getText()), 
					m.getJtfUser().getText(), 
					passwd, 
					m.getMdxTextArea().getText()
					);

			System.out.println(data.toString());
			mc = new MondrianConfig(data);
			System.out.println(mc.toString());



			//Load the required database Driver
			Class.forName(mc.getJdbcDriver());
			connect = DriverManager.getConnection(mc.getJdbcUrl(), mc.getData().getUsername(),
					mc.getData().getPassword());

			if (connect != null) {
				System.out.println("Connection OK!");
				String message = "Connection to database [" + data.getDatabaseName() +
						"] is OK!\nHostname:\t" + data.getHostname() + "\n" +
						"Port:\t" + data.getPortNumber() + "\n" +
						"Database name:\t" + data.getDatabaseName() ;

				JOptionPane.showMessageDialog(m, message, "Connection",
						JOptionPane.INFORMATION_MESSAGE);
			}

			//close connection
			connect.close();

		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(m, "Veuillez choisir votre type de connection", 
					"Error", JOptionPane.ERROR_MESSAGE);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(m, "Veuillez entrez le port", "Error", 
					JOptionPane.ERROR_MESSAGE);
		} catch (ClassNotFoundException e) {
			System.out.println("class not found exception");
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.print("sql exception\n");
			System.out.println(e.getMessage());
		} finally {
			data = null;
			mc = null;
		}
	}

}
