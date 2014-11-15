import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.ByteBuffer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import marvin.gui.MarvinImagePanel;
import marvin.image.MarvinImage;
import marvin.image.MarvinImageMask;
import marvin.io.MarvinImageIO;
import marvin.plugin.MarvinImagePlugin;
import marvin.util.MarvinPluginLoader;

public class FaceDetection extends JFrame{

	private MarvinImagePanel 	imagePanel;
	private MarvinImage 		image, 
								backupImage;
	
	private JPanel 				panelBottom;
	
	
	private JButton 			buttonEdges,
								buttonGray, 
								buttonSepia, 
								buttonInvert, 
								buttonReset;
	
	private MarvinImagePlugin 	imagePlugin;
	
	public FaceDetection()
	{
		super("Filters Sample");
		System.out.println("Filtering!");
		
		// Create Graphical Interface
		ButtonHandler buttonHandler = new ButtonHandler();
		buttonEdges = new JButton("Edges");
		buttonEdges.addActionListener(buttonHandler);
		buttonGray = new JButton("Gray");
		buttonGray.addActionListener(buttonHandler);
		buttonSepia = new JButton("Sepia");
		buttonSepia.addActionListener(buttonHandler);
		buttonInvert = new JButton("Invert");
		buttonInvert.addActionListener(buttonHandler);
		buttonReset = new JButton("Reset");
		buttonReset.addActionListener(buttonHandler);
		
		panelBottom = new JPanel();
		panelBottom.add(buttonEdges);
		panelBottom.add(buttonGray);
		panelBottom.add(buttonSepia);
		panelBottom.add(buttonInvert);
		panelBottom.add(buttonReset);
		
		// ImagePanel
		imagePanel = new MarvinImagePanel();
		
		Container l_c = getContentPane();
		l_c.setLayout(new BorderLayout());
		l_c.add(imagePanel, BorderLayout.NORTH);
		l_c.add(panelBottom, BorderLayout.SOUTH);
		
		// Load image
		loadImage();
		
		imagePanel.setImage(image);
		
		setSize(320,600);
		setVisible(true);	
	}
	
	private void loadImage(){
		image = MarvinImageIO.loadImage("./res/1_4_.gif");
		backupImage = image.clone();
	}
	
	public static void main(String args[]){
		FaceDetection t = new FaceDetection();
		t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	 	
	private class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent a_event){
			image = backupImage.clone();
			if(a_event.getSource() == buttonEdges){
				imagePlugin = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.edge.edgeDetector.jar");
				imagePlugin.process(image, image);
			}
			else if(a_event.getSource() == buttonGray){
				imagePlugin = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.color.grayScale.jar");
				imagePlugin.process(image, image);
			}
			else if(a_event.getSource() == buttonSepia){
				imagePlugin = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.color.sepia.jar");
				imagePlugin.setAttribute("hsIntensidade", 50);				
				imagePlugin.process(image, image);
			}
			else if(a_event.getSource() == buttonInvert){
				imagePlugin = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.color.invert.jar");
				imagePlugin.process(image, image);
			}
			image.update();
			imagePanel.setImage(image);
		}
	}

}
