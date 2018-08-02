/**
		Author: @Henry Qiu
**/

#include <gl/glut.h>
void display(void) {
	glClear(GL_COLOR_BUFFER_BIT);
	glColor3f(0.0, 0.0, 255.0); //set line color to blue
	glBegin(GL_LINES); //begin drawing lines
	int a = 400; 
	int b = 0;
	for (int i = 0; i <= 20; i++) { //for loop which will draw lines which connect the top boundary
		glVertex2f(a, 200);			//to the bottom boundary with all lines intersecting in the center
		glVertex2f(b, 0);
		a -= 20;	//decrease the value of a to plot a new line
		b += 20;	//increase the value to b to plot a new line
	}
	glEnd();
	glColor3f(255.0, 0.0, 0.0); //set line color to red
	glBegin(GL_LINES); //begin drawing line
	glVertex2f(0, 125); //places a red line above the center of the window
	glVertex2f(400, 125);
	glVertex2f(0, 75); //places a red line below the center of the window
	glVertex2f(400, 75);
	glEnd();
	glFlush();
}

void main(int argc, char** argv) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowSize(400, 200);
	glutInitWindowPosition(200, 100);
	glutCreateWindow("Hering Illusion");
	glClearColor(255.0, 255.0, 255.0, 0.0);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluOrtho2D(0.0, 400.0, 0.0, 200.0);
	glutDisplayFunc(display);
	glutMainLoop();

}
