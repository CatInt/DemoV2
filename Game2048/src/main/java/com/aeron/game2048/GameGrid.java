package com.aeron.game2048;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

/**
 * Define the view and rule of the game
 */
public class GameGrid extends GridLayout implements CallBack {

    private final static int COLUMN = 4;
    private Card[][] cardsMap;
    private List<Point> blankCard;
    private int mScore;
    private int mLevel;
    private Card mMaxCard;
    private int mStep;

    public GameGrid(Context context) {
        super(context);
        initialGame();
    }

//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public GameGrid(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        initialGame();
//    }

    public GameGrid(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialGame();
    }

    public GameGrid(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialGame();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setUseDefaultMargins(true);

        int lengthOfSide = (Math.min(w, h)-140)/COLUMN;
        //the card is square
        addCards(lengthOfSide);
        onGameStart();
    }

    private void initialGame(){
        setColumnCount(COLUMN);
        cardsMap = new Card[COLUMN][COLUMN];
        blankCard = new ArrayList<>();
        mMaxCard = new Card(getContext());

        setOnTouchListener(new View.OnTouchListener() {

            float posX, posY, offsetX, offsetY;
            boolean moved = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        posX = event.getX();
                        posY = event.getY();
                        Log.d("Game2048", Float.toString(posX) + "," + Float.toString(posY));
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = event.getX() - posX;
                        offsetY = event.getY() - posY;
                        Log.d("Game2048", Float.toString(offsetX) + "," + Float.toString(offsetY));

                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -5) {
                                moved = moveLeft();
                            } else if (offsetX > 5) {
                                moved = moveRight();
                            }
                        } else {
                            if (offsetY < -5) {
                                moved = moveUp();
                            } else if (offsetY > 5) {
                                moved = moveDown();
                            }
                        }
                        break;
                }
                return true;
            }
        });

    }

    //initial and restart;
    public void onGameStart() {
        for (int x=0; x<COLUMN; x++){
            for (int y=0; y<COLUMN; y++){
                cardsMap[x][y].init();
            }
        }
        mScore = 0;
        mLevel = 0;
        mStep = 1;
        mMaxCard.setNum(0,0);
        addScore(0);
        addRandomNum(2);
    }

    //check whether game is over
    private void checkGameState() {
        if(findBlankCard()>0){
            Log.d("Game2048", "hasBlankCard");
            return;
        }

        for(int x=0; x<COLUMN; x++){
            for (int y=0;y<COLUMN;y++){
                if((y+1)<COLUMN && cardsMap[x][y].getNum() == cardsMap[x][y+1].getNum()) {
                    Log.d("Game2048", "con1");
                    return;
                }
                if((x+1)<COLUMN && cardsMap[x][y].getNum() == cardsMap[x+1][y].getNum()) {
                    Log.d("Game2048", "con2");
                    return;
                }
            }
        }

        Log.d("Game2048", "end");
        new AlertDialog.Builder(getContext())
                .setTitle("Oops...")
                .setMessage("Game Over")
                .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onGameStart();
                    }
                })
        .show();
    }

    //called if swipe right
    private boolean moveRight() {
        boolean moved = false;
        for (int x=0; x<COLUMN; x++){
            for (int y=COLUMN-1; y>0; y--){

                if (cardsMap[x][y].getNum() == 0){
                    for(int yy=y; yy>0; yy--){
                        cardsMap[x][yy].setNum(cardsMap[x][yy-1].giveNum(mStep), mStep);
                        if(cardsMap[x][yy].getNum()!=0)moved = true;
                    }
                    break;
                }else if(cardsMap[x][y].getNum() == cardsMap[x][y-1].getNum()){
                    cardsMap[x][y].setNum(cardsMap[x][y-1].giveNum(mStep)*2, mStep);
                    addScore(cardsMap[x][y].getNum());
                    moved = true;
                }
            }
        }
        if (moved) {
            addRandomNum(1);
        }
        checkGameState();
        return moved;
    }

    //called if swipe down
    private boolean moveDown() {
        boolean moved = false;
        for (int y=0; y<COLUMN; y++){
            for (int x=COLUMN-1; x>0; x--){

                if (cardsMap[x][y].getNum() == 0){
                    for(int xx=x; xx>0; xx--){
                        cardsMap[xx][y].setNum(cardsMap[xx-1][y].giveNum(mStep), mStep);
                        if(cardsMap[xx][y].getNum()!=0)
                            moved = true;
                    }
                    break;
                }else if(cardsMap[x][y].getNum() == cardsMap[x-1][y].getNum()){
                    cardsMap[x][y].setNum(cardsMap[x-1][y].giveNum(mStep)*2, mStep);
                    addScore(cardsMap[x][y].getNum());
                    moved = true;
                }

            }
        }
        if (moved) {
            addRandomNum(1);
        }
        checkGameState();
        return moved;
    }

    //called if swipe up
    private boolean moveUp() {
        boolean moved = false;
        for (int y=0; y<COLUMN; y++){
            for (int x=0; x<COLUMN-1; x++){

                if (cardsMap[x][y].getNum() == 0){
                    for(int xx=x; xx<COLUMN-1; xx++){
                        cardsMap[xx][y].setNum(cardsMap[xx+1][y].giveNum(mStep), mStep);
                        if(cardsMap[xx][y].getNum()!=0) moved = true;
                    }
                    break;
                }else if(cardsMap[x][y].getNum() == cardsMap[x+1][y].getNum()){
                    cardsMap[x][y].setNum(cardsMap[x+1][y].giveNum(mStep)*2, mStep);
                    addScore(cardsMap[x][y].getNum());
                    moved = true;
                }
            }
        }
        if (moved) {
            addRandomNum(1);
        }
        checkGameState();
        return moved;
    }

    //called if swipe left
    private boolean moveLeft() {
        boolean moved = false;
        for (int x=0; x<COLUMN; x++){
            for (int y=0; y<COLUMN-1; y++){

                if (cardsMap[x][y].getNum() == 0){
                    for(int yy=y; yy<COLUMN-1; yy++){
                        cardsMap[x][yy].setNum(cardsMap[x][yy+1].giveNum(mStep), mStep);
                        if(cardsMap[x][yy].getNum()!=0)moved = true;
                    }
                    break;
                }else if(cardsMap[x][y].getNum() == cardsMap[x][y+1].getNum()){
                    cardsMap[x][y].setNum(cardsMap[x][y+1].giveNum(mStep)*2, mStep);
                    addScore(cardsMap[x][y].getNum());
                    moved = true;
                }
            }
        }
        if (moved) {
            addRandomNum(1);
        }
        checkGameState();
        return moved;
    }

    //call the method of applying change
    private void makeCardChange() {
        for(int x=0;x<COLUMN;x++){
            for(int y =0;y<COLUMN;y++){
                cardsMap[x][y].makeChangeAccordingToNum();
            }
        }
        mStep++;
    }

    //fill the grid with Card which would be initial to 0 with blank text first;
    private void addCards(int side){

        for (int x=0; x<COLUMN; x++){
            for (int y=0; y<COLUMN; y++){
                cardsMap[x][y] = new Card(getContext());
                addView(cardsMap[x][y], side, side);
            }
        }
    }

    //count is the num of cars would be added with a random num in a random pos
    private void addRandomNum(int count) {
        if (findBlankCard()>=count) {
            while (count-- > 0) {
                Point card = blankCard.remove((int) (blankCard.size() * Math.random()));
                cardsMap[card.x][card.y].setNum(Math.random() > 0.2 ? 2 : 4, mStep);
            }
        }
        makeCardChange(); //every action of swipe will call this method
    }

    private int findBlankCard() {
        blankCard.clear();

        for (int x=0; x<COLUMN; x++){
            for(int y=0;y<COLUMN; y++){
                if (cardsMap[x][y].getNum()==0){
                    blankCard.add(new Point(x ,y));
                }
            }
        }
        return blankCard.size();

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    //add score and update the view of score and the color basing on score
    private void addScore(int add) {
        int addLevel = (int)(Math.log(add)/Math.log(2.0));
        mLevel = addLevel>mLevel?addLevel:mLevel;
        mMaxCard.setNum((int) Math.pow(2.0, (double) mLevel), 0);
        if (addLevel == mLevel){
            mScore+=(add*2);
        }else if (mLevel-addLevel == 1){
            mScore+=(add*1.5);
        }else {
            mScore+=add;
        }

//        MainActivity.mTextScore.setText(mScore + "");
//        MainActivity.mTextScore.setBackgroundTintList(ColorStateList.valueOf(mMaxCard.getAdaptiveColor()));

    }


}
