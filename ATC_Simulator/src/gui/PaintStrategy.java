package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import config.GameConfiguration;
import engine.dynamic.Avion;
import engine.map.Airport;
import engine.map.Block;
import engine.map.City;
import engine.map.Map;
import engine.map.Mountain;
import engine.process.AvionManager;
import engine.process.MobileElementManager;
import engine.process.SimulationUtility;

/**
 * PaintStrategy class that retrieves the necessary images
 * 
 * @author aérien2
 * Date:   2023
 * File : PaintStrategy.java
 */
public class PaintStrategy {
	private double angle = 0;
	DecimalFormat df = new DecimalFormat("#.##");
	/**
	 * Method used to paint the map by drawing images on it.
	 * 
	 * @param map Map of the simulation where the images will be drawn.
	 * @param graphics Graphics object used to draw the images.
	 */
	public void paint(Map map, Graphics graphics) {
		int blockSize = GameConfiguration.BLOCK_SIZE;
		Block[][] blocks = map.getBlocks();
		Image img = SimulationUtility.readImage("src/Image/Green_tree.png");
		Image img2 = SimulationUtility.readImage("src/Image/TexturedGrass.png");
		Image img_pink = SimulationUtility.readImage("src/Image/pink.png");
		Image img_redImage = SimulationUtility.readImage("src/Image/Red_tree.png");
		Image img_nuage = SimulationUtility.readImage("src/Image/Nuage.png");
		Image img_pluie = SimulationUtility.readImage("src/Image/pluie.png");
		Image img_neige = SimulationUtility.readImage("src/Image/Neige.png");
		for (int lineIndex = 0; lineIndex < map.getLineCount(); lineIndex++) {
			for (int columnIndex = 0; columnIndex < map.getColumnCount(); columnIndex++) {
				Block block = blocks[lineIndex][columnIndex];

				/*
				 * if ((lineIndex + columnIndex) % 2 == 0) {
				 * graphics.setColor(Color.decode("#EAE0DA"));
				 * graphics.fillRect(block.getColumn() * blockSize,block.getLine() * blockSize,
				 * blockSize, blockSize);}
				 */
				if ((lineIndex % 48 == 0) && (columnIndex % 32 == 0)) {
					graphics.drawImage(img2, block.getColumn() * blockSize, block.getLine() * blockSize, blockSize * 32,
							blockSize * 48, null);
				}
			}

		}

		for (int lineIndex = 0; lineIndex < map.getLineCount(); lineIndex++) {
			for (int columnIndex = 0; columnIndex < map.getColumnCount(); columnIndex++) {
				Block block = blocks[lineIndex][columnIndex];

				if ((lineIndex % 20 == 0) && (columnIndex % 20 == 0)) {

					if (lineIndex > 650 && columnIndex < 800)
						graphics.drawImage(img, block.getColumn() * blockSize, block.getLine() * blockSize,
								blockSize * 20, blockSize * 20, null);
					else if (lineIndex > 500 && columnIndex > 1000)
						graphics.drawImage(img, block.getColumn() * blockSize, block.getLine() * blockSize,
								blockSize * 20, blockSize * 20, null);

				}
			}
		}
		
		// 	shanghai
		graphics.drawImage(img_redImage, 30 * blockSize, 100 * blockSize,blockSize * 20, blockSize * 20, null);
		
		graphics.drawImage(img_redImage, 50 * blockSize, 120 * blockSize,blockSize * 20, blockSize * 20, null);
		
		graphics.drawImage(img_redImage, 30 * blockSize, 120 * blockSize,blockSize * 20, blockSize * 20, null);
		
		graphics.drawImage(img_redImage, 110 * blockSize, 200 * blockSize,blockSize * 20, blockSize * 20, null);
	
		graphics.drawImage(img_redImage, 90 * blockSize, 200 * blockSize,blockSize * 20, blockSize * 20, null);
		graphics.drawImage(img_redImage, 360 * blockSize, 60 * blockSize,blockSize * 20, blockSize * 20, null);
		
		graphics.drawImage(img_redImage, 380 * blockSize, 60 * blockSize,blockSize * 20, blockSize * 20, null);
		
		graphics.drawImage(img_redImage, 400 * blockSize, 60 * blockSize,blockSize * 20, blockSize * 20, null);
		
		graphics.drawImage(img_redImage, 400 * blockSize, 150 * blockSize,blockSize * 20, blockSize * 20, null);
		
		graphics.drawImage(img_redImage, 440 * blockSize, 150 * blockSize,blockSize * 20, blockSize * 20, null);
		
		graphics.drawImage(img_redImage, 420 * blockSize, 150 * blockSize,blockSize * 20, blockSize * 20, null);
		
		
		//pekin
		graphics.drawImage(img_redImage, 840 * blockSize, 680 * blockSize,blockSize * 20, blockSize * 20, null);
		
		graphics.drawImage(img_redImage, 840 * blockSize, 660 * blockSize,blockSize * 20, blockSize * 20, null);
		graphics.drawImage(img_redImage, 840 * blockSize, 640 * blockSize,blockSize * 20, blockSize * 20, null);
		
		graphics.drawImage(img_redImage, 840 * blockSize, 620 * blockSize,blockSize * 20, blockSize * 20, null);
		graphics.drawImage(img_redImage, 840 * blockSize, 580 * blockSize,blockSize * 20, blockSize * 20, null);
		
		graphics.drawImage(img_redImage, 840 * blockSize, 600 * blockSize,blockSize * 20, blockSize * 20, null);
		graphics.drawImage(img_redImage, 840 * blockSize, 540 * blockSize,blockSize * 20, blockSize * 20, null);
		
		graphics.drawImage(img_redImage, 840 * blockSize, 560 * blockSize,blockSize * 20, blockSize * 20, null);
		
		graphics.drawImage(img_redImage, 860 * blockSize, 600 * blockSize,blockSize * 20, blockSize * 20, null);
		
		graphics.drawImage(img_redImage, 860 * blockSize, 660 * blockSize,blockSize * 20, blockSize * 20, null);
		graphics.drawImage(img_redImage, 860 * blockSize, 640 * blockSize,blockSize * 20, blockSize * 20, null);
		
		graphics.drawImage(img_redImage, 860 * blockSize, 620 * blockSize,blockSize * 20, blockSize * 20, null);
		graphics.drawImage(img_redImage, 860 * blockSize, 580 * blockSize,blockSize * 20, blockSize * 20, null);
	
		//London
		graphics.drawImage(img_nuage, 200 * blockSize, 450 * blockSize,blockSize * 60, blockSize * 36, null);
		graphics.drawImage(img_nuage, 140 * blockSize, 450 * blockSize,blockSize * 60, blockSize * 36, null);
		graphics.drawImage(img_nuage, 100 * blockSize, 470 * blockSize,blockSize * 60, blockSize * 36, null);
		
		graphics.drawImage(img_nuage, 400 * blockSize, 400 * blockSize,blockSize * 60, blockSize * 36, null);
		graphics.drawImage(img_nuage, 480 * blockSize, 420 * blockSize,blockSize * 60, blockSize * 36, null);
		graphics.drawImage(img_nuage, 440 * blockSize, 440 * blockSize,blockSize * 60, blockSize * 36, null);
		
		graphics.drawImage(img_nuage, 400 * blockSize, 600 * blockSize,blockSize * 60, blockSize * 36, null);
		graphics.drawImage(img_nuage, 480 * blockSize, 540 * blockSize,blockSize * 60, blockSize * 36, null);
		graphics.drawImage(img_nuage, 520 * blockSize, 580 * blockSize,blockSize * 60, blockSize * 36, null);
		
		//paris
		graphics.drawImage(img_pluie, 600 * blockSize, 150 * blockSize,blockSize * 72, blockSize * 66, null);
		graphics.drawImage(img_pluie, 560 * blockSize, 100 * blockSize,blockSize * 72, blockSize * 66, null);
		
		//Tokyo
		graphics.drawImage(img_pink, 900 * blockSize, 410 * blockSize,blockSize * 20, blockSize * 20, null);
		graphics.drawImage(img_pink, 920 * blockSize, 430 * blockSize,blockSize * 20, blockSize * 20, null);
		graphics.drawImage(img_pink, 940 * blockSize, 450 * blockSize,blockSize * 20, blockSize * 20, null);
	
		graphics.drawImage(img_pink, 840 * blockSize, 410 * blockSize,blockSize * 20, blockSize * 20, null);
		graphics.drawImage(img_pink, 860 * blockSize, 430 * blockSize,blockSize * 20, blockSize * 20, null);
		graphics.drawImage(img_pink, 880 * blockSize, 450 * blockSize,blockSize * 20, blockSize * 20, null);
		
		graphics.drawImage(img_pink, 780 * blockSize, 410 * blockSize,blockSize * 20, blockSize * 20, null);
		graphics.drawImage(img_pink, 800 * blockSize, 430 * blockSize,blockSize * 20, blockSize * 20, null);
		graphics.drawImage(img_pink, 820 * blockSize, 450 * blockSize,blockSize * 20, blockSize * 20, null);
			
		
		
		//Mexique
		
		graphics.drawImage(img_neige, 1200 * blockSize, 400 * blockSize,blockSize * 60, blockSize * 60, null);
		graphics.drawImage(img_neige, 1100 * blockSize, 430 * blockSize,blockSize * 60, blockSize * 60, null);
		graphics.drawImage(img_neige, 1100 * blockSize, 300 * blockSize,blockSize * 60, blockSize * 60, null);
		
		Graphics2D g2d = (Graphics2D) graphics; // Cast Graphics to Graphics2D
		int strokeWidth = 3; // Set the desired stroke width
		g2d.setStroke(new BasicStroke(strokeWidth));

		for (City city : map.getCities()) {
			Rectangle airspace = city.getAirspace();

			Image cityImage = SimulationUtility.readImage("src/Image/Stone.png");

			int x = city.getAirspace().x;
			int y = city.getAirspace().y;
			int Pointx = city.getAirspace().width;
			int Pointy = city.getAirspace().height;
			for (int lineIndex = x; lineIndex <= x + Pointx; lineIndex++) {
				for (int columnIndex = y; columnIndex <= y + Pointy; columnIndex++) {
					
					if ((lineIndex == x || lineIndex == x + Pointx)) {
						if (lineIndex % 20 == 0 && columnIndex % 20 == 0)
							graphics.drawImage(cityImage, lineIndex * blockSize, columnIndex * blockSize,
									blockSize * 20, blockSize * 20, null);
					}
					if ((columnIndex == y || columnIndex == y + Pointy)) {
						if (lineIndex % 20 == 0 && columnIndex % 20 == 0)
							graphics.drawImage(cityImage, lineIndex * blockSize, columnIndex * blockSize,
									blockSize * 20, blockSize * 20, null);
					}

				}

			}

			// Set the font color
			g2d.setColor(Color.BLACK);
			g2d.setFont(new Font("Arial", Font.BOLD, 20));
			// Calculate the position of the city name
			int textX = airspace.x * blockSize + airspace.width * blockSize / 2;
			int textY = airspace.y * blockSize + airspace.height * blockSize / 2;
			// Draw the city name
			g2d.drawString(city.getNameString(), textX, textY);
		}

	}
	/**
	 * This method allows you to draw an airport.
	 * 
	 * @param airport the airport to draw.
	 * @param graphics the Graphics object used to draw.
	 */
	public void paint(Airport airport, Graphics graphics) {
		Block position = airport.getPosition();
		int blockSize = GameConfiguration.BLOCK_SIZE;
		Image img = SimulationUtility.readImage("src/Image/House.png");
		graphics.drawImage(img, position.getColumn() * blockSize - 10, position.getLine() * blockSize - 10,
				blockSize * 40, blockSize * 40, null);
		// graphics.setColor(Color.decode("#98FF98"));
		// graphics.fillOval(position.getColumn() * blockSize, position.getLine() *
		// blockSize, blockSize*40, blockSize*40);
	}
	/**
	 * This method allows you to draw a mountain.
	 * 
	 * @param mountain the mountain to draw
	 * @param graphics the graphic object used to draw the mountain
	 */
	public void paint(Mountain mountain, Graphics graphics) {
		Block position = mountain.getPosition();
		int blockSize = GameConfiguration.BLOCK_SIZE;
		// graphics.setColor(Color.orange);
		Image img = SimulationUtility.readImage("src/Image/Mountain.png");
		graphics.drawImage(img, position.getColumn() * blockSize, position.getLine() * blockSize, blockSize * 60,
				blockSize * 40, null);
		// graphics.fillRect(position.getColumn() * blockSize, position.getLine() *
		// blockSize, blockSize*20, blockSize*20);
	}
	/**
	 * This method allows you to draw an aircraft, including its image and identification and altitude information.
	 * 
	 * @param avionManager an instance of the class AvionManager representing the plane to draw
	 * @param graphics the graphic object on which the plane is drawn
	 */
	public void paint(AvionManager avionManager, Graphics graphics) {
		Font font = new Font("Arial", Font.BOLD, 12);
		Block position = avionManager.getPosition();
		Image img;
		if (avionManager.isEmergencyLanding()) {
			img = SimulationUtility.readImage("src/Image/plane_1.png");
		} else {
			img = SimulationUtility.readImage("src/Image/plane_2.png");
		}

		int blockSize = GameConfiguration.BLOCK_SIZE;
		int y = position.getLine();
		int x = position.getColumn();
		Block Destination = avionManager.getArrivale_airport(); // l'airport temporaire
		// System.out.println("airport："+avionManager.getArrivale_airport());
		if (Destination.getColumn() > position.getColumn() && Destination.getLine() == position.getLine()) {
			angle = 90;
		} else if (Destination.getColumn() < position.getColumn() && Destination.getLine() == position.getLine()) {
			angle = 270;
		} else if (Destination.getColumn() == position.getColumn() && Destination.getLine() > position.getLine()) {
			angle = 180;
		} else if (Destination.getColumn() == position.getColumn() && Destination.getLine() < position.getLine()) {
			angle = 0;
		} else if (Destination.getColumn() > position.getColumn() && Destination.getLine() > position.getLine()) {
			angle = 135;
		} else if (Destination.getColumn() > position.getColumn() && Destination.getLine() < position.getLine()) {
			angle = 45;
		} else if (Destination.getColumn() < position.getColumn() && Destination.getLine() < position.getLine()) {
			angle = 315;
		} else if (Destination.getColumn() < position.getColumn() && Destination.getLine() > position.getLine()) {
			angle = 225;
		}
		Image rotatedImg = rotateImage(img, angle);
		graphics.drawImage(rotatedImg, x * blockSize, y * blockSize, 20, 20, null);
		if (avionManager.isEmergencyLanding()) {
			graphics.setColor(Color.RED);
		} else {
			graphics.setColor(Color.black);
		}
		graphics.setFont(font);
		graphics.drawString("ID: " + avionManager.getAvionId() + ",Height:" + df.format(avionManager.getAltitude()),
				x * blockSize, y * blockSize);

	}
	/**
	 *This method allows you to rotate an image.
	 *
	 *@param image The image to rotate
	 *@param angle The angle of rotation of the image 
	 *@return The rotated image 
	 */
	public static Image rotateImage(Image image, double angle) {
		BufferedImage rotatedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = rotatedImage.createGraphics();
		g2d.rotate(Math.toRadians(angle), image.getWidth(null) / 2, image.getHeight(null) / 2);
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();
		return rotatedImage;
	}

}
