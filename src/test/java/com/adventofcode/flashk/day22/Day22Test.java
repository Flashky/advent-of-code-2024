package com.adventofcode.flashk.day22;

import java.util.List;
import java.util.Set;

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
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DisplayName(TestDisplayName.DAY_22)
@TestMethodOrder(OrderAnnotation.class)
public class Day22Test extends PuzzleTest {

	private static final String INPUT_FOLDER = TestFolder.DAY_22;

	@Test
	@Order(0)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_ONE_SINGLE_SAMPLE)
	void buyerTest() {

		Buyer buyer = new Buyer(123, 10);
		assertEquals(5908254L, buyer.getLastSecretNumber());

		// Test prices
		assertEquals(3, buyer.getPrices().getFirst());
		assertEquals(0, buyer.getPrices().get(1));
		assertEquals(6, buyer.getPrices().get(2));
		assertEquals(5, buyer.getPrices().get(3));
		assertEquals(4, buyer.getPrices().get(4));
		assertEquals(4, buyer.getPrices().get(5));
		assertEquals(6, buyer.getPrices().get(6));
		assertEquals(4, buyer.getPrices().get(7));
		assertEquals(4, buyer.getPrices().get(8));
		assertEquals(2, buyer.getPrices().get(9));

		// Test prices variations
		assertEquals(-3, 	buyer.getPriceVariations().getFirst());
		assertEquals(6, 	buyer.getPriceVariations().get(1));
		assertEquals(-1, 	buyer.getPriceVariations().get(2));
		assertEquals(-1, 	buyer.getPriceVariations().get(3));
		assertEquals(0, 	buyer.getPriceVariations().get(4));
		assertEquals(2, 	buyer.getPriceVariations().get(5));
		assertEquals(-2, 	buyer.getPriceVariations().get(6));
		assertEquals(0, 	buyer.getPriceVariations().get(7));
		assertEquals(-2, 	buyer.getPriceVariations().get(8));

		// Test sequence generation
		Set<PriceSequence> priceSequences = buyer.getPriceSequences();

		assertEquals(7, priceSequences.size());

		// Test sequence usage
		assertEquals(4, buyer.sell(new PriceSequence(0,2,-2,0)));
		assertEquals(6, buyer.sell(new PriceSequence(-1,-1,0,2)));
	}

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_1_SAMPLE)
	public void testSolvePart1Sample() {

		// Read input file
		List<Integer> inputs = Input.readIntegerLines(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);

		MonkeyMarket monkeyMarket = new MonkeyMarket(inputs);

		assertEquals(37327623L,monkeyMarket.solveA());
	}

	@Test
	@Order(2)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_1_INPUT)
	public void testSolvePart1Input() {

		// Read input file
		List<Integer> inputs = Input.readIntegerLines(INPUT_FOLDER, TestFilename.INPUT_FILE);
		MonkeyMarket monkeyMarket = new MonkeyMarket(inputs);

		assertEquals(14622549304L,monkeyMarket.solveA());

	}

	@Test
	@Order(3)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE_2)
	void testSolvePart2SingleSample() {

		// Read input file
		MonkeyMarket monkeyMarket = new MonkeyMarket(List.of(123),10);

		assertEquals(6L, monkeyMarket.solveB());
	}

	@Test
	@Order(3)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE)
	public void testSolvePart2Sample() {

		// Read input file
		List<Integer> inputs = Input.readIntegerLines(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE_PART_2);
		MonkeyMarket monkeyMarket = new MonkeyMarket(inputs);

		assertEquals(23L,monkeyMarket.solveB());
	}

	@Test
	@Order(4)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_2_INPUT)
	@Disabled
	public void testSolvePart2Input() {

		// Read input file
		List<Integer> inputs = Input.readIntegerLines(INPUT_FOLDER, TestFilename.INPUT_FILE);

		MonkeyMarket monkeyMarket = new MonkeyMarket(inputs);

		// Brute forced
		assertEquals(1735L,monkeyMarket.solveB());

	}

}
