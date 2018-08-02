/*
	Author: Henry Qiu
*/

#include <gl/glut.h>
#include <iostream>
#include <stdlib.h>
#include <cstdlib>
#include <math.h>
using namespace std;

GLfloat pos[] = { -2,4,5,1 },
		amb[] = { 0.3,0.3,0.3,1.0 };
GLfloat front_amb_diff[] = { 0.7,0.5,0.1,1.0 };
GLfloat back_amb_diff[] = { 0.4,0.7,0.1,1.0 };
GLfloat spe[] = { 0.25,0.25,0.25,1.0 };
GLfloat front_amb_diff_snowman[] = { 1, 1, 1, 1 };
GLfloat front_amb_diff_snowman_black[] = { 0, 0, 0, 0 };
GLfloat front_amb_diff_snowman_grey[] = { 0.5, 0.5, 0.5, 0 };
GLfloat front_amb_diff_snowman_orange[] = { 1.0, 0.27, 0, 0 };
GLfloat front_amb_diff_snowman_brown[] = { 0.54, 0.27, 0.07, 0 };
GLfloat front_amb_diff_snowman_red[] = { 1, 0, 0, 0 };
GLfloat front_amb_diff_snowman_blue[] = { 0, 0, 1, 0 };
GLfloat front_amb_diff_snowman_green[] = { 0, 1, 0, 0 };
GLfloat front_amb_diff_snowman_byellow[] = { 1, 1, 0, 0 };
GLfloat theta = 0, theta_moon = 0, dt = 0.05, theta_rotate = 0, axes[3][3] = { {1,0,0},{0,1,0},{0,0,1} };
GLfloat theta_iso = 0, dt_iso = 0.1, dt_d = 0.2, dt_i = 0, theta_d = 0;
int axis = 0, zoom = 0, stop = 0;

void drawSnowman() {
	
	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman);//white
		glTranslatef(0.0, 0.5, 0.0);
		glutSolidSphere(0.3, 20, 20);
		glTranslatef(0.0, -0.7, 0.0);
		glutSolidSphere(0.5, 20, 20);
		glTranslatef(0.0, -1.0, 0.0);
		glutSolidSphere(0.7, 20, 20);
	glPopMatrix();//draw the body
	
	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_black);//black
		//eyes
		glTranslatef(-0.1, 0.6, 0.25);
		glutSolidSphere(0.04, 10, 10);
		glTranslatef(0.2, 0.0, 0.0);
		glutSolidSphere(0.04, 10, 10);
		//mouth
		glTranslatef(0.0, -0.2, 0.0);
		glutSolidSphere(0.02, 10, 10);
		glTranslatef(-0.07, -0.02, 0.01);
		glutSolidSphere(0.02, 10, 10);
		glTranslatef(-0.07, 0.0, 0.0);
		glutSolidSphere(0.02, 10, 10);
		glTranslatef(-0.07, 0.02, -0.01);
		glutSolidSphere(0.02, 10, 10);
	glPopMatrix();//draw the body parts
	
	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_black);//black
		glTranslatef(0.0, 0.05, 0.43);
		glutSolidSphere(0.02, 10, 10);
		glTranslatef(0.0, -0.15, 0.06);
		glutSolidSphere(0.02, 10, 10);
		glTranslatef(0.0, -0.15, 0.0);
		glutSolidSphere(0.02, 10, 10);
	glPopMatrix();//draw the buttons
	
	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_orange);//orange
		glTranslatef(0.0, 0.5, 0.2);
		glutSolidCone(0.05, 0.3, 10, 2);
	glPopMatrix();//draw the nose
	GLUquadricObj * quadObj = gluNewQuadric();
	gluQuadricDrawStyle(quadObj, GLU_FILL);
	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_brown);//brown
	glMaterialfv(GL_BACK, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_brown);//brown
		glTranslatef(0.4, 0.0, 0.0);
		glRotatef(90, 0.5, 1, 0);
		gluCylinder(quadObj, 0.03, 0.03, 0.6, 10.0, 1.0);
	glPopMatrix();// l arm
	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_brown);//brown
	glMaterialfv(GL_BACK, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_brown);//brown */
		glTranslatef(-0.95, -0.25, 0.0);
		glRotatef(210, 0.5, 0.0, 0.5);
		gluCylinder(quadObj, 0.03, 0.03, 0.6, 10.0, 1.0);
	glPopMatrix();//r arm

	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_black);//black
	glMaterialfv(GL_BACK, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_black);//black
		glTranslatef(0.0, 0.8, 0.0);
		glRotatef(-120, 1, 1, 1);
		gluCylinder(quadObj, 0.2, 0.2, 0.4, 33.0, 5.0);
		glTranslatef(0.0, 0.0, -0.1);
		glRotatef(0, 1, 1, 1);
		gluCylinder(quadObj, 0.25, 0.25, 0.1, 10.0, 1.0);
	glPopMatrix();// top hat
}

