
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kazuyuki.T
 */
public class Main {
    public static void main(String[] args) {
        // csv読み込みに使用
        String csvNames = "log_20181122_203453_bt_2flr.csv";
        //String csvFile = "test1.csv";
        int mode = 20;
        int btLimit = -1; // mode=10で必要，記録する同フロア戦闘数制限
        
        EditCsvFile ecf = new EditCsvFile();
        
        String[] csvName = csvNames.split(",", 0);
        for(int i=0; i < csvName.length; i++){
            System.out.println("start - " + csvName[i]);
            ecf.edit(csvName[i], mode, btLimit);
            System.out.println("end - " + csvName[i]);
        }
    }
}