package services;

import models.ServiceLogData;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Scanner;

public class LogReaderService {

    ArrayList<ServiceLogData> serviceLogDataData = new ArrayList<>();
    Hashtable<String, String> pendingRequests = new Hashtable<>();

    public void start() throws FileNotFoundException, ParseException {
        File file = selectFile();
        readFile(file);
    }

    private File selectFile() {
        final JFileChooser fc = new JFileChooser();
        final JButton button = new JButton("Select");
        fc.showOpenDialog(button.getParent());
        File file = fc.getSelectedFile();
        return file;
    }

    private void readFile(File file) throws FileNotFoundException, ParseException {
        Scanner readingFile = new Scanner(file);

        while (readingFile.hasNextLine()) {
            String data = readingFile.nextLine();

            String serviceName = getServiceName(data);
            String serviceId = getServiceId(data);
            String logDateTime = getDateTime(data);

            addServiceName(serviceName);
            updateServiceInformation(serviceName, serviceId, logDateTime);
        }
        readingFile.close();
    }

    private String getServiceName(String data) {

        int startIndex = data.indexOf("(");
        int stopIndexName = data.lastIndexOf(":");
        String serviceName = data.substring(startIndex + 1, stopIndexName);

        return serviceName;
    }

    private String getServiceId(String data){
        int startIndex = data.indexOf("(");
        int stopIndexID = data.indexOf(")");

        String serviceId = data.substring(startIndex, stopIndexID + 1);
        return serviceId;
    }

    private String getDateTime(String data){
        int stopIndexDate = data.indexOf("T");
        int stopIndexTime = data.indexOf(" ");
        String date = data.substring(0, stopIndexDate);
        String time = data.substring(stopIndexDate + 1, stopIndexTime);
        String dateTime = date + " " + time;

        return dateTime;
    }

    private void addServiceName(String serviceName){

        boolean isServiceCreated = false;
        for (int i = 0; i < serviceLogDataData.size(); i++) {
            if (serviceLogDataData.get(i).getName().equals(serviceName)) {
                isServiceCreated = true;
            }
        }

        if (!isServiceCreated) {
            ServiceLogData service = new ServiceLogData();
            service.setName(serviceName);
            serviceLogDataData.add(service);
        }
    }

    private void updateServiceInformation(String serviceName, String serviceId, String logDateTime) throws ParseException {

        if (pendingRequests.containsKey(serviceId)) {
            String startDateTimeService = pendingRequests.get(serviceId);
            String stopDateTimeService = logDateTime;
            long duration = timeCalc(startDateTimeService, stopDateTimeService);

            for (int i = 0; i < serviceLogDataData.size(); i++) {
                ServiceLogData service = serviceLogDataData.get(i);
                if (service.getName().equals(serviceName)) {
                    if (service.getMaxDuration() < duration)
                    {
                        service.setMaxDuration(duration);
                    }
                }
            }

            pendingRequests.remove(serviceId);
        }
        else{
            pendingRequests.put(serviceId, logDateTime);
            for (int i = 0; i < serviceLogDataData.size(); i++) {
                ServiceLogData service = serviceLogDataData.get(i);
                if (service.getName().equals(serviceName)) {
                    service.increaseRequestsCount();
                }
            }
        }
    }

    private long timeCalc(String start, String stop) throws ParseException {

        String date1 = start;
        String date2 = stop;

        String format = "yyyy-MM-dd HH:mm:ss,SSS";

        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Date dateObj1 = sdf.parse(date1);
        Date dateObj2 = sdf.parse(date2);

        long diff = dateObj2.getTime() - dateObj1.getTime();

        return diff;
    }

    public void printServiceReport(){
        for (int i = 0; i < serviceLogDataData.size(); i++) {
            System.out.println("Service Name: " + serviceLogDataData.get(i).getName());
            System.out.println("Max Duration: " + serviceLogDataData.get(i).getMaxDuration() + " ms");
            System.out.println("Request Count: " + serviceLogDataData.get(i).getRequestsCount());
            System.out.println("\n");
        }
    }
}
