#include <jni.h>
#include "calculation.h"
#include <android/log.h>
calculation::calculation(string cal) {
	_cal = cal;
	cout << "계산기 생성자 호출" << endl;
}

// 연산자 우선 순위
int calculation::precedence(char op) {
    if (op == '+' || op == '-') return 1;
    else if (op == '*' || op == '/' || op == '%') return 2;
    else if (op == '^') return 3;
    return 0; // 연산자가 아닌 경우
}

// 두 숫자를 계산 함수
double calculation::applyOperator(double a, double b, char op) {
    switch (op) {
    case '+': return a + b;
    case '-': return a - b;
    case '*': return a * b;
    case '/': return a / b;
    case '%': return fmod(a, b);
    case '^': return pow(a, b);
    default: return 0.0; // 오류 처리
    }
}

// 주어진 수학식을 계산하는 함수를 정의합니다.
double calculation::evaluateExpression() {
    istringstream iss(_cal);
    stack<double> values;
    stack<char> operators;

    string token;

    while (iss >> token) {
        // 연산자를 처리합니다.

        __android_log_print(
                ANDROID_LOG_DEBUG,
                "check",
                "%s", token.c_str());

        if (isdigit(token[0]) || (token[0] == '-' && isdigit(token[1]))) {
            // 피연산자를 스택에 추가합니다.
            double value = stod(token);
            values.push(value);
        }
        else if (token == "\u221A") {
            // 루트 연산자를 만날 때, 루트 안에 있는 표현식을 처리합니다.
            operators.push('r'); // 루트 연산자를 스택에 추가  처리하기 쉽게 r 로 표기.
        }
        else if (token == "(") {
            // 왼쪽 괄호를 스택에 추가합니다.
            operators.push('(');
        }
        else if (token == ")") {
            // 왼쪽 괄호를 만날 때까지 연산자를 처리합니다.

            while (!operators.empty()) {
                // 루트 연산자의 경우에만 처리 하도록 로직 구현.
                if (operators.top() == '(')
                {
                    char op = operators.top();
                    operators.pop();
                    if (operators.top() == 'r')
                    {
                        op = operators.top();
                        operators.pop();
                        // 루트 연산자를 처리합니다.
                        __android_log_print(
                                ANDROID_LOG_DEBUG,
                                "root",
                                "%c", op);

                        double a = values.top();
                        values.pop();
                        values.push(sqrt(a)); // 루트 연산 적용
                    }
                    else
                    {
                        operators.push(op);
                    }
                }
                else {
                    char op = operators.top();
                    operators.pop();

                    double b = values.top();
                    values.pop();
                    double a = values.top();
                    values.pop();
                    values.push(applyOperator(a, b, op));
                }
            }
            // 왼쪽 괄호를 제거합니다.
            if (!operators.empty()){operators.pop();}
        }
        else {
            while (!operators.empty() && precedence(operators.top()) >= precedence(token[0])) {
                char op = operators.top();
                operators.pop();

                double b = values.top();
                values.pop();

                double a = values.top();
                values.pop();

                values.push(applyOperator(a, b, op));
            }
            operators.push(token[0]);
        }
    }


    // 스택에 남아있는 연산을 처리합니다.
    while (!operators.empty()) {
        char op = operators.top();
        operators.pop();

        double b = values.top();
        values.pop();

        double a = values.top();
        values.pop();

        values.push(applyOperator(a, b, op));
    }
    auto result = values.top();
    // 최종 결과를 반환합니다.
    return result;
}

string calculation::memory_record() {
	return _cal;
}

calculation::~calculation() {
	cout << "계산기 소멸자 호출" << endl;
}