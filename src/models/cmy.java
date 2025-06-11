/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.CvType;
import org.opencv.imgproc.Imgproc;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DimitrisM
 */
public class cmy {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public Mat changeCMY(Mat image, double cValue, double mValue, double yValue) {
        if (image.empty()) {
            throw new IllegalArgumentException("Input image is empty");
        }

        // Convert BGR to CMY (CMY = 255 - BGR)
        Mat cmyImage = new Mat();
        Core.subtract(new Mat(image.size(), image.type(), new Scalar(255, 255, 255)), image, cmyImage);

        // Split CMY channels (Use List instead of array)
        List<Mat> cmyChannels = new ArrayList<>();
        Core.split(cmyImage, cmyChannels);

        // Adjust Cyan, Magenta, and Yellow channels
        Core.add(cmyChannels.get(2), new Scalar(cValue), cmyChannels.get(2)); // Adjust Cyan
        Core.add(cmyChannels.get(1), new Scalar(mValue), cmyChannels.get(1)); // Adjust Magenta
        Core.add(cmyChannels.get(0), new Scalar(yValue), cmyChannels.get(0)); // Adjust Yellow

        // Merge adjusted CMY channels back
        Core.merge(cmyChannels, cmyImage);

        // Convert CMY back to BGR (BGR = 255 - CMY)
        Mat resultImage = new Mat();
        Core.subtract(new Mat(cmyImage.size(), cmyImage.type(), new Scalar(255, 255, 255)), cmyImage, resultImage);

        return resultImage;
    }
}
