package org.whatsoftwarecando.legespiel.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.whatsoftwarecando.legespiel.Card;

public class CardToGraphics {

	private static final Color BG_COLOR = new Color(255, 255, 224);
	private static final int CARD_SIZE = 300;
	private static final int MARGIN = 10;
	private static final int INTER_CARD_SPACING = 2;
	private static final int FONT_SIZE = 20;

	public Font calculateFont(List<Card> cards) {
		Font font = new Font("Courier", Font.PLAIN, FONT_SIZE);
		BufferedImage img = new BufferedImage(CARD_SIZE, CARD_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		g2d.setFont(font);
		for (Card card : cards) {
			while (findMaxWidth(g2d, card) > CARD_SIZE / 2 - MARGIN * 3) {
				font = new Font("Courier", Font.PLAIN, font.getSize() - 1);
				g2d.setFont(font);
			}
		}
		return font;
	}

	public byte[] convert(Card card, Font font, String format) throws IOException {
		BufferedImage img = new BufferedImage(CARD_SIZE, CARD_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g2d.setFont(font);
		g2d.setColor(BG_COLOR);
		g2d.fillRect(0, 0, CARD_SIZE, CARD_SIZE);
		g2d.setColor(Color.BLACK);
		String northStr = card.getNorth().toString();
		drawString(g2d, northStr, CARD_SIZE / 2 - strWidth(g2d, northStr) / 2, strHeight(northStr, g2d) / 2 + MARGIN);
		String westStr = card.getWest().toString();
		drawString(g2d, westStr, MARGIN, CARD_SIZE / 2 + strHeight(westStr, g2d) / 2);
		String eastStr = card.getEast().toString();
		drawString(g2d, eastStr, CARD_SIZE - MARGIN - strWidth(g2d, eastStr),
				CARD_SIZE / 2 + strHeight(eastStr, g2d) / 2);
		String southStr = card.getSouth().toString();
		drawString(g2d, southStr, CARD_SIZE / 2 - strWidth(g2d, southStr) / 2, CARD_SIZE - MARGIN);

		Stroke dashed = new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 5, 5 }, 0);
		g2d.setStroke(dashed);
		g2d.drawLine(MARGIN, MARGIN, CARD_SIZE - MARGIN, CARD_SIZE - MARGIN);
		g2d.drawLine(MARGIN, CARD_SIZE - MARGIN, CARD_SIZE - MARGIN, MARGIN);
		g2d.setColor(Color.RED);
		g2d.setStroke(new BasicStroke(1.0f));
		String cardNumberStr = String.valueOf(card.getId());
		drawString(g2d, cardNumberStr, CARD_SIZE / 2 - strWidth(g2d, cardNumberStr) / 2, CARD_SIZE / 2 - MARGIN);
		g2d.setColor(Color.BLACK);
		drawRect(g2d, INTER_CARD_SPACING, INTER_CARD_SPACING, CARD_SIZE - INTER_CARD_SPACING * 2,
				CARD_SIZE - INTER_CARD_SPACING * 2);
		g2d.setBackground(Color.YELLOW);
		g2d.setColor(Color.GRAY);
		drawRect(g2d, 0, 0, CARD_SIZE, CARD_SIZE);
		g2d.dispose();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(img, format, bos);
		return bos.toByteArray();
	}

	private int findMaxWidth(Graphics2D g2d, Card card) {
		String northStr = card.getNorth().toString();
		String westStr = card.getWest().toString();
		String eastStr = card.getEast().toString();
		String southStr = card.getSouth().toString();
		int northWidth = strWidth(g2d, northStr);
		int westWidth = strWidth(g2d, westStr);
		int eastWidth = strWidth(g2d, eastStr);
		int southWidth = strWidth(g2d, southStr);
		return Math.max(Math.max(northWidth, westWidth), Math.max(eastWidth, southWidth));

	}

	private void drawRect(Graphics2D g2d, int x, int y, int width, int height) {
		g2d.drawRect(x, y, width, height);
	}

	private void drawString(Graphics2D g2d, String str, double x, double y) {
		g2d.drawString(str, (int) Math.round(x), (int) Math.round(y));
	}

	private int strWidth(Graphics2D g2d, String eastStr) {
		return g2d.getFontMetrics().stringWidth(eastStr);
	}

	private int strHeight(String str, Graphics2D g2d) {
		Rectangle2D bounds = g2d.getFontMetrics().getStringBounds(str, g2d);
		return (int) Math.round(bounds.getHeight());
	}

}
