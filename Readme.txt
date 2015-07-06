Legespiel-Solver was written to accompany the following blog post:

http://whatsoftwarecando.org/en/backtracking-nursery-solve-legespiel/

The Class org.whatsoftwarecando.legespiel.Solver can find all solutions
of a certain kind of tile-based game (German: "Legespiel"), in which cards have
to be put in a square (or sometimes a rectangle) so that the pictures on the
four sides of the cards fit the ones of the cards around it.

You can solve other games of this kind by creating an own implementation of
IGameConfig. By doing that, you can change the dimensions of the playing field
and the pictures on the cards. When you are done, you can use the class name
of your GameConfig as a program argument for Solver (also see .launch-Files in
this folder).


The software is published under the terms of the following license:


Copyright (c) 2014 Andreas Keilhauer (www.keilhauer.eu)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.