package dip_rgb_converter;


import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Slider;
import javafx.scene.control.Label;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.image.PixelReader;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

import models.rgb;
import models.hsv;
import models.ryb;
import models.yuv;
import models.yiq;
import models.xyz;
import models.hsi;
import models.lab;
import models.hsl;
import models.cmy;



/**
 *
 * @author DimitrisM, PanagiotisT
 */
public class mainFXMLController implements Initializable {

    @FXML
    private MenuItem menuOpenFile;
    
    @FXML
    private MenuItem menuSaveFile;
    
    
    @FXML
    private ImageView imageView;
   
    @FXML //rgb sliders
    private Slider rgbRedSlider, rgbGreenSlider, rgbBlueSlider;
    @FXML // HSV sliders
    private Slider hsvHueSlider,hsvSaturationSlider,hsvValueSlider;
    @FXML //RYB sliders
    private Slider rybRedSlider,rybYellowSlider,rybBlueSlider;
    @FXML // YUV sliders 
    private Slider yuvYSlider,yuvUSlider,yuvVSlider;
    @FXML //YIQ sliders
    private Slider yiqYSlider,yiqISlider,yiqQSlider;
    @FXML //XYZ sliders
    private Slider xyzXSlider,xyzYSlider,xyzZSlider;
    @FXML // HSI sliders 
    private Slider hsiHueSlider,hsiSaturationSlider,hsiIntensitySlider;
    @FXML // CMY sliders
    private Slider cyanSlider,magentaSlider,yellowSlider;
    @FXML // HSL sliders
    private Slider hslHueSlider,hslSaturationSlider,hslLightnessSlider;
    @FXML // LAB sliders
    private Slider labLSlider,labASlider,labBSlider;
    
    // Label Values
    @FXML
    private Label redValueLabel, greenValueLabel, blueValueLabel;
    @FXML
    private Label cyanValueLabel, magentaValueLabel, yellowValueLabel;
    @FXML 
    private Label labLValueLabel,labAValueLabel,labBValueLabel;
    @FXML 
    private Label rybRedValueLabel,rybYellowValueLabel,rybBlueValueLabel;
    @FXML 
    private Label hsvHueValueLabel,hsvSaturationValueLabel,hsvValueValueLabel;
    @FXML 
    private Label hslHueValueLabel,hslSaturationValueLabel,hslLightnessValueLabel;
    @FXML 
    private Label hsiHueValueLabel,hsiSaturationValueLabel,hsiIntensityValueLabel;
    @FXML 
    private Label yuvYValueLabel,yuvUValueLabel,yuvVValueLabel;
    @FXML
    private Label yiqYValueLabel,yiqIValueLabel,yiqQValueLabel;
    @FXML 
    private Label xyzXValueLabel,xyzYValueLabel,xyzZValueLabel;
    
    
    private Image image;
    public Mat imageMat;
    public Mat tempImageMat;
    public Mat finalImage;
    public Mat previousMat;
    
    private String lastUsedColorModel = "rgb";
    
    VideoCapture camera = new VideoCapture(0);
    private boolean cameraActive = false;
    
    
    @FXML
    private void initialize(String c) {
        if("rgb".equals(c)){
            // Ensure the label displays the initial slider value
            redValueLabel.setText(String.valueOf((int) rgbRedSlider.getValue()));
            // Add a listener to update the label when the slider moves
            rgbRedSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
                redValueLabel.setText(String.valueOf(newValue.intValue()))
            );
            
            greenValueLabel.setText(String.valueOf((int) rgbGreenSlider.getValue()));
            rgbGreenSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
                greenValueLabel.setText(String.valueOf(newValue.intValue()))
            );
            
