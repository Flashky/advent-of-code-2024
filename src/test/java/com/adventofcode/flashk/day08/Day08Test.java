package com.adventofcode.flashk.day08;

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

@DisplayName(TestDisplayName.DAY_08)
@TestMethodOrder(OrderAnnotation.class)
class Day08Test extends PuzzleTest {

	private static final String INPUT_FOLDER = TestFolder.DAY_08;

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_1_SAMPLE)
	public void testSolvePart1Sample() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);
		ResonantCollinearity resonantCollinearity = new ResonantCollinearity(inputs);

		assertEquals(14L, resonantCollinearity.solve(false));
	}
	
	@Test
	@Order(2)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_1_INPUT)
	public void testSolvePart1Input() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE);
		ResonantCollinearity resonantCollinearity = new ResonantCollinearity(inputs);

		assertEquals(332L, resonantCollinearity.solve(false));
	}

	@Test
	@Order(3)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.DEBUG)
	@DisplayName(TestDisplayName.PART_2_DEBUG)
	void testSolvePart2Debug() {
		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE_PART_2);
		ResonantCollinearity resonantCollinearity = new ResonantCollinearity(inputs);
		assertEquals(9L, resonantCollinearity.solve(true));

	}

	@Test
	@Order(3)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE)
	public void testSolvePart2Sample() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);
		ResonantCollinearity resonantCollinearity = new ResonantCollinearity(inputs);
		assertEquals(34L, resonantCollinearity.solve(true));
	}
	
	@Test
	@Order(4)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_2_INPUT)
	public void testSolvePart2Input() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE);
		ResonantCollinearity resonantCollinearity = new ResonantCollinearity(inputs);

		assertEquals(1174L, resonantCollinearity.solve(true));
	}

}
