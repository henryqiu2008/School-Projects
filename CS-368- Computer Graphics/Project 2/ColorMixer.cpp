/*
Author: Henry Qiu
Class: Computer Graphics 368
Notes: The slider moves down instead of up, and the mouse interactive for the gradiant is incomplete.
*/

#include <stdlib.h>
#include <gl/glut.h>
#include <sstream>
#include <iostream>
using namespace std;

//starting values of RGB
double Red = 405.0;
double Green = 405.0;
double Blue = 405.0;
//values of mixing the colors of RGB
float slideR = (405.0 - Red) / 255.0;
float slideG = (405.0 - Green) / 255.0;
float slideB = (405.0 - Blue) / 255.0;
//current positions of RGB
int RPos = 0;
int GPos = 0;
int BPos = 0;
int CDiff = 0; //difference between line and cursor
int SMotion = 0; //sliding motion values
//right colors of the gradiant
double RRed = 0.0;
double RGreen = 0.0;
double RBlue = 0.0;
//left colors of the gradiant
double LRed = 0.0;
double LGreen = 0.0;
double LBlue = 0.0;
//boolean values of whether cursor is on the line or not
bool gRed = false;
bool gGreen = false;
bool gBlue = false;
string sRed = "Red";
string sGreen = "Green";
string sBlue = "Blue";
float color[3] = { 0.0 };

void drawDisplay() { //draws the square that needs to be filled with color
	glColor3f(0.0, 0.0, 0.0);
	glBegin(GL_QUADS);
	glVertex2i(400, 300);
	glVertex2i(400, 600);
	glVertex2i(700, 600);
	glVertex2i(700, 300);
	glEnd();
}

void drawFilledDisplay() {//fills in the display with the colors depending on the position of the sliders
	glBegin(GL_QUADS);
	glColor3f(slideR, slideG, slideB);
	glVertex2i(400, 300);
	glVertex2i(400, 600);
	glVertex2i(700, 600);
	glVertex2i(700, 300);
	glEnd();
}

void drawGradiant() { //draws the gradiant that needs to be filled with color
	glBegin(GL_QUADS);
	glColor3f(LRed, LGreen, LBlue);
	glVertex2i(400, 100);
	glVertex2i(400, 200);
	glColor3f(RRed, RGreen, RBlue);
	glVertex2i(700, 200);
	glVertex2i(700, 100);
	glEnd();
}

void drawLine() { //draw the lines of RGB
	glBegin(GL_LINES);
	glColor3f(1.0, 0.0, 0.0);//Red Line
	glVertex2i(100, 150);
	glVertex2i(100, 405);

	glColor3f(0.0, 1.0, 0.0);//Green Line
	glVertex2i(200, 150);
	glVertex2i(200, 405);

	glColor3f(0.0, 0.0, 1.0);//Blue Line
	glVertex2i(300, 150);
	glVertex2i(300, 405);
	glEnd();
}

void drawSlider() { //draws the slider for the lines of RGB
	glBegin(GL_QUADS);
	glColor3f(1.0, 0.0, 0.0); //Red slider
	glVertex2i(80, Red);
	glVertex2i(80, Red + 20);
	glVertex2i(120, Red + 20);
	glVertex2i(120, Red);

	glColor3f(0.0, 1.0, 0.0); //Blue slider
	glVertex2i(180, Green);
	glVertex2i(180, Green + 20);
	glVertex2i(220, Green + 20);
	glVertex2i(220, Green);

	glColor3f(0.0, 0.0, 1.0); //Green slider
	glVertex2i(280, Blue);
	glVertex2i(280, Blue + 20);
	glVertex2i(320, Blue + 20);
	glVertex2i(320, Blue);
	glEnd();
}

void displayString() { //displays RGB under their respective lines
	glColor3f(1.0, 0.0, 0.0);
	glRasterPos2i(90, 100);
	glutBitmapCharacter(GLUT_BITMAP_TIMES_ROMAN_24, 'R');

	glColor3f(0.0, 1.0, 0.0);
	glRasterPos2i(190, 100);
	glutBitmapCharacter(GLUT_BITMAP_TIMES_ROMAN_24, 'G');

	glColor3f(0.0, 0.0, 1.0);
	glRasterPos2i(290, 100);
	glutBitmapCharacter(GLUT_BITMAP_TIMES_ROMAN_24, 'B');
}

string toString(double num) { //function to convert double to string to be displayed above the lines
	ostringstream displayNum;
	displayNum << num;
	return displayNum.str();
}

void keys(unsigned char keys, int x, int y) { //detects the keys pressed to change the color to that of the gradiant
	switch (keys) {
	case 'L': case 'l': //if l or L is pressed, lefthand side changes color
		LRed = slideR;
		LGreen = slideG;
		LBlue = slideB;
		break;
	case 'R': case 'r': //if r or R is pressed, righthand side changes color
		RRed = slideR;
		RGreen = slideG;
		RBlue = slideB;
		break;
	}
	glutPostRedisplay();
}

