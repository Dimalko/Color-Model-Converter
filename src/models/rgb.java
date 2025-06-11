/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import org.opencv.core.Mat;

/**
 *
 * @author DimitrisM, PanagiotisT
 */
public class rgb {
    // Function to modify RGB values
    public Mat changeRGB(Mat mat, int rValue, int gValue, int bValue) {
        Mat modified = mat.clone();
        for (int row = 0; row < mat.rows(); row++) {
            for (int col = 0; col < mat.cols(); col++) {
                double[] pixel = mat.get(row, col);
                
                // set pixel values same as the slider values
                double blue = Math.max(0, Math.min(255, pixel[0] * (bValue / 32)));
                double green = Math.max(0, Math.min(255, pixel[1] * (gValue / 32)));
                double red = Math.max(0, Math.min(255, pixel[2] * (rValue / 32)));

                modified.put(row, col, new double[]{blue, green, red});
            }
        }
        return modified;
    }
}
