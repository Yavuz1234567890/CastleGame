package com.ngdroidapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import java.util.Random;
import java.util.Vector;

import istanbul.gamelab.ngdroid.base.BaseCanvas;
import istanbul.gamelab.ngdroid.util.Log;
import static istanbul.gamelab.ngdroid.util.Utils.*;



/**
 * Created by noyan on 24.06.2016.
 * Nitra Games Ltd.
 */


public class GameCanvas extends BaseCanvas {
    //background
    Bitmap background;
    int backgroundx, backgroundy;
    //castle
    Bitmap castle;
    int castlex, castley;
    int castleimagew, castleimageh;
    int castlew, castleh;
    Rect castlesrc, castlecordinates;
    Rect sellectedcastle;
    boolean showcastle;
    Paint castlefont;
    //okcu
    private Bitmap Okcu;
    Vector<Integer> newOkcu;
    boolean sellectcastle;
    private int Okcuw, Okcuh;
    int attachcounter;
    //soldier list
    Vector<Vector<Integer>> soldierList;
    //enemy
    int ENEMY_X = 0, ENEMY_Y = 1, ENEMY_W = 2, ENEMY_H = 3, ENEMY_SPEED = 4;
    Bitmap enemy;
    int enemyw, enemyh;
    private Vector<Vector<Integer>> enemyList;
    private Vector<Integer> newEnemy;
    int enemycastlestart;
    int counter;
    //enemy castle
    int ENEMY_CASTLE_1 = 0, ENEMY_CASTLE_2 = 1;
    Bitmap enemycastle;
    int enemycastleimagew, enemycastleimageh;
    int enemycastlew, enemycastleh;
    int enemycastlex, enemycastley[];
    Rect enemycastlessrcs, enemycastlescords[];
    int enemycastlehealts[];
    //colision
    Rect hitControlSystem1;
    Rect hitControlSystem2;
    //r
    Random r;
    //math panel
    int BUTTON_0 = 0, BUTTON_1 = 1, BUTTON_2 = 2, BUTTON_3 = 3, BUTTON_4 = 4, BUTTON_5 = 5, BUTTON_6 = 6, BUTTON_7 = 7, BUTTON_8 = 8, BUTTON_9 = 9, BUTTON_CLEAR = 10, BUTTON_MINUS = 11, BUTTON_OK = 12;
    Bitmap mathpanel;
    int mathpanelw, mathpanelh;
    int mathpanelx, mathpanely;
    int number, answer;
    int number1, number2;
    int PLUS = 0, MINUS = 1, TIMES = 2, DIVIDED = 3;
    char operations[], operation;
    Paint answerFont;
    String numberStr;
    Rect mathpanelsrc, mathpanelcoords;
    Rect numberButtons[];
    //time
    boolean gamecontinue;

    public GameCanvas(NgApp ngApp) {
        super(ngApp);
    }

    public void setup() {
        //LOGI("setup");
        r = new Random();
        gamecontinue = true;
        loadBackground();
        loadOkcu();
        loadCastle();
        loadEnemy();
        loadHits();
        loadEnemyCastles();
        loadMathPanel();
    }

