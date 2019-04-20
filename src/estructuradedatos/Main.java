package estructuradedatos;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import estructuradedatos.gui.MainFrame;

/**
 * Clase de inicio del programa
 * @author Pablo Herrera
 *
 */
public class Main {
	private final static String windowsLF = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	private final static String metalLF = "javax.swing.plaf.metal.MetalLookAndFeel";
	private final static String nimbusLF = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
	private final static String defaultLF = nimbusLF;

	public static void main(String[] args) {
		String lookAndFeel = null;
		if (args.length > 0) {
			lookAndFeel = args[0];
		}
		if (lookAndFeel != null && lookAndFeel.equals("windows")) {
			lookAndFeel = windowsLF;
		} else if (lookAndFeel != null && lookAndFeel.equals("metal")) {
			lookAndFeel = metalLF;
		} else if (lookAndFeel != null && lookAndFeel.equals("nimbus")) {
			lookAndFeel = nimbusLF;
		} else {
			lookAndFeel = defaultLF;
		}
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}

}
