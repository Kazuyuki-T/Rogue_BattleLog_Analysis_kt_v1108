
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


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
        String csvNames = "log_20181017_003052_bt.csv,log_20181017_002824_bt.csv";
        //String csvFile = "test1.csv";
        int mode = 0;
        
        String[] csvName = csvNames.split(",", 0);
        for(int i=0; i < csvName.length; i++){
            System.out.println("start - " + csvName[i]);
            divideCsvFile(csvName[i], mode);
            System.out.println("end - " + csvName[i]);
        }
    }
    
    public static void divideCsvFile(String csvFile, int mode){
        // 格納フォルダの作成
        String[] csvName = csvFile.split("\\.", 0);
        File dir = new File(csvName[0]);
        
        if(dir.exists() == true) {
            // フォルダが存在するため，何もしない
        } else {
            if(dir.mkdir() == true) {
            // succece
            } else {
                System.out.println("フォルダの作成に失敗しました");
                System.exit(0);
            }
        }
        
        BufferedReader br = null; // 読み込み文章
        String line = ""; // 1行分
        String index = ""; // csvの先頭要素
        try { // 入力ファイル
            File inputFilename = new File(csvFile); // 入力ファイル
            File outputFilename = null; // 出力ファイル
            br = new BufferedReader(new FileReader(inputFilename)); // 入力ファイルの読み込みバッファー
            index = br.readLine(); // 1行ずつ読み込み
            line = br.readLine(); // 1行ずつ読み込み
            String[] lineSp; // コンマ分割文字列，配列格納
            double[] ele; // コンマ分割数字列，配列格納
            boolean addLine; // 追記するか否か
            
            int gameCountBefore = -1; // 前回記録したゲームカウント
            int btCount = 0; // 同じゲームカウントでの戦闘回数記録，2回以上で次のゲームカウントへ
            
            for(int row = 0; line != null; row++){
                lineSp = line.split(",", 0);
                ele = new double[22]; // gn,rn,dn,flr,hp,lv,sp,pt,ar,st,getflritem,getflrfd,getflrpt,getflrar,getflrst,getallitem,unk,st,br,cfc,nfc,gc
                addLine = true;
                
                for(int i = 0; i < lineSp.length ; i++){
                    ele[i] = Double.parseDouble(lineSp[i]);
                }
                
                if(mode == 0){ // 階層ごとに分割
                    int flr = (int)ele[3];
                    // ファイルが存在しないとき，初期作成＆インデックス追加
                    outputFilename = new File(new String(dir + "/" + dir + "_" + flr + "flr.csv"));
                }
                else if(mode == 1){ // 矢の個数毎に分割
                    int arn = (int)ele[8];
                    outputFilename = new File(new String(dir + "/" + dir + "_" + arn + "ar.csv"));
                }
                else if(mode == 10){ // 2f限定，最初の2回のみ抽出
                    int gc = (int)ele[0]; // ゲームカウント
                    if(gc - gameCountBefore >= 2){ // 前回のゲームカウントとの差が2以上の時
                        gameCountBefore = gc - 1;
                        btCount = 0;
                    }
                    
                    // ゲーム回数比較，異なっているときのみ記録
                    if(gameCountBefore != gc){
                        outputFilename = new File(new String(dir + "/" + dir + "_extracted1bt.csv"));
                        btCount++;
                        addLine = true;
                    }
                    else{
                        addLine = false;
                    }
                    
                    // ゲーム回数更新
                    if(btCount == 2){
                        gameCountBefore = gc;
                        btCount = 0;
                    }
                }
                else if(mode == 20){ // 勝率計算
                    int gc = (int)ele[0]; // ゲームカウント
                    if(gameCountBefore != gc){
                        
                        addLine = true;
                    }
                    else{
                        addLine = false;
                    }
                    
                }
                else{
                    addLine = false;
                }
                
                if(addLine == true){
                    // ファイルが存在しないとき→ラベル付け，存在する→追記
                    if(outputFilename.exists() == false) Logger.OutputFileLog(outputFilename.toString(), new String(index + System.getProperty("line.separator")), true);
                    Logger.OutputFileLog(outputFilename.toString(), new String(line + System.getProperty("line.separator")), true);
                }
                
                line = br.readLine();
                if(row % 1000 == 0 || line == null) System.out.println("row : " + row);
            }
            
            br.close();
        } catch (IOException e) {
            System.out.println(e); // エラー吐き
            System.out.println("constractar error");
        }
    }
}