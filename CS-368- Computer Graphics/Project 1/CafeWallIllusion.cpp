/**
Author: @Henry Qiu
**/

#include <gl/glut.h>
void display(void) {
	glClear(GL_COLOR_BUFFER_BIT);
	glColor3f(0.0, 0.0, 0.0); //sets the color of squares to black
	glBegin(GL_QUADS);
	float a = 0.0; //starting values for the square
	float c = 0.5;
	float b = 30.0;
	float d = 30.5;
	for (int i = 0; i < 15; i++) { //for loops, which will generate a board of black and white squares --rows
		if (i % 2 == 0) { //changes the color of the squares depending on whether i is even or odd
			glColor3f(0.0, 0.0, 0.0); 
		}
		else if (i % 2 == 1) {
			glColor3f(1.0, 1.0, 1.0);
		}
		for (int j = 0; j < 15; j++) { //--columns
			if (j % 2 == 0) {//depending on whether j is even or odd, the position of squares are shifted
				glVertex2f(a, c);
				glVertex2f(b, c); //these four instructions will draw the square
				glVertex2f(b, d);
				glVertex2f(a, d);
			}
			else if (j % 2 == 1) {
				glVertex2f(a-15, c);
				glVertex2f(b-15, c); //these four instructions will draw the square
				glVertex2f(b-15, d);
				glVertex2f(a-15, d);
			}
			c += 31; // the endpoints of each square being altered from bottom to top
			d += 31;
		}
		c = 0.5; // endpoints are reset as the for loop goes onto the next column
		d = 30.5;
		a += 30; // the endpoints of each sqaure being altered from left to right
		b += 30;
	}
	glEnd();
	glFlush();
}

void main(int argc, char** argv) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowSize(300, 300);
	glutInitWindowPosition(200, 100);
	glutCreateWindow("Hermann Grid Illusion");
	glClearColor(0.5, 0.5, 0.5, 0.0); //sets background of window to grey, acts as "mortar"
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluOrtho2D(0.0, 300.0, 0.0, 300.0);
	glutDisplayFunc(display);
	glutMainLoop();

}