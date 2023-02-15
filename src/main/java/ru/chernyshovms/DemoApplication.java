package ru.chernyshovms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        try {
            File file = new File("D://Stackoverflow.com-Posts/Posts.xml");
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            String line = reader.readLine();
            int i = 0;
            FileOutputStream outputStream = new FileOutputStream("D://Stackoverflow.com-Posts/test1.xml");
            while (i != 300) {
                System.out.println(line);
                // считываем остальные строки в цикле
                line = reader.readLine() + "\n";

                byte[] strToBytes = line.getBytes();
                outputStream.write(strToBytes);

                i += 1;
            }
            outputStream.write("</posts>".getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
