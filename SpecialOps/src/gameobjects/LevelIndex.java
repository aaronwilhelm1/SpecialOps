package gameobjects;

import java.util.ArrayList;

import ai.ObstacleMap;
import gameobjects.Players.Player;
import geometry.Coordinate;
import geometry.Rectangle;
import utility.Utility;

public class LevelIndex{
	public static final int LEVEL_WIDTH = (int)(28); //28 to 36
	public static final int LEVEL_HEIGHT = (int)(17); //17 to 22
	
	public static Block[][] warehouseMap;
	public static ArrayList<Block> warehouseList;
	public static Block[][] warehouseTileMap;
	//the locations of the flag in tile coordinates
	//blue is at index zero, green is at index one
	public static ArrayList<Coordinate> warehouseFlags;
	//the spawns in rectangle form in pixel coordinates
	public static ArrayList<Rectangle> warehouseSpawns;
	//the health packs in pixel coordinates
	public static ArrayList<Coordinate> warehouseHealthPacks;
	
	public static Block[][] polarisMap;
	public static ArrayList<Block> polarisList;
	public static Block[][] polarisTileMap;
	//the locations of the flag in tile coordinates
	//blue is at index zero, green is at index one
	public static ArrayList<Coordinate> polarisFlags;
	public static ArrayList<Rectangle> polarisSpawns;
	//the health packs in pixel coordinates
	public static ArrayList<Coordinate> polarisHealthPacks;
	
	public static Block[][] fortsMap;
	public static ArrayList<Block> fortsList;
	public static Block[][] fortsTileMap;
	//the locations of the flag in tile coordinates
	//blue is at index zero, green is at index one
	public static ArrayList<Coordinate> fortsFlags;
	//the spawns in rectangle form in pixel coordinates
	public static ArrayList<Rectangle> fortsSpawns;
	//the health packs in pixel coordinates
	public static ArrayList<Coordinate> fortsHealthPacks;
	
	
	public static ArrayList<Coordinate> getHealthPacks(String map){
		if(map.equals("warehouse")){
			return getWarehouseHealthPacks();
		} else if(map.equals("polaris")){
			return getPolarisHealthPacks();
		} else if(map.equals("forts")){
			return getFortsHealthPacks();
		} else {
			System.out.println("Can't return flags for the map called " + map);
			return getWarehouseHealthPacks();
		}
	}
	
	public static ArrayList<Coordinate> getFlags(String map){
		if(map.equals("warehouse")){
			return getWarehouseFlags();
		} else if(map.equals("polaris")){
			return getPolarisFlags();
		} else if(map.equals("forts")){
			return getFortsFlags();
		} else {
			System.out.println("Can't return flags for the map called " + map);
			return getWarehouseFlags();
		}
	}
	
	public static Block[][] getTileMap(String map){
		if(map.equals("warehouse")){
			return getWarehouseTileMap();
		} else if(map.equals("polaris")){
			return getPolarisTileMap();
		} else if(map.equals("forts")){
			return getFortsTileMap();
		} else {
			System.out.println("Can't return tile map for the map called " + map);
			return getWarehouseTileMap();
		}
	}
	
	public static ArrayList<Rectangle> getSpawns(String map){
		if(map.equals("warehouse")){
			return getWarehouseSpawns();
		} else if(map.equals("polaris")){
			return getPolarisSpawns();
		} else if(map.equals("forts")){
			return getFortsSpawns();
		} else {
			System.out.println("Can't return tile map for the map called " + map);
			return getWarehouseSpawns();
		}
	}
	
	public static Block[][] getWarehouseMap(){
		if(warehouseMap == null){
			constructWarehouseMap();
		}
		return warehouseMap;
	}
	
	public static Block[][] getWarehouseTileMap(){
		if(warehouseTileMap == null){
			constructWarehouseMap();
		}
		return warehouseTileMap;
	}
	
	public static ArrayList<Block> getWarehouseList(){
		if(warehouseList == null){
			constructWarehouseMap();
		}
		return warehouseList;
	}
	
	//returns the location of the flags in tile coordinates
	//blue is at index zero, green is at index one
	public static ArrayList<Coordinate> getWarehouseFlags(){
		if(warehouseFlags == null){
			constructWarehouseFlags();
		}
		return warehouseFlags;
	}
	
	//returns the location of the health packs in pixel coordinates
		public static ArrayList<Coordinate> getWarehouseHealthPacks(){
			if(warehouseHealthPacks == null){
				constructWarehouseHealthPacks();
			}
			return warehouseHealthPacks;
		}
	
