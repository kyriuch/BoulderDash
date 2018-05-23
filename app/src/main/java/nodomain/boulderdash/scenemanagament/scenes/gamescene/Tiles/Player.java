package nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles;

import android.graphics.Rect;
import android.view.MotionEvent;

import nodomain.boulderdash.GlobalVariables;
import nodomain.boulderdash.gameobjects.Sprite;
import nodomain.boulderdash.scenemanagament.scenes.gamescene.Grid;
import nodomain.boulderdash.utils.GridHelper;
import nodomain.boulderdash.utils.Time;
import nodomain.boulderdash.utils.Vector2;
import nodomain.boulderdash.utils.Math;

public class Player extends Sprite {

    private final int IDLE = 1, WALK_LEFT = 2, WALK_RIGHT = 3;

    private final Rect[] idleRects = {
            GridHelper.getRect(1, 1),
            GridHelper.getRect(2, 1),
            GridHelper.getRect(3, 1),
            GridHelper.getRect(4, 1),
            GridHelper.getRect(5, 1),
            GridHelper.getRect(6, 1),
            GridHelper.getRect(7, 1),
    };

    private final Rect[] walkLeftRects = {
            GridHelper.getRect(1, 2),
            GridHelper.getRect(2, 2),
            GridHelper.getRect(3, 2),
            GridHelper.getRect(4, 2),
            GridHelper.getRect(5, 2),
            GridHelper.getRect(6, 2),
            GridHelper.getRect(7, 2),
    };

    private final Rect[] walkRightRects = {
            GridHelper.getRect(1, 3),
            GridHelper.getRect(2, 3),
            GridHelper.getRect(3, 3),
            GridHelper.getRect(4, 3),
            GridHelper.getRect(5, 3),
            GridHelper.getRect(6, 3),
            GridHelper.getRect(7, 3),
    };

    private int currentState;
    private int currentIndex;
    private double expiredTime;
    private float xRatio;
    private float yRatio;
    private Grid grid;

    private int x, y;
    private float lastEventX, lastEventY;

    private boolean isTouchDown;
    private boolean canWalk;
    private double walkTimer;

    public Player(Vector2 position, int width, int height, Grid grid, int x, int y) {
        super(position, width, height, new Rect());

        expiredTime = 0;
        currentState = IDLE;
        this.grid = grid;
        this.x = x;
        this.y = y;

        isTouchDown = false;
        canWalk = true;
    }

    @Override
    public void Update() {
        expiredTime += Time.DeltaTime;

        if(!canWalk)
            walkTimer += Time.DeltaTime;

        if(expiredTime >= 2) {
            expiredTime = 0;
        }

        currentIndex = Math.Lerp(0, 6, expiredTime / 2);

        switch(currentState) {
            case IDLE: srcRect = idleRects[currentIndex]; break;
            case WALK_LEFT: srcRect = walkLeftRects[currentIndex]; break;
            case WALK_RIGHT: srcRect = walkRightRects[currentIndex]; break;
        }

        if(isTouchDown)
        {
            xRatio = lastEventX / GlobalVariables.ScreenWidth;
            yRatio = lastEventY / GlobalVariables.ScreenHeight;

            if(java.lang.Math.abs(xRatio - 0.5) > java.lang.Math.abs(yRatio - 0.5)) {
                if(xRatio > 0.5) {
                    tryWalk(Grid.WALK_RIGHT);
                } else {
                    tryWalk(Grid.WALK_LEFT);
                }
            } else {
                if(yRatio > 0.5) {
                    tryWalk(Grid.WALK_DOWN);
                } else {
                    tryWalk(Grid.WALK_UP);
                }
            }
        }
    }

    public void ReceiveTouchInput(MotionEvent motionEvent) {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
        {
            updateEventParams(motionEvent.getX(), motionEvent.getY());

            isTouchDown = true;
        }

        if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
            isTouchDown = false;
            currentState = 1;
            expiredTime = 0;
        }

        if(motionEvent.getAction() == MotionEvent.ACTION_MOVE && isTouchDown) {
            updateEventParams(motionEvent.getX(), motionEvent.getY());
        }
    }

    private void updateEventParams(float x, float y) {
        lastEventX = x;
        lastEventY = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void UpdateDestRect(Vector2 newPosition) {
        this.destRect = new Rect();
        destRect.left = (int)(newPosition.X);
        destRect.right = (int)(newPosition.X + width);
        destRect.top = (int)(newPosition.Y);
        destRect.bottom = (int)(newPosition.Y + height);
    }

    private void tryWalk(int direction) {
        if(canWalk) {
            grid.TryWalk(direction);

            switch (direction) {
                case Grid.WALK_LEFT: {
                    if(currentState != WALK_LEFT) {
                        currentState = WALK_LEFT;
                        expiredTime = 0;
                    }

                    break;
                }
                case Grid.WALK_RIGHT: {
                    if(currentState != WALK_RIGHT) {
                        currentState = WALK_RIGHT;
                        expiredTime = 0;
                    }

                    break;
                }
            }

            canWalk = false;
        } else {
            if(walkTimer > 0.1) {
                canWalk = true;
                walkTimer = 0;
            }
        }
    }
}