void drawSnowman2() {
	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman);//white
	glTranslatef(0.0, 0.5, -1.5);
	glutSolidSphere(0.3, 20, 20);
	glTranslatef(0.0, -0.7, 0.0);
	glutSolidSphere(0.5, 20, 20);
	glTranslatef(0.0, -1.0, 0.0);
	glutSolidSphere(0.7, 20, 20);
	glPopMatrix();//draw the body

	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_black);//black
		glTranslatef(0.1, 0.4, -1.75);
		glutSolidSphere(0.02, 10, 10);
		glTranslatef(-0.07, -0.02, -0.02);
		glutSolidSphere(0.02, 10, 10);
		glTranslatef(-0.07, 0.0, 0.0);
		glutSolidSphere(0.02, 10, 10);
		glTranslatef(-0.07, 0.02, 0.02);
		glutSolidSphere(0.02, 10, 10);
	glPopMatrix(); // mouth

	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_black);//black
	glMaterialfv(GL_BACK, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_black);//black
	glTranslatef(-0.1, 0.6, -1.75);
	glutSolidSphere(0.04, 10, 10);
	glTranslatef(0.2, 0.0, 0.0);
	glutSolidSphere(0.04, 10, 10);
	glTranslatef(-0.1, -0.2, 0.0);
	glMaterialfv(GL_BACK, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_red);//black
	glTranslatef(0.0, 0.1, 0.0);
	glRotatef(180, 0, 0, 0);
	glutSolidCone(0.05, 0.3, 10, 2);
	glPopMatrix(); //eyes and nose

	GLUquadricObj * quadObj = gluNewQuadric();
	gluQuadricDrawStyle(quadObj, GLU_FILL);
	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_brown);//brown
	glMaterialfv(GL_BACK, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_brown);//brown
	glTranslatef(0.4, 0.0, -1.5);
	glRotatef(90, 0.5, 1, 0);
	gluCylinder(quadObj, 0.03, 0.03, 0.6, 10.0, 1.0);
	glPopMatrix();// l arm
	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_brown);//brown
	glMaterialfv(GL_BACK, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_brown);//brown */
	glTranslatef(-0.95, -0.25, -1.5);
	glRotatef(210, 0.5, 0.0, 0.5);
	gluCylinder(quadObj, 0.03, 0.03, 0.6, 10.0, 1.0);
	glPopMatrix();//r arm
		
	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_grey);//grey
	glMaterialfv(GL_BACK, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_grey);//grey
		glTranslatef(0.0, 0.7, -1.5);
		glRotatef(-120, 1, 1, 1);
		gluCylinder(quadObj, 0.25, 0.25, 0.1, 10.0, 1.0);
		glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_red);//red
		glutSolidCone(0.25, 0.6, 10, 10);
		glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman);//white
		glTranslatef(0.0, 0.0, 0.6);
		glutSolidSphere(0.1, 10, 10); //red santa hat
	glPopMatrix();
}

void drawHouse() {
	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_blue);
		glTranslatef(-5.0, 0, 0);
		glutSolidCube(4); //body of the house
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_red);
	glMaterialfv(GL_BACK, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_red);
		glTranslatef(0.0, 1.9, 0.0);
		glRotatef(90, -1, 0, 0);
		glutSolidCone(3, 4, 10, 10); //roof of the house
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman);
		glTranslatef(-1.3, -1.6, -2);
		glutSolidCube(1);
		glTranslatef(2.5, 0, 0);
		glutSolidCube(1); //windows to the house
	glPopMatrix();
}

