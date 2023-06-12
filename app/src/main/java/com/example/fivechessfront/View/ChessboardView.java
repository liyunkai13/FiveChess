package com.example.fivechessfront.View;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.fivechessfront.Entity.Board;
import com.example.fivechessfront.R;

public class ChessboardView extends View {
    private static final int BOARD_SIZE = 9;  // 棋盘大小
    private int boardWidth;   // 棋盘宽度
    private int GRID_WIDTH = 1 ;   // 网格线宽度
    private int CELL_SIZE;     // 格子宽度
    private float MIN_DIS = 25f;// 最小点击距离
    public Board board; // 引用 Board 类的实例
    public interface OnChessboardClickListener {                        /*定义接口，其实还是要配合onTouchEvent实现*/
        void onChessboardClick(float x, float y,int row, int col);
    }
    private OnChessboardClickListener onChessboardClickListener;
    // 其他成员变量和方法
    public ChessboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ChessboardView(Context context, AttributeSet attrs, Board board) {
        super(context, attrs);
        this.board = board;
    }
    public void setBoard(Board board) {
        this.board = board; // 设置 Board 实例的引用
        invalidate(); // 重绘棋盘
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        boardWidth = Math.min(width, height);
        // 根据屏幕尺寸计算格子尺寸和格子框线尺寸
        setMeasuredDimension( boardWidth,  boardWidth);
    }
    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        // 计算棋盘宽度
        boardWidth = Math.min(width, height);
        // 计算每个格子的尺寸
        CELL_SIZE = (boardWidth - (BOARD_SIZE) * GRID_WIDTH) / (BOARD_SIZE+1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制棋盘
        if (board != null) {
            drawChessboard(canvas);
        }
    }
    public void setOnChessboardClickListener(OnChessboardClickListener listener) {
        this.onChessboardClickListener = listener;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 处理用户的点击事件
        // 根据点击位置计算行列坐标
        // 将行列坐标传递给 Board 实例进行处理
        // 根据 Board 实例的状态重绘界面
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            int row = Math.round((y - CELL_SIZE - GRID_WIDTH / 2f) / (CELL_SIZE + GRID_WIDTH));
            int col = Math.round((x - CELL_SIZE - GRID_WIDTH / 2f) / (CELL_SIZE + GRID_WIDTH));
            float py = row * (CELL_SIZE + GRID_WIDTH) + CELL_SIZE + GRID_WIDTH / 2f;
            float px = col * (CELL_SIZE + GRID_WIDTH) + CELL_SIZE + GRID_WIDTH / 2f;
            float distance = (float) Math.sqrt((px-x)*(px-x)+(py-y)*(py-y));
            if (onChessboardClickListener != null) {
                //增加距离检测
                if(distance<=MIN_DIS) {
                    onChessboardClickListener.onChessboardClick(x, y, row, col);
                }
            }
            invalidate(); // 重绘棋盘
            return true;
        }
        return super.onTouchEvent(event);
    }


    private void drawChessboard(Canvas canvas) {
        // 绘制棋盘的逻辑
        // 根据 Board 实例的状态确定绘制内容
        // 绘制棋盘
        Paint paint = new Paint();
        //绘制棋盘背景
        paint.setColor(ContextCompat.getColor(getContext(), R.color.boardColor));
        Rect rect = new Rect((int) (CELL_SIZE*0.5), (int) (CELL_SIZE*0.5), (int) (boardWidth-CELL_SIZE*0.5), (int) (boardWidth-CELL_SIZE*0.5));
        canvas.drawRect(rect, paint);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(GRID_WIDTH);
        paint.setAntiAlias(true);
        for (int i = 0; i < BOARD_SIZE; i++) {
            // 绘制棋盘的横线
            canvas.drawLine(CELL_SIZE, CELL_SIZE + i * (CELL_SIZE+GRID_WIDTH), boardWidth - CELL_SIZE-GRID_WIDTH, CELL_SIZE + i * (CELL_SIZE+GRID_WIDTH), paint);
            // 绘制棋盘的竖线
            canvas.drawLine(CELL_SIZE + i * (CELL_SIZE+GRID_WIDTH),CELL_SIZE, CELL_SIZE + i * (CELL_SIZE+GRID_WIDTH), boardWidth - CELL_SIZE-GRID_WIDTH, paint);
        }
        // 绘制棋子
        paint.setColor(Color.BLACK);
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE;j++) {
                if (board.boardArray[i][j] == 1) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.black_chequer);
                    Matrix matrix = new Matrix();
                    matrix.postScale((float)(CELL_SIZE/3)/128, (float)(CELL_SIZE/3)/128);
                    Bitmap newBitMap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    // 缩放原图
                    canvas.drawBitmap(newBitMap,CELL_SIZE + j * (CELL_SIZE+GRID_WIDTH)-48,CELL_SIZE + i * (CELL_SIZE+GRID_WIDTH)-48,paint);
                    //canvas.drawCircle(CELL_SIZE + j * (CELL_SIZE+GRID_WIDTH), CELL_SIZE + i * (CELL_SIZE+GRID_WIDTH), (float)CELL_SIZE/3, paint);
                }
            }
        }
        paint.setColor(Color.WHITE);
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE;j++) {
                if (board.boardArray[i][j] == 2) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.white_chequer);
                    Matrix matrix = new Matrix();
                    matrix.postScale((float)(CELL_SIZE/3)/128, (float)(CELL_SIZE/3)/128);
                    Bitmap newBitMap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    // 缩放原图
                    canvas.drawBitmap(newBitMap,CELL_SIZE + j * (CELL_SIZE+GRID_WIDTH) -48 ,CELL_SIZE + i * (CELL_SIZE+GRID_WIDTH)-48,paint);
                    //canvas.drawCircle(CELL_SIZE + j * (CELL_SIZE+GRID_WIDTH), CELL_SIZE + i * (CELL_SIZE+GRID_WIDTH), (float)CELL_SIZE/3, paint);
                }
            }
        }
    }
}

