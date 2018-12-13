
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
        String csvNames = 
                  "data_2f_gameclear_newData_onehot_allitem.csv";

        //String csvFile = "test1.csv";
        
        // mode 0 : 階層ごとに分割
        // mode 1 : 矢の個数毎に分割
        // mode 2 : 回復薬の個数毎に分割
        // mode 3 : 杖の個数毎に分割
        // mode10 : 同フロア中最初の BATTLELOG_LIMIT_N バトルのみ抽出
        // mode20 : csv分割なし，矢の個数ごとにデータ数，正例数，ゲーム数，勝利数をカウント
        // mode21 : csv分割なし，薬の個数ごとにデータ数，正例数，ゲーム数，勝利数をカウント
        // mode22 : csv分割なし，杖の個数ごとにデータ数，正例数，ゲーム数，勝利数をカウント
        // mode30 : csv分割なし，読み込みresult.csvのデータ数集計
        // 以降学習データの加工
        // mode40 : csv分割なし, normal -> all onehot
        // mode41 : csv分割なし, only pt onehot -> all item onehot
        // mode42 : csv分割なし, only ar onehot -> all item onehot
        // mode43 : csv分割なし, only st onehot -> all item onehot
        // mode49 : csv分割なし, ラベル整数化
        // mode50 : csv分割なし, only ar onehot -> + hp onehot(20刻み，5分割)
        // mode51 : csv分割なし, all item onehot -> + hp onehot(20刻み，5分割)
        // mode52 : csv分割なし, only ar onehot -> + hp onehot(10刻み，10分割)
        // mode53 : csv分割なし, all item onehot -> + hp onehot(10刻み，10分割)
        // mode60 : csv分割なし, all item onehot -> hp datanum,posrate count
        
        int mode = 60;
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