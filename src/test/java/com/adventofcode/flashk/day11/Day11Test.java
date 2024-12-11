package com.adventofcode.flashk.day11;

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

@DisplayName(TestDisplayName.DAY_11)
@TestMethodOrder(OrderAnnotation.class)
@Disabled // TODO Remove comment when implemented
public class Day11Test extends PuzzleTest {

	private static final String INPUT_FOLDER = TestFolder.DAY_11;

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_1_SAMPLE)
	public void testSolvePart1Sample() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);

		PlutonianPebbles plutonianPebbles = new PlutonianPebbles(inputs);

		assertEquals(22L ,plutonianPebbles.solveA(6));

		plutonianPebbles = new PlutonianPebbles(inputs);
		assertEquals(55312L ,plutonianPebbles.solveA(25));

	}

	@Test
	@Order(2)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_1_INPUT)
	public void testSolvePart1Input() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);
		PlutonianPebbles plutonianPebbles = new PlutonianPebbles(inputs);

		System.out.println("Solution: "+plutonianPebbles.solveA(75));
		//assertEquals(189547L,0L);

	}

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE)
	public void testSolvePart2Sample() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);

		PlutonianPebbles plutonianPebbles = new PlutonianPebbles(inputs);

		assertEquals(22L ,plutonianPebbles.solveB(6));

		plutonianPebbles = new PlutonianPebbles(inputs);
		assertEquals(55312L ,plutonianPebbles.solveB(25));

	}

	@Test
	@Order(4)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_2_INPUT)
	public void testSolvePart2Input() {

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);

		PlutonianPebbles plutonianPebbles = new PlutonianPebbles(inputs);

		assertEquals(224577979481346L, plutonianPebbles.solveB(75));

	}

}
