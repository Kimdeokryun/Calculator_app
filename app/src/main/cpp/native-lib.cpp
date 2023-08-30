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

// string 계산식  계산 후 string 결과값 return 함수
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_calculator_CalculatorLogic_resultFromCalculator(
        JNIEnv* env,
        jobject,
        jstring expression
        /* this */) {

    //jstring -> string 변환
    const char *cstr = env->GetStringUTFChars(expression, NULL);
    string input_expression = string(cstr);

    __android_log_print(
            ANDROID_LOG_DEBUG,
            "CHECK",
            "%s", input_expression.c_str());

    // calculation 클래스 선언
    calculation cal_class = calculation(input_expression);
    // calculation evaluateExpression 으로 결과값 반환
    double result = cal_class.evaluateExpression();

    int int_result = int(result);

    // 20자리의 formatted result 변수 선언
    char formattedResult[21];

    // 정수 변환 형 result 값과 double 형 result 값이 같다면 정수형.
    if (int_result == result) {
        // 정수 형식
        snprintf(formattedResult, sizeof(formattedResult), "%d", int_result);
    } else {
        // double 형식 소수 6자리까지
        snprintf(formattedResult, sizeof(formattedResult), "%.6lf", result);
    }

    // string 형 변환
    string cal_result = string(formattedResult);

    // string -> jstring 형 변환 후 return
    return env->NewStringUTF(cal_result.c_str());
}