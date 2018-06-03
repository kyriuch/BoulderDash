package nodomain.boulderdash.scenemanagament.scenes.gamescene;

import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.view.MotionEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import nodomain.boulderdash.GlobalVariables;
import nodomain.boulderdash.gameobjects.GameObject;
import nodomain.boulderdash.gameobjects.Sprite;
import nodomain.boulderdash.memory.Memory;
import nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles.BlackTile;
import nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles.BorderObstacle;
import nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles.BrickObstacle;
import nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles.DiamondTile;
import nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles.DirtObstacle;
import nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles.ExitTile;
import nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles.ExplosionTile;
import nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles.Player;
import nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles.RockTile;
import nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles.Tile;
import nodomain.boulderdash.utils.Time;
import nodomain.boulderdash.utils.Vector2;

public class Grid {
    public static final int WALK_LEFT = 1, WALK_UP = 2, WALK_RIGHT = 3, WALK_DOWN = 4;

    private int xTiles;
    private int yTiles;
    private int tileWidth;
    private int tileHeight;
    private int yOffset = 50;

    private GameScene gameScene;

    private Tile[][] tiles;

    public Player player;

    private float currentXOffset, currentYOffset;
    private final float smoothSpeed = 0.08f;

    public int diamondsBeforeCollection;
    public int diamondsAfterCollection;
    public int diamondsToCollect;

    private int exitX;
    private int exitY;

    private int diedX;
    private int diedY;

    private double moveTilesTime;
    private boolean moveNow;

    private boolean playerDied;
    private double diedTimer;

    private int triedPush;

    Grid(int level, int tileWidth, int tileHeight, GameScene gameScene) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.gameScene = gameScene;
        triedPush = 0;

