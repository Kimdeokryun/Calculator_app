#include "total_memory.h"


memory::memory() {
	cout << "메모리 생성자 호출" << endl;
}

void memory::memory_update(string calculation, double result) {
	cal_memory_stack.push_back(calculation);
	result_memory_stack.push_back(result);
}

void memory::memory_delete(int index) {
	cal_memory_stack.erase(cal_memory_stack.begin()+index);
	result_memory_stack.erase(result_memory_stack.begin() + index);
}

total_memory memory::memory_show() {
	total_memory tm;
	tm.cal_memory_stack = cal_memory_stack;
	tm.result_memory_stack = result_memory_stack;

	return tm;
}


memory::~memory() {
	cout << "메모리 소멸자 호출" << endl;
}