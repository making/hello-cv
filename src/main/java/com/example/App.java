package com.example;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_highgui.*;

public class App 
{
    public static void main( String[] args ) {
        smooth(args[0]);
    }

    public static void smooth(String filename) { 
        IplImage image = cvLoadImage(filename);
        System.out.println("image = " + image);
        if (image != null) {
            cvSmooth(image, image);
            cvSaveImage(filename, image);
            cvReleaseImage(image);
        }
    }
}