void drawMoon() {
	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman);
	glMaterialfv(GL_BACK, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman);
	glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, spe);
	glLightfv(GL_LIGHT0, GL_AMBIENT, amb);
		glRotated(theta_moon, 0, 0, 1);
		glTranslated(11, 9, -7);
		glutSolidSphere(1.7, 48, 48); //draws the moon floating in the background
	glPopMatrix();
}

void drawGround() {
	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_grey);
		glTranslatef(0.0, -9.3, 0.0);
		glutSolidCube(15); //draws the cube on which house and snowmen are sitiuated
	glPopMatrix();
}

void drawRandomIco() {
	GLUquadricObj * quadObj = gluNewQuadric();
	gluQuadricDrawStyle(quadObj, GLU_FILL);
	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_brown);//brown
	glMaterialfv(GL_BACK, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_brown);//brown
	glTranslatef(5.0, 7.0, -1.5);
	glRotatef(90, 1, 0, 0);
	gluCylinder(quadObj, 0.5, 0.5, 10, 10.0, 1.0); //cylinder which props up icosahedron
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_byellow);
	glMaterialfv(GL_BACK, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_byellow);
	glScalef(2.0, 2.0, 2.0);
	glRotated(theta_iso, 1, 0, 0); //spins 
	glutSolidIcosahedron();
	glPopMatrix();
}

void drawFace() {
	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_byellow);
	glMaterialfv(GL_BACK, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_byellow);
		glTranslated(0, -20, 0);
		glutSolidSphere(13, 50, 50);
		glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_grey);
		glTranslated(-5, 0, 7);
		glutSolidSphere(5, 50, 50);
		glTranslated(10, 0, 0);
		glutSolidSphere(5, 50, 50);
		glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_blue);
		glTranslated(0, 0, -14);
		glutSolidSphere(5, 50, 50);
		glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_red);
		glTranslated(-10, 0, 0);
		glutSolidSphere(5, 50, 50);
	glPopMatrix();

	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_grey);
		glTranslated(0, -23, 10);
		glutSolidSphere(3, 50, 50);
		glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_green);
		glTranslated(0, 0, -20);
		glutSolidSphere(3, 50, 50);
	glPopMatrix();
}

void drawRandomOcto() {
	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_blue);
		glTranslated(20, -10, 0);
		glScalef(10.0, 10.0, 10.0);
		glRotated(theta_d, 1, 0, 0); //spins 
		glutWireOctahedron();
	glPopMatrix();

}

void drawRandomDec() {
	glPushMatrix();
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff_snowman_red);
		glTranslated(-25, -13, 0);
		glScalef(7.0, 7.0, 7.0);
		glRotated(theta_d, 1, 0, 0); //spins 
		glutWireDodecahedron();
	glPopMatrix();
}

void cmds() {
	cout << "Commands for this Project." << endl;
	cout << "Arrow Key Up zooms in." << endl;
	cout << "Arrow Key Down zooms out." << endl;
	cout << "Arrow Key Right rotates right when held." << endl;
	cout << "Arrow Key Left rotates left when held." << endl;
	cout << "Key s changes the rotation of the moon towards the right." << endl;
	cout << "Key a changes the rotation of the moon towards the left." << endl;
	cout << "Key f rotates the Icosahedron on top of the pole forwards." << endl;
	cout << "Key d rotates the Icosahedron on top of the pole backwards." << endl;
	cout << "Key z rotates the Dodecahedron and Octahedron forward." << endl;
	cout << "key x rotates the Dodecahedron and Octahedron backwards." << endl;
}
void display(void) {
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glPushMatrix();
	glRotated(theta_rotate, 0, 1, 0);
	drawSnowman();
	drawSnowman2();
	drawHouse();
	drawMoon();
	drawGround();
	drawRandomIco();
	drawRandomOcto();
	drawRandomDec();
	drawFace();
	glPopMatrix();
	glutSwapBuffers();
}

