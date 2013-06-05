package com.cellshop.login;

import com.cellshop.main.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class NinePointView extends View {
    
    private final Bitmap defaultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lock_default);;
    private final Bitmap selectedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lock_selected);;
    private final int selectedBitmapDiameter = selectedBitmap.getWidth();
    private final int selectedBitmapRadius = selectedBitmapDiameter / 2;
    private final int defaultBitmapDiameter = defaultBitmap.getWidth();
    private final int defaultBitmapRadius = defaultBitmapDiameter / 2;
    private int width;
    private int height;
    private FreshPassword freshPassword;
    private PointInfo startPoint;
    private PointInfo movePoint;
    //用于记录上一个点
    private PointInfo tempPoint;
    private final PointInfo[] points = new PointInfo[9];
    private final StringBuilder password = new StringBuilder();
    private Paint linePaint = new Paint();
    private Paint textpaint = new Paint();
    Canvas canvas;

    public NinePointView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        
    }

    public NinePointView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NinePointView(Context context,FreshPassword freshPassword) {
        super(context);
        this.freshPassword=freshPassword;
        init();
    }
    
    private void init(){
        linePaint.setColor(Color.GRAY);
        linePaint.setStrokeWidth(5);
        linePaint.setStrokeCap(Cap.ROUND);
        textpaint.setColor(Color.BLACK);
        textpaint.setTextSize(40);
    }
    
    private void iterateDrawLine(Canvas canvas, PointInfo startPoint){
        if(startPoint.getNextPoint() != null){
            canvas.drawLine(startPoint.getCenterX(), startPoint.getCenterY(), startPoint.getNextPoint().getCenterX(), startPoint.getNextPoint().getCenterY(), linePaint);
            iterateDrawLine(canvas, startPoint.getNextPoint());
        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = getWidth();
        height = getHeight();
        if(width != 0 && height != 0){
            initPoints();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.drawText("password:" + password.toString(), 0, 50, textpaint);
        this.canvas=canvas;
        for(PointInfo temp:points){
            if(temp.selected){
                canvas.drawBitmap(selectedBitmap, temp.getSeletedX(), temp.getSeletedY(), null);
            }
            canvas.drawBitmap(defaultBitmap, temp.getDefaultX(), temp.getDefaultY(),null);
        }
        if(startPoint != null){
            iterateDrawLine(canvas, startPoint);
        }
        super.onDraw(canvas);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        int len = points.length;
        switch (event.getAction()) {
        case MotionEvent.ACTION_UP:
            for(PointInfo temp:points){
                temp.setSelected(false);
                temp.setNextPoint(null);
               
            } 
            getPassword();
            password.setLength(0);
            invalidate();
            return false;
        case MotionEvent.ACTION_MOVE:
            for(int i = 0; i < len; i++){
                if(points[i].isInMyPlace(x, y) && !points[i].isSelected()){
                    // 手势在点上移动且点未被选中
                    if(startPoint == null){
                        startPoint = points[i];
                        tempPoint = points[i];
                    }else{
                        tempPoint.setNextPoint(points[i]);
                        tempPoint = points[i];
                    }
                    points[i].selected = true;
                    password.append(i);
                }else if(tempPoint != null){
                    // 手势在点外或者已经选中的点上移动
                    movePoint = new PointInfo(len, null, true, 0, 0, x - selectedBitmapRadius, y - selectedBitmapRadius);
                    tempPoint.setNextPoint(movePoint);
                }
              
				
            }
            invalidate();
            return true;
        case MotionEvent.ACTION_DOWN:
            for(int i = 0; i < len; i++){
                if(points[i].isInMyPlace(x, y)){
                    startPoint = points[i];
                    points[i].selected = true;
                    password.append(i);
                    tempPoint = points[i];
                }
                
            }
            invalidate();
            return true;
        default:
            return false;
        }
    }
    
    private void initPoints(){
        int selectedSpacing = (width - 3*selectedBitmapDiameter)/4;
        int selectedX = selectedSpacing;
        int selectedY = selectedSpacing + height - width;
        int defaultX = selectedX + selectedBitmapRadius - defaultBitmapRadius;
        int defaultY = selectedY + selectedBitmapRadius - defaultBitmapRadius;
        int len = points.length;
        for(int i = 0; i < len; i++){
            if(i == 3 || i== 6){
                selectedX = selectedSpacing;
                selectedY += selectedSpacing + selectedBitmapDiameter;
                defaultX = selectedX + selectedBitmapRadius - defaultBitmapRadius;
                defaultY = selectedY + selectedBitmapRadius - defaultBitmapRadius;
            }
            PointInfo temp = new PointInfo(i, null, false, defaultX, defaultY, selectedX, selectedY);
            points[i] = temp;
            selectedX += selectedBitmapDiameter + selectedSpacing;
            defaultX = selectedX + selectedBitmapRadius - defaultBitmapRadius;
        }
    }
    public void getPassword() {
    	freshPassword.freshPassword(password.toString());
    }
    
    public class PointInfo {

        private int id;
        private PointInfo nextPoint;
        private boolean selected;
        private int defaultX;
        private int defaultY;
        private int selectedX;
        private int selectedY;
        
        public PointInfo(int id, PointInfo nextPoint, boolean selected, int defaultX,
                int defaultY, int selectedX, int selectedY) {
            super();
            this.id = id;
            this.nextPoint = nextPoint;
            this.selected = selected;
            this.defaultX = defaultX;
            this.defaultY = defaultY;
            this.selectedX = selectedX;
            this.selectedY = selectedY;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public PointInfo getNextPoint() {
            return nextPoint;
        }

        public void setNextPoint(PointInfo nextPoint) {
            this.nextPoint = nextPoint;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public int getDefaultX() {
            return defaultX;
        }

        public void setDefaultX(int defaultX) {
            this.defaultX = defaultX;
        }

        public int getDefaultY() {
            return defaultY;
        }

        public void setDefaultY(int defaultY) {
            this.defaultY = defaultY;
        }

        public int getSeletedX() {
            return selectedX;
        }

        public void setSeletedX(int seletedX) {
            this.selectedX = seletedX;
        }

        public int getSeletedY() {
            return selectedY;
        }

        public void setSeletedY(int seletedY) {
            this.selectedY = seletedY;
        }
        
        public int getCenterX() {
            return selectedX + selectedBitmapRadius;
        }

        public int getCenterY() {
            return selectedY + selectedBitmapRadius;
        }
        
        public boolean isInMyPlace(int x, int y) {
            boolean flagX = x > selectedX
                    && x < selectedX + selectedBitmapDiameter;
            boolean flagY = y > selectedY
                    && y < selectedY + selectedBitmapDiameter;
            return flagX && flagY;
        }
    }
}
