package ucergy.stage.mondrian;

import java.io.IOException;

import org.jdom2.JDOMException;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String path = EnvFactory.determineTomcatDir();
		//String path = System.getProperty("catalina.base");
		log(path);

		String mdx = "select \n   {[Measures].[somme_note]} on columns \nfrom mondrianC";

		try {
			MondrianData data = new MondrianData("localhost", "workbench", "mysql",
					"/home/mkab/exemple.xml", 3306, "root", "ucergyproject", mdx);
			MondrianConfig m = new MondrianConfig(data);
			m.updateXmlProperties("/home/mkab/datasources.xml");
			m.updateMondrianProperties(path + "/webapps/mondrian/WEB-INF/queries/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void log(Object msg) {
		System.out.println(msg);
	}

}