	//returns the location of the spawns in pixel coordinates
		//blue is at index zero, green is at index one
		public static ArrayList<Rectangle> getWarehouseSpawns(){
			if(warehouseSpawns == null){
				constructWarehouseSpawns();
			}
			return warehouseSpawns;
		}
	
	private static void constructWarehouseMap(){
		warehouseList = new ArrayList<Block>();
		warehouseList.add(constructBlock(0, 10, 1));
		warehouseList.add(constructBlock(0, 26, 2));
		warehouseList.add(constructBlock(1, 6, 2));
		warehouseList.add(constructBlock(2, 3, 1));
		warehouseList.add(constructBlock(2, 20, 1));
		warehouseList.add(constructBlock(2, 22, 1));
		warehouseList.add(constructBlock(3, 1, 1));
		warehouseList.add(constructBlock(3, 14, 1));
		warehouseList.add(constructBlock(3, 20, 3));
		warehouseList.add(constructBlock(4, 13, 1));
		warehouseList.add(constructBlock(4, 14, 1));
		warehouseList.add(constructBlock(4, 15, 1));
		warehouseList.add(constructBlock(4, 16, 1));
		warehouseList.add(constructBlock(4, 18, 2));
		warehouseList.add(constructBlock(4, 25, 2));
		warehouseList.add(constructBlock(5, 0, 1));
		warehouseList.add(constructBlock(5, 2, 1));
		warehouseList.add(constructBlock(5, 6, 2));
		warehouseList.add(constructBlock(5, 11, 2));
		warehouseList.add(constructBlock(6, 0, 1));
		warehouseList.add(constructBlock(6, 14, 3));
		warehouseList.add(constructBlock(7, 8, 2));
		warehouseList.add(constructBlock(8, 11, 1));
		warehouseList.add(constructBlock(8, 12, 1));
		warehouseList.add(constructBlock(8, 13, 1));
		warehouseList.add(constructBlock(8, 17, 2));
		warehouseList.add(constructBlock(8, 21, 1));
		warehouseList.add(constructBlock(8, 22, 1));
		warehouseList.add(constructBlock(10, 6, 3));
		warehouseList.add(constructBlock(10, 27, 1));
		warehouseList.add(constructBlock(11, 0, 2));
		warehouseList.add(constructBlock(11, 4, 1));
		warehouseList.add(constructBlock(11, 14, 1));
		warehouseList.add(constructBlock(11, 22, 2));
		warehouseList.add(constructBlock(11, 25, 1));
		warehouseList.add(constructBlock(12, 19, 1));
		warehouseList.add(constructBlock(13, 4, 1));
		warehouseList.add(constructBlock(13, 27, 1));
		warehouseList.add(constructBlock(14, 9, 2));
		warehouseList.add(constructBlock(14, 16, 1));
		warehouseList.add(constructBlock(15, 1, 2));
		//warehouseList.add(constructBlock(15, 4, 1));
		warehouseList.add(constructBlock(15, 13, 1));
		warehouseList.add(constructBlock(15, 19, 2));
		warehouseList.add(constructBlock(15, 24, 1));
		warehouseList.add(constructBlock(16, 14, 1));
		//warehouseList.add(constructBlock(17, 8, 2));
		//warehouseList.add(constructBlock(17, 18, 3));
		//warehouseList.add(constructBlock(18, 14, 3));
		//warehouseList.add(constructBlock(19, 0, 2));
		warehouseMap = listToMap(warehouseList, warehouseMap);
		//warehouseTileMap = constructWarehouseTileMap();
		warehouseTileMap = constructTileMap(warehouseMap);
	}
	
	private static ArrayList<Coordinate> constructWarehouseFlags(){
		warehouseFlags = new ArrayList<Coordinate>();
		//add one to the coordinates below so its in the middle of the block
		warehouseFlags.add(new Coordinate((2 * ObstacleMap.TILES_PER_SQUARE) + 1, (8 * ObstacleMap.TILES_PER_SQUARE)+1));
		warehouseFlags.add(new Coordinate((25 * ObstacleMap.TILES_PER_SQUARE) + 1, (8 * ObstacleMap.TILES_PER_SQUARE)+1));
		return warehouseFlags;
	}
	
