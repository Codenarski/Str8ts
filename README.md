# Str8ts

## Motivation
This project was implemented as part of the lecture Software Engineering 2 of the dual study of Applied Computer Science at the DHBW Mosbach.

## Description
Str8ts is a kind of logic puzzle that has similarities with Sudoku. Also in Str8ts, a 9 × 9 grid is filled with the digits 1 to 9 in such a way that each digit occurs only once in each column and in each row. Unlike Sudoku, Str8ts also has black squares like in crossword puzzles. Only the white fields are filled. Black fields can be empty or prefilled with a digit.

The name "Str8ts" is derived from "straight", the "street" in poker. "Str8ts" is pronounced like the English word "straights". Contiguous white squares in rows or columns form a straight in Str8ts, so they must contain a sequence of contiguous numbers. The sequence is arbitrary.

## How to play
This project offers the possibility to import your own playing fields using a Json file. This Json file can be loaded after the start via drag and drop on the Gui. An example file is included in the resource folder. Please make sure that the exact format is followed.

The game can be played with the help of the included GUI. In addition, there is the possibility to use F6 (Step) or F7 (Solve) to release the board from the integrated solver. 

The solver uses the simplest known Str8ts strategies to reduce the number of candidates per field. If these strategies are no longer sufficient, a backtracking procedure is used with the remaining candidates, so all fields can be solved.
