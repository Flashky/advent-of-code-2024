package com.adventofcode.flashk.day10;

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

@DisplayName(TestDisplayName.DAY_10)
@TestMethodOrder(OrderAnnotation.class)
public class Day10Test extends PuzzleTest {

	private static final String INPUT_FOLDER = TestFolder.DAY_10;

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_1_SAMPLE)
	public void testSolvePart1Sample() {

		// Read input file
		int[][] inputs = Input.read2DIntArray(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);

		HoofIt hoofIt = new HoofIt(inputs);
		assertEquals(36L,hoofIt.solve(false));
	}
	
	@Test
	@Order(2)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_1_INPUT)
	public void testSolvePart1Input() {

		// Read input file
		int[][] inputs = Input.read2DIntArray(INPUT_FOLDER, TestFilename.INPUT_FILE);

		HoofIt hoofIt = new HoofIt(inputs);
		assertEquals(825L,hoofIt.solve(false));

	}
	
	@Test
	@Order(3)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE)
	public void testSolvePart2Sample() {

		// Read input file
		int[][] inputs = Input.read2DIntArray(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);

		HoofIt hoofIt = new HoofIt(inputs);
		assertEquals(81L, hoofIt.solve(true));
	}
	
	@Test
	@Order(4)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_2_INPUT)
	public void testSolvePart2Input() {

		// Read input file
		int[][] inputs = Input.read2DIntArray(INPUT_FOLDER, TestFilename.INPUT_FILE);

		HoofIt hoofIt = new HoofIt(inputs);
		assertEquals(1805L,hoofIt.solve(true));

	}

}