	private static ArrayList<Rectangle> constructWarehouseSpawns(){
		warehouseSpawns = new ArrayList<Rectangle>();
		warehouseSpawns.add(new Rectangle(new Coordinate(0, 0), 3 * Block.BLOCK_SIZE, 3 * Block.BLOCK_SIZE));
		warehouseSpawns.add(new Rectangle(new Coordinate((LevelIndex.LEVEL_WIDTH - 3) * Block.BLOCK_SIZE, (LevelIndex.LEVEL_HEIGHT - 3) * Block.BLOCK_SIZE), 3 * Block.BLOCK_SIZE, 3 * Block.BLOCK_SIZE));
		return warehouseSpawns;
	}
	
	private static ArrayList<Coordinate> constructWarehouseHealthPacks(){
		warehouseHealthPacks = new ArrayList<Coordinate>();
		//add one to the coordinates below so its in the middle of the block
		warehouseHealthPacks.add(new Coordinate(Block.BLOCK_SIZE * 21 + HealthPack.WIDTH / 6, Block.BLOCK_SIZE * 2 + HealthPack.HEIGHT / 6));
		warehouseHealthPacks.add(new Coordinate(Block.BLOCK_SIZE * 3 + HealthPack.WIDTH / 6, Block.BLOCK_SIZE * 16 + HealthPack.HEIGHT / 6));
		return warehouseHealthPacks;
	}
	
	private static Block[][] listToMap(ArrayList<Block> list, Block[][] map){
		map = new Block[LevelIndex.LEVEL_HEIGHT][LevelIndex.LEVEL_WIDTH];
		for(Block block : list){
			placeBlockOnMap(map, block, getBlockRow(block), getBlockCol(block));
		}
		return map;
	}
	
	//places a block with top left entry at row, col
	private static void placeBlockOnMap(Block[][] map, Block block, int row, int col){
		for(int j = 0; j < block.getSize(); j++){
			for(int k = 0; k < block.getSize(); k++){
				map[row + j][col + k] = block;
			}
		}
	}
	
	private static Block constructBlock(int row, int col, int size){//If You change the coordinate, change getBlockRow and getBlockCol
		return new Block(new Coordinate(col * Block.BLOCK_SIZE, (row * Block.BLOCK_SIZE)), size);
	}									//don't change row * Block.BLOCK_SIZE, or the col one with changing getBlockRow and getBlockCol
	
	private static int getBlockRow(Block block){
		return  (int)((block.getRectangle().getCoords()[0].getY()) / Block.BLOCK_SIZE);
	}
	
	private static int getBlockCol(Block block){
		return  (int)(block.getRectangle().getCoords()[0].getX() / Block.BLOCK_SIZE);
	}
	
	public static Block[][] getPolarisMap(){
		if(polarisMap == null){
			constructPolarisMap();
		}
		return polarisMap;
	}
	
	public static Block[][] getPolarisTileMap(){
		if(polarisTileMap == null){
			constructPolarisMap();
		}
		return polarisTileMap;
	}
	
	public static ArrayList<Block> getPolarisList(){
		if(polarisList == null){
			constructPolarisMap();
		}
		return polarisList;
	}
	
	//returns the location of the flags in tile coordinates
	//blue is at index zero, green is at index one
	public static ArrayList<Coordinate> getPolarisFlags(){
		if(polarisFlags == null){
			constructPolarisFlags();
		}
		return polarisFlags;
	}
	
	//returns location of health packs in pixel coordinates
	public static ArrayList<Coordinate> getPolarisHealthPacks(){
		if(polarisHealthPacks == null){
			constructPolarisHealthPacks();
		}
		return polarisHealthPacks;
	}
	
	//returns the location of the spawns in pixel coordinates
		//blue is at index zero, green is at index one
		public static ArrayList<Rectangle> getPolarisSpawns(){
			if(polarisSpawns == null){
				constructPolarisSpawns();
			}
			return polarisSpawns;
		}
	
