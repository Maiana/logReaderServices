import services.LogReaderService;

import java.io.*;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, ParseException {
        LogReaderService logReaderService = new LogReaderService();
        logReaderService.start();
        logReaderService.printServiceReport();
    }
}
