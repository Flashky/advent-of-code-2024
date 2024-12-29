package com.adventofcode.flashk.day21;

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

@DisplayName(TestDisplayName.DAY_21)
@TestMethodOrder(OrderAnnotation.class)
@Disabled("[Disabled] Day 21 - Work in Progress")
class Day21Test {

	private static final String INPUT_FOLDER = TestFolder.DAY_21;

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_1_SAMPLE)
	void part1SampleTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);

		KeypadConundrum keypadConundrum = new KeypadConundrum(inputs, 2);

		assertEquals(126384L, keypadConundrum.solveA());

	}

	@Test
	@Order(2)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_1_INPUT)
	void part1InputTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);

		KeypadConundrum keypadConundrum = new KeypadConundrum(inputs, 2);

		assertEquals(206798L,keypadConundrum.solveA());

	}


	@Test
	@Order(4)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_2_SAMPLE)
	void part2SampleTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);

		KeypadConundrum keypadConundrum = new KeypadConundrum(inputs, 25);

		// Source: https://www.reddit.com/r/adventofcode/comments/1hjb7hh/2024_day_21_part_2_can_someone_share_what_the/

		assertEquals(154115708116294L,keypadConundrum.solveA());

	}

	@Test
	@Order(4)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_2_INPUT)
	void part2InputTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);

		KeypadConundrum keypadConundrum = new KeypadConundrum(inputs, 25);

		assertEquals(251508572750680L,keypadConundrum.solveA());

	}

}
