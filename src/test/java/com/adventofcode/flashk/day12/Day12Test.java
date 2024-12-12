package com.adventofcode.flashk.day12;

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

@DisplayName(TestDisplayName.DAY_12)
@TestMethodOrder(OrderAnnotation.class)
@Disabled // TODO Remove comment when implemented
public class Day12Test extends PuzzleTest {

	private static final String INPUT_FOLDER = TestFolder.DAY_12;

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_1_SAMPLE)
	public void testSolvePart1Sample() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);

		GardenGroups gardenGroups = new GardenGroups(inputs);
		assertEquals(1930L, gardenGroups.solveA());
	}

	@Test
	@Order(2)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_1_INPUT)
	public void testSolvePart1Input() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE);

		GardenGroups gardenGroups = new GardenGroups(inputs);
		assertEquals(1424472L,gardenGroups.solveA());

	}

	@Test
	@Order(3)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE)
	public void testSolvePart2Sample() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);
		GardenGroups gardenGroups = new GardenGroups(inputs);

		assertEquals(1206L,gardenGroups.solveB());
	}

	@Test
	@Order(3)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE)
	public void testSolvePart2SingleSample() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE_SINGLE_SAMPLE);
		GardenGroups gardenGroups = new GardenGroups(inputs);

		assertEquals(80L,gardenGroups.solveB());
	}

	@Test
	@Order(3)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE_2)
	public void testSolvePart2SingleSample2() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE_SINGLE_SAMPLE_2);
		GardenGroups gardenGroups = new GardenGroups(inputs);

		assertEquals(436L,gardenGroups.solveB());
	}

	@Test
	@Order(3)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE_3)
	public void testSolvePart2SingleSample3() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE_SINGLE_SAMPLE_3);
		GardenGroups gardenGroups = new GardenGroups(inputs);

		assertEquals(236L,gardenGroups.solveB());
	}

	@Test
	@Order(3)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE_4)
	public void testSolvePart2SingleSample4() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE_SINGLE_SAMPLE_4);
		GardenGroups gardenGroups = new GardenGroups(inputs);

		assertEquals(368L,gardenGroups.solveB());
	}

	@Test
	@Order(3)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE_5)
	public void testSolvePart2SingleSample5() {

		// Reddit: [2024 Day 12] Another test case
		// https://www.reddit.com/r/adventofcode/s/Vxjv0Hf8J0

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE_SINGLE_SAMPLE_5);
		GardenGroups gardenGroups = new GardenGroups(inputs);

		// Should be:
		// A - 39 blocks - 22 fences
		// C - 2 blocks - 4 fences
		// B - 4 blocks - 4 fences
		// D - 2 blocks - 4 fences
		// B - 4 blocks - 4 fences
		// D - 5 blocks - 8 fences

		// Región A explicada:
		// AAAAAAAA
		// AA.....A
		// AA...AAA
		// A..AAAAA
		// A..A...A
		// AAAA.A.A
		// AAAAAAAA
		//
		//
		// Filas exteriores: 4
		// Filas horizontales: 9
		// Filas verticales: 9
		//
		// Total = 4 + 9 + 9 = 22

		assertEquals(946L, gardenGroups.solveB());
	}

	@Test
	@Order(3)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE_6)
	public void testSolvePart2SingleSample6() {

		// Reddit: [2024 Day 12 (Part 2)] I am losing my mind.
		// https://www.reddit.com/r/adventofcode/s/imxtVGMII2

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE_SINGLE_SAMPLE_6);
		GardenGroups gardenGroups = new GardenGroups(inputs);

		assertEquals(160L,gardenGroups.solveB());
	}

	@Test
	@Order(4)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_2_INPUT)
	public void testSolvePart2Input() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE);
		GardenGroups gardenGroups = new GardenGroups(inputs);
		System.out.println("Solution: "+gardenGroups.solveB());

		// 875718 -> Too high
		// 871792 -> Too high (tras ajustar lógica para los convexos internos.
		assertEquals(0L,0L);

	}

}