	private static void constructPolarisMap(){
		polarisList = new ArrayList<Block>();
		polarisList.add(constructBlock(0, 0, 1));
		polarisList.add(constructBlock(0, 1, 1));
		polarisList.add(constructBlock(1, 0, 1));
		polarisList.add(constructBlock(0, 5, 1));
		polarisList.add(constructBlock(0, 17, 4));
		polarisList.add(constructBlock(2, 7, 1));
		polarisList.add(constructBlock(2, 16, 1));
		polarisList.add(constructBlock(3, 5, 2));
		polarisList.add(constructBlock(3, 15, 1));
		polarisList.add(constructBlock(4, 1, 1));
		polarisList.add(constructBlock(4, 2, 1));
		polarisList.add(constructBlock(5, 9, 1));
		polarisList.add(constructBlock(5, 10, 1));
		polarisList.add(constructBlock(5, 24, 1));
		polarisList.add(constructBlock(5, 25, 1));
		polarisList.add(constructBlock(6, 11, 1));
		polarisList.add(constructBlock(6, 17, 2));
		polarisList.add(constructBlock(6, 21, 2));
		polarisList.add(constructBlock(7, 0, 1));
		polarisList.add(constructBlock(7, 5, 1));
		polarisList.add(constructBlock(7, 12, 1));
		polarisList.add(constructBlock(7, 13, 1));
		polarisList.add(constructBlock(15, 27, 1));
		polarisList.add(constructBlock(16, 26, 1));
		polarisList.add(constructBlock(16, 27, 1));
		polarisList.add(constructBlock(8, 1, 1));
		polarisList.add(constructBlock(8, 5, 1));
		polarisList.add(constructBlock(8, 22, 1));
		polarisList.add(constructBlock(8, 27, 1));
		polarisList.add(constructBlock(9, 5, 2));
		polarisList.add(constructBlock(9, 9, 2));
		polarisList.add(constructBlock(9, 14, 1));
		polarisList.add(constructBlock(9, 15, 1));
		polarisList.add(constructBlock(9, 22, 1));
		polarisList.add(constructBlock(9, 27, 1));
		polarisList.add(constructBlock(11, 2, 1));
		polarisList.add(constructBlock(11, 3, 1));
		polarisList.add(constructBlock(10, 16, 1));
		polarisList.add(constructBlock(11, 17, 1));
		polarisList.add(constructBlock(11, 18, 1));
		polarisList.add(constructBlock(12, 21, 2));
		polarisList.add(constructBlock(12, 25, 1));
		polarisList.add(constructBlock(12, 26, 1));
		polarisList.add(constructBlock(13, 7, 4));
		polarisList.add(constructBlock(13, 12, 1));
		polarisList.add(constructBlock(14, 11, 1));
		polarisList.add(constructBlock(14, 20, 1));
		polarisList.add(constructBlock(16, 22, 1));
		//polarisList.add(constructBlock(18, 14, 3));
		//polarisList.add(constructBlock(19, 0, 2));
		polarisMap = listToMap(polarisList, polarisMap);
		//polarisTileMap = constructPolarisTileMap();
		polarisTileMap = constructTileMap(polarisMap);
	}
	
	private static ArrayList<Coordinate> constructPolarisFlags(){
		polarisFlags = new ArrayList<Coordinate>();
		//add one to the coordinates below so its in the middle of the block
		polarisFlags.add(new Coordinate((1 * ObstacleMap.TILES_PER_SQUARE) + 1, (1 * ObstacleMap.TILES_PER_SQUARE)+1));
		polarisFlags.add(new Coordinate((26 * ObstacleMap.TILES_PER_SQUARE) + 1, (15 * ObstacleMap.TILES_PER_SQUARE)+1));
		return polarisFlags;
	}
	
	private static ArrayList<Coordinate> constructPolarisHealthPacks(){
		polarisHealthPacks = new ArrayList<Coordinate>();
		//add one to the coordinates below so its in the middle of the block
		polarisHealthPacks.add(new Coordinate(Block.BLOCK_SIZE * 11 + HealthPack.WIDTH / 6, Block.BLOCK_SIZE * 15 + HealthPack.HEIGHT / 6));
		polarisHealthPacks.add(new Coordinate(Block.BLOCK_SIZE * 16 + HealthPack.WIDTH / 6, Block.BLOCK_SIZE * 1 + HealthPack.HEIGHT / 6));
		return polarisHealthPacks;
	}
	
	private static ArrayList<Rectangle> constructPolarisSpawns(){
		polarisSpawns = new ArrayList<Rectangle>();
		//add one to the coordinates below so its in the middle of the block
		polarisSpawns.add(new Rectangle(new Coordinate(0, (LevelIndex.LEVEL_HEIGHT - 3) * Block.BLOCK_SIZE), 3 * Block.BLOCK_SIZE, 3 * Block.BLOCK_SIZE));
		polarisSpawns.add(new Rectangle(new Coordinate((LevelIndex.LEVEL_WIDTH - 3) * Block.BLOCK_SIZE, 0), 3 * Block.BLOCK_SIZE, 3 * Block.BLOCK_SIZE));
		return polarisSpawns;
	}
	
