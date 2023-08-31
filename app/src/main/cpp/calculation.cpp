#include <jni.h>
#include "calculation.h"
#include <android/log.h>
calculation::calculation(string cal) {
	_cal = cal;
	cout << "���� ������ ȣ��" << endl;
}

// ������ �켱 ����
int calculation::precedence(char op) {
    if (op == '+' || op == '-') return 1;
    else if (op == '*' || op == '/' || op == '%') return 2;
    else if (op == '^') return 3;
    return 0; // �����ڰ� �ƴ� ���
}

// �� ���ڸ� ��� �Լ�
double calculation::applyOperator(double a, double b, char op) {
    switch (op) {
    case '+': return a + b;
    case '-': return a - b;
    case '*': return a * b;
    case '/': return a / b;
    case '%': return fmod(a, b);
    case '^': return pow(a, b);
    default: return 0.0; // ���� ó��
    }
}

// �־��� ���н��� ����ϴ� �Լ��� �����մϴ�.
double calculation::evaluateExpression() {
    istringstream iss(_cal);
    stack<double> values;
    stack<char> operators;

    string token;

    while (iss >> token) {
        // �����ڸ� ó���մϴ�.

        __android_log_print(
                ANDROID_LOG_DEBUG,
                "check",
                "%s", token.c_str());

        if (isdigit(token[0]) || (token[0] == '-' && isdigit(token[1]))) {
            // �ǿ����ڸ� ���ÿ� �߰��մϴ�.
            double value = stod(token);
            values.push(value);
        }
        else if (token == "\u221A") {
            // ��Ʈ �����ڸ� ���� ��, ��Ʈ �ȿ� �ִ� ǥ������ ó���մϴ�.
            operators.push('r'); // ��Ʈ �����ڸ� ���ÿ� �߰�  ó���ϱ� ���� r �� ǥ��.
        }
        else if (token == "(") {
            // ���� ��ȣ�� ���ÿ� �߰��մϴ�.
            operators.push('(');
        }
        else if (token == ")") {
            // ���� ��ȣ�� ���� ������ �����ڸ� ó���մϴ�.

            while (!operators.empty()) {
                // ��Ʈ �������� ��쿡�� ó�� �ϵ��� ���� ����.
                if (operators.top() == '(')
                {
                    char op = operators.top();
                    operators.pop();
                    if (operators.top() == 'r')
                    {
                        op = operators.top();
                        operators.pop();
                        // ��Ʈ �����ڸ� ó���մϴ�.
                        __android_log_print(
                                ANDROID_LOG_DEBUG,
                                "root",
                                "%c", op);

                        double a = values.top();
                        values.pop();
                        values.push(sqrt(a)); // ��Ʈ ���� ����
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
            // ���� ��ȣ�� �����մϴ�.
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


    // ���ÿ� �����ִ� ������ ó���մϴ�.
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
    // ���� ����� ��ȯ�մϴ�.
    return result;
}

string calculation::memory_record() {
	return _cal;
}

calculation::~calculation() {
	cout << "���� �Ҹ��� ȣ��" << endl;
}