/**
Author: @Henry Qiu
**/

#include <gl/glut.h>
void display(void) {
	glClear(GL_COLOR_BUFFER_BIT);
	glBegin(GL_QUADS);
	float max = 300.0; //max length
	float min = 300.0; //max height
	float update = 0.0; //starting/updated coordinate
	float green = 0.0; //color code of green set to 0.0 intensity
	for (int i = 0; i <= 15; i++) { //for loop to construct pyramid illusion with a limit of 15 squares
		glColor3f(0.0, green, 1.0); //sets the color of the squares
		
		glVertex2f(update, update); //coordinates of each sqaure in pyramid illusion
		glVertex2f(max - update, update);
		glVertex2f(max - update, min - update);
		glVertex2f(update, min - update);
		
		update += 10.0; //updates the new coordinates of squares
		green += 0.05; //increments the hue of color green
	}
	glEnd();
	glFlush();
}

void main(int argc, char** argv) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowSize(300, 300);
	glutInitWindowPosition(200, 100);
	glutCreateWindow("Pyramid Illusion");
	glClearColor(255.0, 255.0, 255.0, 0.0);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluOrtho2D(0.0, 300.0, 0.0, 300.0);
	glutDisplayFunc(display);
	glutMainLoop();

}