package istad.co.darambbankingapi.util;

public class MediaUtil {

    public static String extractExtension (String mediaName){

        // Extract extension for file upload
        // Example profile.png
        int lastDotIndex = mediaName
                .lastIndexOf(".");
        return mediaName
                .substring(lastDotIndex + 1); // substring use to get extension after .
    }
}
