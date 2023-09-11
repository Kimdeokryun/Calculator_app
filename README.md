# Calculator_app
C++ NDK 를 사용한 기능 개선 계산기 안드로이드 앱


## 프로젝트 개요

모바일 어플리케이션에서 Native Language로 구현하여 빠른 실행동작을 구현을 경험해보기 위하여 진행한 프로젝트 입니다.

삼성 갤럭시 내장 계산기와 아이폰 내장 계산기의 차이점을 확인하고, 윈도우 내장 계산기 및 네이버 계산기를 모두 벤치마킹하여

주로 사용하는 계산기의 연산자를 포함하고, STT 기능과 계산 히스토리 기능을 추가한 기능 개선 계산기 앱을 구현하였습니다.

계산기 어플리케이션의 계산식은 모두 Stinrg 입니다.

JNI로 함수 호출 후 JString 형 계산식을 string형 으로 변환 후 C/C++ 런타임 라이브러리를 활용하여 istringstream으로 쪼개고, 연산자에 따른 계산을 진행하는 로직을 구현하였습니다.

계산의 결과값을 String 형으로 반환하여 어플리케이션에서 결과값을 나타내고, Drawer에 계산 히스토리 내역이 보여지고 로드할 수 있도록 구현하였습니다.


## 개발 사항

계산기 어플리케이션의 버튼 동작 구현 (숫자, 연산자, STT 등)

버튼 동작 및 STT 기능으로 계산식을 표현

JNI로 함수 호출 후 C/C++ 런타임 라이브러리를 활용하여
연산자에 따른 계산을 진행하는 로직을 구현

계산식과 결과값을 Drawer에 버튼형 히스토리화 및 로드화


## 주요 기능

- 주요 계산기 기능 (다크 모드 적용)
- 계산식 음성인식 기능
- 계산 히스토리 기능


### 시연영상
- 다크 모드 전환
  
![다크모드](https://github.com/Kimdeokryun/Calculator_app/assets/96904134/3d82a389-3c7c-4bf6-bfd6-5c6358dcf608)

  
- 계산식 음성인식
  
![음성인식](https://github.com/Kimdeokryun/Calculator_app/assets/96904134/90ce8c5d-ae98-4fca-8e75-44735ca2dc08)

  
- 계산 히스토리 및 불러오기
  
![계산기능](https://github.com/Kimdeokryun/Calculator_app/assets/96904134/14e67a88-7f50-46fe-9348-0fd30ed5a652)



## 사용 기술 및 도구
### 언어
Java

C++


### 라이브러리 및 환경
android speech Recognition(ko-KR)

NDK 23.1 (Java 와 Kotlin 모두 NDK 활용 가능.)

Android SDK 33

Android Studio


## 프로젝트 설명

### C++ 함수 호출 관련

JNI 함수를 생성하여 Java에서 호출하고

클래스 내부 함수(계산 로직)를 호출하여 

String 형 계산식을 계산하고 결과값을 return 하도록 구현하였습니다.

```c++
// c++ ndk  string 계산식  계산 후 string 결과값 return 함수
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_calculator_CalculatorLogic_resultFromCalculator(
        JNIEnv* env,
        jobject,
        jstring expression
        ) {
    //jstring -> string 변환
    const char *cstr = env->GetStringUTFChars(expression, NULL);
    string input_expression = string(cstr);

    __android_log_print(
            ANDROID_LOG_DEBUG,
            "CHECK",
            "%s", input_expression.c_str());
```
```c++
    // calculation 클래스 선언
    calculation cal_class = calculation(input_expression);
    // calculation evaluateExpression 으로 결과값 반환
    double result = cal_class.evaluateExpression();

     /* 중략 */

    // string 형 변환
    string cal_result = string(formattedResult);

    // string -> jstring 형 변환 후 return
    return env->NewStringUTF(cal_result.c_str());
}
```


### 기능 개선
음성 인식 버튼을 통해 간단한 계산식을 표현 할 수 있습니다.

계산 히스토리 기능을 추가하여 계산 이후 히스토리에 버튼형으로 추가되고, 버튼 클릭 시 로드할 수 있도록 구현하였습니다.

#### 음성인식 버튼
![image](https://github.com/Kimdeokryun/Calculator_app/assets/96904134/61e0a09f-b96d-4b8d-b451-a2082d929c06)

#### 계산 히스토리 (다크 모드)
![image](https://github.com/Kimdeokryun/Calculator_app/assets/96904134/e5a9ea07-9ba2-4813-8443-b164009f5641)

#### 계산 히스토리 (라이트 모드)
![image](https://github.com/Kimdeokryun/Calculator_app/assets/96904134/f34c3a70-db48-4673-9089-983f6a862f9a)

