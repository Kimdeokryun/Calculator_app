# Calculator_app
C++ NDK 를 사용한 기능 개선 계산기 안드로이드 앱


## 프로젝트 개요

모바일 어플리케이션에서 Native Language로 구현하여 빠른 실행동작을 구현을 경험해보기 위하여 진행한 프로젝트 입니다.

삼성 갤럭시 내장 계산기와 아이폰 내장 계산기의 차이점을 확인하고, 윈도우 내장 계산기 및 네이버 계산기를 모두 벤치마킹하여

주로 사용하는 계산기의 연산자를 포함하고, STT 기능과 계산 히스토리 기능을 추가한 기능 개선 계산기 앱을 구현하였습니다.

계산기 어플리케이션의 계산식은 모두 Stinrg 입니다.

JNI로 함수 호출 후 JString 형 계산식을 string형 으로 변환 후 C/C++ 런타임 라이브러리를 활용하여 istringstream으로 쪼개고, 연산자에 따른 계산을 진행하는 로직을 구현하였습니다.

계산의 결과값을 String 형으로 반환하여 어플리케이션에서 결과값을 나타내고, Drawer에 계산 히스토리 내역이 보여지고 로드할 수 있도록 구현하였습니다.

## 주요 기능

- 주요 계산기 기능 (다크 모드 적용)
- 계산식 음성인식 기능
- 계산 히스토리 기능

## 사용 기술 및 도구
### 언어
Java

C++


### 라이브러리 및 환경
android speech Recognition(ko-KR)

NDK 23.1 (Java 와 Kotlin 모두 NDK 활용 가능.)

Android SDK 33

Android Studio

### 시연영상
- 다크 모드 전환
  
![다크모드](https://github.com/Kimdeokryun/Calculator_app/assets/96904134/3d82a389-3c7c-4bf6-bfd6-5c6358dcf608)

  
- 계산식 음성인식
  
![음성인식](https://github.com/Kimdeokryun/Calculator_app/assets/96904134/90ce8c5d-ae98-4fca-8e75-44735ca2dc08)

  
- 계산 히스토리 및 불러오기
  
![계산기능](https://github.com/Kimdeokryun/Calculator_app/assets/96904134/14e67a88-7f50-46fe-9348-0fd30ed5a652)

