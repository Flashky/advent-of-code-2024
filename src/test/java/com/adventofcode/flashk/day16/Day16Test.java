package com.adventofcode.flashk.day16;

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

@DisplayName(TestDisplayName.DAY_16)
@TestMethodOrder(OrderAnnotation.class)
@Disabled // TODO Remove comment when implemented
public class Day16Test extends PuzzleTest {

	private static final String INPUT_FOLDER = TestFolder.DAY_16;

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_1_SAMPLE)
	public void testSolvePart1Sample() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);
		ReindeerMaze reindeerMaze = new ReindeerMaze(inputs);

		assertEquals(7036L,reindeerMaze.solveA());
	}

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_1_SAMPLE)
	public void testSolvePart1Sample2() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE_SINGLE_SAMPLE_2);
		ReindeerMaze reindeerMaze = new ReindeerMaze(inputs);

		assertEquals(11048L,reindeerMaze.solveA());
	}

	@Test
	@Order(2)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_1_INPUT)
	public void testSolvePart1Input() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE);
		ReindeerMaze reindeerMaze = new ReindeerMaze(inputs);

		System.out.println("Solution: "+reindeerMaze.solveA());
		assertEquals(0L,0L);

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
