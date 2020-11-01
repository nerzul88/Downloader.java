package nerzul88;

import java.io.*;
import java.net.URL;

public class DownloadThread extends Thread{
    String urlStr, outputFolder, fileToSave;
    long[] t = new long[10];
    int i = 0;
    @Override
    public void run(){
        i++;
        try (BufferedInputStream in = new BufferedInputStream(new URL(urlStr).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(outputFolder + fileToSave)){
            System.out.println("Загружается файл: " + outputFolder + fileToSave);
            t[i] = System.currentTimeMillis();
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1){
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            t[i] = System.currentTimeMillis() - t[i];
            System.out.println("Файл: " + outputFolder + fileToSave + " размер " +
                    new File(outputFolder + fileToSave).length()/1024 + "kb " +
                    " успешно загружен за " + t[i] + " мс");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public DownloadThread(String urlStr, String outputFolder, String fileToSave){
        this.urlStr = urlStr;
        this.outputFolder = outputFolder;
        this.fileToSave = fileToSave;
    }
}