	public static Block[][] getFortsMap(){
		if(fortsMap == null){
			constructFortsMap();
		}
		return fortsMap;
	}
	
	public static Block[][] getFortsTileMap(){
		if(fortsTileMap == null){
			constructFortsMap();
		}
		return fortsTileMap;
	}
	
	public static ArrayList<Block> getFortsList(){
		if(fortsList == null){
			constructFortsMap();
		}
		return fortsList;
	}
	
	//returns the location of the flags in tile coordinates
	//blue is at index zero, green is at index one
	public static ArrayList<Coordinate> getFortsFlags(){
		if(fortsFlags == null){
			constructFortsFlags();
		}
		return fortsFlags;
	}
	
	//returns location of health packs in pixel coordinates
	public static ArrayList<Coordinate> getFortsHealthPacks(){
		if(fortsHealthPacks == null){
			constructFortsHealthPacks();
		}
		return fortsHealthPacks;
	}
	
	//returns the location of the spawns in pixel coordinates
		//blue is at index zero, green is at index one
		public static ArrayList<Rectangle> getFortsSpawns(){
			if(fortsSpawns == null){
				constructFortsSpawns();
			}
			return fortsSpawns;
		}
	
	private static void constructFortsMap(){
		fortsList = new ArrayList<Block>();
		fortsList.add(constructBlock(0, 19, 1));
		fortsList.add(constructBlock(1, 13, 1));
		fortsList.add(constructBlock(2, 11, 1));
		fortsList.add(constructBlock(2, 26, 1));
		fortsList.add(constructBlock(2, 27, 1));
		fortsList.add(constructBlock(3, 4, 1));
		fortsList.add(constructBlock(3, 5, 1));
		fortsList.add(constructBlock(3, 6, 3));
		fortsList.add(constructBlock(3, 19, 3));
		fortsList.add(constructBlock(3, 22, 1));
		fortsList.add(constructBlock(3, 23, 1));
		fortsList.add(constructBlock(4, 15, 1));
		fortsList.add(constructBlock(5, 13, 1));
		fortsList.add(constructBlock(6, 2, 1));
		fortsList.add(constructBlock(6, 8, 2));
		fortsList.add(constructBlock(6, 18, 2));
		fortsList.add(constructBlock(6, 25, 1));
		fortsList.add(constructBlock(7, 2, 1));
		fortsList.add(constructBlock(7, 25, 1));
		fortsList.add(constructBlock(8, 2, 1));
		fortsList.add(constructBlock(8, 12, 1));
		fortsList.add(constructBlock(8, 15, 1));
		fortsList.add(constructBlock(8, 25, 1));
		fortsList.add(constructBlock(9, 2, 1));
		fortsList.add(constructBlock(9, 8, 2));
		fortsList.add(constructBlock(9, 18, 2));
		fortsList.add(constructBlock(9, 25, 1));
		fortsList.add(constructBlock(10, 2, 1));
		fortsList.add(constructBlock(10, 25, 1));
		fortsList.add(constructBlock(11, 6, 3));
		fortsList.add(constructBlock(11, 13, 1));
		fortsList.add(constructBlock(11, 16, 1));
		fortsList.add(constructBlock(11, 19, 3));
		fortsList.add(constructBlock(13, 4, 1));
		fortsList.add(constructBlock(13, 5, 1));
		fortsList.add(constructBlock(13, 12, 1));
		fortsList.add(constructBlock(13, 22, 1));
		fortsList.add(constructBlock(13, 23, 1));
		fortsList.add(constructBlock(14, 0, 1));
		fortsList.add(constructBlock(14, 1, 1));
		fortsList.add(constructBlock(14, 16, 1));
		fortsList.add(constructBlock(15, 7, 1));
		fortsList.add(constructBlock(16, 12, 1));
		fortsMap = listToMap(fortsList, fortsMap);
		fortsTileMap = constructTileMap(fortsMap);
	}
	
	private static ArrayList<Coordinate> constructFortsFlags(){
		fortsFlags = new ArrayList<Coordinate>();
		//add one to the coordinates below so its in the middle of the block
		fortsFlags.add(new Coordinate((5 * ObstacleMap.TILES_PER_SQUARE) + 1, (8 * ObstacleMap.TILES_PER_SQUARE)+1));
		fortsFlags.add(new Coordinate((22 * ObstacleMap.TILES_PER_SQUARE) + 1, (8 * ObstacleMap.TILES_PER_SQUARE)+1));
		return fortsFlags;
	}
	
