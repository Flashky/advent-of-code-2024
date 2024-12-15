package com.adventofcode.flashk.day15;

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

@DisplayName(TestDisplayName.DAY_15)
@TestMethodOrder(OrderAnnotation.class)
public class Day15Test extends PuzzleTest {

	private static final String INPUT_FOLDER = TestFolder.DAY_15;


	@Test
	@Order(0)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_ONE_SINGLE_SAMPLE)
	public void testSolvePart1SingleSample() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE_SINGLE_SAMPLE);

		WarehouseWoes warehouseWoes = new WarehouseWoes(inputs);

		assertEquals(2028L, warehouseWoes.solveA());

	}

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_1_SAMPLE)
	public void testSolvePart1Sample() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);

		WarehouseWoes warehouseWoes = new WarehouseWoes(inputs);

		assertEquals(10092L,warehouseWoes.solveA());
	}



	@Test
	@Order(2)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_1_INPUT)
	public void testSolvePart1Input() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);
		WarehouseWoes warehouseWoes = new WarehouseWoes(inputs);

		assertEquals(1294459L,warehouseWoes.solveA());

	}

	@Test
	@Order(3)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE)
	public void testSolvePart2Sample1() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE_PART_2);

		ScaledWarehouseWoes scaledWarehouseWoes = new ScaledWarehouseWoes(inputs);

		// Not in puzzle test, but manually calculated:
		// 1,5 = 100*1 + 5 = 105
		// 2,7 = 100*2 + 7 = 207
		// 3,6 = 100*3 + 6 = 306
		// Total: 618

		assertEquals(618L,scaledWarehouseWoes.solveB());
	}

	@Test
	@Order(3)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE)
	public void testSolvePart2Sample() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);

		ScaledWarehouseWoes scaledWarehouseWoes = new ScaledWarehouseWoes(inputs);

		assertEquals(9021L,scaledWarehouseWoes.solveB());
	}

	@Test
	@Order(4)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_2_INPUT)
	public void testSolvePart2Input() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);

		ScaledWarehouseWoes scaledWarehouseWoes = new ScaledWarehouseWoes(inputs);

		assertEquals(1319212L,scaledWarehouseWoes.solveB());

	}

	@Test
	@Order(4)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_2_DEBUG)
	public void testSolvePart2Debug1() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, "debug_vertical_edge_1.input");

		// Edge case:
		// ############################
		// ##........................##
		// ##........................##
		// ##........................##
		// ##........................##
		// ##........................##
		// ##........................##
		// ##........................##
		// ##........................##
		// ##..............##........##
		// ##............[][]........##
		// ##.............[].........##
		// ##..............@.........##
		// ############################

		ScaledWarehouseWoes scaledWarehouseWoes = new ScaledWarehouseWoes(inputs);

		// 10,14 -> 100*10 + 14 = 1014
		// 10,16 -> 100*10 + 16 = 1016
		// 11,15 -> 100*11 + 15 = 1115
		// Total: 3145

		assertEquals(3145L, scaledWarehouseWoes.solveB());

	}

	@Test
	@Order(4)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_2_DEBUG)
	public void testSolvePart2Debug3() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, "debug_vertical_edge_2.input");

		// Edge case:
		// ############################
		// ##........................##
		// ##........................##
		// ##........................##
		// ##........................##
		// ##........................##
		// ##........................##
		// ##........................##
		// ##........................##
		// ##............##..........##
		// ##............[][]........##
		// ##.............[].........##
		// ##..............@.........##
		// ############################

		ScaledWarehouseWoes scaledWarehouseWoes = new ScaledWarehouseWoes(inputs);

		// 10,14 -> 100*10 + 14 = 1014
		// 10,16 -> 100*10 + 16 = 1016
		// 11,15 -> 100*11 + 15 = 1115
		// Total: 3145

		assertEquals(3145L, scaledWarehouseWoes.solveB());

	}

}
