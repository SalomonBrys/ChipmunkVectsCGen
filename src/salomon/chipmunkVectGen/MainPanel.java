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
	
	private ArrayList<Point> _points = new ArrayList<Point>();
	private Point _imgPos = new Point(0, 0);
	private Point _activePoint = null;

	private Image _img;
	private boolean _isNewShape;
	
	public void setImg(Image img) {
		this._img = img;
		this.startNewShape();
	}

	public void startNewShape() {
		_isNewShape = true;
		_points.clear();
		this.repaint();
	}

	public void endNewShape() {
		this._isNewShape = false;
		this.repaint();
	}

	public boolean isNewShape() {
		return this._isNewShape;
	}

	public MainPanel() {
        //setBorder(BorderFactory.createLineBorder(Color.black));
        this.addMouseListener(this);
    }

	private void drawPoint(Graphics2D g2, Point p) {
		if (p == _activePoint)
			g2.setColor(new Color(0xCC, 0xCC, 0xFF));
		else
			g2.setColor(new Color(0xFF, 0xCC, 0xCC));
		g2.fillOval(p.x + _imgPos.x, p.y + _imgPos.y, 8, 8);
		g2.setColor(new Color(0x00, 0x00, 0x00));
		g2.drawOval(p.x + _imgPos.x, p.y + _imgPos.y, 8, 8);
	}

	private void drawLine(Graphics2D g2, Point s, Point e) {
		g2.setColor(new Color(0x00, 0x00, 0x88));
		g2.setStroke(new BasicStroke(2.0f));
		g2.drawLine(s.x + _imgPos.x + 4, s.y + _imgPos.y + 4, e.x + _imgPos.x + 4, e.y + _imgPos.y + 4);
		g2.setStroke(new BasicStroke(1.0f));
	}
	
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        if (_img == null) {
        	g2.drawString("Please load an image", this.getSize().width / 3, this.getSize().height / 3);
        	return ;
        }

        _imgPos.x = (this.getSize().width - _img.getWidth(null)) / 2;
        _imgPos.y = (this.getSize().height - _img.getHeight(null)) / 2;
        g2.drawImage(_img, _imgPos.x, _imgPos.y, null);
        
        Point lastPoint = null;
        for (Point point : _points) {
        	if (lastPoint != null) {
        		drawLine(g2, lastPoint, point);

        		drawPoint(g2, lastPoint);
        	}
        	lastPoint = point;
        	
    		drawPoint(g2, point);
		}
        if (!_isNewShape && _points.size() > 2) {
    		drawLine(g2, lastPoint, _points.get(0));
    		drawPoint(g2, lastPoint);
    		drawPoint(g2, _points.get(0));
        }
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		if (_img == null)
			return;
		
		if (e.getButton() != MouseEvent.BUTTON1)
			return ;
		
		if (_isNewShape) {
			_points.add(new Point(e.getX() - _imgPos.x - 6, e.getY() - _imgPos.y - 6));
			this.repaint();
		}
		else if (e.getClickCount() == 2) {
			// NEW point
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
		if (e.getButton() != MouseEvent.BUTTON1)
			return ;

		if (_isNewShape)
			return ;
		
		for (Point point : _points) {
			if (Math.abs(e.getX() - 6 - _imgPos.x - point.x) < 6 && Math.abs(e.getY() - 6 - _imgPos.y - point.y) < 6) {
				_activePoint = point;
				break ;
			}
		}
		this.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}  
}
