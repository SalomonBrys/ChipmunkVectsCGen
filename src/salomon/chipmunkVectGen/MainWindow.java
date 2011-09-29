package salomon.chipmunkVectGen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

public class MainWindow {

	private JFrame frame;
	
	private File previousDir;
	
	public JButton newShapeButton;
	public JButton exportShapeButton;
	public JButton importShapeButton;
	
	public MainPanel mainPanel;

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
		
		mainPanel = new MainPanel(this);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		frame.getContentPane().add(mainPanel);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(new Rectangle(21, 0, 0, 0));
		buttonPanel.setBackground(UIManager.getColor("Button.background"));
		buttonPanel.setMaximumSize(new Dimension(200, 32767));
		buttonPanel.setMinimumSize(new Dimension(200, 10));
		buttonPanel.setPreferredSize(new Dimension(200, 10));
		frame.getContentPane().add(buttonPanel);
		
		newShapeButton = new JButton("New shape");
		newShapeButton.setEnabled(false);

		importShapeButton = new JButton("Import Shape Vects");
		importShapeButton.setEnabled(false);

		exportShapeButton = new JButton("Export Shape Vects");
		exportShapeButton.setEnabled(false);

		JButton loadImageButton = new JButton("Load Image");
		loadImageButton.addActionListener(new ActionListener() {
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
						mainPanel.setImg(ImageIO.read(f));
						newShapeButton.setText("End Shape");
						importShapeButton.setEnabled(true);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		buttonPanel.add(loadImageButton);

		newShapeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (mainPanel.isNewShape()) {
					mainPanel.endNewShape();
					exportShapeButton.setEnabled(true);
				}
				else {
					mainPanel.startNewShape();
					newShapeButton.setEnabled(false);
					exportShapeButton.setEnabled(false);
				}
			}
		});
		buttonPanel.add(newShapeButton);
		
		exportShapeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrame txtFrame = new JFrame();
				txtFrame.setBounds(150, 150, 300, 400);
				JTextPane txtPane = new JTextPane();
				txtPane.setEditable(false);
				txtPane.setText(mainPanel.getCArray());
				txtFrame.getContentPane().add(txtPane, BorderLayout.CENTER);
				txtFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				txtFrame.setVisible(true);
			}
		});
		buttonPanel.add(exportShapeButton);
		
		importShapeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoadFrame load = new LoadFrame(MainWindow.this);
				load.setVisible(true);
				load.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		buttonPanel.add(importShapeButton);
	}

}
