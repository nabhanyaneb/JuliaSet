import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class JuliaProgNabhanyaNeb extends JPanel implements AdjustmentListener,ActionListener,ItemListener{
	JFrame frame;
	JScrollBar bar,bar2,bar3,bar4,bar5;
	JCheckBox box,box2,box3;
	JLabel label,label2,label3,label4,label5,twolabel,twolabel2,twolabel3;
	JPanel scrollPanel;
	JPanel checkPanel;
	JPanel labelPanel;
	JPanel totLabelPanel;
	JPanel totPanel;
	JPanel twolabelPanel;
	Color color;
	int r=0;
	int b=0;
	int g=0;
	int w=1000;
	int h=550;
	double A=0;
	double B=0;
	int zoom=1;
	float maxIter=100;
	float hue=1;
	float sat=1;
	float brt=1;
	boolean circ=true;
	boolean parab=false;
	boolean hyper=false;
	boolean user=true;

	public JuliaProgNabhanyaNeb(){

		color=new Color(r,g,b);

		frame=new JFrame("Julia Program");
		frame.add(this);

		bar=new JScrollBar(JScrollBar.HORIZONTAL,0,0,0,3000); //Horizontal or Vertical, where to start, size of scrolling thing, potential Beginning, potential End
		bar.setPreferredSize(new Dimension(200,22));//only works w/o GridLayout for ScrollPanel
		bar2=new JScrollBar(JScrollBar.HORIZONTAL,0,0,0,3000);
		bar2.setPreferredSize(new Dimension(200,22));
		bar3=new JScrollBar(JScrollBar.HORIZONTAL,1000,0,1000,12000);
		bar3.setPreferredSize(new Dimension(200,22));
		bar4=new JScrollBar(JScrollBar.HORIZONTAL,1000,0,0,1000);
		bar4.setPreferredSize(new Dimension(200,22));
		bar5=new JScrollBar(JScrollBar.HORIZONTAL,1000,0,0,6000);
		bar5.setPreferredSize(new Dimension(200,22));

		hue=(float)bar3.getValue()/1000;
		sat=(float)bar4.getValue()/1000;
		brt=(float)bar5.getValue()/1000;

		box=new JCheckBox();
		box2=new JCheckBox();
		box3=new JCheckBox();


		label=new JLabel();
		label.setText("a: "+A);
		label2=new JLabel();
		label2.setText("b: "+B);
		label3=new JLabel();
		label3.setText("Hue");
		label4=new JLabel();
		label4.setText("Saturation");
		label5=new JLabel();
		label5.setText("Brightness");

		twolabel=new JLabel();
		twolabel.setText("Circle");
		twolabel2=new JLabel();
		twolabel2.setText("Parabola");
		twolabel3=new JLabel();
		twolabel3.setText("Hyperbola");


		scrollPanel=new JPanel();
		scrollPanel.setLayout(new GridLayout(5,1));
		scrollPanel.add(bar);
		scrollPanel.add(bar2);
		scrollPanel.add(bar3);
		scrollPanel.add(bar4);
		scrollPanel.add(bar5);

		checkPanel=new JPanel();
		checkPanel.setLayout(new GridLayout(3,1));
		checkPanel.add(box);
		checkPanel.add(box2);
		checkPanel.add(box3);

		box.setSelected(true);

		labelPanel=new JPanel();
		labelPanel.setLayout(new GridLayout(5,1));
		labelPanel.add(label);
		labelPanel.add(label2);
		labelPanel.add(label3);
		labelPanel.add(label4);
		labelPanel.add(label5);

		twolabelPanel=new JPanel();
		twolabelPanel.setLayout(new GridLayout(3,1));
		twolabelPanel.add(twolabel);
		twolabelPanel.add(twolabel2);
		twolabelPanel.add(twolabel3);

		totLabelPanel=new JPanel();
		totLabelPanel.setLayout(new GridLayout(1,2));
		totLabelPanel.add(checkPanel);
		totLabelPanel.add(twolabelPanel);

		totPanel=new JPanel();
		totPanel.setLayout(new BorderLayout());
		totPanel.add(BorderLayout.WEST,labelPanel);
		totPanel.add(BorderLayout.CENTER,scrollPanel);
		totPanel.add(BorderLayout.EAST,totLabelPanel);


		frame.add(BorderLayout.SOUTH,totPanel);

		bar.addAdjustmentListener(this);
		bar2.addAdjustmentListener(this);
		bar3.addAdjustmentListener(this);
		bar4.addAdjustmentListener(this);
		bar5.addAdjustmentListener(this);

		box.addItemListener(this);
		box2.addItemListener(this);
		box3.addItemListener(this);

		frame.setSize(1000,700);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void paintComponent(Graphics g){ //goes with JPanel
		super.paintComponent(g);
		drawJulia(g);
	}

	public void drawJulia(Graphics g){
			BufferedImage image=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
				for (int i=0;i<w;i++){
					for (int j=0;j<h;j++){
						float iter=maxIter;
						double zx=(1.5)*((i-(w/2))/((zoom/2.0)*w));
						double zy=((j-(h/2))/((zoom/2.0)*h));

						if ((circ) && !(parab) && !(hyper)){
							while ((zx*zx)+(zy*zy)<3 && iter>0){
								double diff=(zx*zx)-(zy*zy)+A;
								zy=2*zx*zy+B;
								zx=diff;
								iter--;
							}
						}
						else if (!(circ) && (parab) && !(hyper)){
							while ((zx*zx)-(zy)<3 && iter>0){
								double diff=(zx*zx)-(zy)+A;
								zy=2*zx*zy+B;
								zx=diff;
								iter--;
							}
						}
						else if (!(circ) && !(parab) && (hyper)){
							while (0.5*(zx*zx/1.5)-2.0*(zy*zy/1.5)<3 && iter>0){
								double diff=0.5*(zx*zx/1.5)-2.0*(zy*zy/1.5)+A;
								zy=2*zx*zy+B;
								zx=diff;
								iter--;
							}
						}

						int c=0;
						if (iter>0) c=Color.HSBtoRGB((maxIter / iter) % 1*hue, 1*sat, brt);
						else c = Color.HSBtoRGB(maxIter / iter, 1, 0);

						image.setRGB(i,j,c);
					}
			}

			g.drawImage(image,0,0,null);
	}


	public void adjustmentValueChanged(AdjustmentEvent e){
		if (e.getSource()==bar){
			A=bar.getValue()/1000.0;
			label.setText("a: "+A);
			System.out.println(A+" "+B);
		}
		if (e.getSource()==bar2){
			B=bar2.getValue()/1000.0;
			label2.setText("b: "+B);
			System.out.println(A+" "+B);
		}
		if (e.getSource()==bar3){
			hue=(float)bar3.getValue()/(float)1000.0;
			System.out.println(hue);
		}
		if (e.getSource()==bar4){
			sat=(float)bar4.getValue()/(float)1000.0;
			System.out.println(sat);
		}
		if (e.getSource()==bar5){
			brt=(float)bar5.getValue()/(float)1000.0;
		}
		repaint();
	}
	public void actionPerformed(ActionEvent e){

	}
	public void itemStateChanged(ItemEvent e){
		if (e.getSource()==box){
			if (user){
				user=false;
				if (box.isSelected()){
					System.out.println("box 1 on");
					circ=true;
					box2.setSelected(false);
					parab=false;
					box3.setSelected(false);
					hyper=false;
				}
				else{
					System.out.println("box 1 off");
					circ=false;
					box2.setSelected(true);
					parab=true;
					box3.setSelected(false);
					hyper=false;
				}
			}
		}
		if (e.getSource()==box2){
			if (user){
				user=false;
				if (box2.isSelected()){
					System.out.println("box 2 on");
					parab=true;
					box3.setSelected(false);
					hyper=false;
					box.setSelected(false);
					circ=false;
				}
				else{
					System.out.println("box 2 off");
					parab=false;
					box3.setSelected(true);
					hyper=true;
					box.setSelected(false);
					circ=false;

				}
			}
		}
		if (e.getSource()==box3){
			if (user){
				user=false;
				if (box3.isSelected()){
					System.out.println("box 3 on");
					hyper=true;
					box.setSelected(false);
					circ=false;
					box2.setSelected(false);
					parab=false;
				}
				else{
					System.out.println("box 3 off");
					hyper=false;
					box.setSelected(true);
					circ=true;
					box2.setSelected(false);
					parab=false;

				}
			}
		}
		user=true;
		repaint();
	}

	public static void main (String[] args){
		JuliaProgNabhanyaNeb app=new JuliaProgNabhanyaNeb();
	}

}