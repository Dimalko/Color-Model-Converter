/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import org.opencv.core.Mat;
import org.opencv.core.CvType;
import org.opencv.core.Core;
import org.opencv.imgproc.Imgproc;
/**
 *
 * @author User
 */
public class hsi {
    public Mat changeHSI(Mat image, double hueShift, double saturationFactor, double intensityFactor) {
        Mat hsiImage = new Mat();
        Imgproc.cvtColor(image, hsiImage, Imgproc.COLOR_BGR2HSV); // OpenCV δεν έχει COLOR_BGR2HSI, χρησιμοποιούμε HSV ως βάση

        for (int row = 0; row < hsiImage.rows(); row++) {
            for (int col = 0; col < hsiImage.cols(); col++) {
                double[] hsi = hsiImage.get(row, col);

                // Hue (0-180 σε OpenCV)
                hsi[0] = (hsi[0] + hueShift) % 180;  

                // Saturation (0-255)
                hsi[1] = Math.min(255, hsi[1] * saturationFactor);

                // Intensity - Υπολογίζεται διαφορετικά σε σχέση με το Value
                hsi[2] = Math.min(255, hsi[2] * intensityFactor);

                hsiImage.put(row, col, hsi);
            }
        }

        Mat resultImage = new Mat();
        Imgproc.cvtColor(hsiImage, resultImage, Imgproc.COLOR_HSV2BGR);
        return resultImage;
    }
}
