
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
        String csvNames = "log_20181122_203209_bt.csv,log_20181122_203220_bt.csv,log_20181122_203448_bt.csv,log_20181122_204954_bt.csv";
        //String csvFile = "test1.csv";
        
        // mode 0 : 階層ごとに分割
        // mode 1 : 矢の個数毎に分割
        // mode 2 : 回復薬の個数毎に分割
        // mode 3 : 杖の個数毎に分割
        // mode10 : 同フロア中最初の BATTLELOG_LIMIT_N バトルのみ抽出
        // mode20 : csv分割なし，矢の個数ごとにデータ数，正例数，ゲーム数，勝利数をカウント
        
        int mode = 0;
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