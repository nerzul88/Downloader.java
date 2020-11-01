package nerzul88;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Integer.parseInt;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        List<String> urlStr = new ArrayList<String>();
        List<String> fileToSave = new ArrayList<String>();
        String urlFile = "/Users/nerzul88/Documents/DownloaderTest/links.txt"; //="links.txt"
        int numThread = 3;
        String outputFolder = "/Users/nerzul88/Documents/DownloaderTest/"; //="адрес для скачивания"
        try{
            numThread = parseInt(args[0]);
            outputFolder = args[1];
            urlFile = args[2];
        }catch (Exception e){
            System.out.println("Ошибка! Неверные входные параметры!" + e);
        }
        System.out.println("Параметры скачивания:\n" +
                "\nколичество потоков: " + numThread +
                "\nадрес для скачивания файлов: " + outputFolder +
                "\nпуть к файлу со списком ссылок: " + urlFile +
                "\nСодержимое файла: \n");
        List<String> lines = null;

        try {
            lines = Files.readAllLines(Paths.get(urlFile), StandardCharsets.UTF_8);
        }catch (IOException e){
            e.printStackTrace();
        }
        for(String line : lines){
            urlStr.add(line.split(" ")[0]);
            System.out.println(line);
        }
        System.out.println();

        long tm = System.currentTimeMillis();
        int totalSize = 0;
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < urlStr.size(); i++){
            threads.add(new DownloadThread(urlStr.get(i), outputFolder, fileToSave.get(i)));
        }
        for (Thread thread : threads){
            thread.start();
        }
        for (Thread thread : threads){
            thread.join();
        }
        for (int i = 0; i < urlStr.size(); i++) {
            totalSize += new File(outputFolder + fileToSave.get(i)).length() / 1024;
        }
        tm = System.currentTimeMillis() - tm;

        System.out.println("\nОбщее время загрузки файлов " + tm + " мс\n" +
                "Общий размер файлов " + totalSize + " kb \n" +
                "Средняя скорость загрузки " + totalSize * 1000/(int)tm + " kb/s");
    }
}