        Restart(level);
    }

    public void Restart(int level) {
        playerDied = false;
        triedPush = 0;

        try {
            InputStream inputStream = GlobalVariables.Context.getAssets().open("level" + String.valueOf(level) + ".level");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            List<String> lines = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            processLines(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }

        currentXOffset = -(xTiles * tileWidth / 2);
        currentYOffset = -(yTiles * tileHeight / 2);

        initBorderObstacles();
        moveTilesTime = 0;
        moveNow = true;
    }

    private void processLines(List<String> lines) {
        int x = 1;
        int y = 1;

        xTiles = 2 + lines.get(0).length();
        yTiles = 1 + lines.size();

        tiles = new Tile[xTiles][yTiles];

        GlobalVariables.maxYOffset = ((lines.size() + 2) * tileHeight) - GlobalVariables.ScreenHeight;

        String line;

        for (int i = 0; i < lines.size(); i++) {
            line = lines.get(i);

            if (i == 0) {
                GlobalVariables.maxXOffset = ((line.length() + 2) * tileWidth) - GlobalVariables.ScreenWidth;
            }

            if (line.contains("OPTIONS")) {
                String[] split = line.split(":");
                String[] secondSplit = split[1].split(",");

                diamondsToCollect = Integer.parseInt(secondSplit[0]);
                diamondsBeforeCollection = Integer.parseInt(secondSplit[1]);
                diamondsAfterCollection = Integer.parseInt(secondSplit[2]);

                continue;
            }

            for (int c = 0; c < line.length(); c++) {
                switch (line.charAt(c)) {
                    case 'P': {
                        player = new Player(new Vector2(x * tileWidth, y * tileHeight + yOffset),
                                tileWidth, tileHeight, this, x, y);
                        tiles[x][y] = new Tile(player);

                        break;
                    }
                    case 'E': {
                        tiles[x][y] = new Tile(new BlackTile(new Vector2(x * tileWidth, y * tileHeight + yOffset),
                                tileWidth, tileHeight), true);

                        break;
                    }
                    case 'D': {
                        tiles[x][y] = new Tile(new DirtObstacle(new Vector2(x * tileWidth, y * tileHeight + yOffset),
                                tileWidth, tileHeight), true);

                        break;
                    }
                    case 'B': {
                        tiles[x][y] = new Tile(new BrickObstacle(new Vector2(x * tileWidth, y * tileHeight + yOffset),
                                tileWidth, tileHeight));

                        break;
                    }
                    case 'R': {
                        tiles[x][y] = new Tile(new RockTile(new Vector2(x * tileWidth, y * tileHeight + yOffset),
                                tileWidth, tileHeight));

                        break;
                    }
                    case 'J': {
                        tiles[x][y] = new Tile(new DiamondTile(new Vector2(x * tileWidth, y * tileHeight + yOffset),
                                tileWidth, tileHeight), true);

                        break;
                    }
                    case 'X': {
                        tiles[x][y] = new Tile(new BorderObstacle(new Vector2(x * tileWidth, y * tileHeight + yOffset),
                                tileWidth, tileHeight), false);

                        exitX = x;
                        exitY = y;

                        break;
                    }
                    default: {
                        tiles[x][y] = new Tile(new BorderObstacle(new Vector2(x * tileWidth, y * tileHeight + yOffset),
                                tileWidth, tileHeight));

                        break;
                    }
                }


                if(tiles[x][y].getGameObject() instanceof Sprite) {
                    ((Sprite) tiles[x][y].getGameObject()).SetColorFilter(new PorterDuffColorFilter(Color.rgb(87, 83, 83), PorterDuff.Mode.DST_OVER));
                }

                x++;
            }

            x = 1;
            y++;
        }
    }

    private void initBorderObstacles() {
        for (int x = 0; x < xTiles; x++) {
            tiles[x][0] = new Tile(new BorderObstacle(new Vector2(x * tileWidth, yOffset),
                    tileWidth, tileHeight));

            tiles[x][yTiles - 1] = new Tile(new BorderObstacle(new Vector2(x * tileWidth, (yTiles - 1) * tileHeight + yOffset),
                    tileWidth, tileHeight));
        }

        for (int y = 1; y < yTiles - 1; y++) {
            tiles[0][y] = new Tile(new BorderObstacle(new Vector2(0, y * tileHeight + yOffset),
                    tileWidth, tileHeight));

            tiles[xTiles - 1][y] = new Tile(new BorderObstacle(new Vector2((xTiles - 1) * tileWidth, y * tileHeight + yOffset),
                    tileWidth, tileHeight));
        }


    }

    public void Update() {
        moveTilesTime += Time.DeltaTime;

        GlobalVariables.xOffset = -nodomain.boulderdash.utils.Math.Clamp(0, GlobalVariables.maxXOffset, player.getX() * tileWidth - (GlobalVariables.ScreenWidth / 2));
        GlobalVariables.yOffset = -nodomain.boulderdash.utils.Math.Clamp(0, GlobalVariables.maxYOffset, player.getY() * tileHeight - (GlobalVariables.ScreenHeight / 2));

        if (moveTilesTime > 0.16) {
            moveNow = true;
            moveTilesTime = 0;
        }

        if (playerDied) {
            diedTimer += Time.DeltaTime;

            if (diedTimer >= 0.75) {
                playerDied = false;

                tiles[diedX - 1][diedY].setGameObject(new BlackTile(new Vector2((diedX - 1) * tileWidth, diedY * tileHeight + yOffset),
                        tileWidth, tileHeight));
                tiles[diedX - 1][diedY + 1].setGameObject(new BlackTile(new Vector2((diedX - 1) * tileWidth, (diedY + 1) * tileHeight + yOffset),
                        tileWidth, tileHeight));
                tiles[diedX - 1][diedY - 1].setGameObject(new BlackTile(new Vector2((diedX - 1) * tileWidth, (diedY - 1) * tileHeight + yOffset),
                        tileWidth, tileHeight));
                tiles[diedX][diedY - 1].setGameObject(new BlackTile(new Vector2(diedX * tileWidth, (diedY - 1) * tileHeight + yOffset),
                        tileWidth, tileHeight));
                tiles[diedX][diedY].setGameObject(new BlackTile(new Vector2(diedX * tileWidth, diedY * tileHeight + yOffset),
                        tileWidth, tileHeight));
                tiles[diedX][diedY + 1].setGameObject(new BlackTile(new Vector2(diedX * tileWidth, (diedY + 1) * tileHeight + yOffset),
                        tileWidth, tileHeight));
                tiles[diedX + 1][diedY - 1].setGameObject(new BlackTile(new Vector2((diedX + 1) * tileWidth, (diedY - 1) * tileHeight + yOffset),
                        tileWidth, tileHeight));
                tiles[diedX + 1][diedY].setGameObject(new BlackTile(new Vector2((diedX + 1) * tileWidth, diedY * tileHeight + yOffset),
                        tileWidth, tileHeight));
                tiles[diedX + 1][diedY + 1].setGameObject(new BlackTile(new Vector2((diedX + 1) * tileWidth, (diedY + 1) * tileHeight + yOffset),
                        tileWidth, tileHeight));
            }
        }

        currentXOffset = nodomain.boulderdash.utils.Math.Lerp(currentXOffset, GlobalVariables.xOffset, smoothSpeed);
        currentYOffset = nodomain.boulderdash.utils.Math.Lerp(currentYOffset, GlobalVariables.yOffset, smoothSpeed);

        for (int y = yTiles - 1; y >= 0; y--) {
            for (int x = xTiles - 1; x >= 0; x--) {
                if (tiles[x][y] != null) {
                    tiles[x][y].Update();
                }

                if (moveNow && !gameScene.DidPlayerFinish()) {
                    if (y < yTiles - 1) {
                        int newX = x;
                        int newY = y;

                        if (tiles[x][y].getGameObject() instanceof RockTile ||
                                tiles[x][y].getGameObject() instanceof DiamondTile) {

                            if (tiles[x][y + 1].getGameObject() instanceof Player &&
                                    tiles[x][y].isFalling()) {
                                gameScene.PlayerDied();

                                diedX = x;
                                diedY = y + 1;

                                playerDied = true;
                                diedTimer = 0;

                                tiles[diedX - 1][diedY].setGameObject(new ExplosionTile(new Vector2((diedX - 1) * tileWidth, diedY * tileHeight + yOffset),
                                        tileWidth, tileHeight));
                                tiles[diedX - 1][diedY + 1].setGameObject(new ExplosionTile(new Vector2((diedX - 1) * tileWidth, (diedY + 1) * tileHeight + yOffset),
                                        tileWidth, tileHeight));
                                tiles[diedX - 1][diedY - 1].setGameObject(new ExplosionTile(new Vector2((diedX - 1) * tileWidth, (diedY - 1) * tileHeight + yOffset),
                                        tileWidth, tileHeight));
                                tiles[diedX][diedY - 1].setGameObject(new ExplosionTile(new Vector2(diedX * tileWidth, (diedY - 1) * tileHeight + yOffset),
                                        tileWidth, tileHeight));
                                tiles[diedX][diedY].setGameObject(new ExplosionTile(new Vector2(diedX * tileWidth, diedY * tileHeight + yOffset),
                                        tileWidth, tileHeight));
                                tiles[diedX][diedY + 1].setGameObject(new ExplosionTile(new Vector2(diedX * tileWidth, (diedY + 1) * tileHeight + yOffset),
                                        tileWidth, tileHeight));
                                tiles[diedX + 1][diedY - 1].setGameObject(new ExplosionTile(new Vector2((diedX + 1) * tileWidth, (diedY - 1) * tileHeight + yOffset),
                                        tileWidth, tileHeight));
                                tiles[diedX + 1][diedY].setGameObject(new ExplosionTile(new Vector2((diedX + 1) * tileWidth, diedY * tileHeight + yOffset),
                                        tileWidth, tileHeight));
                                tiles[diedX + 1][diedY + 1].setGameObject(new ExplosionTile(new Vector2((diedX + 1) * tileWidth, (diedY + 1) * tileHeight + yOffset),
                                        tileWidth, tileHeight));


                                return;
                            }

                            if (tiles[x][y + 1].getGameObject() instanceof BlackTile) {
                                newY = y + 1;
                            }

                            if (tiles[x][y + 1].getGameObject() instanceof RockTile
                                    || tiles[x][y + 1].getGameObject() instanceof BrickObstacle) {

                                if (x > 0) {
                                    if ((tiles[x - 1][y + 1].getGameObject() instanceof BlackTile) &&
                                            tiles[x - 1][y].getGameObject() instanceof BlackTile) {
                                        newX = x - 1;
                                    }
                                }

                                if (x < xTiles - 1) {
                                    if ((tiles[x + 1][y + 1].getGameObject() instanceof BlackTile) &&
                                            tiles[x + 1][y].getGameObject() instanceof BlackTile) {
                                        newX = x + 1;
                                    }
                                }
                            }

                            if (newX != x || newY != y) {
                                tiles[newX][newY].setGameObject(tiles[x][y].getGameObject());
                                ((Sprite) tiles[newX][newY].getGameObject()).UpdateDestRect(new Vector2(newX * tileWidth, newY * tileHeight + yOffset));
                                tiles[x][y].setGameObject(new BlackTile(new Vector2(x * tileWidth, y * tileHeight + yOffset),
                                        tileWidth, tileHeight));

                                tiles[x][y].setFalling(false);
                                tiles[newX][newY].setFalling(true);

                                if (tiles[newX][newY].getGameObject() instanceof RockTile) {
                                    tiles[x][y].setWalkableByPlayer(true);
                                    tiles[newX][newY].setWalkableByPlayer(false);

                                    if (!(tiles[newX][newY + 1].getGameObject() instanceof BlackTile) &&
                                            !(tiles[newX][newY + 1].getGameObject() instanceof Player)) {
                                        Memory.crackSound.start();
                                    }
                                } else if (tiles[newX][newY].getGameObject() instanceof DiamondTile) {
                                    if (!(tiles[newX][newY + 1].getGameObject() instanceof BlackTile) &&
                                            !(tiles[newX][newY + 1].getGameObject() instanceof Player)) {
                                        Memory.diamondFallSound.start();
                                    }
                                }
                            } else if (tiles[x][y].isFalling()) {
                                tiles[x][y].setFalling(false);
                            }
                        }
                    }
                }
            }
        }

        moveNow = false;
    }

    public void Draw(Canvas canvas) {
        canvas.save();
        canvas.translate(currentXOffset, currentYOffset);

        for (int x = 0; x < xTiles; x++) {
            for (int y = 0; y < yTiles; y++) {
                if (tiles[x][y] != null) {
                    tiles[x][y].Draw(canvas);
                }
            }
        }

        canvas.restore();
    }

    public void PlayerDied() {
        for (int i = 0; i < xTiles; i++) {
            for (int j = 0; j < yTiles; j++) {
                if (tiles[i][j].getGameObject() instanceof Player) {
                    diedX = i;
                    diedY = j + 1;

                    playerDied = true;
                    diedTimer = 0;

                    tiles[diedX - 1][diedY].setGameObject(new ExplosionTile(new Vector2((diedX - 1) * tileWidth, diedY * tileHeight + yOffset),
                            tileWidth, tileHeight));
                    tiles[diedX - 1][diedY + 1].setGameObject(new ExplosionTile(new Vector2((diedX - 1) * tileWidth, (diedY + 1) * tileHeight + yOffset),
                            tileWidth, tileHeight));
                    tiles[diedX - 1][diedY - 1].setGameObject(new ExplosionTile(new Vector2((diedX - 1) * tileWidth, (diedY - 1) * tileHeight + yOffset),
                            tileWidth, tileHeight));
                    tiles[diedX][diedY - 1].setGameObject(new ExplosionTile(new Vector2(diedX * tileWidth, (diedY - 1) * tileHeight + yOffset),
                            tileWidth, tileHeight));
                    tiles[diedX][diedY].setGameObject(new ExplosionTile(new Vector2(diedX * tileWidth, diedY * tileHeight + yOffset),
                            tileWidth, tileHeight));
                    tiles[diedX][diedY + 1].setGameObject(new ExplosionTile(new Vector2(diedX * tileWidth, (diedY + 1) * tileHeight + yOffset),
                            tileWidth, tileHeight));
                    tiles[diedX + 1][diedY - 1].setGameObject(new ExplosionTile(new Vector2((diedX + 1) * tileWidth, (diedY - 1) * tileHeight + yOffset),
                            tileWidth, tileHeight));
                    tiles[diedX + 1][diedY].setGameObject(new ExplosionTile(new Vector2((diedX + 1) * tileWidth, diedY * tileHeight + yOffset),
                            tileWidth, tileHeight));
                    tiles[diedX + 1][diedY + 1].setGameObject(new ExplosionTile(new Vector2((diedX + 1) * tileWidth, (diedY + 1) * tileHeight + yOffset),
                            tileWidth, tileHeight));

                    i = xTiles - 1;
                    break;
                }
            }
        }
    }

    public void ReceiveTouchEvent(MotionEvent motionEvent) {
        player.ReceiveTouchInput(motionEvent);
    }

    public void TryWalk(int direction) {
        switch (direction) {
            case WALK_LEFT: {
                if (player.getX() == 0)
                    return;

                if (!tiles[player.getX() - 1][player.getY()].isWalkableByPlayer()) {
                    if (tiles[player.getX() - 1][player.getY()].getGameObject() instanceof RockTile) {
                        if (player.getX() - 1 > 0 &&
                                tiles[player.getX() - 2][player.getY()].getGameObject() instanceof BlackTile) {
                            triedPush++;
                        }
                    } else {
                        return;
                    }


                    if (triedPush == 5) {
                        push(player.getX() - 1, player.getY(), direction);
                    }

                    return;
                }

                triedPush = 0;

                movePlayer(player.getX() - 1, player.getY());

                break;
            }
            case WALK_UP: {
                if (player.getY() == 0 || !tiles[player.getX()][player.getY() - 1].isWalkableByPlayer())
                    return;

                triedPush = 0;

                movePlayer(player.getX(), player.getY() - 1);

                break;
            }
            case WALK_RIGHT: {
                if (player.getX() == tiles.length - 1)
                    return;

                if (!tiles[player.getX() + 1][player.getY()].isWalkableByPlayer()) {
                    if (tiles[player.getX() + 1][player.getY()].getGameObject() instanceof RockTile) {
                        if (player.getX() + 1 < tiles.length - 1 &&
                                tiles[player.getX() + 2][player.getY()].getGameObject() instanceof BlackTile) {
                            triedPush++;
                        }
                    } else {
                        return;
                    }


                    if (triedPush == 5) {
                        push(player.getX() + 1, player.getY(), direction);
                    }

                    return;
                }

                triedPush = 0;

                movePlayer(player.getX() + 1, player.getY());

                break;
            }
            case WALK_DOWN: {
                if (player.getY() == tiles[0].length - 1 || !tiles[player.getX()][player.getY() + 1].isWalkableByPlayer())
                    return;

                movePlayer(player.getX(), player.getY() + 1);

                break;
            }
        }
    }

    private void movePlayer(int newX, int newY) {
        int currentPlayerX = player.getX();
        int currentPlayerY = player.getY();

        GameObject currentGameObject = tiles[newX][newY].getGameObject();

        if (currentGameObject instanceof DiamondTile) {
            Memory.pickDiamondSound.start();
            this.gameScene.DiamondPickedUp();
        } else if (currentGameObject instanceof ExitTile) {
            this.gameScene.PlayerFinished();
        } else if (currentGameObject instanceof DirtObstacle) {
            Memory.dirtSound.start();
        } else if (currentGameObject instanceof BlackTile) {
            Memory.emptySound.start();
        }

        tiles[newX][newY].setGameObject(tiles[currentPlayerX][currentPlayerY].getGameObject());
        player.setX(newX);
        player.setY(newY);
        player.UpdateDestRect(new Vector2(newX * tileWidth, newY * tileHeight + yOffset));
        tiles[currentPlayerX][currentPlayerY].setGameObject(new BlackTile(new Vector2(currentPlayerX * tileWidth, currentPlayerY * tileHeight + yOffset),
                tileWidth, tileHeight));
        tiles[currentPlayerX][currentPlayerY].setWalkableByPlayer(true);
    }

    private void push(int newX, int newY, int direction) {
        int currentPlayerX = player.getX();
        int currentPlayerY = player.getY();


        tiles[newX][newY].setGameObject(tiles[currentPlayerX][currentPlayerY].getGameObject());
        player.setX(newX);
        player.setY(newY);
        player.UpdateDestRect(new Vector2(newX * tileWidth, newY * tileHeight + yOffset));
        tiles[currentPlayerX][currentPlayerY].setGameObject(new BlackTile(new Vector2(currentPlayerX * tileWidth, currentPlayerY * tileHeight + yOffset),
                tileWidth, tileHeight));
        tiles[currentPlayerX][currentPlayerY].setWalkableByPlayer(true);


        switch (direction) {
            case WALK_LEFT:
                tiles[newX - 1][newY].setGameObject(new RockTile(new Vector2((newX - 1) * tileWidth, newY * tileHeight + yOffset),
                        tileWidth, tileHeight));
                ((Sprite) tiles[newX - 1][newY].getGameObject()).UpdateDestRect(new Vector2((newX - 1) * tileHeight, newY * tileHeight + yOffset));
                tiles[newX - 1][newY].setWalkableByPlayer(false);

                break;
            case WALK_RIGHT:
                tiles[newX + 1][currentPlayerY].setGameObject(new RockTile(new Vector2((newX + 2) * tileWidth, newY * tileHeight + yOffset),
                        tileWidth, tileHeight));
                ((Sprite) tiles[newX + 1][newY].getGameObject()).UpdateDestRect(new Vector2((newX + 1) * tileHeight, newY * tileHeight + yOffset));
                tiles[newX + 1][currentPlayerY].setWalkableByPlayer(false);

                break;
        }

        triedPush = 0;
        Memory.crackSound.start();

    }

    public void OpenExit() {
        tiles[exitX][exitY].setGameObject(new ExitTile(new Vector2(exitX * tileWidth, exitY * tileHeight + yOffset),
                tileWidth, tileHeight));
        tiles[exitX][exitY].setWalkableByPlayer(true);
    }
}
