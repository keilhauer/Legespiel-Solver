# Legespiel-Solver

## What it is And How it Works
Legespiel-Solver was written to accompany the following blog post:

[Backtracking in The Nursery – How to Solve Scramble Squares](https://whatsoftwarecando.org/backtracking-nursery-solve-scramble-squares/)

There is also a [List of Scramble Squares Puzzles with Solutions](https://whatsoftwarecando.org/list-of-scramble-squares-puzzles-with-solutions/) and a [Scramble Squares Solver Online](https://whatsoftwarecando.org/en/solve-scramble-squares-puzzles-online/) available.

The Class org.whatsoftwarecando.legespiel.Solver can find all solutions
of a certain kind of tile-based game called Scramble Squares (in German: "Legespiel"),
in which cards have to be put in a square (or sometimes a rectangle) so that the
pictures on the four sides of the cards fit the ones of the cards around it.

You can solve other games of this kind by creating your own implementation of
GameConfig. By doing that, you can change the dimensions of the playing field
and the pictures on the cards. When you are done, you can use the class name
of your GameConfig as a program argument for Solver (see .launch-Files in
this folder).
