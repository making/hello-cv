package com.example;


import java.nio.file.Paths;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_objdetect.*;

public class App {
    public static void main(String[] args) {
        String filepath = args.length > 0 ? args[0] : App.class.getResource("/lena.png").getFile();
        //resize(filepath);
        faceDetect(filepath);
    }

    public static void resize(String filepath) {
        IplImage source = cvLoadImage(filepath, CV_LOAD_IMAGE_ANYDEPTH | CV_LOAD_IMAGE_ANYCOLOR);
        System.out.println("path = " + filepath);
        System.out.println("image = " + source);
        if (source != null) {
            IplImage dest = cvCreateImage(cvSize(source.width() / 2, source.height() / 2), source.depth(), source.nChannels());
            cvResize(source, dest, CV_INTER_NN);
            cvSaveImage("half-" + Paths.get(filepath).getFileName().toString(), dest);
            cvReleaseImage(source);
            cvReleaseImage(dest);
        }
    }

    public static void faceDetect(String filepath) {
        String classifierName = App.class.getResource("/haarcascade_frontalface_default.xml").getFile();
        CascadeClassifier faceDetector = new CascadeClassifier(classifierName);
        Mat source = imread(filepath);
        Rect faceDetections = new Rect();
        faceDetector.detectMultiScale(source, faceDetections);
        for (int i = 0, n = faceDetections.limit(); i < n; i++) {
            Rect r = faceDetections.position(i);
            rectangle(source, new Point(r.x(), r.y())
                    , new Point(r.x() + r.width(), r.y() + r.height()),
                    new Scalar(0, 0, 255, 0));
        }
        imwrite("faces.png", source);
    }
}
