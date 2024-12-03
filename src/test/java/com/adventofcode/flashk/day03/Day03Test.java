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

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName(TestDisplayName.DAY_03)
@TestMethodOrder(OrderAnnotation.class)
public class Day03Test extends PuzzleTest {

	private static final String INPUT_FOLDER = TestFolder.DAY_03;

	@Test
	@Order(1)
	@Tag(TestTag.PART_ONE)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_ONE_SAMPLE)
	public void testSolvePart1Sample() {
		
		System.out.print("1 | sample | ");
		
		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);

		MullItOver mullItOver = new MullItOver(inputs);

		assertEquals(161, mullItOver.solveA());
	}
	
	@Test
	@Order(2)
	@Tag(TestTag.PART_ONE)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_ONE_INPUT)
	public void testSolvePart1Input() {
		
		System.out.print("1 | input  | ");
		
		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);
		MullItOver mullItOver = new MullItOver(inputs);

		assertEquals(160672468, mullItOver.solveA());

	}

	@Test
	@Order(2)
	@Tag(TestTag.PART_ONE)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_ONE_DEBUG)
	public void testSolvePart1InputByLines() {

		System.out.print("1 | input  | ");

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);


		MullItOver mullItOver = new MullItOver(List.of(inputs.get(0)));

		assertEquals(30243322, mullItOver.solveA());

		mullItOver = new MullItOver(List.of(inputs.get(1)));
		assertEquals(29041171, mullItOver.solveA());

		mullItOver = new MullItOver(List.of(inputs.get(2)));
		assertEquals(22674551, mullItOver.solveA());

		mullItOver = new MullItOver(List.of(inputs.get(3)));
		assertEquals(26491961, mullItOver.solveA());

		mullItOver = new MullItOver(List.of(inputs.get(4)));
		assertEquals(24532437, mullItOver.solveA());

		mullItOver = new MullItOver(List.of(inputs.get(5)));
		assertEquals(27689026, mullItOver.solveA());


	}

	@Test
	@Order(2)
	@Tag(TestTag.PART_TWO)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_TWO_DEBUG)
	void testSolvePart2InputByLines() {

		System.out.print("1 | input  | ");

		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);


		MullItOver mullItOver = new MullItOver(List.of(inputs.get(0)));

		assertEquals(26546863, mullItOver.solveB());

	}
	
	@Test
	@Order(3)
	@Tag(TestTag.PART_TWO)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_TWO_SAMPLE)
	public void testSolvePart2Sample() {
		
		System.out.print("2 | sample | ");
		
		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);
		MullItOver mullItOver = new MullItOver(List.of("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"));

		assertEquals(48, mullItOver.solveB());
	}
	
	@Test
	@Order(4)
	@Tag(TestTag.PART_TWO)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_TWO_INPUT)
	public void testSolvePart2Input() {
		
		System.out.print("2 | input  | ");
		
		// Read input file
		List<String> inputs = Input.readStringLines(INPUT_FOLDER, TestFilename.INPUT_FILE);
		MullItOver mullItOver = new MullItOver(inputs);

		assertEquals(84893551, mullItOver.solveB());

	}


}
