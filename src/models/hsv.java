/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
import org.opencv.core.Mat;
import org.opencv.core.Core;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author DimitrisM,PanagiotisT
 */
public class hsv {
    public Mat changeHSV(Mat image, double hueShift, double saturationFactor, double valueFactor) {
        Mat hsvImage = new Mat();
        Imgproc.cvtColor(image, hsvImage, Imgproc.COLOR_BGR2HSV);

        // Διαχωρισμός των καναλιών HSV
        List<Mat> hsvChannels = new ArrayList<>();
        Core.split(hsvImage, hsvChannels);

        // Υπολογισμός των νέων τιμών, λαμβάνοντας υπόψη το 0 ως ουδέτερο σημείο
        double newHueShift = hueShift - 90; // Το 0 σημαίνει καμία αλλαγή
        double newSaturationFactor = 1.0 + (saturationFactor / 100.0); // 100 -> 2.0, 0 -> 1.0
        double newValueFactor = 1.0 + (valueFactor / 100.0); // 100 -> 2.0, 0 -> 1.0

        // Hue (0-180)
        Core.add(hsvChannels.get(0), new Scalar(newHueShift), hsvChannels.get(0));
        Core.min(hsvChannels.get(0), new Scalar(179), hsvChannels.get(0));
        Core.max(hsvChannels.get(0), new Scalar(0), hsvChannels.get(0));

        // Saturation (0-255)
        Core.multiply(hsvChannels.get(1), new Scalar(newSaturationFactor), hsvChannels.get(1));
        Core.min(hsvChannels.get(1), new Scalar(255), hsvChannels.get(1));
        Core.max(hsvChannels.get(1), new Scalar(0), hsvChannels.get(1));

        // Value (0-255)
        Core.multiply(hsvChannels.get(2), new Scalar(newValueFactor), hsvChannels.get(2));
        Core.min(hsvChannels.get(2), new Scalar(255), hsvChannels.get(2));
        Core.max(hsvChannels.get(2), new Scalar(0), hsvChannels.get(2));

        // Συγχώνευση των καναλιών
        Core.merge(hsvChannels, hsvImage);

        // Επιστροφή σε BGR
        Mat resultImage = new Mat();
        Imgproc.cvtColor(hsvImage, resultImage, Imgproc.COLOR_HSV2BGR);

        return resultImage;
    }
}