    private void loadMathPanel() {
        mathpanel = loadImage("math_pad.png");
        mathpanelw = 1300;
        mathpanelh = 725;
        mathpanelx = (getWidth() - mathpanelw) / 2;
        mathpanely = (getHeight() - mathpanelh) / 2;
        mathpanelsrc = new Rect();
        mathpanelsrc.set(0, 0, mathpanel.getWidth(), mathpanel.getHeight());
        mathpanelcoords = new Rect();
        mathpanelcoords.set(mathpanelx, mathpanely, mathpanelx+mathpanelw, mathpanely+mathpanelh);
        numberButtons = new Rect[13];
        numberButtons[BUTTON_0] = new Rect();
        numberButtons[BUTTON_0].set(1406, 548, 1480, 622);
        numberButtons[BUTTON_1] = new Rect();
        numberButtons[BUTTON_1].set(1304, 450, 1378, 522);
        numberButtons[BUTTON_2] = new Rect();
        numberButtons[BUTTON_2].set(1406, 450, 1480, 522);
        numberButtons[BUTTON_3] = new Rect();
        numberButtons[BUTTON_3].set(1511, 450, 1578, 522);
        numberButtons[BUTTON_4] = new Rect();
        numberButtons[BUTTON_4].set(1308, 355, 1373, 422);
        numberButtons[BUTTON_5] = new Rect();
        numberButtons[BUTTON_5].set(1406, 355, 1480, 419);
        numberButtons[BUTTON_6] = new Rect();
        numberButtons[BUTTON_6].set(1511, 355, 1578, 422);
        numberButtons[BUTTON_7] = new Rect();
        numberButtons[BUTTON_7].set(1308, 257, 1375, 324);
        numberButtons[BUTTON_8] = new Rect();
        numberButtons[BUTTON_8].set(1406, 257, 1475, 325);
        numberButtons[BUTTON_9] = new Rect();
        numberButtons[BUTTON_9].set(1511, 257, 1578, 325);
        numberButtons[BUTTON_CLEAR] = new Rect();
        numberButtons[BUTTON_CLEAR].set(1308, 551, 1375, 618);
        numberButtons[BUTTON_MINUS] = new Rect();
        numberButtons[BUTTON_MINUS].set(1511, 551, 1578, 618);
        numberButtons[BUTTON_OK] = new Rect();
        numberButtons[BUTTON_OK].set(1330, 634, 1560, 854);
        number1 = r.nextInt(100);
        number2 = r.nextInt(100);
        operations = new char[4];
        operations[PLUS] = '+';
        operations[MINUS] = '-';
        operations[TIMES] = 'x';
        operations[DIVIDED] = ':';
        genMathOperation();
        answerFont = new Paint();
        answerFont.setColor(Color.BLACK);
        answerFont.setTypeface(Typeface.DEFAULT_BOLD);
        answerFont.setTextSize(100);
    }

    private void loadEnemyCastles() {
        enemycastle = loadImage("castle.png");
        enemycastleimagew = enemycastle.getWidth();
        enemycastleimageh = enemycastle.getHeight();
        enemycastlew = 180;
        enemycastleh = 285;
        enemycastlehealts = new int[2];
        enemycastlehealts[0] = 100;
        enemycastlehealts[1] = 100;
        enemycastlex = (getWidth() - (enemycastlew + 20));
        enemycastley = new int[2];
        enemycastley[ENEMY_CASTLE_1] = 120;
        enemycastley[ENEMY_CASTLE_2] = (getHeight() - enemycastleh) - 200;
        enemycastlessrcs = new Rect();
        enemycastlessrcs.set(0, 0, enemycastleimagew, enemycastleimageh);
        enemycastlescords = new Rect[2];
        enemycastlescords[ENEMY_CASTLE_1] = new Rect();
        enemycastlescords[ENEMY_CASTLE_1].set(enemycastlex, enemycastley[ENEMY_CASTLE_1],
                enemycastlex + enemycastlew, enemycastley[ENEMY_CASTLE_1] + enemycastleh);
        enemycastlescords[ENEMY_CASTLE_2] = new Rect();
        enemycastlescords[ENEMY_CASTLE_2].set(enemycastlex, enemycastley[ENEMY_CASTLE_2],
                enemycastlex + enemycastlew, enemycastley[ENEMY_CASTLE_2] + enemycastleh);
    }

    private void loadHits() {
        hitControlSystem1 = new Rect();
        hitControlSystem2 = new Rect();
    }

    private void loadEnemy() {
        enemy = loadImage("Dusman_0.png");
        enemyList = new Vector<Vector<Integer>>();
        enemyw = enemy.getWidth();
        enemyh = enemy.getHeight();
        counter = 250;
    }

    private void spawnEnemy(int x, int y){
        newEnemy = new Vector<Integer>();
        newEnemy.add(x);//x
        newEnemy.add(y);//y
        newEnemy.add(enemyw);//w
        newEnemy.add(enemyh);//h
        newEnemy.add(-10);//speed
        enemyList.add(newEnemy);//add list
    }

    private void loadCastle() {
        castle = loadImage("castle.png");
        castleimagew = castle.getWidth();
        castleimageh = castle.getHeight();
        castlew = 180;
        castleh = 285;
        castlex = 20;
        castley = (getHeight() - castleh) / 2;
        castlesrc = new Rect();
        castlesrc = new Rect(0, 0, castleimagew, castleimageh);
        castlecordinates = new Rect();
        castlecordinates.set(castlex, castley, castlex+castlew, castley+castleh);
        showcastle = false;
        sellectcastle = false;
        counter = 0;
        castlefont = new Paint();
        castlefont.setTextAlign(Paint.Align.CENTER);
        castlefont.setTypeface(Typeface.SANS_SERIF);
        castlefont.setColor(Color.BLUE);
        castlefont.setTextSize(50);
    }

