package com.example;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_objdetect.*;

import java.net.URISyntaxException;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) throws URISyntaxException {
        String filepath = args.length > 0 ? args[0] : Paths.get(
                App.class.getResource("/lena.png").toURI()).toString();
        faceDetect(filepath);
    }

    public static void faceDetect(String filepath) {
        String classifierName = App.class.getResource(
                "/haarcascade_frontalface_default.xml").getFile();
        CascadeClassifier faceDetector = new CascadeClassifier(classifierName);
        System.out.println("load " + filepath);
        Mat source = imread(filepath);
        Rect faceDetections = new Rect();
        faceDetector.detectMultiScale(source, faceDetections);
        int numOfFaces = faceDetections.limit();
        System.out.println(numOfFaces + " faces are detected!");
        for (int i = 0; i < numOfFaces; i++) {
            Rect r = faceDetections.position(i);
            System.out.println(r.width() + " " + r.y());
            rectangle(source, new Point(r.x(), r.y()), new Point(r.x()
                    + r.width(), r.y() + r.height()), new Scalar(0, 0, 255, 0));
        }
        imwrite("faces.png", source);
    }
}
