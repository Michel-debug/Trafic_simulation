
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import config.GameConfiguration;
import engine.process.*;
import engine.map.*;
/**
 * The MainGUI class represents the main graphical interface of the game.
 * It implements the Runnable interface and extends the JFrame class.
 * 
 * @author aérien2
 * Date:   2023
 * File : MainGUI.java
 */

/**
 * A JFrame that implements the Runnable interface is the MainGUI class. 
 * It serves as primary for our program.
 * It includes a HashMap of JLabels, a Map, MobileElementManager, ATCDisplay, and JPanel.
 * A title for the JFrame is sent in to create the class. The GameConfiguration specifies the JFrame's preferred size at initialization.
 * The init() method is then used by the constructor to start the GUI.
 * @param title, title that will appear in the JFrame.
 */

public class MainGUI extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;

	private final static Dimension preferredSize = new Dimension(GameConfiguration.WINDOW_WIDTH, GameConfiguration.WINDOW_HEIGHT);
	
	private Map map;
	private MobileElementManager manager;
	private ATCDisplay dashboard;
	private JPanel infoJPanel;
	private HashMap<String,JLabel> flightLabels = new HashMap<>();
	/**
	 * Constructs a new MainGUI object with a title.
	 * @param title , title that will appear in the JFrame.
	*/
	public MainGUI(String title) {
		super(title);
		init();
	}

	private void init() {
		
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		infoJPanel = new JPanel();
		infoJPanel.setOpaque(false);
//		infoJPanel.setPreferredSize(new Dimension(200,getHeight()));
		infoJPanel.setLayout(new BoxLayout(infoJPanel, BoxLayout.Y_AXIS));
		JScrollPane infoScrollPane = new JScrollPane(infoJPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		    // 设置 JScrollPane 的首选大小，而不是设置 infoJPanel 的首选大小
		infoScrollPane.setPreferredSize(new Dimension(200, getHeight()));

		    // 将 JScrollPane 添加到内容面板，而不是将 infoJPanel 直接添加到内容面板
		contentPane.add(infoScrollPane, BorderLayout.EAST);
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		//la button d'emergency
		JButton emergencyLandingButton = new JButton("Emergency landing");
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
		emergencyLandingButton.addActionListener(e->performEmergencyLanding());
		buttonPanel.add(emergencyLandingButton);
		buttonPanel.add(exitButton);
		contentPane.add(buttonPanel,BorderLayout.SOUTH);
		map = MapBuilder.buildMap();
		manager = MapBuilder.buildInitMobile(map);
		dashboard = new ATCDisplay(map,manager);

		dashboard.setPreferredSize(preferredSize);
		contentPane.add(dashboard, BorderLayout.CENTER);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE); //set up shutdown methode
		pack();
		setVisible(true);
		setPreferredSize(preferredSize);
		setResizable(true);
	}
	/**
	 * Updates the avion managers' flight information
	 * @param avionManagers a list of avion managers to update
	 */
	public void updateFlightInfo(List<AvionManager> avionManagers) {
		for(AvionManager avionManager : avionManagers) {
			Block airport_depart	=  avionManager.getdepartPosition();
			Airport depart = map.getAirport(airport_depart);
			Block airport_Destination = avionManager.getArrivale_airport();
			Airport destination = map.getAirport(airport_Destination);
			if(depart==null) {
				System.out.println("Airport:"+airport_depart);
			}
			String flightInfo = "<html>ID: "+ avionManager.getAvionId()+"-Departure Time: "+ avionManager.getDepartureTime()+"s" +"<br> Depart_airport : "+depart.getName()+"<br> Destination : "+destination.getName()+
					"<br>speed :"+avionManager.getSpeed()+"<br>Altitude : "+avionManager.getAltitude()+"</html>";
	
			JLabel flightLabel = flightLabels.get(avionManager.getAvionId());
			if (flightLabel == null) {
				flightLabel = new JLabel(flightInfo);
				flightLabel.setOpaque(true);
				flightLabel.setBackground(Color.WHITE);
				
				 flightLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
				infoJPanel.add(flightLabel);
				flightLabels.put(avionManager.getAvionId(),flightLabel);
				
				
				
		            
			}else {
				
				flightLabel.setText(flightInfo);
			}
			
		}
		infoJPanel.revalidate();
		infoJPanel.repaint();
	}
	/**
	 * This method performs an emergency landing for a randomly selected aircraft in flight.
	 * The airport closest to the aircraft is determined and set as the destination, which activates the emergency landing mode.
	 */
	private void performEmergencyLanding() {
		List<AvionManager> flyingAvions = manager.getFlyingAvionManagers();
		if(flyingAvions.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No avions are currently flying","Error",JOptionPane.ERROR_MESSAGE);
		}
		Random random = new Random();
		AvionManager selectedAvion = flyingAvions.get(random.nextInt(flyingAvions.size()));
		Airport nearAirport = findNearestAirport(selectedAvion, map.getAirports());
		selectedAvion.setDestinationAirport(nearAirport);
		selectedAvion.setEmergencyLanding(true);
		JOptionPane.showMessageDialog(this, "Emergency landing initiated for Avion: "+selectedAvion.getAvionId(),"Emergency landing",JOptionPane.INFORMATION_MESSAGE);
	}
	/** 
	 * This method finds the closest airport to a given plane from a list of airports.
	 * 
	 * @param airplaneManager the airplane whose nearest airport is sought.
	 * @param airports the list of airports to consider.
	 * @return the closest airport to the plane.
	 */
	private Airport findNearestAirport(AvionManager avionManager,List<Airport> airports) {
		Block avionPosition = avionManager.getPosition();
		double minDistance = Double.MAX_VALUE;
		Airport nearAirport = null;
		for(Airport airport : airports) {
			Block airportPosition = airport.getPosition();
			double distance = avionPosition.CalculateDistance(airportPosition);
			if (distance < minDistance) {
				minDistance = distance;
				nearAirport = airport;
			}
		}
		return nearAirport;
	
	}
	/** 
	 * This method executes the simulation of the aircraft flight.
	 */
	@Override
	public void run() {
	
		int time = 0;
		while(true) {
			SimulationUtility.unitTime();
			Iterator<AvionManager> avionManagerIterator = this.manager.getAvionManager().iterator();
			while(avionManagerIterator.hasNext()) {
				AvionManager avionManager = avionManagerIterator.next();
				if (time == avionManager.getDepartureTime()) {
					Block position_depart = avionManager.getPosition();
					int row = position_depart.getLine() / GameConfiguration.Canton_Size;
					int col = position_depart.getColumn() / GameConfiguration.Canton_Size;
					BlockManager FlightZone = this.manager.getBlockManagers()[row][col];
					if (FlightZone.isFree()) {
						FlightZone.entered(avionManager);
						avionManager.setBlockManager(FlightZone);
						avionManager.setFlying(true);
						avionManager.start();	
					}
				}
			}
			updateFlightInfo(manager.getAvionManager());
			dashboard.repaint();
			time++;
		}
		
	}

}
