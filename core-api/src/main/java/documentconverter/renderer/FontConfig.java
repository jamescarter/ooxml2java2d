package documentconverter.renderer;

import java.awt.Font;
import java.awt.font.FontRenderContext;

public class FontConfig {
	private Font font = new Font(Font.SERIF, Font.PLAIN, 12);
	private String name = font.getName();
	private float size;

	public FontConfig() {
		// Set font size in points to force resize
		setSize(font.getSize());
	}

	public void setName(String name) {
		if (!font.getName().equals(name)) {
			this.name = name;
			this.font = new Font(name, font.getStyle(), font.getSize());
			setSize(size);
		}
	}

	public void setSize(float sizePt) {
		size = sizePt * 20 * 96 / 72;
		font = font.deriveFont(size);
	}

	public String getName() {
		return name;
	}

	public float getSize() {
		return size;
	}

	public double getWidth(String text) {
		return font.getStringBounds(text, new FontRenderContext(font.getTransform(), true, true)).getWidth();
	}

	public Font getFont() {
		return font;
	}
} 
