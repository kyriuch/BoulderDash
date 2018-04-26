package nodomain.boulderdash.scenemanagament.scenes.gamescene;

import android.graphics.Canvas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import nodomain.boulderdash.GlobalVariables;
import nodomain.boulderdash.utils.Vector2;

public class Grid
{
    private int xTiles;
    private int yTiles;
    private int tileWidth;
    private int tileHeight;

    private Tile[][] tiles;

    public Grid(int level, int tileWidth, int tileHeight) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        try {
            InputStream inputStream = GlobalVariables.Context.getAssets().open("level1.level");
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
            for(int c = 0; c < line.length(); c++) {
                switch(line.charAt(c)) {
                    case 'P': {
                        tiles[x][y] = new Tile(new BlackTile(new Vector2(x * tileWidth, y * tileHeight),
                                tileWidth, tileHeight));

                        break;
                    }
                    case 'B': {
                        tiles[x][y] = new Tile(new BlackTile(new Vector2(x * tileWidth, y * tileHeight),
                                tileWidth, tileHeight));

                        break;
                    }
                    case 'Z': {
                        tiles[x][y] = new Tile(new DirtObstacle(new Vector2(x * tileWidth, y * tileHeight),
                                tileWidth, tileHeight));

                        break;
                    }
                    default: {
                        tiles[x][y] = new Tile(new BorderObstacle(new Vector2(x * tileWidth, y * tileHeight),
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
            tiles[x][0] = new Tile(new BorderObstacle(new Vector2(x * tileWidth, 0),
                    tileWidth, tileHeight));

            tiles[x][yTiles - 1] = new Tile(new BorderObstacle(new Vector2(x * tileWidth, (yTiles - 1) * tileHeight),
                    tileWidth, tileHeight));
        }

        for(int y = 1; y < yTiles - 1; y++) {
            tiles[0][y] = new Tile(new BorderObstacle(new Vector2(0, y * tileHeight),
                    tileWidth, tileHeight));

            tiles[xTiles - 1][y] = new Tile(new BorderObstacle(new Vector2((xTiles - 1) * tileWidth, y * tileHeight),
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
        for(int x = 0; x < xTiles; x++) {
            for(int y = 0; y < yTiles; y++) {
                if(tiles[x][y] != null) {
                    tiles[x][y].Draw(canvas);
                }
            }
        }
    }

}
