/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
/**
 *
 * @author User
 */
public class xyz {
    public Mat changeXYZ(Mat image, double xFactor, double yFactor, double zFactor) {
        Mat xyzImage = new Mat();
        Imgproc.cvtColor(image, xyzImage, Imgproc.COLOR_BGR2XYZ);

        for (int row = 0; row < xyzImage.rows(); row++) {
            for (int col = 0; col < xyzImage.cols(); col++) {
                double[] xyz = xyzImage.get(row, col);

                xyz[0] = xyz[0] * (xFactor / 100.0); // X
                xyz[1] = xyz[1] * (yFactor / 100.0); // Y
                xyz[2] = xyz[2] * (zFactor / 100.0); // Z

                xyzImage.put(row, col, xyz);
            }
        }

        Mat resultImage = new Mat();
        Imgproc.cvtColor(xyzImage, resultImage, Imgproc.COLOR_XYZ2BGR);
        return resultImage;
    }
}