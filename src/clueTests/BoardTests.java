package clueTests;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.RoomCell;

public class BoardTests {

	private static Board board;
	public static final int NUM_ROOMS = 10;
	public static final int NUM_ROWS = 16;
	public static final int NUM_COLUMNS = 17;
	public static final int NUM_DOORS = 17;

	@BeforeClass
	public static void setUp() {
		board = new Board();
	}

	@Test
	public void testRooms() {
		Map<Character, String> testRooms = board.getRooms();
		assertEquals(NUM_ROOMS,	testRooms.size());
		assertEquals("Green Center", testRooms.get('G'));
		assertEquals("Alderson", testRooms.get('A'));
		assertEquals("Stratton", testRooms.get('S'));
		assertEquals("Walkway", testRooms.get('W'));
		assertEquals("Kafadar", testRooms.get('K'));
	}
	
	@Test
	public void testBoardDimensions() {
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
	}
	
	@Test
	public void testDoorDirections() {
		RoomCell room = board.getRoomCellAt(0, 3);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getRoomCellAt(7, 13);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection());
		room = board.getRoomCellAt(12, 1);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection());
		room = board.getRoomCellAt(3, 8);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection());
		room = board.getRoomCellAt(0, 0);
		assertFalse(room.isDoorway());
		BoardCell cell = board.getBoardCellAt(board.calcIndex(5, 0));
		assertFalse(cell.isDoorway());
	}
	
	@Test
	public void testNumberOfDoorways() {
		int numDoors = 0;
		int totalCells = board.getNumColumns() * board.getNumRows();
		assertEquals(NUM_ROWS*NUM_COLUMNS, totalCells);
		for (int i = 0; i < totalCells; ++i) 
			if (board.getBoardCellAt(i).isDoorway()) ++numDoors;
		assertEquals(NUM_DOORS, numDoors);
	}
	
	@Test
	public void testRoomInitials() {
		assertEquals('M', board.getRoomCellAt(15, 8).getRoomInitial());
		assertEquals('U', board.getRoomCellAt(0, 8).getRoomInitial());
		assertEquals('C', board.getRoomCellAt(2, 15).getRoomInitial());
		assertEquals('B', board.getRoomCellAt(3, 0).getRoomInitial());
		assertEquals('Y', board.getRoomCellAt(14, 3).getRoomInitial());
	}
	
	@Test
	public void testCalcIndex() {
		assertEquals(0, board.calcIndex(0, 0));
		assertEquals(NUM_COLUMNS-1, board.calcIndex(0, NUM_COLUMNS-1));
		assertEquals((NUM_ROWS-1)*NUM_COLUMNS, board.calcIndex(NUM_ROWS-1, 0));
		assertEquals(NUM_ROWS*NUM_COLUMNS-1, board.calcIndex(NUM_ROWS-1, NUM_COLUMNS-1));
		assertEquals(18, board.calcIndex(1, 1));
		assertEquals(49, board.calcIndex(2, 15));	
	}
}
