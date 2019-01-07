
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

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
    // mode30用　result統合
    // 所持数ごとにカウント
    // アイテム所持数，データ数，正例数，負例数，ゲーム数，勝利数，敗北数
    // リストにより管理，新しい所持数ごとに追加
    Map<Integer, GameLog> totalResult; // アイテム数，対応成績
    
    public EditCsvFile(){
        totalResult = new HashMap<Integer, GameLog>();
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
            
            
            // modeが40番台のとき，indexはアイテムワンホットに
            if(mode == 41 || mode == 42 || mode == 43) index = "hp,lv,sp,pt_4over,pt_3,pt_2,pt_1,pt_0,ar_12over,ar_9,ar_6,ar_3,ar_0,st_4over,st_3,st_2,st_1,st_0,unknown area,stair,game clear";
            if(mode == 44 || mode == 45) index = "hp_100over,hp_90,hp_80,hp_70,hp_60,hp_50,hp_40,hp_30,hp_20,hp_10,hp_0,lv,sp,pt,ar,st,unknown area,stair,game clear";
            if(mode == 46) index = "hp_120over,hp_90,hp_60,hp_30,hp_1,hp_0,lv,sp,pt,ar,st,unknown area,stair,game clear";
            if(mode == 47) index = "hp_120over,hp_90,hp_60,hp_30,hp_1,hp_0,lv,sp,pt_4over,pt_3,pt_2,pt_1,pt_0,ar_12over,ar_9,ar_6,ar_3,ar_0,st_4over,st_3,st_2,st_1,st_0,unknown area,stair,game clear";
            if(mode == 50) index = "hp_100over,hp_80,hp_60,hp_40,hp_20,hp_0,lv,sp,pt,ar_12over,ar_9,ar_6,ar_3,ar_0,st,unknown area,stair,game clear";
            if(mode == 51) index = "hp_100over,hp_80,hp_60,hp_40,hp_20,hp_0,lv,sp,pt_4over,pt_3,pt_2,pt_1,pt_0,ar_12over,ar_9,ar_6,ar_3,ar_0,st_4over,st_3,st_2,st_1,st_0,unknown area,stair,game clear";
            if(mode == 52) index = "hp_100over,hp_90,hp_80,hp_70,hp_60,hp_50,hp_40,hp_30,hp_20,hp_10,hp_0,lv,sp,pt,ar_12over,ar_9,ar_6,ar_3,ar_0,st,unknown area,stair,game clear";
            if(mode == 53) index = "hp_100over,hp_90,hp_80,hp_70,hp_60,hp_50,hp_40,hp_30,hp_20,hp_10,hp_0,lv,sp,pt_4over,pt_3,pt_2,pt_1,pt_0,ar_12over,ar_9,ar_6,ar_3,ar_0,st_4over,st_3,st_2,st_1,st_0,unknown area,stair,game clear";
            if(mode == 54) index = "hp_100over,hp_90,hp_80,hp_70,hp_60,hp_50,hp_40,hp_30,hp_20,hp_10,hp_0,lv,sp,pt,ar_12over,ar_9,ar_6,ar_3,ar_0,st,unknown area,stair,game clear";
            if(mode == 55) index = "hp_120over,hp_90,hp_60,hp_30,hp_1,hp_0,lv,sp,pt,ar_12over,ar_9,ar_6,ar_3,ar_0,st,unknown area,stair,game clear";
            if(mode == 56) index = "hp_120over,hp_90,hp_60,hp_30,hp_1,hp_0,lv,sp,pt_4over,pt_3,pt_2,pt_1,pt_0,ar_12over,ar_9,ar_6,ar_3,ar_0,st_4over,st_3,st_2,st_1,st_0,unknown area,stair,game clear";
            if(mode == 70) index = "hp_120over,hp_90,hp_60,hp_30,hp_1,hp_0,lv,sp_100over,sp_80,sp_60,sp_40,sp_20,sp_0,pt_4over,pt_3,pt_2,pt_1,pt_0,ar_12over,ar_9,ar_6,ar_3,ar_0,st_4over,st_3,st_2,st_1,st_0,unknown area,stair,game clear";
            if(mode == 71) index = "hp_120over,hp_90,hp_60,hp_30,hp_1,hp_0,lv,sp_100over,sp_75,sp_50,sp_25,sp_0,pt_4over,pt_3,pt_2,pt_1,pt_0,ar_12over,ar_9,ar_6,ar_3,ar_0,st_4over,st_3,st_2,st_1,st_0,unknown area,stair,game clear";
            if(mode == 72) index = "hp_120over,hp_90,hp_60,hp_30,hp_1,hp_0,lv,sp_120over,sp_90,sp_60,sp_30,sp_0,pt_4over,pt_3,pt_2,pt_1,pt_0,ar_12over,ar_9,ar_6,ar_3,ar_0,st_4over,st_3,st_2,st_1,st_0,unknown area,stair,game clear";
            if(mode == 73) index = "hp_120over,hp_90,hp_60,hp_30,hp_1,hp_0,lv,sp_120over,sp_90,sp_60,sp_30,sp_1,sp_0,pt_4over,pt_3,pt_2,pt_1,pt_0,ar_12over,ar_9,ar_6,ar_3,ar_0,st_4over,st_3,st_2,st_1,st_0,unknown area,stair,game clear";

                        
            int gameCountBefore = -1; // 前回記録したゲームカウント
            int btCount = 0; // 同じゲームカウントでの戦闘回数記録，2回以上で次のゲームカウントへ
            
            Map<Integer, GameLog> result = new HashMap<Integer, GameLog>(); // 各アイテム数ごとにデータ数等集計
            
            for(int row = 0; line != null; row++){
                lineSp = line.split(",", 0);
                ele = new double[40]; // gn,rn,dn,flr,hp,lv,sp,pt,ar,st,getflritem,getflrfd,getflrpt,getflrar,getflrst,getallitem,unk,st,br,cfc,nfc,gc
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
                    if(result.containsKey(itemnun) == false){
                        result.put(itemnun, new GameLog());
                    }
                    gamelog = result.get(itemnun);
                    
                    
                    gamelog.dataCounter(); // データ数をカウント
                    int gametf = (int)ele[21]; // 該当ゲームの勝敗の記録
                    if(gametf == 1)    gamelog.posCounter(); // 正例数をカウント
                    if(gametf == -1)   gamelog.negCounter(); // 負例数をカウント
                    
                    
                    int gc = (int)ele[0]; // ゲームカウント
                    // 前の記録ゲーム数と異なるとき，ゲーム数及び勝利数をカウント
                    if(gameCountBefore != gc){
                        gamelog.gameCounter();
                        if(gametf == 1) gamelog.winCounter();
                        if(gametf == -1)gamelog.loseCounter();
                    }
                    
                    result.put(itemnun, gamelog); // 上書き
                    
                    gameCountBefore = gc;
                    
                    addLine = false;
                }
                else if(mode == 21){ // csv分割なし，回復薬の個数ごとにデータ数，正例数，ゲーム数，勝利数をカウント
                    int itemnun = (int)ele[7];
                    GameLog gamelog;
                    // mapに該当アイテム数が追加されていないとき
                    if(result.containsKey(itemnun) == false){
                        result.put(itemnun, new GameLog());
                    }
                    gamelog = result.get(itemnun);
                    
                    
                    gamelog.dataCounter(); // データ数をカウント
                    int gametf = (int)ele[21]; // 該当ゲームの勝敗の記録
                    if(gametf == 1)    gamelog.posCounter(); // 正例数をカウント
                    if(gametf == -1)   gamelog.negCounter(); // 負例数をカウント
                    
                    
                    int gc = (int)ele[0]; // ゲームカウント
                    // 前の記録ゲーム数と異なるとき，ゲーム数及び勝利数をカウント
                    if(gameCountBefore != gc){
                        gamelog.gameCounter();
                        if(gametf == 1) gamelog.winCounter();
                        if(gametf == -1)gamelog.loseCounter();
                    }
                    
                    result.put(itemnun, gamelog); // 上書き
                    
                    gameCountBefore = gc;
                    
                    addLine = false;
                }
                else if(mode == 22){ // csv分割なし，杖の個数ごとにデータ数，正例数，ゲーム数，勝利数をカウント
                    int itemnun = (int)ele[9];
                    GameLog gamelog;
                    // mapに該当アイテム数が追加されていないとき
                    if(result.containsKey(itemnun) == false){
                        result.put(itemnun, new GameLog());
                    }
                    gamelog = result.get(itemnun);
                    
                    
                    gamelog.dataCounter(); // データ数をカウント
                    int gametf = (int)ele[21]; // 該当ゲームの勝敗の記録
                    if(gametf == 1)    gamelog.posCounter(); // 正例数をカウント
                    if(gametf == -1)   gamelog.negCounter(); // 負例数をカウント
                    
                    
                    int gc = (int)ele[0]; // ゲームカウント
                    // 前の記録ゲーム数と異なるとき，ゲーム数及び勝利数をカウント
                    if(gameCountBefore != gc){
                        gamelog.gameCounter();
                        if(gametf == 1) gamelog.winCounter();
                        if(gametf == -1)gamelog.loseCounter();
                    }
                    
                    result.put(itemnun, gamelog); // 上書き
                    
                    gameCountBefore = gc;
                    
                    addLine = false;
                }
                else if(mode == 30){ // resultのデータ数等集計
                    int itemnun = (int)ele[0];
                    GameLog gamelog;
                    // mapに該当アイテム数が追加されていないとき
                    if(totalResult.containsKey(itemnun) == false){
                        totalResult.put(itemnun, new GameLog());
                    }
                    gamelog = totalResult.get(itemnun);
                    
                    //System.out.println(itemnun);
                    
                    gamelog.adddataCount((int)ele[1]);
                    gamelog.addposCount((int)ele[2]);
                    gamelog.addnegCount((int)ele[3]);
                    gamelog.addgameCount((int)ele[4]);
                    gamelog.addwinCount((int)ele[5]);
                    gamelog.addloseCount((int)ele[6]);
                    
                    totalResult.put(itemnun, gamelog); // 上書き
                    
                    addLine = false;
                }
                
                // mode40 : csv分割なし, normal -> all onehot
                else if(mode == 40){
                    double[] eleplus = new double[21];
                    
                    // eleからeleplus作成
                    // i : 変換前
                    // j : 変換後
                    for(int i=0, j=0; i<9; i++){
                        if(i == 3){
                            // 所持数 -> ワンホット表現
                            int[] tmpOnehotPt = getOnehotArrayPtorSt((int)ele[i]);
                            for(int k=0; k<tmpOnehotPt.length; k++, j++){
                                eleplus[j] = tmpOnehotPt[k];
                            }
                        }
                        else if(i == 4){
                            double[] tmpOnehotAr = getOnehotArrayAr((int)ele[i]);
                            for(int k=0; k<tmpOnehotAr.length; k++, j++){
                                eleplus[j] = tmpOnehotAr[k];
                            }
                        }
                        else if(i == 5){
                            int[] tmpOnehotSt = getOnehotArrayPtorSt((int)ele[i]);
                            for(int k=0; k<tmpOnehotSt.length; k++, j++){
                                eleplus[j] = tmpOnehotSt[k];
                            }
                        }
                        else{
                            eleplus[j++] = ele[i];
                        }
                    }
                    
                    // eleplusからnewline作成
                    StringBuilder stb_line_ItemOnehot = new StringBuilder(); 
                    for(int j = 0; j < eleplus.length; j++){
                        if(j != eleplus.length - 1) stb_line_ItemOnehot.append(eleplus[j] + ",");
                        else                        stb_line_ItemOnehot.append((int)eleplus[j]);
                    }
                    
                    // lineの上書き
                    line = new String(stb_line_ItemOnehot);
                    
                    outputFilename = new File(new String(dir + "/" + dir + "_Mode" + mode + ".csv"));
                }
                
                // mode41 : csv分割なし, only pt onehot -> all item onehot
                else if(mode == 41){
                    double[] eleplus = new double[21];
                    
                    for(int i=0, j=0; i<13; i++){
                        if(i == 8){
                            double[] tmpOnehotAr = getOnehotArrayAr((int)ele[i]);
                            for(int k=0; k<tmpOnehotAr.length; k++, j++){
                                eleplus[j] = tmpOnehotAr[k];
                            }
                        }
                        else if(i == 9){
                            int[] tmpOnehotSt = getOnehotArrayPtorSt((int)ele[i]);
                            for(int k=0; k<tmpOnehotSt.length; k++, j++){
                                eleplus[j] = tmpOnehotSt[k];
                            }
                        }
                        else{
                            eleplus[j++] = ele[i];
                        }
                    }
                    
                    StringBuilder stb_line_ItemOnehot = new StringBuilder(); 
                    for(int j = 0; j < eleplus.length; j++){
                        if(j != eleplus.length - 1) stb_line_ItemOnehot.append(eleplus[j] + ",");
                        else                        stb_line_ItemOnehot.append((int)eleplus[j]);
                    }
                    line = new String(stb_line_ItemOnehot);
                    
                    outputFilename = new File(new String(dir + "/" + dir + "_Mode" + mode + ".csv"));
                }
                
                // mode42 : csv分割なし, only ar onehot -> all item onehot
                else if(mode == 42){
                    double[] eleplus = new double[21];
                    
                    for(int i=0, j=0; i<13; i++){
                        if(i == 3){
                            // 所持数 -> ワンホット表現
                            int[] tmpOnehotPt = getOnehotArrayPtorSt((int)ele[i]);
                            for(int k=0; k<tmpOnehotPt.length; k++, j++){
                                eleplus[j] = tmpOnehotPt[k];
                            }
                        }
                        else if(i == 9){
                            int[] tmpOnehotSt = getOnehotArrayPtorSt((int)ele[i]);
                            for(int k=0; k<tmpOnehotSt.length; k++, j++){
                                eleplus[j] = tmpOnehotSt[k];
                            }
                        }
                        else{
                            eleplus[j++] = ele[i];
                        }
                    }
                    
                    StringBuilder stb_line_ItemOnehot = new StringBuilder(); 
                    for(int j = 0; j < eleplus.length; j++){
                        if(j != eleplus.length - 1) stb_line_ItemOnehot.append(eleplus[j] + ",");
                        else                        stb_line_ItemOnehot.append((int)eleplus[j]);
                    }
                    line = new String(stb_line_ItemOnehot);
                    
                    outputFilename = new File(new String(dir + "/" + dir + "_Mode" + mode + ".csv"));
                }
                
                // mode43 : csv分割なし, only st onehot -> all item onehot
                else if(mode == 43){
                    double[] eleplus = new double[21];
                    
                    for(int i=0, j=0; i<13; i++){
                        if(i == 3){
                            // 所持数 -> ワンホット表現
                            int[] tmpOnehotPt = getOnehotArrayPtorSt((int)ele[i]);
                            for(int k=0; k<tmpOnehotPt.length; k++, j++){
                                eleplus[j] = tmpOnehotPt[k];
                            }
                        }
                        else if(i == 4){
                            double[] tmpOnehotAr = getOnehotArrayAr((int)ele[i]);
                            for(int k=0; k<tmpOnehotAr.length; k++, j++){
                                eleplus[j] = tmpOnehotAr[k];
                            }
                        }
                        else{
                            eleplus[j++] = ele[i];
                        }
                    }
                    
                    StringBuilder stb_line_ItemOnehot = new StringBuilder(); 
                    for(int j = 0; j < eleplus.length; j++){
                        if(j != eleplus.length - 1) stb_line_ItemOnehot.append(eleplus[j] + ",");
                        else                        stb_line_ItemOnehot.append((int)eleplus[j]);
                    }
                    line = new String(stb_line_ItemOnehot);
                    
                    outputFilename = new File(new String(dir + "/" + dir + "_Mode" + mode + ".csv"));
                }
                
                // mode44 : csv分割なし, normal(9col) -> hp onehot(10刻み，10分割)(19col)
                else if(mode == 44){
                    double[] eleplus = new double[19];
                    
                    for(int i=0, j=0; i<9; i++){
                        if(i == 0){
                            // hp -> ワンホット表現
                            double[] tmpOnehotHp = getOnehotArrayHpDiv10((int)ele[0]);
                            for(int k=0; k<tmpOnehotHp.length; k++, j++){
                                eleplus[j] = tmpOnehotHp[k];
                            }
                        }
                        else{
                            eleplus[j++] = ele[i];
                        }
                    }
                    
                    StringBuilder stb_line_HpOnehot = new StringBuilder(); 
                    for(int j = 0; j < eleplus.length; j++){
                        if(j != eleplus.length - 1) stb_line_HpOnehot.append(eleplus[j] + ",");
                        else                        stb_line_HpOnehot.append((int)eleplus[j]);
                    }
                    line = new String(stb_line_HpOnehot);
                    
                    outputFilename = new File(new String(dir + "/" + dir + "_Mode" + mode + ".csv"));
                }
                
                // mode45 : csv分割なし, normal(9col) -> hp onehot(10刻み，10分割)(19col), except hp==0
                else if(mode == 45){
                    double[] eleplus = new double[19];
                    
                    // hpが0の時をスキップ
                    if((int)ele[0] != 0){
                        for(int i=0, j=0; i<9; i++){
                            if(i == 0){
                                // hp -> ワンホット表現
                                double[] tmpOnehotHp = getOnehotArrayHpDiv10((int)ele[0]);
                                for(int k=0; k<tmpOnehotHp.length; k++, j++){
                                    eleplus[j] = tmpOnehotHp[k];
                                }
                            }
                            else{
                                eleplus[j++] = ele[i];
                            }
                        }

                        StringBuilder stb_line_HpOnehot = new StringBuilder(); 
                        for(int j = 0; j < eleplus.length; j++){
                            if(j != eleplus.length - 1) stb_line_HpOnehot.append(eleplus[j] + ",");
                            else                        stb_line_HpOnehot.append((int)eleplus[j]);
                        }
                        line = new String(stb_line_HpOnehot);

                        outputFilename = new File(new String(dir + "/" + dir + "_Mode" + mode + ".csv"));
                    }
                    else{
                        addLine = false;
                    }
                }
                
                // mode46 : csv分割なし, normal(9col) -> hp onehot(30刻み+1，6分割[0,1,30,60,90,120])(14col)
                else if(mode == 46){
                    double[] eleplus = new double[14];
                    
                    for(int i=0, j=0; i<9; i++){
                        if(i == 0){
                            // hp -> ワンホット表現
                            double[] tmpOnehotHp = getOnehotArrayHpSpDiv04p1((int)ele[0]);
                            for(int k=0; k<tmpOnehotHp.length; k++, j++){
                                eleplus[j] = tmpOnehotHp[k];
                            }
                        }
                        else{
                            eleplus[j++] = ele[i];
                        }
                    }
                    
                    StringBuilder stb_line_HpOnehot = new StringBuilder(); 
                    for(int j = 0; j < eleplus.length; j++){
                        if(j != eleplus.length - 1) stb_line_HpOnehot.append(eleplus[j] + ",");
                        else                        stb_line_HpOnehot.append((int)eleplus[j]);
                    }
                    line = new String(stb_line_HpOnehot);
                    
                    outputFilename = new File(new String(dir + "/" + dir + "_Mode" + mode + ".csv"));
                }
                
                // mode49 : csv分割なし, ラベル整数化
                else if(mode == 49){
                    outputFilename = new File(new String(dir + "/" + dir + "_Mode" + mode + ".csv"));
                }
                
                // mode50 : csv分割なし, only ar onehot(13col) -> + hp onehot(20刻み，5分割)(18col)
                else if(mode == 50){
                    double[] eleplus = new double[18];
                    
                    for(int i=0, j=0; i<13; i++){
                        if(i == 0){
                            // hp -> ワンホット表現
                            double[] tmpOnehotHp = getOnehotArrayHpSpDiv05((int)ele[0]);
                            for(int k=0; k<tmpOnehotHp.length; k++, j++){
                                eleplus[j] = tmpOnehotHp[k];
                            }
                        }
                        else{
                            eleplus[j++] = ele[i];
                        }
                    }
                    
                    StringBuilder stb_line_ItemOnehot = new StringBuilder(); 
                    for(int j = 0; j < eleplus.length; j++){
                        if(j != eleplus.length - 1) stb_line_ItemOnehot.append(eleplus[j] + ",");
                        else                        stb_line_ItemOnehot.append((int)eleplus[j]);
                    }
                    line = new String(stb_line_ItemOnehot);
                    
                    outputFilename = new File(new String(dir + "/" + dir + "_hpOnehot.csv"));
                }
                else if(mode == 51){
                    double[] eleplus = new double[26];
                    
                    for(int i=0, j=0; i<21; i++){
                        if(i == 0){
                            // hp -> ワンホット表現
                            double[] tmpOnehotHp = getOnehotArrayHpSpDiv05((int)ele[0]);
                            for(int k=0; k<tmpOnehotHp.length; k++, j++){
                                eleplus[j] = tmpOnehotHp[k];
                            }
                        }
                        else{
                            eleplus[j++] = ele[i];
                        }
                    }
                    
                    StringBuilder stb_line_ItemOnehot = new StringBuilder(); 
                    for(int j = 0; j < eleplus.length; j++){
                        if(j != eleplus.length - 1) stb_line_ItemOnehot.append(eleplus[j] + ",");
                        else                        stb_line_ItemOnehot.append((int)eleplus[j]);
                    }
                    line = new String(stb_line_ItemOnehot);
                    
                    outputFilename = new File(new String(dir + "/" + dir + "_hpOnehot.csv"));
                }
                else if(mode == 52){
                    double[] eleplus = new double[23];
                    
                    for(int i=0, j=0; i<13; i++){
                        if(i == 0){
                            // hp -> ワンホット表現
                            double[] tmpOnehotHp = getOnehotArrayHpDiv10((int)ele[0]);
                            for(int k=0; k<tmpOnehotHp.length; k++, j++){
                                eleplus[j] = tmpOnehotHp[k];
                            }
                        }
                        else{
                            eleplus[j++] = ele[i];
                        }
                    }
                    
                    StringBuilder stb_line_ItemOnehot = new StringBuilder(); 
                    for(int j = 0; j < eleplus.length; j++){
                        if(j != eleplus.length - 1) stb_line_ItemOnehot.append(eleplus[j] + ",");
                        else                        stb_line_ItemOnehot.append((int)eleplus[j]);
                    }
                    line = new String(stb_line_ItemOnehot);
                    
                    outputFilename = new File(new String(dir + "/" + dir + "_hpOnehot.csv"));
                }
                else if(mode == 54){
                    double[] eleplus = new double[23];
                    
                    // hpが0の時をスキップ
                    if((int)ele[0] != 0){
                        for(int i=0, j=0; i<13; i++){
                            if(i == 0){
                                // hp -> ワンホット表現
                                double[] tmpOnehotHp = getOnehotArrayHpDiv10((int)ele[0]);
                                for(int k=0; k<tmpOnehotHp.length; k++, j++){
                                    eleplus[j] = tmpOnehotHp[k];
                                }
                            }
                            else{
                                eleplus[j++] = ele[i];
                            }
                        }

                        StringBuilder stb_line_ItemOnehot = new StringBuilder(); 
                        for(int j = 0; j < eleplus.length; j++){
                            if(j != eleplus.length - 1) stb_line_ItemOnehot.append(eleplus[j] + ",");
                            else                        stb_line_ItemOnehot.append((int)eleplus[j]);
                        }
                        line = new String(stb_line_ItemOnehot);

                        outputFilename = new File(new String(dir + "/" + dir + "_hpOnehot.csv"));
                    }
                    else{
                        addLine = false;
                    }
                }
                else if(mode == 55){
                    double[] eleplus = new double[18];
                    
                    for(int i=0, j=0; i<13; i++){
                        if(i == 0){
                            // hp -> ワンホット表現
                            double[] tmpOnehotHp = getOnehotArrayHpSpDiv04p1((int)ele[0]);
                            for(int k=0; k<tmpOnehotHp.length; k++, j++){
                                eleplus[j] = tmpOnehotHp[k];
                            }
                        }
                        else{
                            eleplus[j++] = ele[i];
                        }
                    }
                    
                    StringBuilder stb_line_ItemOnehot = new StringBuilder(); 
                    for(int j = 0; j < eleplus.length; j++){
                        if(j != eleplus.length - 1) stb_line_ItemOnehot.append(eleplus[j] + ",");
                        else                        stb_line_ItemOnehot.append((int)eleplus[j]);
                    }
                    line = new String(stb_line_ItemOnehot);
                    
                    outputFilename = new File(new String(dir + "/" + dir + "_hpOnehot.csv"));
                }
                else if(mode == 56){
                    double[] eleplus = new double[26];
                    
                    for(int i=0, j=0; i<21; i++){
                        if(i == 0){
                            // hp -> ワンホット表現
                            double[] tmpOnehotHp = getOnehotArrayHpSpDiv04p1((int)ele[0]);
                            for(int k=0; k<tmpOnehotHp.length; k++, j++){
                                eleplus[j] = tmpOnehotHp[k];
                            }
                        }
                        else{
                            eleplus[j++] = ele[i];
                        }
                    }
                    
                    StringBuilder stb_line = new StringBuilder(); 
                    for(int j = 0; j < eleplus.length; j++){
                        if(j != eleplus.length - 1) stb_line.append(eleplus[j] + ",");
                        else                        stb_line.append((int)eleplus[j]);
                    }
                    line = new String(stb_line);
                    
                    outputFilename = new File(new String(dir + "/" + dir + "_hpOnehot.csv"));
                }
                else if(mode == 60){
                    int hpval = (ele[0] > 100) ? 100 : (int)(ele[0] / 10.0 + 0.5) * 10;
                    //System.out.println(ele[0] + "->" + hpval);
                    GameLog gamelog;
                    // mapに該当アイテム数が追加されていないとき
                    if(result.containsKey(hpval) == false){
                        result.put(hpval, new GameLog());
                    }
                    gamelog = result.get(hpval);
                    
                    gamelog.dataCounter(); // データ数をカウント
                    int gametf = (int)ele[20]; // 該当ゲームの勝敗の記録
                    if(gametf == 1)    gamelog.posCounter(); // 正例数をカウント
                    else if(gametf == -1)   gamelog.negCounter(); // 負例数をカウント
                    else System.out.println("error!");
                    
                    result.put(hpval, gamelog); // 上書き
                    
                    addLine = false;
                }
                else if(mode == 61){
                    int hpval = (ele[0] > 100) ? 100 : (int)(ele[0] / 10.0 + 0.5) * 10;
                    //System.out.println(ele[0] + "->" + hpval);
                    GameLog gamelog;
                    // mapに該当アイテム数が追加されていないとき
                    if(result.containsKey(hpval) == false){
                        result.put(hpval, new GameLog());
                    }
                    gamelog = result.get(hpval);
                    
                    gamelog.dataCounter(); // データ数をカウント
                    int gametf = (int)ele[12]; // 該当ゲームの勝敗の記録
                    if(gametf == 1)    gamelog.posCounter(); // 正例数をカウント
                    else if(gametf == -1)   gamelog.negCounter(); // 負例数をカウント
                    else System.out.println("error!");
                    
                    result.put(hpval, gamelog); // 上書き
                    
                    addLine = false;
                }
                else if(mode == 62){
                    int label = (int)(ele[18]);
                    GameLog gamelog;
                    if(result.containsKey(label) == false){
                        result.put(label, new GameLog());
                    }
                    gamelog = result.get(label);
                    
                    gamelog.dataCounter(); // データ数をカウント
                    int gametf = (int)ele[18]; // 該当ゲームの勝敗の記録
                    if(gametf == 1)    gamelog.posCounter(); // 正例数をカウント
                    else if(gametf == -1)   gamelog.negCounter(); // 負例数をカウント
                    else System.out.println("error!");
                    
                    result.put(label, gamelog); // 上書き
                    
                    addLine = false;
                }
                else if(mode == 63){
                    int label = (int)(ele[13]);
                    GameLog gamelog;
                    if(result.containsKey(label) == false){
                        result.put(label, new GameLog());
                    }
                    gamelog = result.get(label);
                    
                    gamelog.dataCounter(); // データ数をカウント
                    int gametf = (int)ele[13]; // 該当ゲームの勝敗の記録
                    if(gametf == 1)    gamelog.posCounter(); // 正例数をカウント
                    else if(gametf == -1)   gamelog.negCounter(); // 負例数をカウント
                    else System.out.println("error!");
                    
                    result.put(label, gamelog); // 上書き
                    
                    addLine = false;
                }
                
                // mode70 : csv分割なし, hp(div4p1) + all item onehot (26col) -> + sp([0,20,40,60,80,100]) (31col)
                else if(mode == 70){
                    double[] eleplus = new double[31];
                    
                    // eleからeleplus作成
                    // i : 変換前
                    // j : 変換後
                    for(int i=0, j=0; i<26; i++){
                        if(i == 7){
                            double[] tmpOnehotSp = getOnehotArrayHpSpDiv05((int)ele[i]);
                            for(int k=0; k<tmpOnehotSp.length; k++, j++){
                                eleplus[j] = tmpOnehotSp[k];
                            }
                        }
                        else{
                            //System.out.println("i:" + i + ", j:" + j);
                            eleplus[j++] = ele[i];
                        }
                    }
                    
                    // eleplusからnewline作成
                    StringBuilder stb_line_ItemOnehot = new StringBuilder(); 
                    for(int j = 0; j < eleplus.length; j++){
                        if(j != eleplus.length - 1) stb_line_ItemOnehot.append(eleplus[j] + ",");
                        else                        stb_line_ItemOnehot.append((int)eleplus[j]);
                    }
                    
                    // lineの上書き
                    line = new String(stb_line_ItemOnehot);
                    
                    outputFilename = new File(new String(dir + "/" + dir + "_Mode" + mode + ".csv"));
                }
                
                // mode71 : csv分割なし, hp(div4p1) + all item onehot (26col) -> + sp([0,25,50,75,100]) (30col)
                else if(mode == 71){
                    double[] eleplus = new double[30];
                    
                    // eleからeleplus作成
                    // i : 変換前
                    // j : 変換後
                    for(int i=0, j=0; i<26; i++){
                        if(i == 7){
                            double[] tmpOnehotSp = getOnehotArrayHpSpDiv04((int)ele[i]);
                            for(int k=0; k<tmpOnehotSp.length; k++, j++){
                                eleplus[j] = tmpOnehotSp[k];
                            }
                        }
                        else{
                            //System.out.println("i:" + i + ", j:" + j);
                            eleplus[j++] = ele[i];
                        }
                    }
                    
                    // eleplusからnewline作成
                    StringBuilder stb_line_ItemOnehot = new StringBuilder(); 
                    for(int j = 0; j < eleplus.length; j++){
                        if(j != eleplus.length - 1) stb_line_ItemOnehot.append(eleplus[j] + ",");
                        else                        stb_line_ItemOnehot.append((int)eleplus[j]);
                    }
                    
                    // lineの上書き
                    line = new String(stb_line_ItemOnehot);
                    
                    outputFilename = new File(new String(dir + "/" + dir + "_Mode" + mode + ".csv"));
                }
                
                // mode72 : csv分割なし, hp(div4p1) + all item onehot (26col) -> + sp([0,30,60,90,120]) (30col)
                else if(mode == 72){
                    double[] eleplus = new double[30];
                    
                    // eleからeleplus作成
                    // i : 変換前
                    // j : 変換後
                    for(int i=0, j=0; i<26; i++){
                        if(i == 7){
                            double[] tmpOnehotSp = getOnehotArrayHpSpDiv04p0((int)ele[i]);
                            for(int k=0; k<tmpOnehotSp.length; k++, j++){
                                eleplus[j] = tmpOnehotSp[k];
                            }
                        }
                        else{
                            //System.out.println("i:" + i + ", j:" + j);
                            eleplus[j++] = ele[i];
                        }
                    }
                    
                    // eleplusからnewline作成
                    StringBuilder stb_line_ItemOnehot = new StringBuilder(); 
                    for(int j = 0; j < eleplus.length; j++){
                        if(j != eleplus.length - 1) stb_line_ItemOnehot.append(eleplus[j] + ",");
                        else                        stb_line_ItemOnehot.append((int)eleplus[j]);
                    }
                    
                    // lineの上書き
                    line = new String(stb_line_ItemOnehot);
                    
                    outputFilename = new File(new String(dir + "/" + dir + "_Mode" + mode + ".csv"));
                }
                
                // mode73 : csv分割なし, hp(div4p1) + all item onehot (26col) -> + sp([0,1,30,60,90,120]) (31col)
                else if(mode == 73){
                    double[] eleplus = new double[31];
                    
                    // eleからeleplus作成
                    // i : 変換前
                    // j : 変換後
                    for(int i=0, j=0; i<26; i++){
                        if(i == 7){
                            double[] tmpOnehotSp = getOnehotArrayHpSpDiv04p1((int)ele[i]);
                            for(int k=0; k<tmpOnehotSp.length; k++, j++){
                                eleplus[j] = tmpOnehotSp[k];
                            }
                        }
                        else{
                            //System.out.println("i:" + i + ", j:" + j);
                            eleplus[j++] = ele[i];
                        }
                    }
                    
                    // eleplusからnewline作成
                    StringBuilder stb_line_ItemOnehot = new StringBuilder(); 
                    for(int j = 0; j < eleplus.length; j++){
                        if(j != eleplus.length - 1) stb_line_ItemOnehot.append(eleplus[j] + ",");
                        else                        stb_line_ItemOnehot.append((int)eleplus[j]);
                    }
                    
                    // lineの上書き
                    line = new String(stb_line_ItemOnehot);
                    
                    outputFilename = new File(new String(dir + "/" + dir + "_Mode" + mode + ".csv"));
                }
                
                else{
                    addLine = false;
                    System.out.println("Mode [" + mode + "] is not found.");
                    System.exit(0);
                }
                
                if(addLine == true){
                    // ファイルが存在しないとき→ラベル付け，存在する→追記
                    if(outputFilename.exists() == false) Logger.OutputFileLog(outputFilename.toString(), new String(index + System.getProperty("line.separator")), true);
                    Logger.OutputFileLog(outputFilename.toString(), new String(line + System.getProperty("line.separator")), true);
                }
                
                line = br.readLine();
                if(row % 1000 == 0 || line == null) System.out.println("row : " + row);
            }
            
            if(mode == 20 || mode == 21 || mode == 22 || mode == 60 || mode == 61 || mode == 62 || mode == 63){ // ゲーム数・勝率等の出力
                outputFilename = new File(new String(dir + "/" + dir + "_result.csv"));

                // ファイルが存在しないとき→ラベル付け，存在する→追記
                String resultIndex = "num,datanum,posnum,negnum,gamenum,winnum,losenum";
                StringBuilder stb_main = new StringBuilder();
                
                for(Map.Entry<Integer, GameLog> entry : result.entrySet()){
                    GameLog gamelog = entry.getValue();
                    stb_main.append(entry.getKey());
                    stb_main.append("," + gamelog.getdataCount());
                    stb_main.append("," + gamelog.getposCount());
                    stb_main.append("," + gamelog.getnegCount());
                    stb_main.append("," + gamelog.getgameCount());
                    stb_main.append("," + gamelog.getwinCount());
                    stb_main.append("," + gamelog.getloseCount());
                    stb_main.append(System.getProperty("line.separator"));
                }
                System.out.println(new String(stb_main));
                
                if(outputFilename.exists() == false) Logger.OutputFileLog(outputFilename.toString(), new String(resultIndex + System.getProperty("line.separator")), true);
                Logger.OutputFileLog(outputFilename.toString(), new String(stb_main), true);
            }
            
            if(mode == 30){
                // ファイルが存在しないとき→ラベル付け，存在する→追記
                String resultIndex = "ar,datanum,posnum,negnum,gamenum,winnum,losenum";
                StringBuilder stb_main = new StringBuilder();
                
                for(int n = 0; totalResult.containsKey(n) == true; n++){
                    GameLog gamelog = totalResult.get(n);
                    stb_main.append(n);
                    stb_main.append("," + gamelog.getdataCount());
                    stb_main.append("," + gamelog.getposCount());
                    stb_main.append("," + gamelog.getnegCount());
                    stb_main.append("," + gamelog.getgameCount());
                    stb_main.append("," + gamelog.getwinCount());
                    stb_main.append("," + gamelog.getloseCount());
                    stb_main.append(System.getProperty("line.separator"));
                }
                
                System.out.println(stb_main);
            }
            
            br.close();
        } catch (IOException e) {
            System.out.println(e); // エラー吐き
            System.out.println("constractar error");
        }
    }
    
    public double[] getOnehotArrayAr(int arNum){
        double[] onehot = {0.0, 0.0, 0.0, 0.0, 0.0};
        
        if(arNum >= 12) onehot[0] = 1.0;
        else if(11 >= arNum && arNum >= 9){
            onehot[0] = (arNum - 9) / 3.0;
            onehot[1] = (12 - arNum) / 3.0;
        }
        else if(8 >= arNum && arNum >= 6){
            onehot[1] = (arNum - 6) / 3.0;
            onehot[2] = (9 - arNum) / 3.0;
        }
        else if(5 >= arNum && arNum >= 3){
            onehot[2] = (arNum - 3) / 3.0;
            onehot[3] = (6 - arNum) / 3.0;
        }
        else if(2 >= arNum){
            onehot[3] = arNum / 3.0;
            onehot[4] = (3 - arNum) / 3.0;
        }
        
        return onehot;
    }
    
    public int[] getOnehotArrayPtorSt(int psNum){
        int[] onehot = {0, 0, 0, 0, 0};
        
        if(psNum >= 4) onehot[0] = 1;
        else if(psNum == 3) onehot[1] = 1;
        else if(psNum == 2) onehot[2] = 1;
        else if(psNum == 1) onehot[3] = 1;
        else if(psNum == 0) onehot[4] = 1;
        
        return onehot;
    }
    
    public double[] getOnehotArrayHpSpDiv04(int np){
        // 0,25,50,75,100
        double[] onehot = {0.0, 0.0, 0.0, 0.0, 0.0};
        
        if(np >= 100){
            onehot[0] = 1;
        }
        else if(100 > np && np >= 75){
            onehot[0] = (np - 75) / 25.0;
            onehot[1] = (100 - np) / 25.0;
        }
        else if(75 > np && np >= 50){
            onehot[1] = (np - 50) / 25.0;
            onehot[2] = (75 - np) / 25.0;
        }
        else if(50 > np && np >= 25){
            onehot[2] = (np - 25) / 25.0;
            onehot[3] = (50 - np) / 25.0;
        }
        else if(25 > np){
            onehot[3] = (np - 0) / 25.0;
            onehot[4] = (25 - np) / 25.0;
        }
        
        return onehot;
    }
    
    public double[] getOnehotArrayHpSpDiv04p0(int np){
        // 0,30,60,90,120
        double[] onehot = {0.0, 0.0, 0.0, 0.0, 0.0};
        
        if(np >= 120){
            onehot[0] = 1;
        }
        else if(120 > np && np >= 90){
            onehot[0] = (np - 90) / 30.0;
            onehot[1] = (120 - np) / 30.0;
        }
        else if(90 > np && np >= 60){
            onehot[1] = (np - 60) / 30.0;
            onehot[2] = (90 - np) / 30.0;
        }
        else if(60 > np && np >= 30){
            onehot[2] = (np - 30) / 30.0;
            onehot[3] = (60 - np) / 30.0;
        }
        else if(30 > np){
            onehot[3] = (np - 0) / 30.0;
            onehot[4] = (30 - np) / 30.0;
        }
        
        return onehot;
    }
    
    public double[] getOnehotArrayHpSpDiv04p1(int np){
        // 0,1,30,60,90,120
        double[] onehot = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        
        if(np >= 120){
            onehot[0] = 1;
        }
        else if(120 > np && np >= 90){
            onehot[0] = (np - 90) / 30.0;
            onehot[1] = (120 - np) / 30.0;
        }
        else if(90 > np && np >= 60){
            onehot[1] = (np - 60) / 30.0;
            onehot[2] = (90 - np) / 30.0;
        }
        else if(60 > np && np >= 30){
            onehot[2] = (np - 30) / 30.0;
            onehot[3] = (60 - np) / 30.0;
        }
        else if(30 > np && np >= 1){
            onehot[3] = (np - 1) / 29.0;
            onehot[4] = (30 - np) / 29.0;
        }
        else if(np == 0){
            onehot[5] = 1;
        }
        
        return onehot;
    }
    
    public double[] getOnehotArrayHpSpDiv05(int hp){
        // 0,20,40,60,80,100
        double[] onehot = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        
        if(hp >= 100){
            onehot[0] = 1;
        }
        else if(100 > hp && hp >= 80){
            onehot[0] = (hp - 80) / 20.0;
            onehot[1] = (100 - hp) / 20.0;
        }
        else if(80 > hp && hp >= 60){
            onehot[1] = (hp - 60) / 20.0;
            onehot[2] = (80 - hp) / 20.0;
        }
        else if(60 > hp && hp >= 40){
            onehot[2] = (hp - 40) / 20.0;
            onehot[3] = (60 - hp) / 20.0;
        }
        else if(40 > hp && hp >= 20){
            onehot[3] = (hp - 20) / 20.0;
            onehot[4] = (40 - hp) / 20.0;
        }
        else if(20 > hp){
            onehot[4] = hp / 20.0;
            onehot[5] = (20 - hp) / 20.0;
        }
        
        return onehot;
    }
    
    public double[] getOnehotArrayHpDiv10(int hp){
        // 0,10,20,30,40,50,60,70,80,90,100
        double[] onehot = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        
        if(hp >= 100){
            onehot[0] = 1;
        }
        else if(100 > hp && hp >= 90){
            onehot[0] = (hp - 90) / 10.0;
            onehot[1] = (100 - hp) / 10.0;
        }
        else if(90 > hp && hp >= 80){
            onehot[1] = (hp - 80) / 10.0;
            onehot[2] = (90 - hp) / 10.0;
        }
        else if(80 > hp && hp >= 70){
            onehot[2] = (hp - 70) / 10.0;
            onehot[3] = (80 - hp) / 10.0;
        }
        else if(70 > hp && hp >= 60){
            onehot[3] = (hp - 60) / 10.0;
            onehot[4] = (70 - hp) / 10.0;
        }
        else if(60 > hp && hp >= 50){
            onehot[4] = (hp - 50) / 10.0;
            onehot[5] = (60 - hp) / 10.0;
        }
        else if(50 > hp && hp >= 40){
            onehot[5] = (hp - 40) / 10.0;
            onehot[6] = (50 - hp) / 10.0;
        }
        else if(40 > hp && hp >= 30){
            onehot[6] = (hp - 30) / 10.0;
            onehot[7] = (40 - hp) / 10.0;
        }
        else if(30 > hp && hp >= 20){
            onehot[7] = (hp - 20) / 10.0;
            onehot[8] = (30 - hp) / 10.0;
        }
        else if(20 > hp && hp >= 10){
            onehot[8] = (hp - 10) / 10.0;
            onehot[9] = (20 - hp) / 10.0;
        }
        else if(10 > hp){
            onehot[9] = hp / 10.0;
            onehot[10] = (10 - hp) / 10.0;
        }
        
        return onehot;
    }
    
    public class GameLog{
        private int dataCount; // データ数
        private int posCount; // 正例数
        private int negCount; // 負例数
        private int gameCount; // ゲーム回数
        private int winCount; // 勝利数
        private int loseCount; // 敗北数
        
        public GameLog(){
            dataCount = 0;
            posCount = 0;
            negCount = 0;
            gameCount = 0;
            winCount = 0;
            loseCount = 0;
        }
        
        public void dataCounter(){
            dataCount++;
        }
        public void posCounter(){
            posCount++;
        }
        public void negCounter(){
            negCount++;
        }
        public void gameCounter(){
            gameCount++;
        }
        public void winCounter(){
            winCount++;
        }
        public void loseCounter(){
            loseCount++;
        }
        
        public int getdataCount(){
            return dataCount;
        }
        public int getposCount(){
            return posCount;
        }
        public int getnegCount(){
            return negCount;
        }
        public int getgameCount(){
            return gameCount;
        }
        public int getwinCount(){
            return winCount;
        }
        public int getloseCount(){
            return loseCount;
        }
        
        public void adddataCount(int dataCount){
            this.dataCount += dataCount;
        }
        public void addposCount(int posCount){
            this.posCount += posCount;
        }
        public void addnegCount(int negCount){
            this.negCount += negCount;
        }
        public void addgameCount(int gameCount){
            this.gameCount += gameCount;
        }
        public void addwinCount(int winCount){
            this.winCount += winCount;
        }
        public void addloseCount(int loseCount){
            this.loseCount += loseCount;
        }
    }
}
