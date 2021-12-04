package org.whatsoftwarecando.legespiel.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.IPicture;

public class CardToGraphicsConverter {

	private static final Color BG_COLOR = new Color(250, 250, 250);
	protected static final int CARD_SIZE = 400;
	private static final int MARGIN = 13;
	private static final int INTER_CARD_SPACING = 2;

	public Font calculateFont(List<Card> availableCards) {
		return calculateFont(new Font("SansSerif", Font.PLAIN, 25), availableCards);
	}

	public Font calculateFont(Font largestFont, List<Card> cards) {
		BufferedImage img = new BufferedImage(CARD_SIZE, CARD_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		g2d.setFont(largestFont);
		for (Card card : cards) {
			while (findMaxWidth(g2d, card) > CARD_SIZE / 2.0 - MARGIN * 3) {
				largestFont = largestFont.deriveFont((float) largestFont.getSize() - 1);
				g2d.setFont(largestFont);
			}
		}
		return largestFont;
	}

	public byte[] convert(Card card, Font font, Color color, String format) throws IOException {
		BufferedImage img = convert(card, font, color);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(img, format, bos);
		return bos.toByteArray();
	}

	public BufferedImage convert(Card card, Font font, Color color) {
		BufferedImage img = new BufferedImage(CARD_SIZE, CARD_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		drawCard(card, font, color, g2d);
		g2d.dispose();
		return img;
	}

	protected void drawCard(Card card, Font font, Color color, Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g2d.setFont(font);
		if (color == null) {
			g2d.setColor(BG_COLOR);
		} else {
			g2d.setColor(color);
		}
		g2d.fillRect(0, 0, CARD_SIZE, CARD_SIZE);
		g2d.setColor(Color.BLACK);
		String northStr = pictureToStr(card.getNorth());
		drawString(g2d, northStr, (CARD_SIZE - strWidth(g2d, northStr)) / 2.0, strHeight(northStr, g2d, font) + MARGIN);
		String westStr = pictureToStr(card.getWest());
		drawString(g2d, westStr, MARGIN, (CARD_SIZE + strHeight(westStr, g2d, font)) / 2.0);
		String eastStr = pictureToStr(card.getEast());
		drawString(g2d, eastStr, CARD_SIZE - MARGIN - strWidth(g2d, eastStr),
				(CARD_SIZE + strHeight(eastStr, g2d, font)) / 2.0);
		String southStr = pictureToStr(card.getSouth());
		drawString(g2d, southStr, (CARD_SIZE - strWidth(g2d, southStr)) / 2.0, CARD_SIZE - MARGIN);

		Stroke dashed = new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 5, 5 }, 0);
		g2d.setStroke(dashed);
		g2d.drawLine(MARGIN, MARGIN, CARD_SIZE - MARGIN, CARD_SIZE - MARGIN);
		g2d.drawLine(MARGIN, CARD_SIZE - MARGIN, CARD_SIZE - MARGIN, MARGIN);
		g2d.setColor(Color.RED);
		g2d.setStroke(new BasicStroke(1.0f));
		String cardNumberStr = String.valueOf(card.getId());
		drawString(g2d, cardNumberStr, (CARD_SIZE - strWidth(g2d, cardNumberStr)) / 2.0, CARD_SIZE / 2.0 - MARGIN);
		g2d.setColor(Color.BLACK);
		g2d.drawRect(INTER_CARD_SPACING, INTER_CARD_SPACING, CARD_SIZE - INTER_CARD_SPACING * 2,
				CARD_SIZE - INTER_CARD_SPACING * 2);
		g2d.setBackground(Color.YELLOW);
		g2d.setColor(Color.GRAY);
		g2d.drawRect(0, 0, CARD_SIZE, CARD_SIZE);
	}

	private String pictureToStr(IPicture picture) {
		return picture.toString().replace('_', ' ');
	}

	private int findMaxWidth(Graphics2D g2d, Card card) {
		String northStr = pictureToStr(card.getNorth());
		String westStr = pictureToStr(card.getWest());
		String eastStr = pictureToStr(card.getEast());
		String southStr = pictureToStr(card.getSouth());
		int northWidth = strWidth(g2d, northStr);
		int westWidth = strWidth(g2d, westStr);
		int eastWidth = strWidth(g2d, eastStr);
		int southWidth = strWidth(g2d, southStr);
		return Math.max(Math.max(northWidth, westWidth), Math.max(eastWidth, southWidth));

	}

	private void drawString(Graphics2D g2d, String str, double x, double y) {
		g2d.drawString(str, (int) Math.round(x), (int) Math.round(y));
	}

	private int strWidth(Graphics2D g2d, String str) {
		return g2d.getFontMetrics().stringWidth(str);
	}

	private double strHeight(String str, Graphics2D g2d, Font font) {
		double height = font.createGlyphVector(g2d.getFontMetrics().getFontRenderContext(), str).getVisualBounds()
				.getHeight();
		return height;
	}

}
