
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kazuyuki.T
 */
public class EditCsvFile {
    public EditCsvFile(){
        
    }
    
    public void edit(String csvFile, int mode, int BATTLELOG_LIMIT_N){
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
            
            Map<Integer, GameLog> map = new HashMap<Integer, GameLog>(); 
            
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
                else if(mode == 2){ // 回復薬の個数毎に分割
                    int ptn = (int)ele[7];
                    outputFilename = new File(new String(dir + "/" + dir + "_" + ptn + "pt.csv"));
                }
                else if(mode == 3){ // 杖の個数毎に分割
                    int stn = (int)ele[9];
                    outputFilename = new File(new String(dir + "/" + dir + "_" + stn + "st.csv"));
                }
                else if(mode == 10){ // 同フロア中最初の BATTLELOG_LIMIT_N バトルのみ抽出
                    int gc = (int)ele[0]; // ゲームカウント
                    if(gc - gameCountBefore >= 2){ // 前回のゲームカウントとの差が2以上の時
                        gameCountBefore = gc - 1;
                        btCount = 0;
                    }
                    
                    // ゲーム回数比較，異なっているときのみ記録
                    if(gameCountBefore != gc){
                        outputFilename = new File(new String(dir + "/" + dir + "_extracted" + BATTLELOG_LIMIT_N + "bt.csv"));
                        btCount++;
                        addLine = true;
                    }
                    else{
                        addLine = false;
                    }
                    
                    // ゲーム回数更新
                    if(btCount == BATTLELOG_LIMIT_N){
                        gameCountBefore = gc;
                        btCount = 0;
                    }
                }
                else if(mode == 20){ // csv分割なし，矢の個数ごとにデータ数，正例数，ゲーム数，勝利数をカウント
                    int itemnun = (int)ele[8];
                    GameLog gamelog;
                    // mapに該当アイテム数が追加されていないとき
                    if(map.containsKey(itemnun) == false){
                        map.put(itemnun, new GameLog());
                    }
                    gamelog = map.get(itemnun);
                    
                    
                    gamelog.dataCounter(); // データ数をカウント
                    int gametf = (int)ele[21]; // 該当ゲームの勝敗の記録
                    if(gametf == 1) gamelog.posCounter(); // 正例数をカウント
                    
                    
                    int gc = (int)ele[0]; // ゲームカウント
                    // 前の記録ゲーム数と異なるとき，ゲーム数及び勝利数をカウント
                    if(gameCountBefore != gc){
                        gamelog.gameCounter();
                        if(gametf == 1) gamelog.winCounter();
                    }
                    
                    map.put(itemnun, gamelog); // 上書き
                    
                    gameCountBefore = gc;
                    
                    addLine = false;
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
            
            if(mode == 20){ // ゲーム数・勝率等の出力
                outputFilename = new File(new String(dir + "/" + dir + "_result.csv"));

                // ファイルが存在しないとき→ラベル付け，存在する→追記
                String resultIndex = "ar,datanum,posnum,negnum,gamenum,winnum,losenum";
                StringBuilder stb_main = new StringBuilder();
                
                for(int n = 0; map.containsKey(n) == true; n++){
                    GameLog gamelog = map.get(n);
                    stb_main.append(n);
                    stb_main.append("," + gamelog.getdataCount());
                    stb_main.append("," + gamelog.getposCount());
                    stb_main.append("," + gamelog.getnegCount());
                    stb_main.append("," + gamelog.getgameCount());
                    stb_main.append("," + gamelog.getwinCount());
                    stb_main.append("," + gamelog.getloseCount());
                    stb_main.append(System.getProperty("line.separator"));
                }
                
                if(outputFilename.exists() == false) Logger.OutputFileLog(outputFilename.toString(), new String(resultIndex + System.getProperty("line.separator")), true);
                Logger.OutputFileLog(outputFilename.toString(), new String(stb_main + System.getProperty("line.separator")), true);
            }
            
            br.close();
        } catch (IOException e) {
            System.out.println(e); // エラー吐き
            System.out.println("constractar error");
        }
    }
    
    public class GameLog{
        private int dataCount; // データ数
        private int posCount; // 正例数
        private int gameCount; // ゲーム回数
        private int winCount; // 勝利数
        
        public GameLog(){
            dataCount = 0;
            posCount = 0;
            gameCount = 0;
            winCount = 0;
        }
        
        public void dataCounter(){
            dataCount++;
        }
        public void posCounter(){
            posCount++;
        }
        public void gameCounter(){
            gameCount++;
        }
        public void winCounter(){
            winCount++;
        }
        
        public int getdataCount(){
            return dataCount;
        }
        public int getposCount(){
            return posCount;
        }
        public int getnegCount(){
            return dataCount - posCount;
        }
        public int getgameCount(){
            return gameCount;
        }
        public int getwinCount(){
            return winCount;
        }
        public int getloseCount(){
            return gameCount - winCount;
        }
    }
}
