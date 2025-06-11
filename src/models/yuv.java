/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
import org.opencv.core.Mat;
import org.opencv.core.Core;
import org.opencv.imgproc.Imgproc;
/**
 *
 * @author User
 */
public class yuv {
    public Mat changeYUV(Mat image, double yFactor, double uFactor, double vFactor) {
        Mat yuvImage = new Mat();
        Imgproc.cvtColor(image, yuvImage, Imgproc.COLOR_BGR2YUV); // Μετατροπή σε YUV

        for (int y = 0; y < yuvImage.rows(); y++) {
            for (int x = 0; x < yuvImage.cols(); x++) {
                double[] yuv = yuvImage.get(y, x);

                yuv[0] = Math.min(255, yuv[0] * yFactor / 100.0);  // Y - Φωτεινότητα
                yuv[1] = Math.min(255, yuv[1] * uFactor / 100.0);  // U - Απόχρωση μπλε
                yuv[2] = Math.min(255, yuv[2] * vFactor / 100.0);  // V - Απόχρωση κόκκινου

                yuvImage.put(y, x, yuv);
            }
        }

        Mat resultImage = new Mat();
        Imgproc.cvtColor(yuvImage, resultImage, Imgproc.COLOR_YUV2BGR); // Μετατροπή πίσω σε BGR
        return resultImage;
    }
}