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
		buttonReset = new JButton("Reset");
		buttonReset.addActionListener(buttonHandler);
		
		panelBottom = new JPanel();
		panelBottom.add(buttonEdges);
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
			image.update();
			imagePanel.setImage(image);
		}
	}

}
