package nodomain.boulderdash.scenemanagament.scenes.gamescene;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import nodomain.boulderdash.GlobalVariables;
import nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles.BlackTile;
import nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles.BorderObstacle;
import nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles.BrickObstacle;
import nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles.DiamondTile;
import nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles.DirtObstacle;
import nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles.ExitTile;
import nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles.Player;
import nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles.RockTile;
import nodomain.boulderdash.scenemanagament.scenes.gamescene.Tiles.Tile;
import nodomain.boulderdash.utils.Vector2;
import nodomain.boulderdash.utils.Math;

public class Grid
{
    public static final int WALK_LEFT = 1, WALK_UP = 2, WALK_RIGHT = 3, WALK_DOWN = 4;

    private int xTiles;
    private int yTiles;
    private int tileWidth;
    private int tileHeight;
    private int yOffset = 120;

    private Tile[][] tiles;

    private Player player;
    private Tile playerTile;

    public Grid(int level, int tileWidth, int tileHeight) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        try {
            InputStream inputStream = GlobalVariables.Context.getAssets().open("level" + String.valueOf(level) + ".level");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            List<String> lines = new ArrayList<>();

            while((line = reader.readLine()) != null) {
                lines.add(line);
            }

            processLines(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }

        initBorderObstacles();

    }

    private void processLines(List<String> lines) {
        int x = 1;
        int y = 1;


        xTiles = 2 + lines.get(0).length();
        yTiles = 2 + lines.size();

        tiles = new Tile[xTiles][yTiles];


        // P - Player
        // B - Black
        // Z - Dirt
        // X - Border
        for(String line : lines) {
            GlobalVariables.maxXOffset = ((line.length() + 2) * tileWidth) - GlobalVariables.ScreenWidth;
            System.out.println("dlugosc " + GlobalVariables.maxXOffset);

            for(int c = 0; c < line.length(); c++) {
                switch(line.charAt(c)) {
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
                        tiles[x][y] = new Tile(new ExitTile(new Vector2(x * tileWidth, y * tileHeight + yOffset),
                                tileWidth, tileHeight), true);
                    }
                    default: {
                        tiles[x][y] = new Tile(new BorderObstacle(new Vector2(x * tileWidth, y * tileHeight + yOffset),
                                tileWidth, tileHeight));

                        break;
                    }
                }

                x++;
            }

            x = 1;
            y++;
        }
    }

    private void initBorderObstacles() {
        for(int x = 0; x < xTiles; x++) {
            tiles[x][0] = new Tile(new BorderObstacle(new Vector2(x * tileWidth, yOffset),
                    tileWidth, tileHeight));

            tiles[x][yTiles - 1] = new Tile(new BorderObstacle(new Vector2(x * tileWidth, (yTiles - 1) * tileHeight + yOffset),
                    tileWidth, tileHeight));
        }

        for(int y = 1; y < yTiles - 1; y++) {
            tiles[0][y] = new Tile(new BorderObstacle(new Vector2(0, y * tileHeight + yOffset),
                    tileWidth, tileHeight));

            tiles[xTiles - 1][y] = new Tile(new BorderObstacle(new Vector2((xTiles - 1) * tileWidth, y * tileHeight + yOffset),
                    tileWidth, tileHeight));
        }


    }

    public void Update() {
        for(int x = 0; x < xTiles; x++) {
            for(int y = 0; y < yTiles; y++) {
                if(tiles[x][y] != null) {
                    tiles[x][y].Update();
                }
            }
        }
    }

    public void Draw(Canvas canvas) {
        canvas.translate(GlobalVariables.xOffset, GlobalVariables.yOffset);

        for(int x = 0; x < xTiles; x++) {
            for(int y = 0; y < yTiles; y++) {
                if(tiles[x][y] != null) {
                    tiles[x][y].Draw(canvas);
                }
            }
        }
    }

    public void ReceiveTouchEvent(MotionEvent motionEvent) {
        player.ReceiveTouchInput(motionEvent);
    }

    public void TryWalk(int direction) {
        switch(direction) {
            case WALK_LEFT : {
                if(player.getX() == 0 || !tiles[player.getX() - 1][player.getY()].isWalkableByPlayer())
                    return;

                if(GlobalVariables.xOffset < 0) {
                    GlobalVariables.xOffset += tileWidth;
                }

                movePlayer(player.getX() - 1, player.getY());

                break;
            }
            case WALK_UP : {
                if(player.getY() == 0 || !tiles[player.getX()][player.getY() - 1].isWalkableByPlayer())
                    return;

                GlobalVariables.yOffset += 70;
                movePlayer(player.getX(), player.getY() - 1);

                break;
            }
            case WALK_RIGHT : {
                if(player.getX() == tiles.length - 1 || !tiles[player.getX() + 1][player.getY()].isWalkableByPlayer())
                    return;

                if(java.lang.Math.abs(GlobalVariables.xOffset) < (GlobalVariables.maxXOffset))
                {
                    GlobalVariables.xOffset -= tileWidth;
                    System.out.println(GlobalVariables.xOffset);
                }

                movePlayer(player.getX() + 1, player.getY());

                break;
            }
            case WALK_DOWN : {
                if(player.getY() == tiles[0].length - 1 || !tiles[player.getX()][player.getY() + 1].isWalkableByPlayer())
                    return;

                GlobalVariables.yOffset -=  70;
                movePlayer(player.getX(), player.getY() + 1);

                break;
            }
        }
    }

    private void movePlayer(int newX, int newY) {
        int currentPlayerX = player.getX();
        int currentPlayerY = player.getY();

        tiles[newX][newY].setGameObject(tiles[currentPlayerX][currentPlayerY].getGameObject());
        player.setX(newX);
        player.setY(newY);
        player.UpdateDestRect(new Vector2(newX * tileWidth, newY * tileHeight + yOffset));
        tiles[currentPlayerX][currentPlayerY].setGameObject(new BlackTile(new Vector2(currentPlayerX * tileWidth, currentPlayerY * tileHeight + yOffset),
                tileWidth, tileHeight));
        tiles[currentPlayerX][currentPlayerY].setWalkableByPlayer(true);
    }
}
