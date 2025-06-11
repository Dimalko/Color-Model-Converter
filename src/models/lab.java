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
public class lab {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    
    public Mat changeLAB(Mat image, double lValue, double aValue, double bValue) {
        if (image.empty()) {
            throw new IllegalArgumentException("Input image is empty");
        }
        
        Mat labImage = new Mat();
        Imgproc.cvtColor(image, labImage, Imgproc.COLOR_BGR2Lab);
        
        List<Mat> labChannels = new ArrayList<>();
        Core.split(labImage, labChannels);
        
        // Adjust L, A, and B channels
        Core.add(labChannels.get(0), new Scalar(lValue), labChannels.get(0));
        Core.add(labChannels.get(1), new Scalar(aValue), labChannels.get(1));
        Core.add(labChannels.get(2), new Scalar(bValue), labChannels.get(2));
        
        Core.merge(labChannels, labImage);
        Imgproc.cvtColor(labImage, labImage, Imgproc.COLOR_Lab2BGR);
        
        return labImage;
    }
}