	private static ArrayList<Coordinate> constructFortsHealthPacks(){
		fortsHealthPacks = new ArrayList<Coordinate>();
		fortsHealthPacks.add(new Coordinate(Block.BLOCK_SIZE * 0 + HealthPack.WIDTH / 6, Block.BLOCK_SIZE * 15 + HealthPack.HEIGHT / 6));
		fortsHealthPacks.add(new Coordinate(Block.BLOCK_SIZE * 27 + HealthPack.WIDTH / 6, Block.BLOCK_SIZE * 1 + HealthPack.HEIGHT / 6));
		return fortsHealthPacks;
	}
	
	private static ArrayList<Rectangle> constructFortsSpawns(){
		fortsSpawns = new ArrayList<Rectangle>();
		//add one to the coordinates below so its in the middle of the block
		fortsSpawns.add(new Rectangle(new Coordinate(0, 0), 3 * Block.BLOCK_SIZE, 3 * Block.BLOCK_SIZE));
		fortsSpawns.add(new Rectangle(new Coordinate((LevelIndex.LEVEL_WIDTH - 3) * Block.BLOCK_SIZE, (LevelIndex.LEVEL_HEIGHT - 3) * Block.BLOCK_SIZE), 3 * Block.BLOCK_SIZE, 3 * Block.BLOCK_SIZE));
		return fortsSpawns;
	}
	
	
	public static Block[][] constructTileMap(Block[][] map){
		Block[][] toReturn = new Block[ObstacleMap.WIDTH][ObstacleMap.HEIGHT];
		//Note: toReturn is (x, y) NOT (row, col)
		Block[][] temp = Utility.RCToXY(map);
		//Note: temp is (x, Y) NOT (row, col)
		//first mark where the blocks are
		for(int y = 0; y < temp[0].length; y++){
			for(int x = 0; x < temp.length; x++){
				if(map[y][x] != null){
					for(int row = 0; row < ObstacleMap.TILES_PER_SQUARE + 1; row++){
						for(int col = 0; col < ObstacleMap.TILES_PER_SQUARE + 1; col++){
							//if inside the bounds of the array still
							if(((y * ObstacleMap.TILES_PER_SQUARE) + row < ObstacleMap.HEIGHT) &&((x * ObstacleMap.TILES_PER_SQUARE) + col < ObstacleMap.WIDTH)){
								toReturn[(x * ObstacleMap.TILES_PER_SQUARE) + col][(y * ObstacleMap.TILES_PER_SQUARE) + row] = temp[x][y];	
								
							}
						}
					}
				}
			}
		}
		//now remove inaccessible areas on the edges
		Block b = new Block(new Coordinate(Double.MAX_VALUE, Double.MAX_VALUE), 1);
		double numOnEdgesDouble = Player.PLAYER_RADIUS/ (Block.BLOCK_SIZE / ObstacleMap.TILES_PER_SQUARE);
		int numOnEdges = (int) numOnEdgesDouble;
		numOnEdges = (int)Utility.round(numOnEdges, numOnEdgesDouble, numOnEdges+1);
		//Top Edge
		for(int y = 0; y < numOnEdges + 1; y++){
			
			for(int x = 0; x < toReturn.length; x++){
				toReturn[x][y] = b;
			}
		}
		//Bottom Edge
		for(int y = toReturn[0].length - numOnEdges; y < toReturn[0].length; y++){
			for(int x = 0; x < toReturn[0].length; x++){
				toReturn[x][y] = b;
			}
		}
		//Left Edge
		for(int x = 0; x < numOnEdges + 1; x++){
			for(int y = 0; y < toReturn[0].length; y++){
				toReturn[x][y] = b;
			}
		}
		//Right Edge
		for(int x = toReturn.length - numOnEdges; x < toReturn.length; x++){
			for(int y = 0; y < toReturn[0].length; y++){
				toReturn[x][y] = b;
			}
		}
		//Remove nodes too close to block to be reached
		//TODO: Without this part the algorithm will only work with two blocks
		//may want to remove the code up above that gets rid of right and bottom sides of block
		//i.e the "+ 1" in the loop max for the loop control variables row and col
		return toReturn;
	}
	
}
