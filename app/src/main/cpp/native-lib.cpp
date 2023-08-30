#include <jni.h>
#include <string>

#include "calculation.h"
#include "total_memory.h"

#include <android/log.h>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_calculator_CalculatorLogic_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {


    std::string hello = "Hello from C++";

    return env->NewStringUTF(hello.c_str());
}

// string ����  ��� �� string ����� return �Լ�
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_calculator_CalculatorLogic_resultFromCalculator(
        JNIEnv* env,
        jobject,
        jstring expression
        /* this */) {

    //jstring -> string ��ȯ
    const char *cstr = env->GetStringUTFChars(expression, NULL);
    string input_expression = string(cstr);

    __android_log_print(
            ANDROID_LOG_DEBUG,
            "CHECK",
            "%s", input_expression.c_str());

    // calculation Ŭ���� ����
    calculation cal_class = calculation(input_expression);
    // calculation evaluateExpression ���� ����� ��ȯ
    double result = cal_class.evaluateExpression();

    int int_result = int(result);

    // 20�ڸ��� formatted result ���� ����
    char formattedResult[21];

    // ���� ��ȯ �� result ���� double �� result ���� ���ٸ� ������.
    if (int_result == result) {
        // ���� ����
        snprintf(formattedResult, sizeof(formattedResult), "%d", int_result);
    } else {
        // double ���� �Ҽ� 6�ڸ�����
        snprintf(formattedResult, sizeof(formattedResult), "%.6lf", result);
    }

    // string �� ��ȯ
    string cal_result = string(formattedResult);

    // string -> jstring �� ��ȯ �� return
    return env->NewStringUTF(cal_result.c_str());
}