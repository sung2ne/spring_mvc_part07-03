# 소설처럼 읽는 스프링 MVC

## PART 07. 인증 기능 만들기

이 파트에서는 Spring MVC를 활용하여 사용자 인증 기능을 구현하는 과정을 다룹니다.
회원가입, 로그인, 로그아웃, 프로필 관리, 비밀번호 초기화 및 회원 탈퇴 등의 사용자 인증 관련 기능을 단계별로 구현합니다.
Spring Security와 BCrypt를 활용하여 보안 강화도 함께 진행합니다.

### 01. 설계하기

사용자 인증 시스템의 기능 및 흐름을 설계합니다.
회원가입, 로그인, 로그아웃, 프로필 관리, 비밀번호 초기화, 회원 탈퇴 기능의 데이터 흐름과 API 설계를 정의합니다.

📖 교재: [https://wikidocs.net/267734](https://wikidocs.net/267734)
💻 코드: [https://github.com/sung2ne/spring_mvc_part07-01](https://github.com/sung2ne/spring_mvc_part07-01)

### 02. 데이터베이스

사용자 정보를 저장하기 위한 회원 정보 테이블(users) 설계하고 생성합니다.

📖 교재: [https://wikidocs.net/267735](https://wikidocs.net/267735)
💻 코드: [https://github.com/sung2ne/spring_mvc_part07-01](https://github.com/sung2ne/spring_mvc_part07-01)

### 03. 인증 설정 만들기

Spring Security를 적용하여 사용자 인증 및 권한 관리를 설정합니다.
비밀번호 암호화(BCryptPasswordEncoder)를 적용하고, 로그인 및 로그아웃을 처리하는 보안 설정을 구현합니다.

📖 교재: [https://wikidocs.net/271342](https://wikidocs.net/271342)
💻 코드: [https://github.com/sung2ne/spring_mvc_part07-01](https://github.com/sung2ne/spring_mvc_part07-01)

### 04. 회원가입 만들기

사용자가 회원가입할 수 있도록 회원가입 폼 및 처리 로직을 구현합니다.
입력값 검증 및 비밀번호 암호화(BCryptPasswordEncoder)를 적용하여 보안을 강화합니다.

📖 교재: [https://wikidocs.net/267736](https://wikidocs.net/267736)
💻 코드: [https://github.com/sung2ne/spring_mvc_part07-01](https://github.com/sung2ne/spring_mvc_part07-01)

### 05. 로그인 만들기

사용자가 로그인할 수 있도록 로그인 폼 및 인증 로직을 구현합니다.
Spring Security를 활용하여 사용자 정보 검증 및 로그인 성공/실패 처리를 수행합니다.

📖 교재: [https://wikidocs.net/271340](https://wikidocs.net/271340)
💻 코드: [https://github.com/sung2ne/spring_mvc_part07-01](https://github.com/sung2ne/spring_mvc_part07-01)

### 06. 로그아웃 만들기

사용자가 안전하게 로그아웃할 수 있도록 로그아웃 기능을 구현합니다.
로그아웃 시 세션을 삭제하고, 로그인 페이지로 리다이렉트하는 기능을 설정합니다.

📖 교재: [https://wikidocs.net/267739](https://wikidocs.net/267739)
💻 코드: [https://github.com/sung2ne/spring_mvc_part07-01](https://github.com/sung2ne/spring_mvc_part07-01)

### 07. 프로필 보기 만들기

로그인한 사용자가 자신의 프로필 정보를 조회할 수 있도록 프로필 페이지를 구현합니다.

📖 교재: [https://wikidocs.net/267740](https://wikidocs.net/267740)
💻 코드: [https://github.com/sung2ne/spring_mvc_part07-01](https://github.com/sung2ne/spring_mvc_part07-01)

### 08. 프로필 수정 만들기

사용자가 자신의 이름, 이메일 등의 기본 정보를 수정할 수 있도록 프로필 수정 기능을 구현합니다.
수정된 정보는 데이터베이스에 반영되며, 보안 강화를 위해 비밀번호 변경은 별도로 처리합니다.

📖 교재: [https://wikidocs.net/267741](https://wikidocs.net/267741)
💻 코드: [https://github.com/sung2ne/spring_mvc_part07-01](https://github.com/sung2ne/spring_mvc_part07-01)

### 09. 비밀번호 수정 만들기

사용자가 비밀번호를 변경할 수 있도록 비밀번호 수정 기능을 구현합니다.
현재 비밀번호 확인 후, 새로운 비밀번호를 입력받아 BCrypt 해싱 후 저장합니다.

📖 교재: [https://wikidocs.net/267742](https://wikidocs.net/267742)
💻 코드: [https://github.com/sung2ne/spring_mvc_part07-01](https://github.com/sung2ne/spring_mvc_part07-01)

### 10. 아이디 찾기 만들기

사용자가 이메일을 입력하면 가입된 아이디를 찾아 알려주는 기능을 구현합니다.

📖 교재: [https://wikidocs.net/267737](https://wikidocs.net/267737)
💻 코드: [https://github.com/sung2ne/spring_mvc_part07-01](https://github.com/sung2ne/spring_mvc_part07-01)

### 11. 비밀번호 초기화 만들기

사용자가 비밀번호를 잊었을 경우 재설정할 수 있도록 비밀번호 초기화 기능을 구현합니다.

📖 교재: [https://wikidocs.net/267738](https://wikidocs.net/267738)
💻 코드: [https://github.com/sung2ne/spring_mvc_part07-01](https://github.com/sung2ne/spring_mvc_part07-01)

### 12. 회원 탈퇴 만들기

사용자가 자신의 계정을 삭제할 수 있도록 회원 탈퇴 기능을 구현합니다.
회원 탈퇴 시 사용자 데이터 삭제 및 관련 데이터 처리를 진행합니다.

📖 교재: [https://wikidocs.net/271347](https://wikidocs.net/271347)
💻 코드: [https://github.com/sung2ne/spring_mvc_part07-01](https://github.com/sung2ne/spring_mvc_part07-01)