    private int OKCU_W = 0, OKCU_H = 1, OKCU_X = 2, OKCU_Y = 3, OKCU_SPEED = 4;

    private void spawnOkcu(int x, int y){
        newOkcu = new Vector<Integer>();
        newOkcu.add(Okcuw);//width
        newOkcu.add(Okcuh);//height
        newOkcu.add(x);//x
        newOkcu.add(y);//y
        newOkcu.add(7);//speed
        soldierList.add(newOkcu);//add list
    }

    private void loadOkcu() {
        Okcu = loadImage("Okcu_1.png");
        soldierList = new Vector<Vector<Integer>>();
        Okcuw = Okcu.getWidth();
        Okcuh = Okcu.getHeight();
        attachcounter = 0;
    }

    private void loadBackground() {
        background = loadImage("Map_1.jpg");
        backgroundx = 0;
        backgroundy = 0;
    }

    public void update() {
        if(!gamecontinue)return;
        okcuMovement();
        hitcontrol();
        enemySpawnControl();
        enemyMovement();
    }

    private void hitcontrol() {
        for (int i = 0; i < enemyList.size(); i++) {
            hitControlSystem1.set(enemyList.get(i).get(ENEMY_X), enemyList.get(i).get(ENEMY_Y),
                    enemyList.get(i).get(ENEMY_X)+enemyList.get(i).get(ENEMY_W),
                    enemyList.get(i).get(ENEMY_Y)+enemyList.get(i).get(ENEMY_H));

            for (int j = 0; j <soldierList.size(); j++) {
                hitControlSystem2.set(soldierList.get(i).get(OKCU_X), soldierList.get(i).get(OKCU_Y),
                        soldierList.get(i).get(OKCU_X)+soldierList.get(i).get(OKCU_W),
                        soldierList.get(i).get(OKCU_Y)+soldierList.get(i).get(OKCU_H));
                if(hitControlSystem1.intersect(hitControlSystem2)){
                    enemyList.remove(i);
                    soldierList.remove(j);
                    break;
                }
            }

        }

    }

    private void enemyMovement() {
        for(int i = 0; i < enemyList.size(); i++){
            if(enemyList.get(i).get(ENEMY_X) > (-enemyw)){
                //movement
                enemyList.get(i).set(ENEMY_X, enemyList.get(i).get(ENEMY_X)+enemyList.get(i).get(ENEMY_SPEED));
            }else enemyList.remove(i);
        }
    }

    private void enemySpawnControl() {
        if (counter > 250) {
            counter = 0;
            if(enemycastlehealts[ENEMY_CASTLE_1] > 0 ||
                    enemycastlehealts[ENEMY_CASTLE_2] > 0) {
                spawnEnemy(enemycastlex, castley);
            }
        } else counter++;
    }

    private void okcuMovement() {

        for (int i = 0; i < soldierList.size(); i++) {
            if(soldierList.get(i).get(OKCU_X) < getWidth()){
                if(soldierList.get(i).get(OKCU_X) < enemycastlex-100) {
                    soldierList.get(i).set(OKCU_X, soldierList.get(i).get(OKCU_X) + soldierList.get(i).get(OKCU_SPEED));
                }else{
                    if(attachcounter > 100){
                        if(enemycastlehealts[ENEMY_CASTLE_1] <= 100 || enemycastlehealts[ENEMY_CASTLE_2] <= 100) {
                            if (sellectedcastle == enemycastlescords[ENEMY_CASTLE_1]) enemycastlehealts[0] -= 5;
                            if (sellectedcastle == enemycastlescords[ENEMY_CASTLE_2]) enemycastlehealts[1] -= 5;
                        }
                        attachcounter = 0;
                    }else attachcounter++;
                }
            }else{
                soldierList.remove(i);
            }
        }
    }

    public void draw(Canvas canvas) {
        //LOGI("draw");
        canvas.drawColor(Color.BLACK);
        drawBackground();
        drawcastle();
        drawokcu();
        drawenemy();
        drawenemycastle();
        drawmathpanel();
        drawsellectcastletext();
    }

