package brickBreaker;

import java.awt.Color;
import java.awt.Graphics;

public class MapGenerator {
	public int map[][];
	public int brickWidth, brickHeight;
	public int brickX, brickY;
	public int xWidth, yWidth;
	public MapGenerator(int row, int col) {
		map = new int[row][col];
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				map[i][j] = 1;
			}
		}
		brickWidth = 500;
		brickHeight = 150;
		brickX = (700 - brickWidth)/2;
		brickY = (500 - brickHeight)/2 - 70;
		xWidth = brickWidth/map[0].length;
		yWidth = brickHeight/map.length;
	}
	public void draw(Graphics g) {
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				if (map[i][j] > 0){
					g.setColor(Color.white);
					g.fillRect(brickX + j*xWidth, brickY + i*yWidth, xWidth - 3, yWidth - 3);
				}
			}
		}		
	}
	public void setBrickValue(int val, int row, int col){
		map[row][col] = val;
	}
}
