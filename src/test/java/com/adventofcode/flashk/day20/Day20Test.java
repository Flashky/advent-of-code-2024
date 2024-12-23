package com.adventofcode.flashk.day20;

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
		raceCondition.runRaces(2);

		assertEquals(0L,raceCondition.solve(100));
		assertEquals(1L,raceCondition.solve(64));
		assertEquals(2L,raceCondition.solve(40));
		assertEquals(3L,raceCondition.solve(38));
		assertEquals(4L,raceCondition.solve(36));
		assertEquals(5L,raceCondition.solve(20));
		assertEquals(8L,raceCondition.solve(12));
		assertEquals(10L,raceCondition.solve(10));
		assertEquals(14L,raceCondition.solve(8));
		assertEquals(16,raceCondition.solve(6));
		assertEquals(30,raceCondition.solve(4));
		assertEquals(44,raceCondition.solve(2));

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
		raceCondition.runRaces(2);

		assertEquals(1463L,raceCondition.solve(100));

	}


	@Test
	@Order(3)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE)
	public void testSolvePart2Sample() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);

		RaceCondition raceCondition = new RaceCondition(inputs);
		raceCondition.runRaces(20);

		assertEquals(3L,raceCondition.solve(76));
		assertEquals(7L,raceCondition.solve(74));
		assertEquals(29L,raceCondition.solve(72));
		assertEquals(41L,raceCondition.solve(70));
		assertEquals(55L,raceCondition.solve(68));
		assertEquals(67L,raceCondition.solve(66));
		assertEquals(86L,raceCondition.solve(64));
		assertEquals(106L,raceCondition.solve(62));
		assertEquals(129L,raceCondition.solve(60));
		assertEquals(154L,raceCondition.solve(58));
		assertEquals(193,raceCondition.solve(56));
		assertEquals(222,raceCondition.solve(54));
		assertEquals(253,raceCondition.solve(52));
		assertEquals(285,raceCondition.solve(50));

	}

	@Test
	@Order(4)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_2_INPUT)
	public void testSolvePart2Input() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE);

		RaceCondition raceCondition = new RaceCondition(inputs);
		raceCondition.runRaces(20);

		assertEquals(985332L,raceCondition.solve(100));

	}

}
