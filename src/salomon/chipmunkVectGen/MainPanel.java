package salomon.chipmunkVectGen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class MainPanel extends JPanel implements MouseListener {

	private static final long	serialVersionUID	= 4152275898896523755L;
	
	private ArrayList<Point> points = new ArrayList<Point>();
	private Point imgPos = new Point(0, 0);

	public Image img;
	
    public MainPanel() {
        //setBorder(BorderFactory.createLineBorder(Color.black));
        this.addMouseListener(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        if (img == null) {
        	g2.drawString("Please load an image", this.getSize().width / 3, this.getSize().height / 3);
        	return ;
        }

        imgPos.x = (this.getSize().width - img.getWidth(null)) / 2;
        imgPos.y = (this.getSize().height - img.getHeight(null)) / 2;
        g2.drawImage(img, imgPos.x, imgPos.y, null);
        
        Point lastPoint = null;
        for (Point point : points) {
        	if (lastPoint != null) {
        		g2.setColor(new Color(0x00, 0x00, 0x88));
        		g2.setStroke(new BasicStroke(2.0f));
        		g2.drawLine(lastPoint.x + imgPos.x + 4, lastPoint.y + imgPos.y + 4, point.x + imgPos.x + 4, point.y + imgPos.y + 4);
        		g2.setStroke(new BasicStroke(1.0f));

        		g2.setColor(new Color(0xFF, 0xCC, 0xCC));
        		g2.fillOval(lastPoint.x + imgPos.x, lastPoint.y + imgPos.y, 8, 8);
        		g2.setColor(new Color(0x00, 0x00, 0x00));
        		g2.drawOval(lastPoint.x + imgPos.x, lastPoint.y + imgPos.y, 8, 8);
        	}
        	lastPoint = point;
        	
        	g2.setColor(new Color(0xFF, 0xCC, 0xCC));
        	g2.fillOval(point.x + imgPos.x, point.y + imgPos.y, 8, 8);
        	g2.setColor(new Color(0x00, 0x00, 0x00));
        	g2.drawOval(point.x + imgPos.x, point.y + imgPos.y, 8, 8);
		}
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		if (img == null)
			return;
		
		if (e.getButton() == MouseEvent.BUTTON1) {
			points.add(new Point(e.getX() - imgPos.x - 6, e.getY() - imgPos.y - 6));
			this.repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}  
}
