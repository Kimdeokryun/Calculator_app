#pragma once
#include <iostream>
#include <string>
#include <vector>
#include <stack>
#include <sstream>
#include <math.h>
#include <cmath>

using namespace std;

class calculation
{
public:
	string _cal;

public:

    calculation(string cal);

	double evaluateExpression();

	string memory_record();

	~calculation();

private:

	int precedence(char op);

	double applyOperator(double a, double b, char op);
};

