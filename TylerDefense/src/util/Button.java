package util;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Button {
	
	public int width, height;
	public int x, y;
	public Color bgColor;
	public String text;
	public Color fgColor;
	public Image image;
	
	public Button(int x, int y, int width, int height, Color bgColor, Color fgColor, String text)
	{
		image = null;
		this.x = x; 
		this.y = y;
		this.width = width;
		this.height = height;
		this.bgColor = bgColor;
		this.fgColor = fgColor;
		this.text = text;
	}
	
	public Button(Image i, int x, int y)
	{
		image = i;
		this.x = x;
		this.y = y;
		bgColor = fgColor = null;
		text = null;
		width = image.getWidth();
		height = image.getHeight();
	}
	
	public void draw(Graphics g)
	{
		if(image == null)
		{
			int tx, ty;
			Color c = g.getColor();
			g.setColor(bgColor);
			g.fillRect(x, y, width, height);
			tx = (x + width/2) - g.getFont().getWidth(text)/2;
			ty = (y + height/2) - g.getFont().getHeight(text)/2;
			g.setColor(fgColor);
			g.drawString(text, tx, ty);
			g.setColor(c);
		} else {
			image.draw(x, y);
		}
	}
	
	public boolean pointInBounds(int x, int y)
	{
		return (x > this.x && x < (this.x + width)) && (y > this.y && y < (this.y + height));
	}

}
