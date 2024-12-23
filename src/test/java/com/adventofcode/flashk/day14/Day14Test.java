package com.adventofcode.flashk.day14;

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
import com.adventofcode.flashk.common.test.utils.Input;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName(TestDisplayName.DAY_14)
@TestMethodOrder(OrderAnnotation.class)
class Day14Test {

	private static final String INPUT_FOLDER = TestFolder.DAY_14;

	private static final int MAX_ROWS_SAMPLE = 7;
	private static final int MAX_COLS_SAMPLE = 11;

	private static final int MAX_ROWS_INPUT = 103;
	private static final int MAX_COLS_INPUT = 101;

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_1_SAMPLE)
	void part1SampleTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);

		RestroomRedoubt restroomRedoubt = new RestroomRedoubt(inputs, MAX_ROWS_SAMPLE, MAX_COLS_SAMPLE);

		assertEquals(12L,restroomRedoubt.solveA(100));
	}

	@Test
	@Order(2)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_1_INPUT)
	void part1InputTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);
		RestroomRedoubt restroomRedoubt = new RestroomRedoubt(inputs, MAX_ROWS_INPUT, MAX_COLS_INPUT);

		assertEquals(214109808,restroomRedoubt.solveA(100));

	}


	@Test
	@Order(3)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_2_INPUT)
	@Disabled("[Disabled] Day 14 - part2InputTest: Takes a long time to execute, must be optimized.")
	void part2InputTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);
		RestroomRedoubt restroomRedoubt = new RestroomRedoubt(inputs, MAX_ROWS_INPUT, MAX_COLS_INPUT);

		assertEquals(7687, restroomRedoubt.solveB());

	}

}
