package clasificadordehuevos;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Principal {
    private JFrame frame = new JFrame();
    private JPanel panel;
    private JTextField textFieldPeso;
    private JTextField textFieldColor;
    private JButton clasificarButton;
    private JLabel labelResultado;
    private JSlider sliderColor;

    private JMenu menu;
    private JMenuItem i1, i2;



    public Principal(){

    }

    public void load(){
        frame.setTitle("Clasificador de Huevos");
        /*
        frame.setContentPane(this.panel );
        frame.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
        frame.pack();
        frame.setLocationRelativeTo( null );
        frame.setVisible( true );*/
        frame.setSize(800,600);
        frame.add(panel);

        frame.setLocationRelativeTo(null);

        initBar();
        frame.setVisible(true);
    }

    public void initBar(){
        JMenuBar mb=new JMenuBar();
        menu=new JMenu("Archivo");
        //submenu=new JMenu("Sub Menu");
        i1=new JMenuItem("Abrir");
        i2=new JMenuItem("Salir");
        menu.add(i1);
        menu.addSeparator();
        menu.add(i2);
        mb.add(menu);
        frame.setJMenuBar(mb);

        clasificarButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                double valPeso = Double.valueOf(textFieldPeso.getText().toString());
                double valColor = Double.valueOf(sliderColor.getValue());
                clasificacionhuevos c = new clasificacionhuevos();
                double[] b = c.crispInference(new double[] {valPeso,valColor});
                labelResultado.setText(String.valueOf(b[0]));

            }
        });

        i1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    //System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    //System.out.println("Selected file: " + selectedFile.getName());
                    //System.out.println("-------------------------");
                    //System.out.println(selectedFile.getAbsolutePath().substring(0, selectedFile.getAbsolutePath().lastIndexOf("\\")) );
                    Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
                    System.out.println("mat = " + mat.dump());

                    // prepare to convert a RGB image in gray scale
                    //String location = "resources/Poli.jpg";
                    //System.out.print("Convert the image at " + location + " in gray scale... ");
                    // get the jpeg image from the internal resource folder
                    Mat image = Imgcodecs.imread(selectedFile.getAbsolutePath());
                    // convert the image in gray scale
                    Imgproc.cvtColor(image, image, Imgproc.COLOR_RGB2GRAY);
                    // write the new image on disk
                    Imgcodecs.imwrite("./Poli-gray.jpg", image);

                }
            }
        });

        i2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

    }
}