    private void drawsellectcastletext() {
        if(sellectcastle)drawText("Sellect castle to attach", 100, 150);
    }

    private void drawmathpanel() {
        setFont(answerFont);
        if(showcastle) {
            drawBitmap(mathpanel, mathpanelsrc, mathpanelcoords);
            drawText(toStr(number), mathpanelx + 150, 730);
            drawText(toStr(number1) + operation + number2, mathpanelx + 170, 450);
        }
    }

    private void drawenemycastle() {
        for(int i = 0; i < 2; i++){
            if(enemycastlehealts[i] > 0){
                drawBitmap(enemycastle, enemycastlessrcs, enemycastlescords[i]);
                setFont(castlefont);
                drawText(toStr(enemycastlehealts[i]), enemycastlex+100, enemycastley[i]-10);
            }
        }
    }

    private void drawenemy() {
        for (int i = 0; i < enemyList.size(); i++) drawBitmap(enemy, enemyList.get(i).get(ENEMY_X), enemyList.get(i).get(ENEMY_Y));
    }

    private void drawcastle() {
        drawBitmap(castle, castlesrc, castlecordinates);
    }

    private void drawokcu() {
        for (int i = 0; i < soldierList.size(); i++) {
            drawBitmap(Okcu, soldierList.get(i).get(OKCU_X), soldierList.get(i).get(OKCU_Y));
        }
    }

    private void drawBackground() {
        drawBitmap(background, backgroundx, backgroundy);
    }

    public void keyPressed(int key) {

    }

    public void keyReleased(int key) {

    }

    public boolean backPressed() {
        return true;
    }

    public void surfaceChanged(int width, int height) {

    }

    public void surfaceCreated() {

    }

    public void surfaceDestroyed() {

    }

    public void touchDown(int x, int y, int id) {
        if(!sellectcastle) {
            if (castlecordinates.contains(x, y)) {
                showcastle = true;
            }
        }

        if(showcastle)mathpanelbuttonsdown(x, y);

        if(sellectcastle) {
            if (enemycastlescords[ENEMY_CASTLE_1].contains(x, y)) {
                spawnOkcu(castlex+Okcuw, enemycastlescords[ENEMY_CASTLE_1].top);
                sellectedcastle = enemycastlescords[ENEMY_CASTLE_1];
                sellectcastle = false;
            }

            if (enemycastlescords[ENEMY_CASTLE_2].contains(x, y)) {
                spawnOkcu(castlex+Okcuw, enemycastlescords[ENEMY_CASTLE_2].top);
                sellectedcastle = enemycastlescords[ENEMY_CASTLE_2];
                sellectcastle = false;
            }
        }
    }

    private void mathpanelbuttonsdown(int x, int y) {
        for (int i = 0; i < 10; i++) {
            if(numberButtons[i].contains(x, y)){
                numberStr = numberStr + i;
                number = Integer.parseInt(numberStr);
            }
        }

        if(numberButtons[BUTTON_CLEAR].contains(x, y)){
            numberStr = "";
            number = 0;
        }

        if(numberButtons[BUTTON_MINUS].contains(x, y)){
            number = -number;
        }

        if (numberButtons[BUTTON_OK].contains(x, y)) {
            showcastle = false;
            if(number == answer)sellectcastle = true;
            genMathOperation();
        }
    }

    private void genMathOperation(){
        numberStr = "";
        number = 0;
        number1 = r.nextInt(100);
        number2 = r.nextInt(100);
        operation = operations[r.nextInt(4)];
        switch (operation){
            case '+':
                answer = number1 + number2;
                break;
            case '-':
                answer = number1 - number2;
                break;
            case 'x':
                if(number1 == 0)number1 = 1 + r.nextInt(5);
                answer = number1 * number2;
                break;
            case ':':
                number2 = 1 + r.nextInt(20);
                number1 = number2 + r.nextInt(50);
                answer = number1 / number2;
                break;
        }
    }

    public void touchMove(int x, int y, int id) {

    }

    public void touchUp(int x, int y, int id) {

    }


    public void pause() {

    }


    public void resume() {

    }


    public void reloadTextures() {

    }


    public void showNotify() {

    }

    public void hideNotify() {

    }

}
