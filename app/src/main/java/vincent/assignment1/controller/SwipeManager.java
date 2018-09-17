package vincent.assignment1.controller;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_SWIPE;
import static android.support.v7.widget.helper.ItemTouchHelper.LEFT;
import static android.support.v7.widget.helper.ItemTouchHelper.RIGHT;

public class SwipeManager extends ItemTouchHelper.Callback {

    private boolean swipeBack;
    private ButtonState buttonShowedState = ButtonState.GONE;
    private float buttonWidth;
    private RectF buttonInstance;
    private SwipeControlAction buttonAction;
    private RecyclerView.ViewHolder currentItemViewHolder = null;



    enum ButtonState{GONE,RIGHT_VISIBLE, LEFT_VISIBLE,SWIPE_TO_RIGHT}

    public SwipeManager(SwipeControlAction buttonAction){
        this.buttonAction = buttonAction;
    }



    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, LEFT | RIGHT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }


    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        Log.i("Swipe test", "call +++++ convertToAbsoluteDirection");

        //block swipe out the items
        if(swipeBack) {
            swipeBack = buttonShowedState != ButtonState.GONE;
            return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void onChildDraw(Canvas c,
                            RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder,
                            float dX,
                            float dY,
                            int actionState,
                            boolean isCurrentlyActive) {

        buttonWidth = c.getWidth()/4;


        if (actionState == ACTION_STATE_SWIPE) {
            if (buttonShowedState != ButtonState.GONE) {
                if (buttonShowedState == ButtonState.LEFT_VISIBLE) dX = Math.max(dX, buttonWidth);
                if (buttonShowedState == ButtonState.RIGHT_VISIBLE) dX = Math.min(dX, -buttonWidth);
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
            else {
                setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }

        if (buttonShowedState == ButtonState.GONE) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
            currentItemViewHolder = viewHolder;

    }

    private void setTouchListener (final Canvas c,
                                   final RecyclerView recyclerView,
                                   final RecyclerView.ViewHolder viewHolder,
                                   final float dX,
                                   final float dY,
                                   final int actionState,
                                   final boolean isCurrentlyActive){

        /**
         * after finishing swiping, set swipeBack to true in order to block swipe out the item
         */

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                swipeBack = event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP;
                if (swipeBack) {

                    //check how much to the left (or right) user swiped the item. If enough, we change state to show buttons
                    if (dX < -buttonWidth) {
                        buttonShowedState = ButtonState.RIGHT_VISIBLE;
                    }
                    else if (dX > buttonWidth) {
                        buttonShowedState = ButtonState.LEFT_VISIBLE;
                    }

                    if (buttonShowedState != ButtonState.GONE) {
                        setTouchDownListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        setItemsClickable(recyclerView, false);
                    }
                }

                return false;
            }
        });
    }


    /**
     *
     *  if buttonShowedState is different than GONE we need to overwrite touch listener and simulate click on RecyclerView .
     *  Why simulate? Because we could already have onclick listener on items, so we need to disable it because of glitches
     */
    private void setTouchDownListener(final Canvas c,
                                      final RecyclerView recyclerView,
                                      final RecyclerView.ViewHolder viewHolder,
                                      final float dX, final float dY,
                                      final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
                return false;
            }
        });
    }

    private void setTouchUpListener(final Canvas c,
                                    final RecyclerView recyclerView,
                                    final RecyclerView.ViewHolder viewHolder,
                                    final float dX, final float dY,
                                    final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    SwipeManager.super.onChildDraw(c, recyclerView, viewHolder, 0F, dY, actionState, isCurrentlyActive);
                    recyclerView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });
                    setItemsClickable(recyclerView, true);
                    swipeBack = false;

                    //check how much to the left (or right) user swiped the item. If enough, we change state to show buttons
                    if (buttonAction != null && buttonInstance != null && buttonInstance.contains(event.getX(), event.getY())) {
                        if (buttonShowedState == ButtonState.LEFT_VISIBLE) {
                            buttonAction.onLeftClicked(viewHolder.getAdapterPosition());
                        }
                        else if (buttonShowedState == ButtonState.RIGHT_VISIBLE) {
                            buttonAction.onRightClicked(viewHolder.getAdapterPosition());
                        }
                    }
                    buttonShowedState = ButtonState.GONE;
                    currentItemViewHolder = null;
                }
                return false;
            }
        });
    }

    private void setItemsClickable(RecyclerView recyclerView, boolean isClickable) {
        for (int i = 0; i < recyclerView.getChildCount(); ++i) {
            recyclerView.getChildAt(i).setClickable(isClickable);
        }
    }


    /**
     * draw buttons (Edit & Delete)
     */

    private void drawButtons(Canvas c, RecyclerView.ViewHolder viewHolder){

        float corners = 16;
        View itemView = viewHolder.itemView;
        Paint paint = new Paint();

        buttonInstance = null;
        if (buttonShowedState == ButtonState.LEFT_VISIBLE) {
            RectF leftButton = new RectF(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + buttonWidth, itemView.getBottom());
            paint.setColor(Color.BLUE);
            c.drawRoundRect(leftButton, corners, corners, paint);
            drawText("EDIT", c, leftButton, paint);
            buttonInstance = leftButton;
        }

        if (buttonShowedState == ButtonState.RIGHT_VISIBLE) {
            RectF rightButton = new RectF(itemView.getRight() - buttonWidth, itemView.getTop(), itemView.getRight(), itemView.getBottom());
            paint.setColor(Color.RED);
            c.drawRoundRect(rightButton, corners, corners, paint);
            drawText("DELETE", c, rightButton, paint);
            buttonInstance = rightButton;
        }
    }

    private void drawText(String text, Canvas c, RectF button, Paint p){
        float textSize = 60;
        p.setColor(Color.WHITE);
        p.setAntiAlias(true);
        p.setTextSize(textSize);

        float textWidth = p.measureText(text);
        c.drawText(text, button.centerX()-(textWidth/2), button.centerY()+(textSize/2), p);
    }

    public void onDraw(Canvas c) {
        if (currentItemViewHolder != null) {
            drawButtons(c, currentItemViewHolder);
        }
    }

}
