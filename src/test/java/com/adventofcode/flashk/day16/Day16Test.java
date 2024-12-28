package com.adventofcode.flashk.day16;


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

@DisplayName(TestDisplayName.DAY_16)
@TestMethodOrder(OrderAnnotation.class)
class Day16Test extends PuzzleTest {

	private static final String INPUT_FOLDER = TestFolder.DAY_16;

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_1_SAMPLE)
	public void testSolvePart1Sample() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);
		ReindeerMaze reindeerMaze = new ReindeerMaze(inputs);

		assertEquals(7036L,reindeerMaze.solveA2());
	}

	@Test
	@Order(1)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_1_SAMPLE)
	public void testSolvePart1Sample2() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE_SINGLE_SAMPLE_2);
		ReindeerMaze reindeerMaze = new ReindeerMaze(inputs);

		assertEquals(11048L,reindeerMaze.solveA2());
	}

	@Test
		@Order(2)
	@Tag(TestTag.PART_1)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_1_INPUT)
	public void testSolvePart1Input() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE);
		ReindeerMaze reindeerMaze = new ReindeerMaze(inputs);

		assertEquals(103512L,reindeerMaze.solveA2());

	}

	/*
	Hay varios mejores caminos (el mejor camino no es único).
	Por lo tanto, se trata de encontrar todos los posibles mejores caminos diferentes y contar
	cuantas celdas diferentes hay en todos esos caminos solapados entre si.

	Por ejemplo, en el primer caso de ejemplo hay 3 posibles caminos con la misma puntuación:

	Camino 1:
	###############
	#.......#....E#
	#.#.###.#.###^#
	#.....#.#...#^#
	#.###.#####.#^#
	#.#.#.......#^#
	#.#.#####.###^#
	#..>>>>>>>>v#^#
	###^#.#####v#^#
	#>>^#.....#v#^#
	#^#.#.###.#v#^#
	#^....#...#v#^#
	#^###.#.#.#v#^#
	#S..#.....#>>^#
	###############

	Camino 2:
	###############
	#.......#....E#
	#.#.###.#.###^#
	#.....#.#...#^#
	#.###.#####.#^#
	#.#.#.......#^#
	#.#.#####.###^#
	#..>>>>>>>>v#^#
	###^#.#####v#^#
	#..^#.....#v#^#
	#.#^#.###.#v#^#
	#>>^..#...#v#^#
	#^###.#.#.#v#^#
	#S..#.....#>>^#
	###############

	Camino 3:
	###############
	#.......#....E#
	#.#.###.#.###^#
	#.....#.#...#^#
	#.###.#####.#^#
	#.#.#.......#^#
	#.#.#####.###^#
	#....>>>>>>v#^#
	###.#^#####v#^#
	#...#^....#v#^#
	#.#.#^###.#v#^#
	#>>>>^#...#v#^#
	#^###.#.#.#v#^#
	#S..#.....#>>^#
	###############

	Si nos fijamos en el punto en el cuál todos estos caminos coinciden,
	para llegar a dicho punto, todos los caminos hacen:
	- 4 giros
	- 7 avances

	###############
	#.......#....O#
	#.#.###.#.###O#
	#.....#.#...#O#
	#.###.#####.#O#
	#.#.#.......#O#
	#.#.#####.###O#
	#..OOOOOOOOO#O#
	###O#O#####O#O#
	#OOO#O....#O#O#
	#O#O#O###.#O#O#
	#OOOOO#...#O#O#
	#O###.#.#.#O#O#
	#O..#.....#OOO#
	###############

	 */
	@Test
	@Order(3)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE)
	public void testSolvePart2Sample() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE_SAMPLE);
		ReindeerMaze reindeerMaze = new ReindeerMaze(inputs);

		// Me da
		assertEquals(45L, reindeerMaze.solveB2());
	}

	@Test
	@Order(4)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE)
	void testSolvePart2Sample2() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE_SINGLE_SAMPLE_2);
		ReindeerMaze reindeerMaze = new ReindeerMaze(inputs);

		assertEquals(64L, reindeerMaze.solveB2());
	}

	@Test
	@Order(5)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_2_INPUT)
	public void testSolvePart2Input() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE);
		ReindeerMaze reindeerMaze = new ReindeerMaze(inputs);

		assertEquals(554L,reindeerMaze.solveB2());

	}


	@Test
	@Order(6)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_2_DEBUG)
	void testSolvePart2Debug() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, "debug_edge_1.input");
		ReindeerMaze reindeerMaze = new ReindeerMaze(inputs);

		// Source: https://www.reddit.com/r/adventofcode/comments/1hfhgl1/2024_day_16_part_1_alternate_test_case/

		// Should fill:
		// ###########################
		// #######################..O#
		// ######################..#O#
		// #####################..##O#
		// ####################..###O#
		// ###################..##OOO#
		// ##################..###O###
		// #################..####OOO#
		// ################..#######O#
		// ###############..##OOOOOOO#
		// ##############..###O#######
		// #############..####OOOOOOO#
		// ############..###########O#
		// ###########..##OOOOOOOOOOO#
		// ##########..###O###########
		// #########..####OOOOOOOOOOO#
		// ########..###############O#
		// #######..##OOOOOOOOOOOOOOO#
		// ######..###O###############
		// #####..####OOOOOOOOOOOOOOO#
		// ####..###################O#
		// ###..##OOOOOOOOOOOOOOOOOOO#
		// ##..###O###################
		// #..####OOOOOOOOOOOOOOOOOOO#
		// #.#######################O#
		// #OOOOOOOOOOOOOOOOOOOOOOOOO#
		// ###########################

		assertEquals(149L,reindeerMaze.solveB2());

	}

	@Test
	@Order(6)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.INPUT)
	@DisplayName(TestDisplayName.PART_2_DEBUG_2)
	@Disabled("[Disabled] Day 16 - testSolvePart2Debug: Debug test case that takes 4s, enable it only for debugging.")
	void testSolvePart2Debug2() {

		// DISCLAIMER: This test is disabled as it takes 3 seconds to finish, enable it only for debugging purposes

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, "debug_edge_2.input");

		// Source: https://www.reddit.com/r/adventofcode/comments/1hfhgl1/2024_day_16_part_1_alternate_test_case/

		// ########################################################
		// #.........#.........#.........#.........#.........#...O#
		// #...OOOOOO#...OOOOOO#...OOOOOO#...OOOOOO#...OOOOOO#...O#
		// #...O#...O#...O#...O#...O#...O#...O#...O#...O#...O#...O#
		// #...O#...O#...O#...O#...O#...O#...O#...O#...O#...O#...O#
		// #...O#...O#...O#...O#...O#...O#...O#...O#...O#...O#...O#
		// #...O#...O#...O#...O#...O#...O#...O#...O#...O#...O#...O#
		// #...O#...OOOOOO#...OOOOOO#...OOOOOO#...OOOOOO#...OOOOOO#
		// #OOOO#.........#.........#.........#.........#.........#
		// ########################################################

		// Camino inicial:
		// 21 giros
		// 110 pasos
		// Coste total = 21100

		// Tras ejecutar el algoritmo de la parte 2 debería quedarse así:
		// ########################################################
		// #.........#.........#.........#.........#.........#...O#
		// #OOOOOOOOO#OOOOOOOOO#OOOOOOOOO#OOOOOOOOO#OOOOOOOOO#...O#
		// #OOOO#OOOO#OOOO#OOOO#OOOO#OOOO#OOOO#OOOO#OOOO#OOOO#...O#
		// #OOOO#OOOO#OOOO#OOOO#OOOO#OOOO#OOOO#OOOO#OOOO#OOOO#...O#
		// #OOOO#OOOO#OOOO#OOOO#OOOO#OOOO#OOOO#OOOO#OOOO#OOOO#...O#
		// #OOOO#OOOO#OOOO#OOOO#OOOO#OOOO#OOOO#OOOO#OOOO#OOOO#...O#
		// #OOOO#OOOOOOOOO#OOOOOOOOO#OOOOOOOOO#OOOOOOOOO#OOOOOOOOO#
		// #OOOO#.........#.........#.........#.........#.........#
		// ########################################################
		//
		//  28 círculos caja izquierda
		// 216 círculos caja centrales
		//  10 círculos verticales
		//	10 círculos en la última caja
		//
		// Total = 264
		// Cualquiera de los caminos óptimos tendría un coste de: 21110

		ReindeerMaze reindeerMaze = new ReindeerMaze(inputs);

		assertEquals(264L,reindeerMaze.solveB2());

	}
}
