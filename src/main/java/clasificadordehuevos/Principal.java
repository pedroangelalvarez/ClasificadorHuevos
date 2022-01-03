package clasificadordehuevos;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.comm.*;
import javax.swing.*;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Principal {
    private JFrame frame = new JFrame();
    private JPanel panel;
    private JTextField textFieldPeso;
    private JTextField textFieldColor;
    private JButton clasificarButton;
    private JLabel labelResultado;
    private JSlider sliderColor;
    private JButton buttonCOM;

    private Enumeration ports = new Vector().elements();
    private Map<String, CommPortIdentifier> portMap = new HashMap<String, CommPortIdentifier>();
    private int selectPort;
    private SerialPort serialPort;

    private JMenu menu;
    private JMenuItem i1, i2, i3;

    private int TIMEOUT = 60;

    public Principal(){

    }

    public void load(){
        frame.setTitle("Clasificador de Huevos");
        selectPort = -1;
        /*
        frame.setContentPane(this.panel );
        frame.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
        frame.pack();
        frame.setLocationRelativeTo( null );
        frame.setVisible( true );*/
        frame.setSize(800,600);

        Hashtable<Integer, JLabel> labelTable =
                new Hashtable<Integer, JLabel>();
        labelTable.put(new Integer( 0 ),
                new JLabel("0") );
        labelTable.put(new Integer( 255 ),
                new JLabel("255") );
        sliderColor.setLabelTable(labelTable);
        sliderColor.setPaintLabels(true);

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
        i2=new JMenuItem("Configuración");
        i3=new JMenuItem("Salir");
        menu.add(i1);
        menu.addSeparator();
        menu.add(i2);
        menu.addSeparator();
        menu.add(i3);
        mb.add(menu);
        frame.setJMenuBar(mb);

        clasificarButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!textFieldPeso.getText().toString().isEmpty()) {
                    double valPeso = Double.valueOf(textFieldPeso.getText().toString());
                    if (valPeso<50){
                        valPeso=50;
                    }
                    else if(valPeso>70){
                        valPeso=70;
                    }
                    double valColor = Double.valueOf(sliderColor.getValue());
                    clasificacionhuevos c = new clasificacionhuevos();
                    double[] b = c.crispInference(new double[]{valPeso, valColor});
                    labelResultado.setText("CLASE: " + String.valueOf((int) (b[0])));
                }
                else{
                    JOptionPane.showMessageDialog(null, "Ingresa Peso");
                }

            }
        });

        buttonCOM.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectPort==-1){
                    JOptionPane.showMessageDialog(null, "Seleccione el puerto de la balanza");
                }
                else{
                    //String puerto = ports[selectPort];
                    String puerto = "COM3";
                    CommPortIdentifier selectedPortIdentifier = (CommPortIdentifier)portMap.get(puerto);
                    CommPort commPort = null;
                    try {
                    commPort = selectedPortIdentifier.open("Send Sms Java", TIMEOUT);
                    serialPort = (SerialPort)commPort;

                    setSerialPortParameters();

                    boolean connected = true;
                    System.out.println("conectado exitosamente a puerto "+puerto);

                    } catch (IOException | PortInUseException ioException) {
                        ioException.printStackTrace();
                    }

                }
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

                    Mat image = Imgcodecs.imread(selectedFile.getAbsolutePath());
                    Mat gris = new Mat();
                    Imgproc.cvtColor(image, gris, Imgproc.COLOR_RGB2GRAY);
                    Mat gauss = new Mat();
                    Imgproc.GaussianBlur(gris, gauss, new Size(5, 5), 0, 0);
                    Mat canny = new Mat();
                    Imgproc.Canny(gauss, canny, 50, 150);

                    List<MatOfPoint> contours = new ArrayList<>();
                    Mat hierarchy =new Mat();
                    Imgproc.findContours(canny, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

                    for ( int contourIdx=0; contourIdx < contours.size(); contourIdx++ )
                    {
                        //Minimun size allowed for consideration
                        MatOfPoint2f approxCurve = new MatOfPoint2f();
                        MatOfPoint2f contour2f = new MatOfPoint2f(contours.get(contourIdx).toArray());

                        //Processing on mMOP2f1 which is in type MatOfPoint2f
                        double approxDistance = Imgproc.arcLength(contour2f,true)*0.02;
                        Imgproc.approxPolyDP(contour2f,approxCurve,approxDistance,true);


                        //show contour kontur
                        if(Imgproc.contourArea(contours.get(contourIdx))>100) {
                            Imgproc.drawContours(image, contours, contourIdx, new Scalar(0,255,0), 5);
                        }
                    }

                    ArrayList<Mat> rgb = new ArrayList<>(3);
                    Core.split(image, rgb);
                    Mat rojo = rgb.get(2);
                    Mat azul = rgb.get(0);
                    Mat verde= rgb.get(1);
                    int cantPixels = 0;
                    int sumatoria= 0;
                    for(int i = 0; i<verde.rows(); i++){
                        boolean Inicio=false;
                        boolean Final=false;
                        for(int j=0; j<verde.cols()-1; j++){
                            //System.out.println((verde.get(i,j)[0]));
                            //System.out.println("R "+(rojo.get(i,j)[0]));
                            //System.out.println("A "+(azul.get(i,j)[0]));
                            if ((int)(verde.get(i,j)[0])==255 && (int)(verde.get(i,j+1)[0])<200){
                                Inicio=true;
                            }
                            else if((int)(verde.get(i,j)[0])<200 && (int)(verde.get(i,j+1)[0])==255) {
                                Final=true;
                            }
                            if (Inicio && !Final){
                                cantPixels+=1;
                                sumatoria+=(int)(rojo.get(i,j)[0]);//((int)(verde.get(i,j)[0])+(int)(rojo.get(i,j)[0])+(int)(azul.get(i,j)[0]))/3;
                            }

                        }
                    }
                    System.out.println(cantPixels);
                    System.out.println(sumatoria);
                    int promedio = sumatoria/cantPixels;
                    if (contours.size()>1){
                        sliderColor.setValue(0);
                    }
                    else{
                        System.out.println(promedio);
                        sliderColor.setValue(promedio);
                    }
                    // convert the image in gray scale

                    // write the new image on disk
                    Imgcodecs.imwrite("./Poli-gray.jpg", image);

                }
            }
        });

        i2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog diag = new JDialog();
                String[] valores = new String[0];
                while (ports.hasMoreElements())
                {
                    CommPortIdentifier curPort = (CommPortIdentifier)ports.nextElement();
                    if (curPort.getPortType() == CommPortIdentifier.PORT_SERIAL)
                    {
                        System.out.println();
                        valores = append(valores, curPort.getName());
                    }
                }
                JComboBox jcd = new JComboBox(valores);
                jcd.setEditable(true);
                Object[] options = new Object[] {};
                JOptionPane jop = new JOptionPane("Seleccione un Puerto",
                        JOptionPane.QUESTION_MESSAGE,
                        JOptionPane.DEFAULT_OPTION,
                        null,options, null);
                jop.add(jcd);

                diag.getContentPane().add(jop);
                diag.pack();
                diag.setLocationRelativeTo(panel);
                diag.setAlwaysOnTop(true);
                diag.setVisible(true);


                System.out.println("Puertos Disponibles:");
                ports = CommPortIdentifier.getPortIdentifiers();
                valores = new String[0];
                while (ports.hasMoreElements()) {
                    CommPortIdentifier curPort = (CommPortIdentifier) ports.nextElement();
                    if (curPort.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                        System.out.println(curPort.getName());
                        portMap.put(curPort.getName(), curPort);
                        valores = append(valores, curPort.getName());
                    }
                }
                jcd.setModel(new DefaultComboBoxModel<>(valores));
                jcd.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectPort = jcd.getSelectedIndex();
                        System.out.println(selectPort);
                        diag.dispose();
                    }
                });
                System.out.println(portMap);
                System.out.println("----------------------");


            }
        });

        i3.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }

    static <T> T[] append(T[] arr, T element) {
        final int N = arr.length;
        arr = Arrays.copyOf(arr, N + 1);
        arr[N] = element;
        return arr;
    }
    
    private void setSerialPortParameters() throws IOException {
        int baudRate = 115200;
        try {
            serialPort.setSerialPortParams(baudRate,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.setFlowControlMode(
                    SerialPort.FLOWCONTROL_NONE);
        } catch (UnsupportedCommOperationException ex) {
            throw new IOException("Unsupported serial port parameter");
        }
    }
}
