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
@Disabled // TODO Remove comment when implemented
public class Day16Test extends PuzzleTest {

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
		assertEquals(45L, reindeerMaze.solveB());
	}

	@Test
	@Order(4)
	@Tag(TestTag.PART_2)
	@Tag(TestTag.SAMPLE)
	@DisplayName(TestDisplayName.PART_2_SAMPLE)
	public void testSolvePart2Sample2() {

		// Read input file
		char[][] inputs = Input.read2DCharArray(INPUT_FOLDER, TestFilename.INPUT_FILE_SINGLE_SAMPLE_2);
		ReindeerMaze reindeerMaze = new ReindeerMaze(inputs);
		// Me da
		assertEquals(64L, reindeerMaze.solveB());
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
		System.out.println("Solution: "+reindeerMaze.solveB());

		// 512 -> too low
		
		assertEquals(0L,0L);

	}

}
