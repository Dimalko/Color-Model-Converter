/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
package models;

import org.opencv.core.Mat;
import org.opencv.core.Core;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import java.util.ArrayList;
import java.util.List;

public class ryb {

    // Μετατροπή από RGB σε RYB
    private double[] rgbToRyb(double r, double g, double b) {
        double w = Math.min(r, Math.min(g, b));
        r -= w;
        g -= w;
        b -= w;

        double mg = Math.max(r, Math.max(g, b));

        double y = Math.min(r, g);
        r -= y;
        g -= y;

        if (b > 0 && g > 0) {
            b /= 2.0;
            g /= 2.0;
        }

        y += g;
        b += w;
        r += w;

        return new double[]{r, y, b};
    }

    // Μετατροπή από RYB σε RGB
    private double[] rybToRgb(double r, double y, double b) {
        double w = Math.min(r, Math.min(y, b));
        r -= w;
        y -= w;
        b -= w;

        double my = Math.max(r, Math.max(y, b));

        double g = Math.min(y, b);
        y -= g;
        b -= g;

        if (b > 0 && y > 0) {
            b *= 2.0;
            y *= 2.0;
        }

        g += y;
        b += w;
        r += w;

        return new double[]{r, g, b};
    }

    // Συνάρτηση που εφαρμόζει αλλαγές με βάση sliders και επιστρέφει νέα εικόνα
    public Mat changeRYB(Mat image, double redFactor, double yellowFactor, double blueFactor) {
        Mat rybImage = image.clone();

        for (int y = 0; y < rybImage.rows(); y++) {
            for (int x = 0; x < rybImage.cols(); x++) {
                double[] rgb = rybImage.get(y, x);
                double[] ryb = rgbToRyb(rgb[2], rgb[1], rgb[0]); // OpenCV stores as BGR
                
                // Εφαρμογή μετασχηματισμών RYB
                ryb[0] *= redFactor / 100.0;
                ryb[1] *= yellowFactor / 100.0;
                ryb[2] *= blueFactor / 100.0;

                // Μετατροπή πίσω σε RGB
                double[] newRgb = rybToRgb(ryb[0], ryb[1], ryb[2]);

                rybImage.put(y, x, newRgb[2], newRgb[1], newRgb[0]); // BGR format
            }
        }

        return rybImage;
    }
}