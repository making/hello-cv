package com.example;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_objdetect.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class App {
    public static void main(String[] args) throws URISyntaxException, IOException {
        String filepath = args.length > 0 ? args[0] : Paths.get(
                App.class.getResource("/lena.png").toURI()).toString();
        faceDetect(filepath);
    }

    public static void faceDetect(String filepath) throws URISyntaxException, IOException {
        String classifierName = Paths.get(
                App.class.getResource("/haarcascade_frontalface_default.xml")
                        .toURI()).toString();
        CascadeClassifier faceDetector = new CascadeClassifier(classifierName);
        System.out.println("load " + filepath);
        Mat source = Mat.createFrom(ImageIO.read(new File(filepath)));
        Rect faceDetections = new Rect();
        faceDetector.detectMultiScale(source, faceDetections);
        int numOfFaces = faceDetections.limit();
        System.out.println(numOfFaces + " faces are detected!");
        for (int i = 0; i < numOfFaces; i++) {
            Rect r = faceDetections.position(i);
            int x = r.x(), y = r.y(), h = r.height(), w = r.width();
            // make the face Duke
            rectangle(source, new Point(x, y), new Point(x + w, y + h / 2),
                    new Scalar(0, 0, 0, 0), -1, CV_AA, 0);
            rectangle(source, new Point(x, y + h / 2), new Point(x + w, y + h),
                    new Scalar(255, 255, 255, 0), -1, CV_AA, 0);
            circle(source, new Point(x + h / 2, y + h / 2), (w + h) / 12,
                    new Scalar(0, 0, 255, 0), -1, CV_AA, 0);
        }

        BufferedImage image = new BufferedImage(source.cols(), source.rows(), source
                .getBufferedImageType());
        source.copyTo(image);

        try (OutputStream out = Files.newOutputStream(Paths
                .get("duked-faces.png"))) {
            ImageIO.write(image, "png", out);
        }
    }
}
