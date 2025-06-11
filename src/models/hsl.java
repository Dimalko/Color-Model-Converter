/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author DimitrisM
 */
public class hsl {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    
    public Mat changeHSL(Mat image, double hValue, double sValue, double lValue) {
        if (image.empty()) {
            throw new IllegalArgumentException("Input image is empty");
        }
        
        Mat hslImage = new Mat();
        Imgproc.cvtColor(image, hslImage, Imgproc.COLOR_BGR2HSV);
        
        List<Mat> hslChannels = new ArrayList<>();
        Core.split(hslImage, hslChannels);
        
        // Adjust H, S, and L (approximated using HSV)
        Core.add(hslChannels.get(0), new Scalar(hValue), hslChannels.get(0)); // Hue
        Core.add(hslChannels.get(1), new Scalar(sValue), hslChannels.get(1)); // Saturation
        Core.add(hslChannels.get(2), new Scalar(lValue), hslChannels.get(2)); // Lightness
        
        Core.merge(hslChannels, hslImage);
        Imgproc.cvtColor(hslImage, hslImage, Imgproc.COLOR_HSV2BGR);
        
        return hslImage;
    }
}
