package com.adventofcode.flashk.day20;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.adventofcode.flashk.common.test.constants.TestDisplayName;
import com.adventofcode.flashk.common.test.constants.TestFilename;
import com.adventofcode.flashk.common.test.constants.TestFolder;
import com.adventofcode.flashk.common.test.constants.TestTag;
import com.adventofcode.flashk.common.test.utils.PuzzleTest;
import com.adventofcode.flashk.common.test.utils.Input;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName(TestDisplayName.DAY_20)
@TestMethodOrder(OrderAnnotation.class)
@Disabled // TODO Remove comment when implemented
public class Day20Test extends PuzzleTest {

	private static final String INPUT_FOLDER = TestFolder.DAY_20;

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_1_SAMPLE)
	public void testSolvePart1Sample() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);

		RaceCondition raceCondition = new RaceCondition(inputs);
		raceCondition.runRaces();
		assertEquals(0L,raceCondition.solveA(100));
		assertEquals(1L,raceCondition.solveA(64));
		assertEquals(2L,raceCondition.solveA(40));
		assertEquals(3L,raceCondition.solveA(38));
		assertEquals(4L,raceCondition.solveA(36));
		assertEquals(5L,raceCondition.solveA(20));
		assertEquals(8L,raceCondition.solveA(12));
		assertEquals(10L,raceCondition.solveA(10));
		assertEquals(14L,raceCondition.solveA(8));
		assertEquals(16,raceCondition.solveA(6));
		assertEquals(30,raceCondition.solveA(4));
		assertEquals(44,raceCondition.solveA(2));

		// TODO cuidado, que igual si ejecuto dijkstra cada vez entonces el mapa se modifique
		//...
	}

	@Test
	@Order(2)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_1_INPUT)
	public void testSolvePart1Input() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE);
		RaceCondition raceCondition = new RaceCondition(inputs);
		raceCondition.runRaces();
		
		assertEquals(1463L,raceCondition.solveA(100));

	}

	@Test
	@Order(3)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE)
	public void testSolvePart2Sample() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);

		assertEquals(0L,0L);
	}

	@Test
	@Order(4)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_2_INPUT)
	public void testSolvePart2Input() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE);

		System.out.println("Solution: ");
		assertEquals(0L,0L);

	}

}
