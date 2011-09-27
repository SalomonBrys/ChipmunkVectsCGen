package salomon.chipmunkVectGen;

import java.awt.Dimension;
import java.awt.EventQueue;
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
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import java.awt.Component;
import java.awt.Rectangle;

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
		
		final MainPanel panel = new MainPanel();
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		frame.getContentPane().add(panel);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(new Rectangle(21, 0, 0, 0));
		buttonPanel.setBackground(UIManager.getColor("Button.background"));
		buttonPanel.setMaximumSize(new Dimension(200, 32767));
		buttonPanel.setMinimumSize(new Dimension(200, 10));
		buttonPanel.setPreferredSize(new Dimension(200, 10));
		frame.getContentPane().add(buttonPanel);
		
		final JButton NewShapeButton = new JButton("New shape");
		NewShapeButton.setEnabled(false);

		final JButton ExportShapeButton = new JButton("Export Shape Vects");
		ExportShapeButton.setEnabled(false);

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
						panel.setImg(ImageIO.read(f));
						NewShapeButton.setText("End Shape");
						NewShapeButton.setEnabled(true);
						ExportShapeButton.setEnabled(true);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		buttonPanel.add(loadImageButton);

		NewShapeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (panel.isNewShape()) {
					panel.endNewShape();
					NewShapeButton.setText("Start New Shape");
				}
				else {
					panel.startNewShape();
					NewShapeButton.setText("End Shape");
				}
			}
		});
		buttonPanel.add(NewShapeButton);
		
		buttonPanel.add(ExportShapeButton);
	}

}
