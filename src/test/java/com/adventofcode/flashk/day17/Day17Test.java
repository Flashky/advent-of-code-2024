package com.adventofcode.flashk.day17;

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

@DisplayName(TestDisplayName.DAY_17)
@TestMethodOrder(OrderAnnotation.class)
class Day17Test extends PuzzleTest {

	private static final String INPUT_FOLDER = TestFolder.DAY_17;

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_1_DEBUG_1)
	void part1Debug1Test() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE_SINGLE_SAMPLE);

		ChronospatialComputer chronospatialComputer = new ChronospatialComputer(inputs);
		chronospatialComputer.solveA();

		assertEquals(1, chronospatialComputer.getB());
	}

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_1_DEBUG_2)
	void part1Debug2Test() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE_SINGLE_SAMPLE_2);

		ChronospatialComputer chronospatialComputer = new ChronospatialComputer(inputs);

		assertEquals("0,1,2", chronospatialComputer.solveA());
	}

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_1_DEBUG_3)
	void part1Debug3Test() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE_SINGLE_SAMPLE_3);

		ChronospatialComputer chronospatialComputer = new ChronospatialComputer(inputs);

		// Register A: 2024
		// Register B: 0
		// Register C: 0

		// Program: 0,1,5,4,3,0

		assertEquals("4,2,5,6,7,7,7,7,3,1,0", chronospatialComputer.solveA());
		assertEquals(0, chronospatialComputer.getA());
	}

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_1_DEBUG_4)
	void part1Debug4Test() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE_SINGLE_SAMPLE_4);

		ChronospatialComputer chronospatialComputer = new ChronospatialComputer(inputs);
		chronospatialComputer.solveA();

		// Se permite el operador reservado porque en este caso, el input tiene un operator 7 que hay que debuggear.

		assertEquals(26, chronospatialComputer.getB());
	}

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_1_DEBUG_5)
	void part1Debug5Test() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE_SINGLE_SAMPLE_5);

		ChronospatialComputer chronospatialComputer = new ChronospatialComputer(inputs);
		chronospatialComputer.solveA();

		assertEquals(44354, chronospatialComputer.getB());
	}

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_1_SAMPLE)
	public void testSolvePart1Sample() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);

		ChronospatialComputer chronospatialComputer = new ChronospatialComputer(inputs);

		assertEquals("4,6,3,5,6,3,5,2,1,0", chronospatialComputer.solveA());
	}

	@Test
	@Order(2)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_1_INPUT)
	public void testSolvePart1Input() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);

		ChronospatialComputer chronospatialComputer = new ChronospatialComputer(inputs);

		assertEquals("6,7,5,2,1,3,5,1,7",chronospatialComputer.solveA());

	}

	@Test
	void programInputTest() {
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);

		Program program = new Program(inputs);
		assertEquals("6,7,5,2,1,3,5,1,7", program.execute());
	}

	@Test
	@Order(3)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE)
	@Disabled("[Disabled] Day 17 - testSolvePart2Sample: Work in Progress")
	public void testSolvePart2Sample() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE_PART_2);

		ChronospatialComputer chronospatialComputer = new ChronospatialComputer(inputs);

		assertEquals(117440L ,chronospatialComputer.solveB());

	}

	@Test
	@Order(4)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_2_INPUT)
	@Disabled("Day 17 - Doesn't give the expected output")
	public void testSolvePart2Input() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);

		ChronospatialComputer chronospatialComputer = new ChronospatialComputer(inputs);

		assertEquals(216549846240877L,chronospatialComputer.solveB());

	}

	@Test
	@Order(4)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_2_INPUT + " - Program")
	void part2InputWithProgramTest() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);

		Program program = new Program(inputs);

		// Help: https://www.reddit.com/r/adventofcode/comments/1hn01ke/2024_day_17_part_2_code_works_until_certain/
		assertEquals(216549846240877L, program.solveB());

	}

}
