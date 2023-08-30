#pragma once
#include<iostream>
#include <vector>
using namespace std;


struct total_memory {
	vector<string> cal_memory_stack;
	vector<double> result_memory_stack;
};


class memory
{
public:
	vector<string> cal_memory_stack;
	vector<double> result_memory_stack;

public:
	memory();

	void memory_update(string calculation, double result);

	void memory_delete(int index);

	total_memory memory_show();

	~memory();
};