            blueValueLabel.setText(String.valueOf((int) rgbBlueSlider.getValue()));
            rgbBlueSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
                blueValueLabel.setText(String.valueOf(newValue.intValue()))
            );
        }
        
        else if ("cmy".equals(c)) {
            cyanValueLabel.setText(String.valueOf((int) cyanSlider.getValue()));
            cyanSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
                cyanValueLabel.setText(String.valueOf(newValue.intValue()))
            );

            magentaValueLabel.setText(String.valueOf((int) magentaSlider.getValue()));
            magentaSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
                magentaValueLabel.setText(String.valueOf(newValue.intValue()))
            );

            yellowValueLabel.setText(String.valueOf((int) yellowSlider.getValue()));
            yellowSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
                yellowValueLabel.setText(String.valueOf(newValue.intValue()))
            );
        }
        
        else if ("lab".equals(c)) {
        labLValueLabel.setText(String.valueOf((int) labLSlider.getValue()));
        labLSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
            labLValueLabel.setText(String.valueOf(newValue.intValue()))
        );

        labAValueLabel.setText(String.valueOf((int) labASlider.getValue()));
        labASlider.valueProperty().addListener((obs, oldValue, newValue) -> 
            labAValueLabel.setText(String.valueOf(newValue.intValue()))
        );

        labBValueLabel.setText(String.valueOf((int) labBSlider.getValue()));
        labBSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
            labBValueLabel.setText(String.valueOf(newValue.intValue()))
        );
        }
        
         else if ("ryb".equals(c)) {
        rybRedValueLabel.setText(String.valueOf((int) rybRedSlider.getValue()));
        rybRedSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
            rybRedValueLabel.setText(String.valueOf(newValue.intValue()))
        );

        rybYellowValueLabel.setText(String.valueOf((int) rybYellowSlider.getValue()));
        rybYellowSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
            rybYellowValueLabel.setText(String.valueOf(newValue.intValue()))
        );

        rybBlueValueLabel.setText(String.valueOf((int) rybBlueSlider.getValue()));
        rybBlueSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
            rybBlueValueLabel.setText(String.valueOf(newValue.intValue()))
        );
        }
         
        else if ("hsv".equals(c)) {
        hsvHueValueLabel.setText(String.valueOf((int) hsvHueSlider.getValue()));
        hsvHueSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
            hsvHueValueLabel.setText(String.valueOf(newValue.intValue()))
        );

        hsvSaturationValueLabel.setText(String.valueOf((int) hsvSaturationSlider.getValue()));
        hsvSaturationSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
            hsvSaturationValueLabel.setText(String.valueOf(newValue.intValue()))
        );

        hsvValueValueLabel.setText(String.valueOf((int) hsvValueSlider.getValue()));
        hsvValueSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
            hsvValueValueLabel.setText(String.valueOf(newValue.intValue()))
        );
        }
        else if ("hsl".equals(c)) {
        hslHueValueLabel.setText(String.valueOf((int) hslHueSlider.getValue()));
        hslHueSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
            hslHueValueLabel.setText(String.valueOf(newValue.intValue()))
        );

        hslSaturationValueLabel.setText(String.valueOf((int) hslSaturationSlider.getValue()));
        hslSaturationSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
            hslSaturationValueLabel.setText(String.valueOf(newValue.intValue()))
        );

        hslLightnessValueLabel.setText(String.valueOf((int) hslLightnessSlider.getValue()));
        hslLightnessSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
            hslLightnessValueLabel.setText(String.valueOf(newValue.intValue()))
        );
        }
        
        else if ("hsi".equals(c)) {
        hsiHueValueLabel.setText(String.valueOf((int) hsiHueSlider.getValue()));
        hsiHueSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
            hsiHueValueLabel.setText(String.valueOf(newValue.intValue()))
        );

        hsiSaturationValueLabel.setText(String.valueOf((int) hsiSaturationSlider.getValue()));
        hsiSaturationSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
            hsiSaturationValueLabel.setText(String.valueOf(newValue.intValue()))
        );

        hsiIntensityValueLabel.setText(String.valueOf((int) hsiIntensitySlider.getValue()));
        hsiIntensitySlider.valueProperty().addListener((obs, oldValue, newValue) -> 
            hsiIntensityValueLabel.setText(String.valueOf(newValue.intValue()))
        );
        }
        
        else if ("yuv".equals(c)) {
        yuvYValueLabel.setText(String.valueOf((int) yuvYSlider.getValue()));
        yuvYSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
            yuvYValueLabel.setText(String.valueOf(newValue.intValue()))
        );

        yuvUValueLabel.setText(String.valueOf((int) yuvUSlider.getValue()));
        yuvUSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
            yuvUValueLabel.setText(String.valueOf(newValue.intValue()))
        );

        yuvVValueLabel.setText(String.valueOf((int) yuvVSlider.getValue()));
        yuvVSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
            yuvVValueLabel.setText(String.valueOf(newValue.intValue()))
        );
        }
        else if ("yiq".equals(c)) {
        yiqYValueLabel.setText(String.valueOf((int) yiqYSlider.getValue()));
        yiqYSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
            yiqYValueLabel.setText(String.valueOf(newValue.intValue()))
        );

        yiqIValueLabel.setText(String.valueOf((int) yiqISlider.getValue()));
        yiqISlider.valueProperty().addListener((obs, oldValue, newValue) -> 
            yiqIValueLabel.setText(String.valueOf(newValue.intValue()))
        );

        yiqQValueLabel.setText(String.valueOf((int) yiqQSlider.getValue()));
        yiqQSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
            yiqQValueLabel.setText(String.valueOf(newValue.intValue()))
        );
        }
        
        else if ("xyz".equals(c)) {
            xyzXValueLabel.setText(String.valueOf((int) xyzXSlider.getValue()));
            xyzXSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
                xyzXValueLabel.setText(String.valueOf(newValue.intValue()))
            );

            xyzYValueLabel.setText(String.valueOf((int) xyzYSlider.getValue()));
            xyzYSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
                xyzYValueLabel.setText(String.valueOf(newValue.intValue()))
            );

            xyzZValueLabel.setText(String.valueOf((int) xyzZSlider.getValue()));
            xyzZSlider.valueProperty().addListener((obs, oldValue, newValue) -> 
                xyzZValueLabel.setText(String.valueOf(newValue.intValue()))
            );
            }
    }
    
    @FXML
    private void handleOpenFile(ActionEvent event) throws MalformedURLException{
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.bmp", "*.png", "*.jpg", "*.gif")
        );
        File file = chooser.showOpenDialog(new Stage());
        
        if(file != null){
            //load image using opencv
            imageMat = Imgcodecs.imread(file.getAbsolutePath());
            //convert mat to to javafx image and display
            matToImage(imageMat);
            
            tempImageMat = imageMat;
            imageView.setImage(image);
        }
        

        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            
        }
    }
    
    
    @FXML
    private void convertImage(){
        if (tempImageMat == null || tempImageMat.empty()){
            JOptionPane.showMessageDialog(null, "No image to convert!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        previousMat = imageMat.clone();
        finalImage = tempImageMat.clone();
        imageMat = finalImage.clone();
        
        if(cameraActive){
            stopCamera();
        }
        
        matToImage(finalImage);
    }
    
    
    @FXML
    private void undo(){
        if ( previousMat == null || previousMat.empty()){
            JOptionPane.showMessageDialog(null, "No steps to undo!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        imageMat = previousMat.clone();
        finalImage = imageMat.clone();
        
        matToImage(finalImage);
    }
    
    
    @FXML
    private void handleSaveFile(ActionEvent event) {
         try {
                if (image == null) { // Ensure an edited image exists
                JOptionPane.showMessageDialog(null, "No edited image to save!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
        }

        // File chooser for saving the image
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");

        // Set file filters for image formats
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG File", "*.png"),
                new FileChooser.ExtensionFilter("JPEG File", "*.jpg"),
                new FileChooser.ExtensionFilter("Bitmap File", "*.bmp")
        );

        // Set default file name
        String editedFileName = "edited_image";  
        fileChooser.setInitialFileName(editedFileName);

        // Show save dialog
        File file = fileChooser.showSaveDialog(imageView.getScene().getWindow());

        if (file != null) {
            // Save the OpenCV Mat image
            boolean saved = Imgcodecs.imwrite(file.getAbsolutePath(), finalImage);

            // Display success or failure message
            if (saved) {
                JOptionPane.showMessageDialog(null, "Image saved successfully!", "Information", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to save the image!", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
      }
    }
    
   
    @FXML
    private void handleChangeRGB(){
        if (tempImageMat != null){
            int r = (int) rgbRedSlider.getValue();
            int g = (int) rgbGreenSlider.getValue();
            int b = (int) rgbBlueSlider.getValue();
            
            rgb rgbProcessor = new rgb();
            tempImageMat = rgbProcessor.changeRGB(imageMat, r, g, b);
            
            lastUsedColorModel = "rgb"; 
            applyColorModel();
            
            initialize("rgb");
            matToImage(tempImageMat);
        }
    }
    
    @FXML
    private void handleChangeHSV() {
        if (imageMat != null) { // Χρησιμοποιούμε την αρχική εικόνα κάθε φορά
            double h = hsvHueSlider.getValue();
            double s = hsvSaturationSlider.getValue();
            double v = hsvValueSlider.getValue();

            hsv hsvProcessor = new hsv();
            tempImageMat = hsvProcessor.changeHSV(imageMat.clone(), h, s, v); // Χρησιμοποιούμε πάντα την αρχική εικόνα
             
            lastUsedColorModel = "hsv"; 
            applyColorModel();
            
            initialize("hsv"); // Ενημέρωση των Labels
            matToImage(tempImageMat);
        }
    }

     @FXML
    private void handleChangeRYB() {
        if (tempImageMat != null) {
            double r = rybRedSlider.getValue();
            double y = rybYellowSlider.getValue();
            double b = rybBlueSlider.getValue();

            ryb rybProcessor = new ryb();
            tempImageMat = rybProcessor.changeRYB(imageMat, r, y, b);
            
            lastUsedColorModel = "ryb"; 
            applyColorModel();
            
            initialize("ryb");//Ενημερωση των Labels
            matToImage(tempImageMat);
        }
    }
    
    @FXML
    private void handleChangeYUV() {
        if (tempImageMat != null) {
            double y = yuvYSlider.getValue();
            double u = yuvUSlider.getValue();
            double v = yuvVSlider.getValue();

            yuv yuvProcessor = new yuv();
            tempImageMat = yuvProcessor.changeYUV(imageMat, y, u, v);
            
            lastUsedColorModel = "yuv"; 
            applyColorModel();
            
            initialize("yuv");// Ενημερωση των Labels
            matToImage(tempImageMat);
        }
    }
    
    @FXML
    private void handleChangeYIQ() {
        if (tempImageMat != null) {
            double y = yiqYSlider.getValue();
            double i = yiqISlider.getValue();
            double q = yiqQSlider.getValue();

            yiq yiqProcessor = new yiq();
            tempImageMat = yiqProcessor.changeYIQ(imageMat, y, i, q);
            
            lastUsedColorModel = "yiq"; 
            applyColorModel();
            
            initialize("yiq");// Ενημερωση των Labels
            matToImage(tempImageMat);
        }
    }
    
    @FXML
    private void handleChangeXYZ() {
        if (imageMat != null) {  // Πάντα ξεκινάμε από την αρχική εικόνα
            double x = xyzXSlider.getValue();  
            double y = xyzYSlider.getValue(); 
            double z = xyzZSlider.getValue();  

            xyz xyzProcessor = new xyz();
            tempImageMat = xyzProcessor.changeXYZ(imageMat.clone(), x, y, z);
            
            lastUsedColorModel = "xyz"; 
            applyColorModel();
            
            initialize("xyz"); // Ενημέρωση των Labels
            matToImage(tempImageMat);
        }
    }

    @FXML
    private void handleChangeHSI() {
        if (imageMat != null) {  // Ξεκινάμε πάντα από την αρχική εικόνα
            double h = hsiHueSlider.getValue();
            double s = hsiSaturationSlider.getValue();
            double i = hsiIntensitySlider.getValue();

            hsi hsiProcessor = new hsi();
            tempImageMat = hsiProcessor.changeHSI(imageMat.clone(), h, s, i);
            
            lastUsedColorModel = "hsi"; 
            applyColorModel();
            
            initialize("hsi"); // Ενημέρωση των Labels
            matToImage(tempImageMat);
        }
    }
    
    @FXML
    private void handleChangeLab(){
        if (tempImageMat != null) {
            double l = labLSlider.getValue(); 
            double a = labASlider.getValue(); 
            double b = labBSlider.getValue(); 

            lab labProcessor = new lab();
            tempImageMat = labProcessor.changeLAB(imageMat, l, a, b);
            
            lastUsedColorModel = "lab"; 
            applyColorModel();
            
            initialize("lab"); // Ενημέρωση των Label
            matToImage(tempImageMat);
        }
    }
    
    @FXML
    private void handleChangeHSL(){
        if (tempImageMat != null) {
            double h = hslHueSlider.getValue(); 
            double s = hslSaturationSlider.getValue(); 
            double l = hslLightnessSlider.getValue(); 

            hsl hslProcessor = new hsl();
            tempImageMat = hslProcessor.changeHSL(imageMat, h, s, l);
            
            lastUsedColorModel = "hsl"; 
            applyColorModel();
            
            initialize("hsl"); // Ενημέρωση των Labels
            matToImage(tempImageMat);
        }
    }
     
     @FXML
    private void handleChangeCMY(){
        if (tempImageMat != null) {
            double c = cyanSlider.getValue(); 
            double m = magentaSlider.getValue(); 
            double y = yellowSlider.getValue(); 

            cmy cmyProcessor = new cmy();
            tempImageMat = cmyProcessor.changeCMY(imageMat, c, m, y);
            
            lastUsedColorModel = "cmy"; 
            applyColorModel();
            
            initialize("cmy"); // Ενημέρωση των Labels
            matToImage(tempImageMat);
        }
    }
     
    @FXML
    private void startCamera() {
        if (!cameraActive) {
            camera.open(0); // Open default camera (0)

            if (camera.isOpened()) {
                cameraActive = true;

                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() {
                        imageMat = new Mat();
                        tempImageMat = new Mat();
                        
                        int frameSkip = 0;
                        
                        while (cameraActive) {
                            if (camera.read(imageMat)) {
                                
                                if (frameSkip % 3 == 0){
                                    // Apply color transformation
                                    Platform.runLater(() -> applyColorModel());

                                    // Convert and display frame
                                    Platform.runLater(() -> matToImage(tempImageMat)); 
                                }
                                frameSkip++;
                                
                            }
                            try {
                                Thread.sleep(30); // 30ms delay for smooth video
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        return null;
                    }
                };

                Thread thread = new Thread(task);
                thread.setDaemon(true);
                thread.start();
            } else {
                System.out.println("Error: Camera not found!");
            }
        }
    }
    
    private void applyColorModel() {
        if (imageMat.empty()) return; //avoid errors if no frame is captured
        
        if (lastUsedColorModel == null){
            lastUsedColorModel = "rgb";
        }
        
        switch (lastUsedColorModel) {
            case "rgb":
                int r = (int) rgbRedSlider.getValue();
                int g = (int) rgbGreenSlider.getValue();
                int b = (int) rgbBlueSlider.getValue();

                rgb rgbProcessor = new rgb();
                tempImageMat = rgbProcessor.changeRGB(imageMat, r, g, b);
                break;

            case "hsv":
                double h = hsvHueSlider.getValue();
                double s = hsvSaturationSlider.getValue();
                double v = hsvValueSlider.getValue();

                hsv hsvProcessor = new hsv();
                tempImageMat = hsvProcessor.changeHSV(imageMat, h, s, v);
                break;

            case "cmy":
                double c = cyanSlider.getValue();
                double m = magentaSlider.getValue();
                double y = yellowSlider.getValue();

                cmy cmyProcessor = new cmy();
                tempImageMat = cmyProcessor.changeCMY(imageMat, c, m, y);
                break;
                
            case "ryb":
                double rr = rybRedSlider.getValue();
                double yy = rybYellowSlider.getValue();
                double bb = rybBlueSlider.getValue();

                ryb rybProcessor = new ryb();
                tempImageMat = rybProcessor.changeRYB(imageMat, rr, yy, bb);
                break;
                
            case "yuv":
                double yuvY = yuvYSlider.getValue();
                double yuvU = yuvUSlider.getValue();
                double yuvV = yuvVSlider.getValue();

                yuv yuvProcessor = new yuv();
                tempImageMat = yuvProcessor.changeYUV(imageMat, yuvY, yuvU, yuvV);
                break;
                
            case "yiq":
                double yiqY = yiqYSlider.getValue();
                double yiqI = yiqISlider.getValue();
                double yiqQ = yiqQSlider.getValue();

                yiq yiqProcessor = new yiq();
                tempImageMat = yiqProcessor.changeYIQ(imageMat, yiqY, yiqI, yiqQ);
                break;
            
            case "xyz":
                double xyzX = xyzXSlider.getValue();
                double xyzY = xyzYSlider.getValue();
                double xyzZ = xyzZSlider.getValue();

                xyz xyzProcessor = new xyz();
                tempImageMat = xyzProcessor.changeXYZ(imageMat, xyzX, xyzY, xyzZ);
                break;
            
            case "hsi":
                double hsiH = hsiHueSlider.getValue();
                double hsiS = hsiSaturationSlider.getValue();
                double hsiI = hsiIntensitySlider.getValue();

                hsi hsiProcessor = new hsi();
                tempImageMat = hsiProcessor.changeHSI(imageMat, hsiH, hsiS, hsiI);
                break;
            
            case "hsl":
                double hslH = hslHueSlider.getValue();
                double hslS = hslSaturationSlider.getValue();
                double hslL = hslLightnessSlider.getValue();

                hsl hslProcessor = new hsl();
                tempImageMat = hslProcessor.changeHSL(imageMat, hslH, hslS, hslL);
                break;
            
            case "lab":
                double labL = labLSlider.getValue();
                double labA = labASlider.getValue();
                double labB = labBSlider.getValue();

                lab labProcessor = new lab();
                tempImageMat = labProcessor.changeLAB(imageMat, labL, labA, labB);
                break;
            
            default:
                tempImageMat = imageMat.clone(); // Keep original if no model is selected
                break;
        }
    }
    
    @FXML
    private void stopCamera() {
        cameraActive = false;
        camera.release();
        imageView.setImage(null);
    }   

    //convert opencv mat to javafx image
    private void  matToImage(Mat mat){
        MatOfByte byteMat = new MatOfByte();
        Imgcodecs.imencode(".png", mat, byteMat);
        
        image =  new Image(new ByteArrayInputStream(byteMat.toArray()));
        
        imageView.setImage(image);
    }

    @FXML
    private void openHelpFile() {
        try {
            // Get the path to the EXE
            String exePath = new File(mainFXMLController.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
            File exeDirectory = new File(exePath).getParentFile(); // Get the folder containing the EXE

            // Build the path to helpFiles
            File helpFilesDir = new File(exeDirectory, "helpFiles");

            // Specify the file you want to open
            File fileToOpen = new File(helpFilesDir, "GettingStarted.pdf");

            // If the file exists, open it
            if (fileToOpen.exists()) {
                System.out.println("File path: " + fileToOpen.getCanonicalPath());
                Desktop.getDesktop().open(fileToOpen);  // Opens the file with the default app
            } else {
                System.out.println("File not found: " + fileToOpen.getCanonicalPath());
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void openModelsFile() {
        try {
            // Get the path to the EXE
            String exePath = new File(mainFXMLController.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
            File exeDirectory = new File(exePath).getParentFile(); // Get the folder containing the EXE

            // Build the path to helpFiles
            File helpFilesDir = new File(exeDirectory, "helpFiles");

            // Specify the file you want to open
            File fileToOpen = new File(helpFilesDir, "ColorModels.pdf");

            // If the file exists, open it
            if (fileToOpen.exists()) {
                System.out.println("File path: " + fileToOpen.getCanonicalPath());
                Desktop.getDesktop().open(fileToOpen);  // Opens the file with the default app
            } else {
                System.out.println("File not found: " + fileToOpen.getCanonicalPath());
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

