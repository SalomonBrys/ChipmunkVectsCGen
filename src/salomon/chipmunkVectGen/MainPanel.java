package salomon.chipmunkVectGen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class MainPanel extends JPanel implements MouseListener, MouseMotionListener {

	private static final long	serialVersionUID	= 4152275898896523755L;

	private static final int DISPLAY_DELTA = 6;
	private static final int EXPORT_DELTA = 4;
	
	
	private ArrayList<Point> _points = new ArrayList<Point>();
	private Point _imgPos = new Point(0, 0);
	private Point _activePoint = null;

	private Image _img;
	private boolean _isNewShape;
	
	private MainWindow _w;
	
	public void setImg(Image img) {
		this._img = img;
		this.startNewShape();
	}

	public void startNewShape() {
		_isNewShape = true;
		_points.clear();
		_w.newShapeButton.setText("End Shape");
		this.repaint();
	}

	public void endNewShape() {
		this._isNewShape = false;
		_w.newShapeButton.setText("Start New Shape");
		this.repaint();
	}

	public boolean isNewShape() {
		return this._isNewShape;
	}

	public MainPanel(MainWindow w) {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        
        _w = w;
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

    private Point _getPointFromMouseEvent(MouseEvent e) {
		Point ret = null;
		for (Point point : _points) {
			if (Math.abs(e.getX() - DISPLAY_DELTA - _imgPos.x - point.x) < 6 && Math.abs(e.getY() - DISPLAY_DELTA - _imgPos.y - point.y) < 6) {
				ret = point;
				break ;
			}
		}
		return ret;
    }
    
	@Override
	public void mouseClicked(MouseEvent e) {
		if (_img == null)
			return;
		
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (_isNewShape) {
				_points.add(new Point(e.getX() - DISPLAY_DELTA - _imgPos.x, e.getY() - DISPLAY_DELTA - _imgPos.y));
				if (_points.size() >= 3)
					_w.newShapeButton.setEnabled(true);
				this.repaint();
			}
			else if (e.getClickCount() == 2) {
				Point close = null;
				for (Point point : _points) {
					if (close == null || (
								Math.abs(e.getX() - DISPLAY_DELTA - _imgPos.x - point.x) + Math.abs(e.getY() - DISPLAY_DELTA - _imgPos.y - point.y)
							<
								Math.abs(e.getX() - DISPLAY_DELTA - _imgPos.x - close.x) + Math.abs(e.getY() - DISPLAY_DELTA - _imgPos.y - close.y)
							)
						)
						close = point;
				}
				
				Point newPoint = new Point(e.getX() - DISPLAY_DELTA - _imgPos.x, e.getY() - DISPLAY_DELTA - _imgPos.y);
				
				int curIndex = _points.indexOf(close);
				int nextIndex = curIndex + 1;
				if (nextIndex >= _points.size())
					nextIndex = 0;
				int prevIndex = curIndex - 1;
				if (prevIndex <= -1)
					prevIndex = _points.size() - 1;
				
				if (
							Math.abs(e.getX() - DISPLAY_DELTA - _imgPos.x - _points.get(nextIndex).x) + Math.abs(e.getY() - DISPLAY_DELTA - _imgPos.y - _points.get(nextIndex).y)
						<=
							Math.abs(e.getX() - DISPLAY_DELTA - _imgPos.x - _points.get(prevIndex).x) + Math.abs(e.getY() - DISPLAY_DELTA - _imgPos.y - _points.get(prevIndex).y)
					)
					_points.add(nextIndex, newPoint);
				else
					_points.add(curIndex, newPoint);

				this.repaint();
			}
		}
		else if (e.getButton() == MouseEvent.BUTTON3 && !_isNewShape && _points.size() > 3) {
			Point p = _getPointFromMouseEvent(e);
			if (p != null)
				_points.remove(p);
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
		if (_isNewShape || e.getButton() != MouseEvent.BUTTON1)
			return ;

		_activePoint = _getPointFromMouseEvent(e);
		this.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (_isNewShape || e.getButton() != MouseEvent.BUTTON1 || _activePoint == null)
			return ;
		
		_activePoint.x = e.getX() - DISPLAY_DELTA - _imgPos.x;
		_activePoint.y = e.getY() - DISPLAY_DELTA - _imgPos.y;
		
		this.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public String getCArray() {
		String ret = "";
		
		for (Point point : _points) {
			if (ret != "")
				ret += ",\n";
			ret += "{"
				+ String.valueOf(point.x + EXPORT_DELTA - _img.getWidth(null) / 2)
				+ ", "
				+ String.valueOf(point.y + EXPORT_DELTA - _img.getHeight(null) / 2)
				+ "}";
		}

		ret += "\n\n\n====================\nNumber of Points: " + String.valueOf(_points.size());
		
		return ret;
	}

	private static int readNumber(String C) {
		int ret = 0;
		if (C.charAt(0) == '-')
			++ret;
		while (C.charAt(ret) >= '0' && C.charAt(ret) <= '9')
			++ret;
		return ret;
	}
	
	public void loadCArray(String C) {
		_points.clear();
		
		C = C.trim();
		
		try {
			while (C.charAt(0) == '{') {
				C = C.substring(1);
				C = C.trim();
	
				Point p = new Point();
				
				int n;
				
				n = readNumber(C);
				if (n == 0)
					return ;
				p.x = Integer.valueOf(C.substring(0, n)) + _img.getWidth(null) / 2 - EXPORT_DELTA;
				C = C.substring(n);
	
				C = C.trim();
				if (C.charAt(0) != ',')
					return ;
				C = C.substring(1);
				C = C.trim();
				
				n = readNumber(C);
				if (n == 0)
					return ;
				p.y = Integer.valueOf(C.substring(0, n)) + _img.getHeight(null) / 2 - EXPORT_DELTA;
				C = C.substring(n);
	
				C = C.trim();
				if (C.charAt(0) != '}')
					return ;
				C = C.substring(1);
				C = C.trim();
				
				_points.add(p);
	
				C = C.trim();
				if (C.charAt(0) != ',')
					return ;
				C = C.substring(1);
				C = C.trim();
			}
	
		}
		catch (StringIndexOutOfBoundsException e) {
			
		}
	}
}
