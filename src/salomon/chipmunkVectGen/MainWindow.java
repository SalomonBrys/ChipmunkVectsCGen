package salomon.chipmunkVectGen;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.UIManager;

public class MainWindow {

	private JFrame	frame;
	
	private File previousDir;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 585, 348);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("file");
		menuBar.add(mnFile);
		
		final MainPanel panel = new MainPanel();

		JMenuItem mntmLoad = new JMenuItem("Load Image");
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fc = new JFileChooser();
				fc.addChoosableFileFilter(new FileFilter() {
					@Override
					public String getDescription() {
						return "JPEG or PNG";
					}
					
					@Override
					public boolean accept(File f) {
						if (f.isDirectory()) {
					        return true;
					    }
						
						int dot = f.getName().lastIndexOf('.');
						String ext = new String();
						if (dot > 0 &&  dot < f.getName().length() - 1) {
				            ext = f.getName().substring(dot + 1).toLowerCase();
				        }
						return (ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png"));
					}
				});
				fc.setAccessory(new ImagePreview(fc));
				if (previousDir != null)
					fc.setCurrentDirectory(previousDir);
				int result = fc.showOpenDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
					previousDir = fc.getCurrentDirectory();
					File f = fc.getSelectedFile();
					try {
						panel.img = ImageIO.read(f);
						panel.repaint();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		mnFile.add(mntmLoad);
		
		JMenuItem mntmPrintCArray = new JMenuItem("Print C array");
		mnFile.add(mntmPrintCArray);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		frame.getContentPane().add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(UIManager.getColor("Button.background"));
		panel_1.setMaximumSize(new Dimension(200, 32767));
		panel_1.setMinimumSize(new Dimension(200, 10));
		panel_1.setPreferredSize(new Dimension(200, 10));
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		frame.getContentPane().add(panel_1);
		
		JButton btnNewButton = new JButton("New shape");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		JButton btnNewButton_1 = new JButton("Load Image");
		panel_1.add(btnNewButton_1);
		panel_1.add(btnNewButton);
		
		JButton btnExportShapeVects = new JButton("Export Shape Vects");
		panel_1.add(btnExportShapeVects);
	}

}