void mouse(int click, int down, int x, int y) { //detects the clicks of the mouse
	int ycord = 640 - 1 - y;
	switch (click) {
	case GLUT_LEFT_BUTTON:
		if (down == GLUT_DOWN) {
			if (x >= 50 && x <= 150 && ycord >= RPos && ycord <= (RPos + 30)) { //moves the slider up and down the lines for red
				RPos = CDiff - ycord;
				gRed = true;
			}
			else if (x >= 150 && x <= 250 && ycord >= GPos && ycord <= (GPos + 30)) {//moves the slider up and down the lines for green
				CDiff = ycord - GPos;
				gGreen = true;
			}
			else if (x >= 250 && x <= 350 && ycord >= BPos && ycord <= (BPos + 30)) {//moves the slider up and down the lines for blue
				CDiff = ycord - BPos;
				gBlue = true;
			}

			if (x >= 50 && x <= 150 && ycord >= RPos && ycord <= (RPos + 30)) { //if cursor is within the range, automatically change the position of the red slider
				Red = 405 - slideR * 255;
				gRed = true;
			}
			else if (x >= 150 && x <= 250 && ycord >= GPos && ycord <= (GPos + 30)) { //if cursor is within the range, automatically change the position of the red slider
				Green = 405 - slideG * 255;
				gGreen = true;
			}
			else if (x >= 250 && x <= 350 && ycord >= BPos && ycord <= (BPos + 30)) { //if cursor is within the range, automatically change the position of the red slider
				Blue = 405 - slideB * 255;
				gBlue = true;
			}

			if (x >= 400 && x <= 699 && ycord >= 100 && ycord <= 200) { //depending on where mouse clicks, the color of the box changes
				glReadPixels(x, ycord, 1, 1, GL_RGB, GL_FLOAT, color);
				slideR = color[0];
				slideG = color[1];
				slideB = color[2];

				Red = 405 - slideR * 255; //changes the positions of the red slider
				Green = 405 - slideG * 255; //changes the position of the green slider
				Blue = 405 - slideB * 255; //changes the position of the blue slider
			}

			glutPostRedisplay();
		}
		break;
	}
}

void motion(int x, int y) { //detects the movements of the mouse
	int ycord = 640 - 1 - y;
	if (x >= 50 && x <= 150 && gGreen == false && gBlue == false) { //changes the values of the red line
		gRed = true;
		SMotion = ycord - CDiff;
		if (SMotion < 150) { //so long as the mouse stays within the perimeter, the slider changes
			Red = 150;
		}
		else if (SMotion > 405) {
			Red = 405;
		}
		else {
			Red = SMotion;
		}
	}
	else if (x >= 150 && x <= 250 && gRed == false && gBlue == false) { //changes the values of the green line
		gGreen = true;
		SMotion = ycord - CDiff;
		if (SMotion < 150) { //so long as the mouse stays within the perimeter, the slider changes
			Green = 150;
		}
		else if (SMotion > 405) {
			Green = 405;
		}
		else {
			Green = SMotion;
		}
	}
	else if (x >= 250 && x <= 350 && gRed == false && gGreen == false) { //changes the values of the blue line
		gBlue = true;
		SMotion = ycord - CDiff;
		if (SMotion < 150) { //so long as the mouse stays within the perimeter, the slider changes
			Blue = 150;
		}
		else if (SMotion > 405) {
			Blue = 405;
		}
		else {
			Blue = SMotion;
		}
	}
	glutPostRedisplay();
}

void passive(int x, int y) { //idles when there is no movement detected
	int ycord = 640 - 1 - y;

	gRed = false;
	gGreen = false;
	gBlue = false;

	RPos = SMotion;
	GPos = SMotion;
	BPos = SMotion;

	glutPostRedisplay();
}

void fillDisplay() { //fills the area of the square with color/ mixes the colors depending on the line
	int count = 1;
	double slidestart = 405.0;
	while (count != 4) {
		if (count == 1) { //displays the red color
			glColor3f(1.0, 0.0, 0.0);
			glRasterPos2i(90, 500);
			slidestart = 405.0 - Red;
			slideR = slidestart / 255.0;
			sRed = toString(slidestart);
			for (string::iterator i = sRed.begin(); i != sRed.end(); ++i) {
				glutBitmapCharacter(GLUT_BITMAP_TIMES_ROMAN_24, *i);
			}
		}
		else if (count == 2) { //displays the green color
			glColor3f(0.0, 1.0, 0.0);
			glRasterPos2i(190, 500);
			slidestart = 405.0 - Green;
			slideG = slidestart / 255.0;
			sGreen = toString(slidestart);
			for (string::iterator i = sGreen.begin(); i != sGreen.end(); ++i) {
				glutBitmapCharacter(GLUT_BITMAP_TIMES_ROMAN_24, *i);
			}
		}
		else if (count == 3) { //displays the blue color
			glColor3f(0.0, 0.0, 1.0);
			glRasterPos2i(290, 500);
			slidestart = 405.0 - Blue;
			slideB = slidestart / 255.0;
			sBlue = toString(slidestart);
			for (string::iterator i = sBlue.begin(); i != sBlue.end(); ++i) {
				glutBitmapCharacter(GLUT_BITMAP_TIMES_ROMAN_24, *i);
			}
		}
		count++;
	}
	count = 1;
}

void display(void) {
	glClear(GL_COLOR_BUFFER_BIT);
	drawDisplay();
	drawGradiant();
	drawLine();
	drawSlider();
	displayString();
	fillDisplay();
	drawFilledDisplay();
	glFlush();
}

void main(int argc, char** argv) {
	glutInit(&argc, argv);
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowSize(800, 640);
	glutInitWindowPosition(200, 100);
	glutCreateWindow("Color Mixer");
	glClearColor(0.5, 0.5, 0.5, 0.0);
	glutKeyboardFunc(keys);
	glutMouseFunc(mouse);
	glutMotionFunc(motion);
	glutPassiveMotionFunc(passive);
	glLoadIdentity();
	gluOrtho2D(0.0, 800.0, 0.0, 640.0);
	glutDisplayFunc(display);
	glutMainLoop();

}