void idle(void) { //idle function that starts the rotation of objects
	theta_iso = (theta_iso < 360) ? theta_iso + dt_iso : dt_iso;
	theta_d = (theta_d < 360) ? theta_d + dt_d : dt_d;
	theta_moon = (theta_moon < 360) ? theta_moon - dt/3 : dt;
	glutPostRedisplay();
}

void keyboardArrow(int key, int x, int y) {
	switch (key) {
	case GLUT_KEY_RIGHT: //rotate right
		theta = (++theta_rotate > 360) ? 0 : theta_rotate;
		theta = (theta < 360) ? theta + dt : dt;
		theta_moon = (theta_moon < 360) ? theta_moon + dt / 3 : dt;
		break;
	case GLUT_KEY_LEFT: //rotate left
		theta = (--theta_rotate < 0) ? 360 : theta_rotate;
		theta = (theta < 360) ? theta + dt : dt;
		theta_moon = (theta_moon < 360) ? theta_moon + dt / 3 : dt;
		break;
	case GLUT_KEY_UP: //zoom in
		zoom = zoom + 1;
		glTranslated(0, 0, zoom);
		zoom = 0;
		break;
	case GLUT_KEY_DOWN: //zoom out
		zoom = zoom - 1;
		glTranslated(0, 0, zoom);
		zoom = 0;
		break;
	}
	glutPostRedisplay();
}

void keyboard(unsigned char key, int x, int y) {
	switch (key) {
	case 's': //rotate moon faster to the right
		if (dt >= 3) {
			dt = 3;
		}
		else
			dt = dt + 0.1;
		break;
	case 'a': //rotate moon faster to the left
		if (dt <= -3) {
			dt = -3;
		}
		else
			dt = dt - 0.1;
		break;
	case 'd'://rotate isohedron slower/backwards
		if (dt_iso <= -3) {
			dt_iso = -3;
		}
		else
			dt_iso = dt_iso - 0.1;
		break;
	case 'f'://rotate isohedron faster/forwards
		if (dt_iso >= 3) {
			dt_iso = 3;
		}
		else
			dt_iso = dt_iso + 0.1;
		break;
	case 'z': //rotates the dodeca and tetra forward
		if (dt_d >= 3) {
			dt_d = 3;
		}
		else
			dt_d = dt_d + 0.1;
		break;
	case 'x': //rotates the dodeca and tetra backwards
		if (dt_d <= -3) {
			dt_d = -3;
		}
		else
			dt_d = dt_d - 0.1;
		break;
	case ' ': //pause all rotations //press again to resume
		if (dt == 0) {
			dt = stop;
		}
		else {
			stop = dt;
			dt = 0;
		}
		break;
	}
	glutPostRedisplay();
}

void main(int argc, char** argv) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
	glutInitWindowSize(600, 600);
	glutInitWindowPosition(200, 100);
	glutCreateWindow("Project 3");
	glClearColor(0.1, 0.1, 0.1, 0.0);
	glEnable(GL_DEPTH_TEST);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluPerspective(60, 1.0, 5, 100);
	glMaterialfv(GL_FRONT, GL_AMBIENT_AND_DIFFUSE, front_amb_diff);
	glMaterialfv(GL_BACK, GL_AMBIENT_AND_DIFFUSE, back_amb_diff);
	glMaterialfv(GL_FRONT_AND_BACK, GL_SPECULAR, spe);
	glMaterialf(GL_FRONT_AND_BACK, GL_SHININESS, 75);
	glLightfv(GL_LIGHT0, GL_AMBIENT, amb);
	glLightModeli(GL_LIGHT_MODEL_TWO_SIDE, GL_TRUE);
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glTranslated(0, 0, -20);
	glLightfv(GL_LIGHT0, GL_POSITION, pos);
	glEnable(GL_LIGHTING);
	glEnable(GL_LIGHT0);
	glEnable(GL_POINT_SMOOTH);
	glutKeyboardFunc(keyboard);
	glutSpecialFunc(keyboardArrow);
	glutDisplayFunc(display);
	glutIdleFunc(idle);
	cmds();
	glutMainLoop();
}