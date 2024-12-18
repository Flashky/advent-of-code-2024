package com.adventofcode.flashk.day03;

import java.util.List;

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

import static com.adventofcode.flashk.common.test.constants.TestFilename.INPUT_FILE_SAMPLE_PART_2;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName(TestDisplayName.DAY_03)
@TestMethodOrder(OrderAnnotation.class)
class Day03Test extends PuzzleTest {

	private static final String INPUT_FOLDER = TestFolder.DAY_03;

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_1_SAMPLE)
	public void testSolvePart1Sample() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);

		MullItOver mullItOver = new MullItOver(inputs);

		assertEquals(161, mullItOver.solve(false));
	}
	
	@Test
	@Order(2)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_1_INPUT)
	public void testSolvePart1Input() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);
		MullItOver mullItOver = new MullItOver(inputs);

		assertEquals(160672468, mullItOver.solve(false));

	}


	@Test
	@Order(2)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_2_DEBUG)
	void testSolvePart2InputByLines() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);


		MullItOver mullItOver = new MullItOver(List.of(inputs.get(0)));

		assertEquals(26546863, mullItOver.solve(true));

	}
	
	@Test
	@Order(3)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE)
	public void testSolvePart2Sample() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, INPUT_FILE_SAMPLE_PART_2);
		MullItOver mullItOver = new MullItOver(inputs);

		assertEquals(48, mullItOver.solve(true));
	}
	
	@Test
	@Order(4)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_2_INPUT)
	public void testSolvePart2Input() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);
		MullItOver mullItOver = new MullItOver(inputs);

		assertEquals(84893551, mullItOver.solve(true));

	}


}
