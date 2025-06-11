/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
import org.opencv.core.Mat;
import org.opencv.core.Core;
import org.opencv.core.CvType;
/**
 *
 * @author User
 */
public class yiq {
    public Mat changeYIQ(Mat image, double yFactor, double iFactor, double qFactor) {
        Mat yiqImage = new Mat(image.size(), CvType.CV_64FC3);

        for (int y = 0; y < image.rows(); y++) {
            for (int x = 0; x < image.cols(); x++) {
                double[] bgr = image.get(y, x);

                // Μετατροπή BGR σε YIQ
                double Y = 0.299 * bgr[2] + 0.587 * bgr[1] + 0.114 * bgr[0];
                double I = 0.596 * bgr[2] - 0.274 * bgr[1] - 0.322 * bgr[0];
                double Q = 0.211 * bgr[2] - 0.523 * bgr[1] + 0.312 * bgr[0];

                // Εφαρμογή των παραγόντων από τα sliders
                Y = Math.min(255, Math.max(0, Y * yFactor / 100.0));
                I = Math.min(255, Math.max(-255, I * iFactor / 100.0));
                Q = Math.min(255, Math.max(-255, Q * qFactor / 100.0));

                // Μετατροπή YIQ πίσω σε BGR
                double R = Y + 0.956 * I + 0.621 * Q;
                double G = Y - 0.272 * I - 0.647 * Q;
                double B = Y - 1.106 * I + 1.703 * Q;

                yiqImage.put(y, x, new double[]{
                        Math.min(255, Math.max(0, B)),
                        Math.min(255, Math.max(0, G)),
                        Math.min(255, Math.max(0, R))
                });
            }
        }
        return yiqImage;
    }
}