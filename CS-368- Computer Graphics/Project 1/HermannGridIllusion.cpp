/**
		Author: @Henry Qiu
**/

#include <gl/glut.h>
void displayHGI(void) {
	glClear(GL_COLOR_BUFFER_BIT);
	glColor3f(0.0, 0.0, 0.0); //sets the color of squares to black
	glBegin(GL_QUADS); //declares the drawing of quads
	float a = 5.0; //starting values for the square
	float c = 5.0;
	float b = 55.0;
	float d = 55.0;
	for (int i = 0; i < 5; i++) { //for loops, which will generate a 5x5 board of black squares --rows
		for (int j = 0; j < 5; j++) { //--columns
			glVertex2f(a, c); 
			glVertex2f(b, c); //these four instructions will draw the square
			glVertex2f(b, d);
			glVertex2f(a, d);
			c += 60; // the endpoints of each square being altered from bottom to top
			d += 60;
		}
		c = 5.0; // endpoints are reset as the for loop goes onto the next column
		d = 55.0;
		a += 60; // the endpoints of each sqaure being altered from left to right
		b += 60;
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
	glClearColor(255.0, 255.0, 255.0, 0.0);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluOrtho2D(0.0, 300.0, 0.0, 300.0);
	glutDisplayFunc(displayHGI);
	glutMainLoop();

}