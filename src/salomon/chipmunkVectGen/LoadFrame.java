package salomon.chipmunkVectGen;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoadFrame extends JFrame {

	private static final long	serialVersionUID	= -5172187784853564269L;

	private JPanel		contentPane;
	private JTextPane	textPane;
	private MainWindow	_w;

	/**
	 * Create the frame.
	 */
	public LoadFrame(MainWindow w) {
		_w = w;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		textPane = new JTextPane();
		contentPane.add(textPane, BorderLayout.CENTER);
		
		JButton btnLoad = new JButton("LOAD");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_w.mainPanel.endNewShape();
				_w.exportShapeButton.setEnabled(true);
				_w.newShapeButton.setEnabled(true);
				_w.mainPanel.loadCArray(textPane.getText());
			}
		});
		contentPane.add(btnLoad, BorderLayout.SOUTH);
	}

}
