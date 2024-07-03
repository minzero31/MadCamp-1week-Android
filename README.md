# 거북이
![image](https://github.com/minzero31/MadCamp-1week-Android/assets/154976598/ecf5d82d-1963-4542-addb-1fd11a6130bf)
![image](https://github.com/minzero31/MadCamp-1week-Android/assets/154976598/3e4a014e-5910-4c86-a78f-017b2f4a4f5e)
![image](https://github.com/minzero31/MadCamp-1week-Android/assets/154976598/851538f3-3990-40ef-8710-578d527ed85e)
![image](https://github.com/minzero31/MadCamp-1week-Android/assets/154976598/003098c8-414b-4ba9-80b5-a9ff246395fb)

## 👨‍🏫 프로젝트 소개
‘장수’의 상징인 동물 거북이 ! 
‘느림’의 상징인 동물 거북이 ! 
약이 거북이의 껍질처럼 사람들을 보호하고 치유하는 역할을 한다는 의미를 담고 있습니다.


## ⏲️ 개발 기간 
- 2024.06.27(목) ~ 2024.07.3(토)
- 
  
## 🧑‍🤝‍🧑 개발자 소개 
- **김민영** : 숙명여자대학교 인공지능공학부 23학번
- **하도현** : 한국과학기술원 전산학부 22학
- 

## 💻 개발환경
- **Version** : Java 17
- **IDE** : IntelliJ
- **Framework** : SpringBoot 2.7.11
- **ORM** : JPA

## ⚙️ 기술 스택
- 개발 언어 : **Kotlin**
- IDE : **AndroidStudio**
- 디자인 : **Figma**
- 버전 및 이슈관리 : **Github**
- 협업 툴 : **Github**

## 📌 주요 기능
## Tab1 ; Contact Tab

- **연락처 추가 기능 ✅**
    
    🛠 오른쪽 상단의 플러스 버튼(+) 클릭 시 연락처 추가가 가능함.
    
    ➡ 플러스 버튼 클릭 시 dialog 화면에서 병원/약국명, 전화번호, 주소를 입력받음.
    
    ➡ 추가 버튼 클릭 시 :앱 내의 연락처에 추가되고 연락처 탭 recycler view 맨 마지막에 새로운 연락처가 추가된다.
    
    ➡ 취소 버튼 클릭 시 : 연락처 추가 창이 사라지고 아무런 액션이 보이지 않음.
    
- **연락처 정보 자세히 보기 기능 ✅**
    
    🛠 Contact 탭에서 하나의 요소를 클릭하면 dialog 창에서 병원/약국의 정보를 보여준다.
    
    ➡ recycler view에서의 요소를 클릭하면 dialog에서 해당하는 부분의 병원 또는 약국의 이름, 전화번호, 주소를 보여준다. (본래 recycler view에서는 이름과 전화번호만 보이지만 클릭 시에는 주소까지 보인다는 특징이 있음.)
    
    ➡ dialog 외부 영역을 클릭하면 창이 사라진다.
    

## Tab 2 ; Medicine Tab

- **복용 중인 약 리스트업 ✅**
    
    🛠 Recycler view를 이용하여, 복용 중인 약을 listup할 수 있다.
    
    ➡ Recycler view의 list가 비어있을 경우, 약을 추가하라는 메세지가 창에 뜨게 함.
    
    ➡ Recycler view의 list 유지를 위하여 shared preference를 이용.
    
- **복용 중인 약 추가 ✅**
    
    🛠오른쪽 상단의 플러스 버튼(+) 클릭 시 복용 중인 약 추가가 가능함
    
    ➡ 플러스 버튼 클릭 시 dialog에서 약의 사진(갤러리, 카메라 선택해서 사용 가능), 복용 시작일, 복용 종료일(date picker를 이용해서 달력에서 날짜 선택 가능)을 추가가 가능함.
    
    ➡ 추가 버튼 클릭 시: 창이 사라지고 입력 받은 정보를 recycler view에 추가해서 가장 아래에 새로운 약이 추가된다.
    
    ➡ 취소 버튼 클릭 시 : dialog창이 사라지고 변경 사항 없음.
    
- **복용 중인 약 자세히 보기 ✅**
    
    🛠Contact 탭에서 하나의 요소를 클릭하면 팝업 창에서 image를 확대해서 크게 보여줌.
    
    ➡ 요소 클릭 시: 사진을 확대한 팝업 창을 화면 정가운데에 보여줌.
    
    ➡ 팝업 창 바깥 클릭 시: 창이 사라진다.
    
- **복용 중인 약 list 마다 checkbox 생성 ✅**
    
    🛠 Recycler view 각 요소의 checkbox의 클릭 여부가 Tab3로 전달됨.
    

## Tab 3 ; Calendar Tab

- **달력 구현 ✅**
    
    🛠 오늘 날짜를 기준으로 이번 달의 달력을 보여준다
    
    ➡년도와 달이 상단에 표시된다.
    
    ➡ 양 옆의 화살표 버튼을 클릭하면 전 달, 다음 달로 이동한다.
    
    ➡ 달 별로 요일에 적합하게 일(숫자)들이 변동 된다.
    
- **매일의 약 복용 여부 기록 기능 ✅**
    
    🛠 두 번째 탭에서 체크박스의 체크 여부를 받아와 오늘의 약 복용 여부를 날짜 아래의 작은 점 유무로 나타내준다.
    
    ➡ 두 번째 medicine Tab에 있는 복용하는 약들의 리스트 중에서 하나의 약이라도 체크박스가 체크되면
    
    ➡ 달력에서 현재 날짜의 숫자 아래에 보라색 점이 생긴다.
    
    ➡ 만약 다시 두 번째 탭의 체크박스를 해제하면, 달력 아래의 점이 비활성화된다.
    
- **매일의 복용한 약 종류 리뷰 기능 ✅**
    
    🛠두 번째 탭에서 체크박스로 체크한 복용한 약의 종류를 날짜 별로 보여준다.
    
    ➡ 날짜를 클릭하면 두 번째 medicine Tab에서 체크된 모든 약들의 종류가 날짜 별로 다르게 보인다.
      

